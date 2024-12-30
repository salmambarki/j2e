package com.example.backend.repository;

import com.example.backend.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface EmployeeRepository extends JpaRepository<Employee, Integer> {
    @Query("SELECT e.id FROM Employee e WHERE e.email = :email AND e.password = :password")
    Optional<Integer> findEmployeeIdByEmailAndPassword(String email, String password);
}
