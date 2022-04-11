package com.scheduling.system.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Entity
@Data
@Table(name = "classes")
public class Class implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "class_sequence")
    @SequenceGenerator(name = "class_sequence", sequenceName = "class_sequence")
    @Column(name="class_id", nullable = false)
    private Long id;

    private String code;
    private String title;
    private String description;

    @ManyToMany(mappedBy = "attendClass")
    @JsonIgnore
    private Set<Student> attends;

    public void removeStudents() {
        for(Student student: new HashSet<>(attends)) {
            this.getAttends().remove(student);
            student.getAttendClass().remove(this);
        }
    }
}
