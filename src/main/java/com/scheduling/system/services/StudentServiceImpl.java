package com.scheduling.system.services;

import com.scheduling.system.dto.StudentDto;
import com.scheduling.system.helpers.StudentResponse;
import com.scheduling.system.models.Class;
import com.scheduling.system.models.Student;
import com.scheduling.system.repositories.ClassRepository;
import com.scheduling.system.repositories.StudentRepository;
import com.scheduling.system.utils.Constants;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class StudentServiceImpl implements StudentService{

    private final ClassRepository classRepository;
    private final StudentRepository studentRepository;

    public StudentServiceImpl(ClassRepository classRepository,
                              StudentRepository studentRepository) {
        this.classRepository = classRepository;
        this.studentRepository = studentRepository;
    }

    @Override
    public StudentResponse getListOfStudents() {
        StudentResponse response = new StudentResponse();
        List<Student> listOfStudents = studentRepository.findAll();
       /* listOfStudents.stream().forEach(student -> {
            student.getAttendClass()
        });*/
        response.setData(new HashSet<>(listOfStudents));
        response.setStatus(Constants.STATUS_OK);
        response.setMessage(Constants.SUCCESS);
        return response;
    }

    @Override
    @Transactional
    public StudentResponse updateStudent(StudentDto newStudentDto) {
        Student currentStudent = studentRepository.getStudentByFirstName(newStudentDto.getFirstName());
        currentStudent.getAttendClass().clear();
        mapDtoToEntity(currentStudent, newStudentDto);
        Student studentDb = studentRepository.save(currentStudent);
        return mapEntityToResponse(studentDb);
    }

    @Override
    @Transactional
    public StudentResponse deleteStudent(Long studentId) {
        StudentResponse response = new StudentResponse();
        Optional<Student> studentDb = studentRepository.findById(studentId);

        if(studentDb.isPresent()) {
            studentDb.get().removeClasses();
            classRepository.deleteById(studentDb.get().getId());
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
    public StudentResponse createStudent(StudentDto newStudent) {
        Student newStudentCreated = new Student();
        mapDtoToEntity(newStudentCreated, newStudent);
        Student studentSaved = studentRepository.save(newStudentCreated);
        return mapEntityToResponse(studentSaved);
    }

    @Override
    public StudentResponse getAllStudentsAssignToClass(Long classId) {
        StudentResponse response = new StudentResponse();
        response.setMessage(Constants.SUCCESS);
        response.setStatus(Constants.STATUS_OK);

        List<Student> listOfStudents = studentRepository.getAllStudentsAssignToClass(classId);
        response.setData(new HashSet<>(listOfStudents));
        return response;
    }

    private StudentResponse mapEntityToResponse(Student studentDb) {
        StudentResponse response = new StudentResponse();
        response.setMessage(Constants.SUCCESS);
        response.setStatus(Constants.STATUS_OK);
        Set<Student> setOfStudent = new HashSet<>();
        setOfStudent.add(studentDb);
        response.setData(setOfStudent);
        return response;
    }

    private void mapDtoToEntity(Student currentStudent, StudentDto newStudentDto) {
        currentStudent.setFirstName(newStudentDto.getFirstName());
        currentStudent.setLastName(newStudentDto.getLastName());

        if(currentStudent.getAttendClass() == null) {
            currentStudent.setAttendClass(new HashSet<>());
        }

        newStudentDto.getListOfClasses().forEach(classAttend -> {
            Class classDb = classRepository.getClassByCode(classAttend.getCode());
            currentStudent.getAttendClass().add(classDb);
        });
    }
}
