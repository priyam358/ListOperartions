package com.example.listcompare.repository;


import com.example.listcompare.dto.EmployeeDTO;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.util.List;


@DataJpaTest
public class ListRepoTest {


        @Autowired
        TestEntityManager testEntityManager;

        @Autowired
        ListRepo listRepo;

        @Test
        public void testRepoInsert() {

            EmployeeDTO employeeDTO = new EmployeeDTO("21", "Pritesh", 100.0, "Blibli search");
            testEntityManager.persist(employeeDTO);
            testEntityManager.flush();

            EmployeeDTO employeeDTO1=listRepo.findById(employeeDTO.getEnployeeId()).get();
            System.out.println(employeeDTO1);

            Assertions.assertThat(employeeDTO1.getEnployeeId()).isEqualTo(employeeDTO.getEnployeeId());

        }


        @Test
    public void testRepoUpdate(){

            EmployeeDTO employeeDTO = new EmployeeDTO("23", "Pritesh", 100.0, "Blibli search");
            testEntityManager.persist(employeeDTO);
            testEntityManager.flush();
            System.out.println(employeeDTO);

            employeeDTO.setDepartment("Blibli.com");
            testEntityManager.persist(employeeDTO);
            testEntityManager.flush();
            System.out.println(employeeDTO);

            EmployeeDTO employeeDTO1=listRepo.findById(employeeDTO.getEnployeeId()).get();
            System.out.println(employeeDTO1);

            Assertions.assertThat(employeeDTO1.getEnployeeId()).isEqualTo(employeeDTO.getEnployeeId());




        }

}

