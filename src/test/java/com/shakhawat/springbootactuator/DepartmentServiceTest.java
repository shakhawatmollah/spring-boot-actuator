package com.shakhawat.springbootactuator;

import com.fasterxml.jackson.core.JsonParseException;
import com.shakhawat.springbootactuator.department.Department;
import com.shakhawat.springbootactuator.department.DepartmentDTO;
import com.shakhawat.springbootactuator.department.DepartmentRepository;
import com.shakhawat.springbootactuator.department.DepartmentService;
import com.shakhawat.springbootactuator.exception.ResourceNotFoundException;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@Log4j2
public class DepartmentServiceTest {

    @Mock
    private DepartmentRepository departmentRepository;

    @InjectMocks
    private DepartmentService departmentService;

    private Department department;

    private DepartmentDTO departmentDTO;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        // Initialize a Department and DepartmentDTO for testing
        department = new Department(1L, "IT", "Information Technology");
        departmentDTO = new DepartmentDTO(1L, "IT", "Information Technology");
    }

    @Test
    void testGetDepartmentById_Success() {
        // Arrange
        when(departmentRepository.findById(1L)).thenReturn(Optional.of(department));

        // Act
        Optional<DepartmentDTO> result = departmentService.getDepartmentById(1L);

        // Assert
        assertNotNull(result);
        assertEquals("IT", result.orElseThrow().getShortName());
        verify(departmentRepository, times(1)).findById(1L);
    }

    @Test
    void testGetDepartmentById_NotFound() {
        // Arrange: Mock the repository to return an empty Optional
        when(departmentRepository.findById(1L)).thenReturn(Optional.empty());

        // Act & Assert: Expect ResourceNotFoundException to be thrown
        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, () -> departmentService.getDepartmentById(1L));
        assertEquals("Department with id: 1 not found", exception.getMessage());

        // Verify the interaction with repository
        verify(departmentRepository, times(1)).findById(1L);
    }

    @Test
    void testCreateDepartment_Success() {
        // Arrange
        when(departmentRepository.save(any(Department.class))).thenReturn(department);

        // Act
        DepartmentDTO result = departmentService.createDepartment(departmentDTO);

        // Assert
        assertNotNull(result);
        assertEquals(departmentDTO.getShortName(), result.getShortName());
        assertEquals(departmentDTO.getName(), result.getName());
        verify(departmentRepository, times(1)).save(any(Department.class));
    }

    @Test
    void testCreateDepartment_SaveMethodCalled() {
        // Act
        departmentService.createDepartment(departmentDTO);

        // Assert
        verify(departmentRepository, times(1)).save(any(Department.class));
    }

    @Test
    void testCreateDepartment_ConversionCorrect() {
        // Arrange
        when(departmentRepository.save(any(Department.class))).thenReturn(department);

        // Act
        DepartmentDTO result = departmentService.createDepartment(departmentDTO);

        // Assert
        assertNotNull(result);
        assertEquals(departmentDTO.getShortName(), result.getShortName());
        assertEquals(departmentDTO.getName(), result.getName());
    }

    @Test
    void testDeleteDepartment_ExistingId() {
        // Arrange
        // Mock the repository to return the department when findById is called
        when(departmentRepository.findById(1L)).thenReturn(Optional.of(department));

        // Act
        departmentService.deleteDepartment(1L);

        // Assert
        verify(departmentRepository).delete(department); // Verify that the delete method was called with the correct department
    }

    @Test
    void testDeleteDepartment_NonExistingId() {
        // Arrange: Mock the repository to return an empty Optional
        when(departmentRepository.findById(1L)).thenReturn(Optional.empty());

        // Act & Assert: Expect ResourceNotFoundException to be thrown
        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, () -> departmentService.deleteDepartment(1L));
        assertEquals("Department not found with id: 1", exception.getMessage());

        // Verify that delete was never called
        verify(departmentRepository, never()).delete(any());
    }

    @Test
    void testDeleteDepartment_NullId() {
        // Act and Assert
        assertThrows(NullPointerException.class, () -> departmentService.deleteDepartment(null));
    }

    @Test
    void testGetDepartmentById_ThrowsExceptionForNullId() {
        // Act & Assert: Expect IllegalArgumentException when id is null
        NullPointerException exception = assertThrows(NullPointerException.class, () -> departmentService.getDepartmentById(null));

        // Assert the exception message
        assertEquals("Department ID must not be null", exception.getMessage());
    }

    @Test
    void testUpdateDepartment_Success() {
        // Arrange
        Long id = 1L;
        Department department = new Department();
        department.setId(id);
        department.setShortName("Old Short Name");
        department.setName("Old Name");
        when(departmentRepository.findById(id)).thenReturn(Optional.of(department));

        DepartmentDTO departmentDTO = new DepartmentDTO();
        departmentDTO.setShortName("New Short Name");
        departmentDTO.setName("New Name");

        // Act
        DepartmentDTO updatedDepartment = departmentService.updateDepartment(id, departmentDTO);

        // Assert
        assertNotNull(updatedDepartment);
        assertEquals("New Short Name", updatedDepartment.getShortName());
        assertEquals("New Name", updatedDepartment.getName());
        verify(departmentRepository, times(1)).save(department);
    }

    @Test
    void testUpdateDepartment_NotFound() {
        // Arrange
        Long id = 1L;
        when(departmentRepository.findById(id)).thenReturn(Optional.empty());

        DepartmentDTO departmentDTO = new DepartmentDTO();

        // Act & Assert
        RuntimeException exception = assertThrows(RuntimeException.class, () -> departmentService.updateDepartment(id, departmentDTO));
        assertEquals("Department not found", exception.getMessage());
    }

    @Test
    void testUpdateDepartment_NullId() {
        // Act & Assert
        NullPointerException exception = assertThrows(NullPointerException.class, () -> departmentService.updateDepartment(null, new DepartmentDTO()));
        assertEquals("Department ID must not be null", exception.getMessage());
    }

    @Test
    void testUpdateDepartment_NullDepartmentDTO() {
        // Act & Assert
        NullPointerException exception = assertThrows(NullPointerException.class, () -> departmentService.updateDepartment(1L, null));
        assertEquals("Department DTO must not be null", exception.getMessage());
    }

}
