package com.project.schoolmanagment;


import com.project.schoolmanagment.entity.enums.Gender;
import com.project.schoolmanagment.entity.enums.RoleType;
import com.project.schoolmanagment.payload.request.user.AdminRequest;
import com.project.schoolmanagment.service.user.AdminService;
import com.project.schoolmanagment.service.user.UserRoleService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.time.LocalDate;

@SpringBootApplication
public class SchoolManagementApplication implements CommandLineRunner {

    private final UserRoleService userRoleService;
    private final AdminService adminService;

    public SchoolManagementApplication(UserRoleService userRoleService, AdminService adminService) {
        this.userRoleService = userRoleService;
        this.adminService = adminService;
    }

    public static void main(String[] args) {
        SpringApplication.run(SchoolManagementApplication.class, args);
    }

    @Override
    public void run(String... args){
        if(userRoleService.getAllUserRole().isEmpty()){
            userRoleService.saveUserRole(RoleType.ADMIN);
            userRoleService.saveUserRole(RoleType.MANAGER);
            userRoleService.saveUserRole(RoleType.ASSISTANT_MANAGER);
            userRoleService.saveUserRole(RoleType.TEACHER);
            userRoleService.saveUserRole(RoleType.STUDENT);
            userRoleService.saveUserRole(RoleType.ADVISORY_TEACHER);
        }

        if(adminService.countAllAdmins()==0){
            AdminRequest adminRequest = new AdminRequest();
            adminRequest.setUsername("superAdmin");
            adminRequest.setSsn("987-65-4321");
            adminRequest.setPassword("Ankara06*");
            adminRequest.setName("Lars");
            adminRequest.setSurname("Urich");
            adminRequest.setPhoneNumber("999-999-9999");
            adminRequest.setGender(Gender.FEMALE);
            adminRequest.setBirthDay(LocalDate.of(1980,2,2));
            adminRequest.setBirthPlace("Texas/San Antonio");
            adminService.saveAdmin(adminRequest);
        }

    }
}









