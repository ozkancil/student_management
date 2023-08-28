package com.project.schoolmanagment.repository.user;

import com.project.schoolmanagment.entity.concretes.user.Admin;
import com.project.schoolmanagment.entity.concretes.user.ViceDean;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ViceDeanRepository extends JpaRepository<ViceDean,Long> {

	boolean existsByUsername(String username);

	boolean existsBySsn(String ssn);

	boolean existsByPhoneNumber(String phoneNumber);

	ViceDean findByUsernameEquals(String username);

}
