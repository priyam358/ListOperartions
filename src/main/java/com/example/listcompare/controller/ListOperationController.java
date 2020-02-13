package com.example.listcompare.controller;


import com.example.listcompare.dto.EmployeeDTO;
import com.example.listcompare.dto.StatusDTO;
import com.example.listcompare.model.EmployeeApi;
import com.example.listcompare.service.ListOperation;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import jdk.net.SocketFlow;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties.*;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Slf4j
@RestController
@Api("List Operations")
@RequestMapping(value=EmployeeApi.BASE_PATH)
public class ListOperationController {

    @Autowired
    ListOperation listOperation;

    StatusDTO statusDTO=new StatusDTO();


    @ApiOperation("Add Data")
    @PostMapping(value = EmployeeApi.ADD_EMPLOYEE)
    public StatusDTO addData(@RequestBody EmployeeDTO employeeDTO) throws InterruptedException {
        statusDTO=listOperation.addEmployee(employeeDTO);
        System.out.println("Data added");
        return statusDTO;
    }

    @ApiOperation("Update Data")
    @PostMapping(value = EmployeeApi.UPDATE_EMPLOYEE)
    public StatusDTO updateData(@RequestBody EmployeeDTO employeeDTO) throws InterruptedException {
        statusDTO=listOperation.updateEmployee(employeeDTO);
        System.out.println("Data updated");



        return statusDTO;
    }


    @ApiOperation("Sort By Name")
    @GetMapping(value = EmployeeApi.SORT_BY_NAME)
    public List<EmployeeDTO> sortByName(){
        System.out.println("Sorted By name");
        return  listOperation.sortByName();
    }

    @ApiOperation("Sort By Salary")
    @GetMapping(value = EmployeeApi.SORT_BY_SALARY)
    public List<EmployeeDTO> sortBySalary(){
        return  listOperation.sortBySalary();
    }

    @ApiOperation("Sort By Id")
    @GetMapping(value = EmployeeApi.SORT_BY_ID)
    public List<EmployeeDTO> sortById(){
        return  listOperation.sortById();
    }

    @ApiOperation("Sort By Department")
    @GetMapping(value = EmployeeApi.SORT_BY_DEPARTMENT)
    public List<EmployeeDTO> sortByDepartment(){
        return  listOperation.sortByDepartment();
    }

}
