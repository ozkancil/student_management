package com.project.schoolmanagment.service.user;

import com.project.schoolmanagment.entity.concretes.user.Admin;
import com.project.schoolmanagment.entity.enums.RoleType;
import com.project.schoolmanagment.exception.ConflictException;
import com.project.schoolmanagment.exception.ResourceNotFoundException;
import com.project.schoolmanagment.payload.mappers.AdminMapper;
import com.project.schoolmanagment.payload.messages.ErrorMessages;
import com.project.schoolmanagment.payload.messages.SuccessMessages;
import com.project.schoolmanagment.payload.request.user.AdminRequest;
import com.project.schoolmanagment.payload.response.message.ResponseMessage;
import com.project.schoolmanagment.payload.response.user.AdminResponse;
import com.project.schoolmanagment.repository.user.AdminRepository;
import com.project.schoolmanagment.service.helper.PageableHelper;
import com.project.schoolmanagment.service.validator.UniquePropertyValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AdminService {

	private final AdminMapper adminMapper;
	private final AdminRepository adminRepository;
	private final UserRoleService userRoleService;
	private final UniquePropertyValidator uniquePropertyValidator;
	private final PageableHelper pageableHelper;
	private final PasswordEncoder passwordEncoder;

	public ResponseMessage<AdminResponse>saveAdmin(AdminRequest adminRequest){

		//we need to check duplication
		uniquePropertyValidator.checkDuplicate(adminRequest.getUsername(),
												adminRequest.getSsn(),
												adminRequest.getPhoneNumber());

		// we must map DTO -> Entity
		Admin admin = adminMapper.mapAdminRequestToAdmin(adminRequest);

		//we are setting role type from roles table
		admin.setUserRole(userRoleService.getUserRole(RoleType.ADMIN));

		//superAdmin will not be deleted or changed
		if(Objects.equals(adminRequest.getUsername(),"superAdmin")){
			admin.setBuiltIn(true);
		}
		//we are encoding the password
      admin.setPassword(passwordEncoder.encode(adminRequest.getPassword()));
		Admin savedAdmin= adminRepository.save(admin);

		// we are returning response DTO by mapping the saved version of admin.
		return ResponseMessage.<AdminResponse>builder()
				.message(SuccessMessages.ADMIN_CREATE)
				.object(adminMapper.mapAdminToAdminResponse(savedAdmin))
				.build();

	}

	public String deleteById(Long id){
		//we should check the database if this id really exist
		if (findAdminById(id).isBuiltIn()) {
			throw new ConflictException(ErrorMessages.NOT_PERMITTED_METHOD_MESSAGE);
		}
		adminRepository.deleteById(id);
		return SuccessMessages.ADMIN_DELETE;
	}

	public Page<Admin> getAllAdminsByPage(int page, int size, String sort, String type){
		Pageable pageable = pageableHelper.getPageableWithProperties(page, size, sort, type);
		return adminRepository.findAll(pageable);
	}

	public long countAllAdmins(){
		return adminRepository.count();
	}

	//private reusable method for checking the database, if exist it returns value otherwise throws exception
	private Admin findAdminById(Long id){
		return adminRepository.findById(id)
				.orElseThrow( ()-> new ResourceNotFoundException(String.format(ErrorMessages.NOT_FOUND_USER_MESSAGE, id)));
	}
	public List<AdminResponse> getAllAdmins(){
		return adminRepository.findAll()
				.stream()
				.map(adminMapper::mapAdminToAdminResponse)
				.collect(Collectors.toList());
	}

	public AdminResponse findById(Long id){
		return adminMapper.mapAdminToAdminResponse(findAdminById(id));
	}


	public List<AdminResponse> findAdminsByUsername(String username) {
		List<Admin> admins = adminRepository.findByUsername(username);
		if(admins.isEmpty()){
			throw new ResourceNotFoundException(String.format(ErrorMessages.NOT_FOUND_USER_MESSAGE_USERNAME,username));
		}
		return adminRepository.findByUsername(username).stream().map(adminMapper::mapAdminToAdminResponse).collect(Collectors.toList());
	}

}
