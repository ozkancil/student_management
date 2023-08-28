package com.project.schoolmanagment.repository.user;

import com.project.schoolmanagment.entity.concretes.user.Admin;
import com.project.schoolmanagment.entity.concretes.user.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TeacherRepository extends JpaRepository<Teacher,Long> {

	boolean existsByUsername(String username);

	boolean existsBySsn(String ssn);

	boolean existsByPhoneNumber(String phoneNumber);

	boolean existsByEmail(String email);

	Teacher findByUsernameEquals(String username);
}
