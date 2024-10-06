package com.shakhawat.springbootactuator.department;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DepartmentDTO {

    private Long id;

    @NotBlank(message = "Short name is required")
    private String shortName;

    @NotBlank(message = "Name is required")
    private String name;

}
