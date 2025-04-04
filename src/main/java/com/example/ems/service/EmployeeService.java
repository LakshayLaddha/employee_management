package com.example.ems.service;

import com.example.ems.dto.EmployeeDTO;
import com.example.ems.exception.ResourceNotFoundException;
import com.example.ems.model.Employee;
import com.example.ems.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;

    public Page<Employee> getAllEmployees(Pageable pageable) {
        return employeeRepository.findAll(pageable);
    }

    public Employee getEmployeeById(Long id) {
        return employeeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found with id: " + id));
    }

    public Page<Employee> searchEmployees(String query, Pageable pageable) {
        return employeeRepository.searchEmployees(query, pageable);
    }

    public List<Employee> getEmployeesByDepartment(String department) {
        return employeeRepository.findByDepartment(department);
    }

    @Transactional
    public Employee createEmployee(EmployeeDTO employeeDTO) {
        Employee employee = new Employee();
        mapDtoToEntity(employeeDTO, employee);
        
        // Set manager if managerId is provided
        if (employeeDTO.getManagerId() != null) {
            Employee manager = employeeRepository.findById(employeeDTO.getManagerId())
                    .orElseThrow(() -> new ResourceNotFoundException("Manager not found with id: " + employeeDTO.getManagerId()));
            employee.setManager(manager);
        }
        
        return employeeRepository.save(employee);
    }

    @Transactional
    public Employee updateEmployee(Long id, EmployeeDTO employeeDTO) {
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found with id: " + id));
        
        mapDtoToEntity(employeeDTO, employee);
        
        // Update manager if managerId is provided
        if (employeeDTO.getManagerId() != null) {
            Employee manager = employeeRepository.findById(employeeDTO.getManagerId())
                    .orElseThrow(() -> new ResourceNotFoundException("Manager not found with id: " + employeeDTO.getManagerId()));
            employee.setManager(manager);
        } else {
            employee.setManager(null);
        }
        
        return employeeRepository.save(employee);
    }

    @Transactional
    public void deleteEmployee(Long id) {
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found with id: " + id));
        
        // Instead of hard delete, we'll set isActive to false
        employee.setIsActive(false);
        employeeRepository.save(employee);
    }

    public List<Employee> getEmployeesByManager(Long managerId) {
        return employeeRepository.findByManagerId(managerId);
    }

    public EmployeeDTO convertToDTO(Employee employee) {
        EmployeeDTO dto = new EmployeeDTO();
        dto.setId(employee.getId());
        dto.setFirstName(employee.getFirstName());
        dto.setLastName(employee.getLastName());
        dto.setEmail(employee.getEmail());
        dto.setPhoneNumber(employee.getPhoneNumber());
        dto.setHireDate(employee.getHireDate());
        dto.setSalary(employee.getSalary());
        dto.setJobTitle(employee.getJobTitle());
        dto.setDepartment(employee.getDepartment());
        dto.setAddress(employee.getAddress());
        dto.setNotes(employee.getNotes());
        dto.setIsActive(employee.getIsActive());
        
        if (employee.getManager() != null) {
            dto.setManagerId(employee.getManager().getId());
            dto.setManagerName(employee.getManager().getFirstName() + " " + employee.getManager().getLastName());
        }
        
        return dto;
    }
    
    private void mapDtoToEntity(EmployeeDTO dto, Employee employee) {
        employee.setFirstName(dto.getFirstName());
        employee.setLastName(dto.getLastName());
        employee.setEmail(dto.getEmail());
        employee.setPhoneNumber(dto.getPhoneNumber());
        employee.setHireDate(dto.getHireDate());
        employee.setSalary(dto.getSalary());
        employee.setJobTitle(dto.getJobTitle());
        employee.setDepartment(dto.getDepartment());
        employee.setAddress(dto.getAddress());
        employee.setNotes(dto.getNotes());
        employee.setIsActive(dto.getIsActive() != null ? dto.getIsActive() : true);
    }
}