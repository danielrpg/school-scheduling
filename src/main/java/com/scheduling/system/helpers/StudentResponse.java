package com.scheduling.system.helpers;

import com.scheduling.system.models.Student;
import lombok.Data;

import java.util.Set;

@Data
public class StudentResponse {
    private String message;
    private Integer status;
    private Set<Student> data;
}
