package com.example.listcompare.service.impl;

import com.example.listcompare.dto.EmployeeDTO;

import java.util.Comparator;

public class SortById implements Comparator<EmployeeDTO> {
    @Override
    public int compare(EmployeeDTO o1, EmployeeDTO o2) {
        return o1.getEnployeeId().compareTo(o2.getEnployeeId());
    }
}
