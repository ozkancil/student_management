package com.project.schoolmanagment.entity.concretes.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.project.schoolmanagment.entity.abstracts.User;
import com.project.schoolmanagment.entity.concretes.businnes.LessonProgram;
import com.project.schoolmanagment.entity.concretes.businnes.Meet;
import com.project.schoolmanagment.entity.concretes.businnes.StudentInfo;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@SuperBuilder
public class Student extends User {

	private String motherName;

	private String fatherName;

	private int studentNumber;

	private boolean isActive;

	@Column(unique = true)
	private String email;

	@JsonIgnore
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "advisory_teacher_id")
	private AdvisoryTeacher advisoryTeacher;

	@JsonIgnore
	@OneToMany(mappedBy = "student", cascade = CascadeType.ALL)
	private List<StudentInfo>studentInfos;

	@JsonIgnore
	@ManyToMany
	@JoinTable(name = "student_lessonprogram",
				joinColumns = @JoinColumn(name = "student_id"),
				inverseJoinColumns = @JoinColumn(name = "lesson_program_id"))
	private Set<LessonProgram> lessonsProgramList;

	@JsonIgnore
	@ManyToMany
	@JoinTable(name = "meet_student_table",
				joinColumns = @JoinColumn(name = "student_id"),
				inverseJoinColumns = @JoinColumn(name = "meet_id"))
	private List<Meet>meetList;




}
