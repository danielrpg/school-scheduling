package com.scheduling.system.repositories;

import com.scheduling.system.helpers.StudentResponse;
import com.scheduling.system.models.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {
    Student getStudentByFirstName(String firstName);

    @Query(value = "SELECT s.first_name, s.last_name " +
                    "FROM students s " +
                    "INNER JOIN class_attend ca ON (ca.student_id = s.student_id) " +
                    "INNER JOIN classes c ON (ca.class_id = c.class_id) " +
                    "WHERE s.student_id =:classId", nativeQuery = true)
    List<Student> getAllStudentsAssignToClass(@Param("classId") Long classId);
}
