package com.project.schoolmanagment.payload.mappers;

import com.project.schoolmanagment.entity.concretes.user.Admin;
import com.project.schoolmanagment.entity.concretes.user.Dean;
import com.project.schoolmanagment.entity.enums.RoleType;
import com.project.schoolmanagment.payload.request.user.AdminRequest;
import com.project.schoolmanagment.payload.request.user.DeanRequest;
import com.project.schoolmanagment.payload.response.user.DeanResponse;
import com.project.schoolmanagment.service.user.UserRoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DeanMapper {

	private final UserRoleService userRoleService;

	public Dean mapDeanRequestToDean(DeanRequest deanRequest){
		return Dean.builder()
				.username(deanRequest.getUsername())
				.name(deanRequest.getName())
				.surname(deanRequest.getSurname())
				.password(deanRequest.getPassword())
				.ssn(deanRequest.getSsn())
				.birthDay(deanRequest.getBirthDay())
				.birthPlace(deanRequest.getBirthPlace())
				.phoneNumber(deanRequest.getPhoneNumber())
				.gender(deanRequest.getGender())
				.build();
	}

	public DeanResponse mapDeanToDeanResponse(Dean dean){
		return DeanResponse.builder()
				.userId(dean.getId())
				.username(dean.getUsername())
				.name(dean.getName())
				.surname(dean.getSurname())
				.birthDay(dean.getBirthDay())
				.birthPlace(dean.getBirthPlace())
				.phoneNumber(dean.getPhoneNumber())
				.gender(dean.getGender())
				.ssn(dean.getSsn())
				.build();
	}
	public Dean mapDeanRequestToUpdatedDean(DeanRequest deanRequest,Long managerId){
		return Dean.builder()
				.id(managerId)
				.username(deanRequest.getUsername())
				.ssn(deanRequest.getSsn())
				.name(deanRequest.getName())
				.surname(deanRequest.getSurname())
				.birthPlace(deanRequest.getBirthPlace())
				.birthDay(deanRequest.getBirthDay())
				.phoneNumber(deanRequest.getPhoneNumber())
				.gender(deanRequest.getGender())
				.userRole(userRoleService.getUserRole(RoleType.MANAGER))
				.build();
	}


}
