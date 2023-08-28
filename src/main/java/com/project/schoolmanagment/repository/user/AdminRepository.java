package com.project.schoolmanagment.repository.user;

import com.project.schoolmanagment.entity.concretes.user.Admin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AdminRepository extends JpaRepository<Admin,Long> {

	boolean existsByUsername(String username);

	boolean existsBySsn(String ssn);

	boolean existsByPhoneNumber(String phoneNumber);


	List<Admin> findByUsername(String username);

	List<Admin> findByNameOrSurname(String name, String surname);

	Admin findByUsernameEquals(String username);
}
