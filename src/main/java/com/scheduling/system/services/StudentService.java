package com.scheduling.system.services;

import com.scheduling.system.dto.StudentDto;
import com.scheduling.system.helpers.StudentResponse;

public interface StudentService {
    StudentResponse getListOfStudents();
    StudentResponse updateStudent(StudentDto newStudent);
    StudentResponse deleteStudent(Long studentId);
    StudentResponse createStudent(StudentDto newStudent);
    StudentResponse getAllStudentsAssignToClass(Long classId);
}
