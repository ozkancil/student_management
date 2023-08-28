package com.project.schoolmanagment.contactmessage.repository;

import com.project.schoolmanagment.contactmessage.entity.ContactMessage;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ContactMessageRepository extends JpaRepository<ContactMessage, Long> {
	/*
	 * existsByEmailEqualsAndDateEquals(String email, LocalDate date)
	 * This method checks if a contact message exists by comparing the email
	 * and date values with the provided email
	 * and date parameters. It uses the equals method for comparison.
	 * existsByEmailAndDate(String email, LocalDate date)
	 * This method also checks if a contact message exists, but it compares the email
	 * and date values using the default comparison behavior, which is generally equivalent
	 * to using the == operator.
	 * So, the main difference lies in the comparison method used for the email
	 * and date parameters. The first method (existsByEmailEqualsAndDateEquals) uses
	 * the equals method, which compares the values of the two objects, while the second method
	 * (existsByEmailAndDate) uses the default comparison behavior,
	 * which compares the references of the two objects.
	 */
	boolean existsByEmailEqualsAndDateTimeEquals(String email, LocalDateTime dateTime);

	@Query("select c from ContactMessage c where FUNCTION('DATE', c.dateTime) between ?1 and ?2")
	List<ContactMessage> findMessagesBetweenDates(LocalDate beginDate, LocalDate endDate);


	@Query("SELECT c FROM ContactMessage c WHERE " +
			"(EXTRACT(HOUR FROM c.dateTime) BETWEEN :startHour AND :endHour) AND " +
			"(EXTRACT(HOUR FROM c.dateTime) != :startHour OR EXTRACT(MINUTE FROM c.dateTime) >= :startMinute) AND " +
			"(EXTRACT(HOUR FROM c.dateTime) != :endHour OR EXTRACT(MINUTE FROM c.dateTime) <= :endMinute)")
	List<ContactMessage> findMessagesBetweenTimes(@Param("startHour") int startHour,
	                                              @Param("startMinute") int startMinute,
	                                              @Param("endHour") int endHour,
	                                              @Param("endMinute") int endMinute);







	boolean existsByEmailAndDateTime(String email, LocalDate date);

	Page<ContactMessage>findByEmailEquals(String email, Pageable pageable);

	Page<ContactMessage>findBySubjectEquals(String subject, Pageable pageable);




}
