package com.example.listcompare.service;

import com.example.listcompare.dto.EmployeeDTO;
import com.example.listcompare.dto.StatusDTO;
import com.example.listcompare.dto.enums.LockEnum;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.List;

public interface ListOperation {

    public StatusDTO addEmployee(EmployeeDTO employeeDTO) throws InterruptedException;
//    public void addEmployeeKafka(EmployeeDTO employeeDTO) throws InterruptedException;

    public StatusDTO updateEmployee(EmployeeDTO employeeDTO);

    public void updateEmployeeKafka(EmployeeDTO employeeDTO) throws InterruptedException;

    public List<EmployeeDTO>  sortByName();

    public List<EmployeeDTO>  sortBySalary();

    public List<EmployeeDTO>  sortById();

    public List<EmployeeDTO>  sortByDepartment();

    public void acquireAndReleaseLock(EmployeeDTO employeeDTO, LockEnum lockEnum) ;


    public boolean acquireLock(EmployeeDTO employeeDTO);

    public boolean releaseLock(EmployeeDTO employeeDTO);

}
