package com.example.listcompare.servicetest;

import com.example.listcompare.dto.EmployeeDTO;
import com.example.listcompare.dto.StatusDTO;
import com.example.listcompare.service.ListOperation;
import com.example.listcompare.service.impl.ListOperationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.security.RunAs;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class ListOperationTest {

    @InjectMocks
    ListOperationService listOperation;


    @BeforeEach
    public void before() {
        System.out.println("Before Each TestCase");
        MockitoAnnotations.initMocks(this);
    }


    @Test
    public void addData() throws InterruptedException {
        StatusDTO statusDTO = listOperation.addEmployee(EmployeeDTO.builder().enployeeId("15").build());
        assertEquals(statusDTO.getMsg(),"Lock Acquired" );
    }

}
