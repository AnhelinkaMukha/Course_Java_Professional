package ru.otus.exercise.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "otus_students")
@SuppressWarnings("java:S125")
public class OtusStudent {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(name = "student_name", nullable = false)
    private String name;

    @OneToOne
     private Avatar avatar;
    @OneToMany(mappedBy = "student", cascade = CascadeType.ALL)
     private List<EMail> emails;

    @ManyToMany
    @JoinTable(name = "student_course",
            joinColumns = @JoinColumn(name = "student_id"),
            inverseJoinColumns = @JoinColumn(name = "course_id"))
     private List<Course> courses;
}
