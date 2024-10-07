package com.shakhawat.springbootactuator.department;

import com.shakhawat.springbootactuator.employee.Employee;
import com.shakhawat.springbootactuator.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Log4j2
@Service
@RequiredArgsConstructor
public class DepartmentService {

    private final DepartmentRepository departmentRepository;

    public List<DepartmentDTO> getAllDepartments() {
        return departmentRepository.findAll().stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    public Optional<DepartmentDTO> getDepartmentById(Long id) {
        if (id == null) {
            throw new NullPointerException("Department ID must not be null");
        }
        Department department = departmentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Department with id: " + id + " not found"));
        return Optional.of(convertToDTO(department));
    }

    public DepartmentDTO createDepartment(DepartmentDTO departmentDTO) {
        Department department = convertToEntity(departmentDTO);
        departmentRepository.save(department);
        return convertToDTO(department);
    }

    public DepartmentDTO updateDepartment(Long id, DepartmentDTO departmentDTO) {
        if (id == null) {
            throw new NullPointerException("Department ID must not be null");
        }
        if (departmentDTO == null) {
            throw new NullPointerException("Department DTO must not be null");
        }
        Department department = departmentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Department not found"));
        if (department != null) {
            department.setShortName(departmentDTO.getShortName());
            department.setName(departmentDTO.getName());
            departmentRepository.save(department);
        }
        assert department != null;
        return convertToDTO(department);
    }

    public void deleteDepartment(Long id) {
        log.info("Deleting department with id: {}", id);
        if (id == null) {
            throw new NullPointerException("Department ID must not be null");
        }
        Department department = departmentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Department not found with id: " + id));
        departmentRepository.delete(department);
    }

    private DepartmentDTO convertToDTO(Department department) {
        DepartmentDTO dto = new DepartmentDTO();
        dto.setId(department.getId());
        dto.setShortName(department.getShortName());
        dto.setName(department.getName());
        return dto;
    }

    private Department convertToEntity(DepartmentDTO departmentDTO) {
        Department department = new Department();
        department.setShortName(departmentDTO.getShortName());
        department.setName(departmentDTO.getName());
        return department;
    }

}
