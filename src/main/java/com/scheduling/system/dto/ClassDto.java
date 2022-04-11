package com.scheduling.system.dto;

import com.scheduling.system.models.Student;
import lombok.Data;

import java.util.Set;

@Data
public class ClassDto {
    private String code;
    private String title;
    private String description;
    private Set<Student> studentList;
}
