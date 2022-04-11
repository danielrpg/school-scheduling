package com.scheduling.system.services;

import com.scheduling.system.dto.ClassDto;
import com.scheduling.system.helpers.ClassResponse;

public interface ClassService {
    ClassResponse getListOfClasses();
    ClassResponse updateClass(ClassDto newClass);
    ClassResponse deleteClass(Long classId);
    ClassResponse createClass(ClassDto newClass);
    ClassResponse getAllClassesAssignToStudent(Long studentId);
}
