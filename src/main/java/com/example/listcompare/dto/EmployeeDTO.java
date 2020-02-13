package com.example.listcompare.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class EmployeeDTO {

    @Id
    private String enployeeId;
    private String employeeName;
    private double salary;
    private String department;


}
