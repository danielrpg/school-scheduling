package com.scheduling.system.models;

import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Entity
@Data
@Table(name = "students")
public class Student implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "student_sequence")
    @SequenceGenerator(name = "student_sequence", sequenceName = "student_sequence")
    @Column(name="student_id", nullable = true)
    private Long id;

    private String firstName;
    private String lastName;

    @ManyToMany(cascade = { CascadeType.MERGE, CascadeType.PERSIST })
    @JoinTable(
            name = "class_attend",
            joinColumns = @JoinColumn(name = "student_id"),
            inverseJoinColumns = @JoinColumn(name = "class_id"))
    private Set<Class> attendClass;

    public void removeClasses() {
        for(Class classAttend: new HashSet<>(attendClass)) {
            this.getAttendClass().remove(classAttend);
            classAttend.getAttends().remove(this);
        }
    }
}
