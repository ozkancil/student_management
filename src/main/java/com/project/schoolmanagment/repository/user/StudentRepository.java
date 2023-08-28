package com.project.schoolmanagment.repository.user;

import com.project.schoolmanagment.entity.concretes.user.Admin;
import com.project.schoolmanagment.entity.concretes.user.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StudentRepository extends JpaRepository<Student,Long> {

	boolean existsByUsername(String username);

	boolean existsBySsn(String ssn);

	boolean existsByPhoneNumber(String phoneNumber);

	boolean existsByEmail(String email);

	Student findByUsernameEquals(String username);
}
