package com.project.schoolmanagment.service.validator;

import com.project.schoolmanagment.entity.abstracts.User;
import com.project.schoolmanagment.entity.concretes.user.Student;
import com.project.schoolmanagment.entity.concretes.user.Teacher;
import com.project.schoolmanagment.exception.ConflictException;
import com.project.schoolmanagment.payload.messages.ErrorMessages;
import com.project.schoolmanagment.payload.request.abstracts.BaseUserRequest;
import com.project.schoolmanagment.payload.request.user.StudentRequest;
import com.project.schoolmanagment.payload.request.user.TeacherRequest;
import com.project.schoolmanagment.repository.user.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UniquePropertyValidator {

	private final AdminRepository adminRepository;
	private final DeanRepository deanRepository;
	private final ViceDeanRepository viceDeanRepository;
	private final StudentRepository studentRepository;
	private final TeacherRepository teacherRepository;

	public void checkUniqueProperties(User user, BaseUserRequest baseUserRequest){
		String updatedUsername = "";
		String updatedSnn = "";
		String updatedPhone = "";
		String updatedEmail = "";
		boolean isChanged = false;
		if(!user.getUsername().equalsIgnoreCase(baseUserRequest.getUsername())){
			updatedUsername = baseUserRequest.getUsername();
			isChanged = true;
		}
		if(!user.getSsn().equalsIgnoreCase(baseUserRequest.getSsn())){
			updatedSnn = baseUserRequest.getSsn();
			isChanged = true;
		}
		if(!user.getPhoneNumber().equalsIgnoreCase(baseUserRequest.getPhoneNumber())){
			updatedPhone = baseUserRequest.getPhoneNumber();
			isChanged = true;
		}
		boolean teacherOrStudent = false;
		if(user instanceof Teacher && baseUserRequest instanceof TeacherRequest){
			Teacher teacher = (Teacher) user;
			TeacherRequest teacherRequest = (TeacherRequest) baseUserRequest;
			if(!teacher.getEmail().equalsIgnoreCase(teacherRequest.getEmail())){
				updatedEmail = teacherRequest.getEmail();
				isChanged = true;
				teacherOrStudent = true;
			}
		}
		if(user instanceof Student && baseUserRequest instanceof StudentRequest){
			Student student = (Student) user;
			StudentRequest studentRequest = (StudentRequest) baseUserRequest;
			if(!student.getEmail().equalsIgnoreCase(studentRequest.getEmail())){
				updatedEmail = studentRequest.getEmail();
				isChanged = true;
				teacherOrStudent = true;
			}
		}
		if(isChanged){
			if(teacherOrStudent){
				checkDuplicate(updatedUsername,updatedSnn,updatedPhone,updatedEmail);
			} else {
				checkDuplicate(updatedUsername,updatedSnn,updatedPhone);
			}
		}
	}


	public void checkDuplicate(String... values){
		String username = values[0];
		String ssn = values[1];
		String phone = values[2];
		String email = "";

		if (values.length==4){
			email = values[3];
		}
		if (adminRepository.existsByUsername(username) || deanRepository.existsByUsername(username) ||
				studentRepository.existsByUsername(username) || teacherRepository.existsByUsername(username) ||
				viceDeanRepository.existsByUsername(username) ) {
			throw new ConflictException(String.format(ErrorMessages.ALREADY_REGISTER_MESSAGE_USERNAME, username));
		} else if (adminRepository.existsBySsn(ssn) || deanRepository.existsBySsn(ssn) ||
				studentRepository.existsBySsn(ssn) || teacherRepository.existsBySsn(ssn) ||
				viceDeanRepository.existsBySsn(ssn) ) {
			throw new ConflictException(String.format(ErrorMessages.ALREADY_REGISTER_MESSAGE_SSN, ssn));
		} else if (adminRepository.existsByPhoneNumber(phone) || deanRepository.existsByPhoneNumber(phone) ||
				studentRepository.existsByPhoneNumber(phone) || teacherRepository.existsByPhoneNumber(phone) ||
				viceDeanRepository.existsByPhoneNumber(phone) ) {
			throw new ConflictException(String.format(ErrorMessages.ALREADY_REGISTER_MESSAGE_PHONE_NUMBER, phone));
		} else if (studentRepository.existsByEmail(email) || teacherRepository.existsByEmail(email)) {
			throw new ConflictException(String.format(ErrorMessages.ALREADY_REGISTER_MESSAGE_EMAIL, email));
		}
	}

}
