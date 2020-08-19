package com.example.securitypractice.controllers;

import com.example.securitypractice.model.Employee;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/management/api/v1/employees")
public class EmployeeManagementController {
    private static final List<Employee> EMPLOYEES = Arrays.asList(
            new Employee(1, "Michael Scott"),
            new Employee(2, "Dwight Schrute"),
            new Employee(3, "Kevin Malone")
    );

    @GetMapping
    @PreAuthorize("hasAnyRole('ROLE_MANAGER', 'ROLE_ACCOUNTANT')")
    public List<Employee> getEmployees() {
        return EMPLOYEES;
    }

    @PostMapping
    @PreAuthorize("hasAuthority('account:write')")
    public void registerEmployee(@RequestBody Employee employee) {
        // just a stub for now
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('account:write')")
    public void deleteEmployee(@PathVariable("id") long id) {
        // just a stub for now
    }

    @PutMapping("/{id}")
    public void updateEmployee(@PathVariable("id") long id, @RequestBody Employee employee) {
        // just a stub for now
    }
}
