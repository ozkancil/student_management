package com.project.schoolmanagment.entity.concretes.businnes;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.project.schoolmanagment.entity.concretes.user.Student;
import com.project.schoolmanagment.entity.concretes.user.Teacher;
import com.project.schoolmanagment.entity.enums.Note;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class StudentInfo {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private Integer absentee;

	private Double midtermExam;

	private Double finalExam;

	private Double examAverage;

	private String infoNote;

	@ManyToOne
	private Student student;

	@JsonIgnore
	@ManyToOne
	private Teacher teacher;

	@Enumerated(EnumType.STRING)
	private Note letterGrade;

	@ManyToOne
	@JsonIgnoreProperties("lesson")
	private Lesson lesson;

	@OneToOne
	private EducationTerm educationTerm;


}
