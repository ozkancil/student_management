package com.project.schoolmanagment.contactmessage.service;

import com.project.schoolmanagment.contactmessage.dto.ContactMessageRequest;
import com.project.schoolmanagment.contactmessage.dto.ContactMessageResponse;
import com.project.schoolmanagment.contactmessage.dto.ContactMessageUpdateRequest;
import com.project.schoolmanagment.contactmessage.dto.ResponseMessage;
import com.project.schoolmanagment.contactmessage.entity.ContactMessage;
import com.project.schoolmanagment.contactmessage.exception.ConflictException;
import com.project.schoolmanagment.contactmessage.exception.ResourceNotFoundException;
import com.project.schoolmanagment.contactmessage.mapper.ContactMessageMapper;
import com.project.schoolmanagment.contactmessage.messages.Messages;
import com.project.schoolmanagment.contactmessage.repository.ContactMessageRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Objects;


@Service
@RequiredArgsConstructor
public class ContactMessageService {

	private final ContactMessageRepository contactMessageRepository;

	private final ContactMessageMapper createContactMessage;

	public ResponseMessage<ContactMessageResponse> save(ContactMessageRequest contactMessageRequest){

		//it is expected to create one message in a day with the same email
		boolean isSameMessageWithSameEmailForToday =
				contactMessageRepository.existsByEmailEqualsAndDateTimeEquals(contactMessageRequest.getEmail(), LocalDateTime.now());

		if(isSameMessageWithSameEmailForToday){
			throw new ConflictException(Messages.ALREADY_SEND_A_MESSAGE_TODAY);
		}

		ContactMessage contactMessage = createContactMessage.requestToContactMessage(contactMessageRequest);
		ContactMessage savedData = contactMessageRepository.save(contactMessage);
		return ResponseMessage.<ContactMessageResponse>builder()
				// this message should be moved to Messages class and called from there.
				.message("Contact Message Created Successfully")
				.httpStatus(HttpStatus.CREATED)
				.object(createContactMessage.contactMessageToResponse(savedData))
				.build();
	}

	public List<ContactMessage>searchByDateBetween(String beginDateString, String endDateString){
		try {
			LocalDate beginDate = LocalDate.parse(beginDateString);
			LocalDate endDate = LocalDate.parse(endDateString);
			return contactMessageRepository.findMessagesBetweenDates(beginDate, endDate);
		} catch (DateTimeParseException e) {
			throw new ConflictException(Messages.WRONG_DATE_FORMAT);
		}
	}

	public String deleteById(Long id){
		getContactMessageById(id);
		contactMessageRepository.deleteById(id);
		return Messages.CONTACT_MESSAGE_DELETED_SUCCESSFULLY;
	}

	public List<ContactMessage>searchByTimeBetween(String startHourString, String startMinuteString,
	                                               String endHourString, String endMinuteString){
		try {
			int startHour = Integer.parseInt(startHourString);
			int startMinute = Integer.parseInt(startMinuteString);
			int endHour = Integer.parseInt(endHourString);
			int endMinute = Integer.parseInt(endMinuteString);
			return contactMessageRepository.findMessagesBetweenTimes(startHour,startMinute,endHour,endMinute);
		} catch (NumberFormatException e) {
			throw new ConflictException(Messages.WRONG_TIME_FORMAT);
		}
	}

	public ContactMessage getContactMessageById(Long id){
		return contactMessageRepository.findById(id).orElseThrow(
				()-> new ResourceNotFoundException(Messages.NOT_FOUND_MESSAGE));

	}


	public ResponseMessage<ContactMessageResponse> updateById(Long id, ContactMessageUpdateRequest contactMessageUpdateRequest){
		ContactMessage contactMessage = getContactMessageById(id);
		if(contactMessageUpdateRequest.getMessage()!=null){
			contactMessage.setMessage(contactMessageUpdateRequest.getMessage());
		}
		if(contactMessageUpdateRequest.getSubject()!=null){
			contactMessage.setSubject(contactMessageUpdateRequest.getSubject());
		}
		if(contactMessageUpdateRequest.getName()!=null){
			contactMessage.setName(contactMessageUpdateRequest.getName());
		}
		if(contactMessageUpdateRequest.getEmail()!=null){
			contactMessage.setEmail(contactMessageUpdateRequest.getEmail());
		}
		contactMessage.setDateTime(LocalDateTime.now());
		contactMessageRepository.save(contactMessage);
		return ResponseMessage.<ContactMessageResponse>builder()
				.message("Contact Message Created Successfully")
				.httpStatus(HttpStatus.CREATED)
				.object(createContactMessage.contactMessageToResponse(contactMessage))
				.build();
	}


	public Page<ContactMessageResponse> getAll(int page,int size, String sort, String type){
		// in this solution type prop. should be instance of Direction
		// method signature -> getAll(int page,int size, String sort, Direction type)
		//Pageable myPageable  = PageRequest.of(page,size,Sort.by(type,sort));

		Pageable pageable = PageRequest.of(page,size, Sort.by(sort).ascending());
		if(Objects.equals(type,"desc")){
			pageable = PageRequest.of(page,size,Sort.by(sort).descending());
		}

		return contactMessageRepository.findAll(pageable).map(createContactMessage::contactMessageToResponse);
	}

	public Page<ContactMessageResponse> searchByEmail(String email, int page, int size, String sort, String type){
		Pageable pageable = PageRequest.of(page, size, Sort.by(sort).ascending());
		if(Objects.equals(type,"desc")){
			pageable = PageRequest.of(page,size,Sort.by(sort).descending());
		}
		return contactMessageRepository.findByEmailEquals(email,pageable).map(createContactMessage::contactMessageToResponse);
	}

	public Page<ContactMessageResponse> searchBySubject(String subject, int page,int size,String sort,String type){
		Pageable pageable = PageRequest.of(page, size, Sort.by(sort).ascending());
		if(Objects.equals(type,"desc")){
			pageable = PageRequest.of(page, size,Sort.by(sort).descending());
		}
		return contactMessageRepository.findBySubjectEquals(subject,pageable).map(createContactMessage::contactMessageToResponse);
	}








}
