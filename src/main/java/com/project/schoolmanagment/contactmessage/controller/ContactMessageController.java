package com.project.schoolmanagment.contactmessage.controller;

import com.project.schoolmanagment.contactmessage.dto.ContactMessageRequest;
import com.project.schoolmanagment.contactmessage.dto.ContactMessageResponse;
import com.project.schoolmanagment.contactmessage.dto.ContactMessageUpdateRequest;
import com.project.schoolmanagment.contactmessage.dto.ResponseMessage;
import com.project.schoolmanagment.contactmessage.entity.ContactMessage;
import com.project.schoolmanagment.contactmessage.service.ContactMessageService;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

@RestController
@RequestMapping("/contactMessages")
@RequiredArgsConstructor
public class ContactMessageController {

	private final ContactMessageService contactMessageService;

	/**
	 * {
	 *     "name": "this is my name",
	 *     "email": "techpro@gmail.com",
	 *     "subject": "starting the project",
	 *     "message": "this is my message"
	 * }
	 */
	@PostMapping("/save")
	public ResponseMessage<ContactMessageResponse> save(@RequestBody @Valid ContactMessageRequest contactMessageRequest){
		return contactMessageService.save(contactMessageRequest);
	}

	/**
	 *
	 * @param page number of selected page
	 * @param size size of the page
	 * @param sort sort property
	 * @param type DESC or ASC
	 * @return ContactMessageResponse
	 */
	@GetMapping("/getAll")
	//@PreAuthorize("hasAnyAuthority('ADMIN','MANAGER','ASSISTANT_MANAGER')")
	public Page<ContactMessageResponse> getAll(
			@RequestParam(value = "page",defaultValue = "0") int page,
			@RequestParam(value = "size",defaultValue = "10") int size,
			@RequestParam(value = "sort",defaultValue = "dateTime") String sort,
			@RequestParam(value = "type", defaultValue = "desc") String type){
		return contactMessageService.getAll(page,size,sort,type);
	}

	@GetMapping("/searchByEmail")
	//@PreAuthorize("hasAnyAuthority('ADMIN','MANAGER','ASSISTANT_MANAGER')")
	public Page<ContactMessageResponse> searchByEmail(
			@RequestParam(value = "email") String email,
			@RequestParam(value = "page",defaultValue = "0") int page,
			@RequestParam(value = "size",defaultValue = "10") int size,
			@RequestParam(value = "sort",defaultValue = "dateTime") String sort,
			@RequestParam(value = "type", defaultValue = "desc") String type){
		return contactMessageService.searchByEmail(email,page,size,sort,type);
	}

	@GetMapping("/searchBySubject")
	//@PreAuthorize("hasAnyAuthority('ADMIN','MANAGER','ASSISTANT_MANAGER')")
	public Page<ContactMessageResponse> searchBySubject(
			@RequestParam(value = "subject") String subject,
			@RequestParam(value = "page",defaultValue = "0") int page,
			@RequestParam(value = "size",defaultValue = "10") int size,
			@RequestParam(value = "sort",defaultValue = "dateTime") String sort,
			@RequestParam(value = "type", defaultValue = "desc") String type){
		return contactMessageService.searchBySubject(subject,page,size,sort,type);
	}

	@GetMapping("/searchBetweenDates")
	//@PreAuthorize("hasAnyAuthority('ADMIN','MANAGER','ASSISTANT_MANAGER')")
	public ResponseEntity<List<ContactMessage>> searchByDateBetween(
			@RequestParam(value = "beginDate") String beginDateString,
			@RequestParam(value = "endDate") String endDateString){
		List<ContactMessage>contactMessages = contactMessageService.searchByDateBetween(beginDateString, endDateString);
		return ResponseEntity.ok(contactMessages);
	}

	@GetMapping("/searchBetweenTimes")
	//@PreAuthorize("hasAnyAuthority('ADMIN','MANAGER','ASSISTANT_MANAGER')")
	public ResponseEntity<List<ContactMessage>> searchByTimeBetween(
			@RequestParam(value = "startHour") String startHour,
			@RequestParam(value = "startMinute") String startMinute,
			@RequestParam(value = "endHour") String endHour,
			@RequestParam(value = "endMinute") String endMinute){
		List<ContactMessage>contactMessages = contactMessageService.searchByTimeBetween(startHour,startMinute,endHour,endMinute);
		return ResponseEntity.ok(contactMessages);
	}

	@DeleteMapping("/deleteById")
	//@PreAuthorize("hasAnyAuthority('ADMIN','MANAGER','ASSISTANT_MANAGER')")
	public ResponseEntity<String> deleteById(@RequestParam(value = "concactMessageId") Long contactMessageId){
		return ResponseEntity.ok(contactMessageService.deleteById(contactMessageId));
	}

	@DeleteMapping("/deleteByIdParam/{contactMessageId}")
	//@PreAuthorize("hasAnyAuthority('ADMIN','MANAGER','ASSISTANT_MANAGER')")
	public ResponseEntity<String> deleteByIdPath(@PathVariable Long contactMessageId){
		return ResponseEntity.ok(contactMessageService.deleteById(contactMessageId));
	}

	@GetMapping("/getByIdParam")
	//@PreAuthorize("hasAnyAuthority('ADMIN','MANAGER','ASSISTANT_MANAGER')")
	public ResponseEntity<ContactMessage> getById(@RequestParam(value = "concactMessageId") Long contactMessageId){
		return ResponseEntity.ok(contactMessageService.getContactMessageById(contactMessageId));
	}

	@GetMapping("/getById/{contactMessageId}")
	//@PreAuthorize("hasAnyAuthority('ADMIN','MANAGER','ASSISTANT_MANAGER')")
	public ResponseEntity<ContactMessage> getByIdPath(@PathVariable Long contactMessageId){
		return ResponseEntity.ok(contactMessageService.getContactMessageById(contactMessageId));
	}

	@PutMapping("/updateById/{contactMessageId}")
	//@PreAuthorize("hasAnyAuthority('ADMIN','MANAGER','ASSISTANT_MANAGER')")
	public ResponseEntity<ResponseMessage<ContactMessageResponse>> updateById(@PathVariable Long contactMessageId, @RequestBody @NotNull ContactMessageUpdateRequest contactMessageUpdateRequest){
		return ResponseEntity.ok(contactMessageService.updateById(contactMessageId,contactMessageUpdateRequest));
	}






}
