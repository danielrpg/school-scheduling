package com.scheduling.system.services;

import com.scheduling.system.dto.ClassDto;
import com.scheduling.system.helpers.ClassResponse;
import com.scheduling.system.helpers.StudentResponse;
import com.scheduling.system.models.Class;
import com.scheduling.system.models.Student;
import com.scheduling.system.repositories.ClassRepository;
import com.scheduling.system.repositories.StudentRepository;
import com.scheduling.system.utils.Constants;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class ClassServiceImpl implements ClassService{

    private final ClassRepository classRepository;
    private final StudentRepository studentRepository;

    public ClassServiceImpl(ClassRepository classRepository,
                            StudentRepository studentRepository) {
        this.classRepository = classRepository;
        this.studentRepository = studentRepository;
    }

    @Override
    public ClassResponse getListOfClasses() {
        ClassResponse response = new ClassResponse();
        List<Class> listOfClasses = classRepository.findAll();

        listOfClasses.forEach(classEntity -> {
            classEntity.setAttends(classEntity.getAttends().stream()
                    .filter(course -> course.getId().equals(classEntity.getId()))
                    .collect(Collectors.toSet()));
        });

        response.setData(new HashSet<>(listOfClasses));
        response.setStatus(Constants.STATUS_OK);
        response.setMessage(Constants.SUCCESS);
        return response;
    }

    @Override
    @Transactional
    public ClassResponse updateClass(ClassDto newClassDto) {
        Class currentClass = classRepository.getClassByCode(newClassDto.getCode());
        currentClass.getAttends().clear();
        mapDtoToEntity(currentClass, newClassDto);
        Class classDb = classRepository.save(currentClass);
        return mapEntityToResponse(classDb);
    }

    @Override
    @Transactional
    public ClassResponse deleteClass(Long classId) {
        ClassResponse response = new ClassResponse();
        Optional<Class> classDb = classRepository.findById(classId);

        if(classDb.isPresent()) {
            classDb.get().removeStudents();
            classRepository.deleteById(classDb.get().getId());
            response.setStatus(Constants.STATUS_OK);
            response.setMessage(Constants.SUCCESS);
            response.setData(null);
            return response;
        }
        response.setStatus(Constants.BAD_REQUEST);
        response.setMessage(Constants.AN_ISSUE);
        response.setData(null);
        return response;
    }

    @Override
    @Transactional
    public ClassResponse createClass(ClassDto newClass) {
        Class newClassCreated = new Class();
        mapDtoToEntity(newClassCreated, newClass);
        Class savedClass = classRepository.save(newClassCreated);
        return mapEntityToResponse(savedClass);
    }

    @Override
    public ClassResponse getAllClassesAssignToStudent(Long studentId) {
        ClassResponse response = new ClassResponse();
        response.setMessage(Constants.SUCCESS);
        response.setStatus(Constants.STATUS_OK);

        List<Class> listOfClassAssignToStudent = classRepository.getAllClassesAssignToStudent(studentId);
        response.setData(new HashSet<>(listOfClassAssignToStudent));
        return response;
    }

    private void mapDtoToEntity(Class newClass, ClassDto classDto) {
        newClass.setCode(classDto.getCode());
        newClass.setTitle(classDto.getTitle());
        newClass.setDescription(classDto.getDescription());

        if(newClass.getAttends() == null) {
            newClass.setAttends(new HashSet<>());
        }

        classDto.getStudentList().stream().forEach(student -> {
            Student studentDb = studentRepository.getStudentByFirstName(student.getFirstName());
            newClass.getAttends().add(studentDb);
        });
    }

    private ClassResponse mapEntityToResponse(Class classDb) {
        ClassResponse response = new ClassResponse();
        response.setMessage(Constants.SUCCESS);
        response.setStatus(Constants.STATUS_OK);
        Set<Class> setOfClass = new HashSet<>();
        setOfClass.add(classDb);
        response.setData(setOfClass);
        return response;
    }
}
