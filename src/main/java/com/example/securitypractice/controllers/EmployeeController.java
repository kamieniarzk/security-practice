package com.example.securitypractice.controllers;

import com.example.securitypractice.model.Employee;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/api/v1/employees")
public class EmployeeController {
    private static final List<Employee> EMPLOYEES = Arrays.asList(
            new Employee(1, "Michael Scott"),
            new Employee(2, "Dwight Schrute"),
            new Employee(3, "Kevin Malone")
            );

    @GetMapping("/{studentId}")
    public Employee getStudent(@PathVariable("studentId") int id) {
        return EMPLOYEES.stream()
                .filter(employee -> id == employee.getId())
                .findFirst()
                .orElseThrow(() -> new IllegalStateException("Student with id " + id + "not found"));
    }


}
