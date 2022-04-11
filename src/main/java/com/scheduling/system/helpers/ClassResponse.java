package com.scheduling.system.helpers;

import com.scheduling.system.models.Class;
import lombok.Data;

import java.util.Set;

@Data
public class ClassResponse {
    private String message;
    private Integer status;
    private Set<Class> data;
}
