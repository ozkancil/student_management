package com.project.schoolmanagment.repository.user;

import com.project.schoolmanagment.entity.concretes.user.Admin;
import com.project.schoolmanagment.entity.concretes.user.Dean;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DeanRepository extends JpaRepository<Dean,Long> {

	boolean existsByUsername(String username);

	boolean existsBySsn(String ssn);

	boolean existsByPhoneNumber(String phoneNumber);

	Dean findByUsernameEquals(String username);
}
