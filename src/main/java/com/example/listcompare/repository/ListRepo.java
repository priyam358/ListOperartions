package com.example.listcompare.repository;

import com.example.listcompare.dto.EmployeeDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ListRepo extends JpaRepository<EmployeeDTO,String> {
}
