package com.scheduling.system.repositories;

import com.scheduling.system.models.Class;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ClassRepository extends JpaRepository<Class, Long> {
    Class getClassByCode(String code);

    @Query(value = "SELECT c.code, c.description, c.title  " +
                   "FROM classes c  " +
                   "INNER JOIN class_attend ca ON (ca.class_id = c.class_id) " +
                   "INNER JOIN students s ON (ca.student_id = s.student_id) "+
                   "WHERE s.student_id =:studentId", nativeQuery = true)
    List<Class> getAllClassesAssignToStudent(@Param("studentId") Long studentId);
}
