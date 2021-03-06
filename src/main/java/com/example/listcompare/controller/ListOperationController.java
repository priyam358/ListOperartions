package com.example.listcompare.controller;


import com.example.listcompare.dto.EmployeeDTO;
import com.example.listcompare.dto.StatusDTO;
import com.example.listcompare.model.EmployeeApi;
import com.example.listcompare.service.ListOperation;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@Slf4j
@RestController
@Api("List Operations")
@RequestMapping(value = EmployeeApi.BASE_PATH)
public class ListOperationController {

    @Autowired
    ListOperation listOperation;

    StatusDTO statusDTO = new StatusDTO();


    @ApiOperation("Add Data")
    @PostMapping(value = EmployeeApi.ADD_EMPLOYEE)
    public StatusDTO addData(@RequestBody EmployeeDTO employeeDTO) throws InterruptedException {


        statusDTO = listOperation.addEmployee(employeeDTO);
        System.out.println("Data added");
        return statusDTO;
    }

    @ApiOperation("Update Data")
    @PostMapping(value = EmployeeApi.UPDATE_EMPLOYEE)
    public StatusDTO updateData(@RequestBody EmployeeDTO employeeDTO) throws InterruptedException {

        System.out.println("Thread name: " + Thread.currentThread().getName());
        statusDTO = listOperation.updateEmployee(employeeDTO);
        System.out.println("Data updated");
        System.out.println(statusDTO.getMsg());

        return statusDTO;
    }


    @ApiOperation("Fetch All Data")
    @PostMapping(value = EmployeeApi.FETCH_ALL_EMPLOYEES)
    public CompletableFuture<List<EmployeeDTO>> fetchALL() throws InterruptedException {


        System.out.println("Thread name: " + Thread.currentThread().getName());

        CompletableFuture<List<EmployeeDTO>> cls = listOperation.fetchAll();
        CompletableFuture<List<EmployeeDTO>> cls2 = listOperation.fetchAll();
        CompletableFuture<List<EmployeeDTO>> cls3 = listOperation.fetchAll();
        CompletableFuture.allOf(cls, cls2, cls3).join();
        return cls;
        //System.out.println("Data updated");
        //System.out.println(statusDTO.getMsg());


    }


    @ApiOperation("Sort By Name")
    @GetMapping(value = EmployeeApi.SORT_BY_NAME)
    public List<EmployeeDTO> sortByName() {
        System.out.println("Sorted By name");
        return listOperation.sortByName();
    }

    @ApiOperation("Sort By Salary")
    @GetMapping(value = EmployeeApi.SORT_BY_SALARY)
    public List<EmployeeDTO> sortBySalary() {
        return listOperation.sortBySalary();
    }

    @ApiOperation("Sort By Id")
    @GetMapping(value = EmployeeApi.SORT_BY_ID)
    public List<EmployeeDTO> sortById() {
        return listOperation.sortById();
    }

    @ApiOperation("Sort By Department")
    @GetMapping(value = EmployeeApi.SORT_BY_DEPARTMENT)
    public List<EmployeeDTO> sortByDepartment() {
        return listOperation.sortByDepartment();
    }

}
