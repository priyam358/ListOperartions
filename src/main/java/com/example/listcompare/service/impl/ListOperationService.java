package com.example.listcompare.service.impl;

import com.example.listcompare.dto.EmployeeDTO;
import com.example.listcompare.dto.StatusDTO;
import com.example.listcompare.dto.enums.LockEnum;
import com.example.listcompare.repository.ListRepo;
import com.example.listcompare.service.ListOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class ListOperationService implements ListOperation {


    List<EmployeeDTO> employeeDTOS = new ArrayList<>();

    @Autowired
    RedisTemplate redisTemplate;

    @Autowired
    ListRepo listRepo;

    StatusDTO statusDTO = new StatusDTO();

    @Autowired
    KafkaTemplate<String, EmployeeDTO> kafkaTemplate;



   // String currentemployee="";

//    @Override
//    @KafkaListener(topics = "queue", groupId = "group-id", containerFactory = "kafkaListenerContainerFactory")
//    public void addEmployeeKafka(EmployeeDTO employeeDTO) throws InterruptedException {
//        Thread.sleep(5000);
//        System.out.println(employeeDTO.getEnployeeId());
//        if(!currentemployee.equals(""))
//            addEmployee(employeeDTO);
//    }

    @Override
    public StatusDTO updateEmployee(EmployeeDTO employeeDTO) {



        System.out.println(employeeDTOS);
        System.out.println("-");
        acquireAndReleaseLock(employeeDTO, LockEnum.ACQUIRE);
        System.out.println(employeeDTOS);
        return statusDTO;
    }

    @Override
    @KafkaListener(topics = "queue", groupId = "group-id", containerFactory = "kafkaListenerContainerFactory")
    public void updateEmployeeKafka(EmployeeDTO employeeDTO) throws InterruptedException {
        Thread.sleep(5000);
        System.out.println(employeeDTO.getEnployeeId());
        //if(!currentemployee.equals(""))
            updateEmployee(employeeDTO);

    }

    @Override
    public StatusDTO addEmployee(EmployeeDTO employeeDTO) throws InterruptedException {
        System.out.println(employeeDTOS);
        System.out.println("-");
        //acquireAndReleaseLock(employeeDTO, LockEnum.ACQUIRE);
        System.out.println(employeeDTOS);
        listRepo.save(employeeDTO);
        statusDTO.setMsg("Data Added Successfully !");
        //return employeeDTOS.get(employeeDTOS.size() - 1).getEnployeeId();
        return statusDTO;
    }

    @Override
    public List<EmployeeDTO> sortByName() {

        employeeDTOS = employeeDTOS.stream().sorted(new SortByName()).collect(Collectors.toList());
        return employeeDTOS;
    }

    @Override
    public List<EmployeeDTO> sortBySalary() {
        Collections.sort(employeeDTOS, new SortBySalary());
        return employeeDTOS;
    }

    @Override
    public List<EmployeeDTO> sortById() {
        Collections.sort(employeeDTOS, new SortById());
        return employeeDTOS;
    }

    @Override
    public List<EmployeeDTO> sortByDepartment() {
        Collections.sort(employeeDTOS, new SortByDepartment());
        return employeeDTOS;
    }

    @Override
    public void acquireAndReleaseLock(EmployeeDTO employeeDTO, LockEnum lockEnum) {
        if (lockEnum.equals(LockEnum.ACQUIRE)) {
            boolean checkStatus = acquireLock(employeeDTO);
            System.out.println(checkStatus);
            if (checkStatus == false) {
                statusDTO.setMsg("Some process is currently using the lock");
                System.out.println("Some process is currently using the lock");
                this.kafkaTemplate.send("queue", employeeDTO);

            } else {
                statusDTO.setMsg("Lock Acquired by " + employeeDTO.getEnployeeId());
                System.out.println("Lock Acquired by " + employeeDTO.getEnployeeId());
                acquireAndReleaseLock(employeeDTO, LockEnum.RELEASE);
            }


        } else {
            boolean ch = releaseLock(employeeDTO);
            if (ch == true) {
                statusDTO.setMsg("Lock Released !");
                System.out.println("Lock Released!");

            } else {
                statusDTO.setMsg("Error in lock release !");
                System.out.println("Error in lock release!");
            }
        }
    }

    @Override
    public boolean acquireLock(EmployeeDTO employeeDTO) {
        try {
            if (Objects.isNull(redisTemplate.opsForValue().get(employeeDTO.getEnployeeId())) || LockEnum.RELEASE.name()
                    .equals(redisTemplate.opsForValue().get(employeeDTO.getEnployeeId()))) {
                redisTemplate.opsForValue().set(employeeDTO.getEnployeeId(), LockEnum.ACQUIRE.name());
                listRepo.save(employeeDTO);
                employeeDTOS.add(employeeDTO);
                //currentemployee = employeeDTO.getEnployeeId();
                Thread.sleep(10000);
                return true;
            } else
                return false;
        } catch (Exception e) {
            System.out.println("Lock not acquired");
            return false;
        }

    }

    @Override
    public boolean releaseLock(EmployeeDTO employeeDTO) {
        try {
            redisTemplate.opsForValue().set(employeeDTO.getEnployeeId(), LockEnum.RELEASE.name());
            return true;
        } catch (Exception e) {
            System.out.println("Lock Released");
            return false;
        }

    }


}
