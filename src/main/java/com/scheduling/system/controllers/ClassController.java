package com.scheduling.system.controllers;

import com.scheduling.system.dto.ClassDto;
import com.scheduling.system.helpers.ClassResponse;
import com.scheduling.system.helpers.StudentResponse;
import com.scheduling.system.services.ClassService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/api/v1/class")
public class ClassController {

    private final ClassService classService;

    public ClassController(ClassService classService) {
        this.classService = classService;
    }

    @GetMapping(value = "/list")
    public ClassResponse getListOfClasses() {
        return classService.getListOfClasses();
    }

    @PostMapping(value = "/create")
    public ClassResponse createClass(@RequestBody ClassDto classDto) {
        return classService.createClass(classDto);
    }

    @PutMapping(value = "/update")
    public ClassResponse updateClass(@RequestBody ClassDto classDto) {
        return classService.updateClass(classDto);
    }

    @DeleteMapping(value = "/delete/{classId}")
    public ClassResponse deleteClass(@PathVariable Long classId) {
        return classService.deleteClass(classId);
    }

    @GetMapping(value = "/list/{studentId}/students")
    public ClassResponse getAllClassesAssignToStudent(@PathVariable Long studentId) {
        return classService.getAllClassesAssignToStudent(studentId);
    }
}
