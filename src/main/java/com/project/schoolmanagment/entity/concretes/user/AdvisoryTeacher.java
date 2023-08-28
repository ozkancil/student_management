package com.project.schoolmanagment.entity.concretes.user;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AdvisoryTeacher {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@OneToOne
	@JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
	private UserRole userRole;

	@OneToOne
	private Teacher teacher;

	//TODO learn about the cascade types
	@OneToMany(mappedBy = "advisoryTeacher",cascade = CascadeType.ALL)
	private List<Student> students;

}
