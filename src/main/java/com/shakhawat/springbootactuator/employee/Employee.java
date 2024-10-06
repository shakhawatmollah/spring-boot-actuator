package com.shakhawat.springbootactuator.employee;

import com.shakhawat.springbootactuator.department.Department;
import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "employees", uniqueConstraints = { @UniqueConstraint(name = "employees_code_unique", columnNames = { "code" }) })
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Code is required")
    @Column(name = "code", unique = true, nullable = false)
    private String code;

    @NotBlank(message = "First name is required")
    private String firstName;

    @NotBlank(message = "Last name is mandatory")
    private String lastName;

    private String fullName;

    @Min(value = 18, message = "Age must be at least 18")
    @Max(value = 65, message = "Age must be less than or equal to 65")
    private int age;

    @NotBlank(message = "Gender is required")
    private String gender;

    private String address;

    @ManyToOne
    @JoinColumn(name = "department_id", nullable = false)
    private Department department;

    @PrePersist
    @PreUpdate
    private void setFullName() {
        this.fullName = this.firstName + " " + this.lastName;
    }

}
