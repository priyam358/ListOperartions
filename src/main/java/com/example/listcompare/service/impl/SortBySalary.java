package com.example.listcompare.service.impl;

import com.example.listcompare.dto.EmployeeDTO;

import java.util.Comparator;

public class SortBySalary implements Comparator<EmployeeDTO> {
    @Override
    public int compare(EmployeeDTO o1, EmployeeDTO o2) {
        if(o1.getSalary() > o2.getSalary())
            return 1;
        else if (o2.getSalary() > o1.getSalary())
            return -1;
        else return 0;
    }

}
