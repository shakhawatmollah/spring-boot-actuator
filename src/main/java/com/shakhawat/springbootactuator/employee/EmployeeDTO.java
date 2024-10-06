package com.shakhawat.springbootactuator.employee;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeDTO {

    private Long id;

    @NotBlank(message = "Code is required")
    private String code;

    @NotBlank(message = "First name is required")
    private String firstName;

    @NotBlank(message = "Last name is required")
    private String lastName;

    private String fullName;

    @Min(value = 18, message = "Age must be at least 18")
    @Max(value = 65, message = "Age must be less than or equal to 65")
    private int age;

    @NotBlank(message = "Gender is required")
    private String gender;

    private String address;

    @NotNull(message = "Department ID is required")
    private Long departmentId;

}
