package com.scheduling.system.controllers;

import com.scheduling.system.dto.StudentDto;
import com.scheduling.system.helpers.StudentResponse;
import com.scheduling.system.services.StudentService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/api/v1/student")
public class StudentController {

    private final StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @GetMapping(value = "/list")
    public StudentResponse getListOfStudents() {
        return studentService.getListOfStudents();
    }

    @PostMapping(value = "/create")
    public StudentResponse createStudent(@RequestBody StudentDto studentDto) {
        return studentService.createStudent(studentDto);
    }

    @PutMapping(value = "/update")
    public StudentResponse updateStudent(@RequestBody StudentDto studentDto) {
        return studentService.updateStudent(studentDto);
    }

    @DeleteMapping(value = "/delete/{studentId}")
    public StudentResponse deleteClass(@PathVariable Long studentId) {
        return studentService.deleteStudent(studentId);
    }

    @GetMapping(value = "/list/{classId}/classes")
    public StudentResponse getAllStudentsAssignToClass(@PathVariable Long classId) {
        return studentService.getAllStudentsAssignToClass(classId);
    }
}
