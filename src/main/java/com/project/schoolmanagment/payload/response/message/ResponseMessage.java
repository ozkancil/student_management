package com.project.schoolmanagment.payload.response.message;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.project.schoolmanagment.entity.abstracts.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.springframework.http.HttpStatus;

import java.util.List;

@Data
@AllArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResponseMessage<T> {

	private T object;
	private String message;
	private HttpStatus httpStatus;

}
