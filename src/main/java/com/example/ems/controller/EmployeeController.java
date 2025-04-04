package com.example.ems.controller;

import com.example.ems.dto.EmployeeDTO;
import com.example.ems.model.Employee;
import com.example.ems.service.EmployeeService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/employees")
public class EmployeeController {
    @Autowired
    private EmployeeService employeeService;

    @GetMapping
    @PreAuthorize("hasRole('USER') or hasRole('MANAGER') or hasRole('ADMIN')")
    public ResponseEntity<Page<EmployeeDTO>> getAllEmployees(Pageable pageable) {
        Page<Employee> employees = employeeService.getAllEmployees(pageable);
        Page<EmployeeDTO> employeeDTOs = employees.map(employee -> employeeService.convertToDTO(employee));
        return ResponseEntity.ok(employeeDTOs);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('USER') or hasRole('MANAGER') or hasRole('ADMIN')")
    public ResponseEntity<EmployeeDTO> getEmployeeById(@PathVariable Long id) {
        Employee employee = employeeService.getEmployeeById(id);
        EmployeeDTO employeeDTO = employeeService.convertToDTO(employee);
        return ResponseEntity.ok(employeeDTO);
    }

    @GetMapping("/search")
    @PreAuthorize("hasRole('USER') or hasRole('MANAGER') or hasRole('ADMIN')")
    public ResponseEntity<Page<EmployeeDTO>> searchEmployees(@RequestParam String query, Pageable pageable) {
        Page<Employee> employees = employeeService.searchEmployees(query, pageable);
        Page<EmployeeDTO> employeeDTOs = employees.map(employee -> employeeService.convertToDTO(employee));
        return ResponseEntity.ok(employeeDTOs);
    }

    @GetMapping("/department/{department}")
    @PreAuthorize("hasRole('USER') or hasRole('MANAGER') or hasRole('ADMIN')")
    public ResponseEntity<List<EmployeeDTO>> getEmployeesByDepartment(@PathVariable String department) {
        List<Employee> employees = employeeService.getEmployeesByDepartment(department);
        List<EmployeeDTO> employeeDTOs = employees.stream()
                .map(employee -> employeeService.convertToDTO(employee))
                .collect(Collectors.toList());
        return ResponseEntity.ok(employeeDTOs);
    }

    @PostMapping
    @PreAuthorize("hasRole('MANAGER') or hasRole('ADMIN')")
    public ResponseEntity<EmployeeDTO> createEmployee(@Valid @RequestBody EmployeeDTO employeeDTO) {
        Employee savedEmployee = employeeService.createEmployee(employeeDTO);
        EmployeeDTO savedEmployeeDTO = employeeService.convertToDTO(savedEmployee);
        return ResponseEntity.ok(savedEmployeeDTO);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('MANAGER') or hasRole('ADMIN')")
    public ResponseEntity<EmployeeDTO> updateEmployee(@PathVariable Long id, 
                                                     @Valid @RequestBody EmployeeDTO employeeDTO) {
        Employee updatedEmployee = employeeService.updateEmployee(id, employeeDTO);
        EmployeeDTO updatedEmployeeDTO = employeeService.convertToDTO(updatedEmployee);
        return ResponseEntity.ok(updatedEmployeeDTO);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> deleteEmployee(@PathVariable Long id) {
        employeeService.deleteEmployee(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/manager/{managerId}")
    @PreAuthorize("hasRole('MANAGER') or hasRole('ADMIN')")
    public ResponseEntity<List<EmployeeDTO>> getEmployeesByManager(@PathVariable Long managerId) {
        List<Employee> employees = employeeService.getEmployeesByManager(managerId);
        List<EmployeeDTO> employeeDTOs = employees.stream()
                .map(employee -> employeeService.convertToDTO(employee))
                .collect(Collectors.toList());
        return ResponseEntity.ok(employeeDTOs);
    }
}