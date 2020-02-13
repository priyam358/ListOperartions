package com.example.listcompare.controller;

import com.example.listcompare.dto.EmployeeDTO;
import com.example.listcompare.model.EmployeeApi;
import com.example.listcompare.service.ListOperation;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.ArgumentMatchers.eq;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(ListOperationController.class)
public class ListOperationControllerTest {

    @MockBean
    ListOperation listOperation;

    @Autowired
    MockMvc mockMvc;

    @InjectMocks
    ListOperationController listOperationController;

    EmployeeDTO employeeDTO;
    @BeforeEach
    public void init() {
        MockitoAnnotations.initMocks(this);
        employeeDTO=new EmployeeDTO("10","Mohit",3456.90,"Blibli");
        mockMvc = MockMvcBuilders.standaloneSetup(listOperationController).build();

    }


    @AfterEach
     void tearDown(){
        Mockito.verifyNoMoreInteractions(listOperation);
    }

    @Test
    public void getSortedEmployees() throws Exception {


        MockHttpServletRequestBuilder mockHttpServletRequestBuilder = get(EmployeeApi.BASE_PATH + EmployeeApi.SORT_BY_NAME)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON);
        MvcResult mvcResult = mockMvc.perform(mockHttpServletRequestBuilder).andExpect(status().isOk()).andReturn();
        //System.out.println(mvcResult.getStatus());
        System.out.println(mvcResult.getRequest());

        Mockito.verify(listOperation).sortByName();
    }

    @SneakyThrows
    @Test
    public void addData() {

        ObjectMapper objectMapper=new ObjectMapper();
        String json=objectMapper.writeValueAsString(employeeDTO);

        MockHttpServletRequestBuilder requestBuilder =
                post(EmployeeApi.BASE_PATH + EmployeeApi.ADD_EMPLOYEE)
                        .content(json).contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON);
        MvcResult mvcResult = mockMvc.perform(requestBuilder).andExpect(status().isOk()).andReturn();
        System.out.println(mvcResult.getRequest());
        Mockito.verify(listOperation).addEmployee(Mockito.eq(employeeDTO));

    }

}
