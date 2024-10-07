package com.shakhawat.springbootactuator;

import com.shakhawat.springbootactuator.department.Department;
import com.shakhawat.springbootactuator.department.DepartmentRepository;
import com.shakhawat.springbootactuator.employee.EmployeeDTO;
import com.shakhawat.springbootactuator.employee.EmployeeService;
import com.shakhawat.springbootactuator.exception.ResourceNotFoundException;
import com.shakhawat.springbootactuator.employee.Employee;
import com.shakhawat.springbootactuator.employee.EmployeeRepository;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@Log4j2
class EmployeeServiceTest {

    @Mock
    private EmployeeRepository employeeRepository;

    @Mock
    private DepartmentRepository departmentRepository;

    @InjectMocks
    private EmployeeService employeeService;

    private Employee employee;

    private EmployeeDTO employeeDTO;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        Department department = new Department();
        department.setId(1L);
        // Initialize an Employee and EmployeeDTO for testing
        employee = new Employee(1L, "EMP001", "John", "Doe", "John Doe", 30, "Male", "123 Street", department);
        employeeDTO = new EmployeeDTO(1L, "EMP001", "John", "Doe", "John Doe", 30, "Male", "123 Street", 1L);
    }

    @Test
    void testCreateEmployee() {
        when(employeeRepository.save(any(Employee.class))).thenReturn(employee);

        when(departmentRepository.findById(1L)).thenReturn(Optional.of(new Department(1L, "IT", "Information Technology")));
        EmployeeDTO createdEmployee = employeeService.createEmployee(employeeDTO);

        assertNotNull(createdEmployee);
        assertEquals(employeeDTO.getCode(), createdEmployee.getCode());
        verify(employeeRepository, times(1)).save(any(Employee.class));
    }

    @Test
    void testGetEmployeeById() {
        //Given
        when(employeeRepository.findById(1L)).thenReturn(Optional.of(employee));

        //When
        Optional<EmployeeDTO> foundEmployee = employeeService.getEmployeeById(1L);

        //Then
        assertNotNull(foundEmployee);
        assertEquals(employeeDTO.getCode(), foundEmployee.orElseThrow().getCode());
        verify(employeeRepository, times(1)).findById(1L);
    }

    @Test
    void testGetEmployeeById_NotFound() {
        when(employeeRepository.findById(1L)).thenReturn(Optional.empty());

        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, () -> employeeService.getEmployeeById(1L));
        assertEquals("Employee not found with id: 1", exception.getMessage());
        verify(employeeRepository, times(1)).findById(1L);
    }

    @Test
    void testUpdateEmployee() {
        when(employeeRepository.findById(1L)).thenReturn(Optional.of(employee));
        when(employeeRepository.save(any(Employee.class))).thenReturn(employee);
        when(departmentRepository.findById(1L)).thenReturn(Optional.of(new Department(1L, "IT", "Information Technology")));

        EmployeeDTO updatedEmployee = employeeService.updateEmployee(1L, employeeDTO);

        assertNotNull(updatedEmployee);
        assertEquals(employeeDTO.getFirstName(), updatedEmployee.getFirstName());
        verify(employeeRepository, times(1)).findById(1L);
        verify(employeeRepository, times(1)).save(any(Employee.class));
    }

    @Test
    void testUpdateEmployee_NotFound() {
        when(employeeRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> employeeService.updateEmployee(1L, employeeDTO));
        verify(employeeRepository, times(1)).findById(1L);
    }

    @Test
    void testDeleteEmployee() {
        when(employeeRepository.findById(1L)).thenReturn(Optional.of(employee));

        employeeService.deleteEmployee(1L);

        verify(employeeRepository, times(1)).findById(1L);
        verify(employeeRepository, times(1)).delete(employee);
    }

    @Test
    void testDeleteEmployee_NotFound() {
        Long employeeId = 1L; // Constant for employee ID
        when(employeeRepository.findById(employeeId)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> {
            log.info("Employee with id: {} not found for deletion", employeeId);
            employeeService.deleteEmployee(employeeId);
        });
        verify(employeeRepository, times(1)).findById(employeeId);
    }

    @Test
    void testGetAllEmployees() {
        List<Employee> employees = Collections.singletonList(employee);
        when(employeeRepository.findAll()).thenReturn(employees);

        List<EmployeeDTO> allEmployees = employeeService.getAllEmployees();

        assertNotNull(allEmployees);
        assertEquals(1, allEmployees.size());
        verify(employeeRepository, times(1)).findAll();
    }
}

