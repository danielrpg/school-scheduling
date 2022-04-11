package com.scheduling.system.dto;

import lombok.Data;

import com.scheduling.system.models.Class;
import java.util.HashSet;
import java.util.Set;

@Data
public class StudentDto {
    private String firstName;
    private String lastName;
    private Set<Class> listOfClasses = new HashSet<>();
}
