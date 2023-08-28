package com.project.schoolmanagment.payload.mappers;

import com.project.schoolmanagment.entity.concretes.user.ViceDean;
import com.project.schoolmanagment.payload.request.user.ViceDeanRequest;
import com.project.schoolmanagment.payload.response.user.ViceDeanResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ViceDeanMapper {

    public ViceDean mapViceDeanRequestToViceDean(ViceDeanRequest viceDeanRequest){
        return ViceDean.builder()
                .username(viceDeanRequest.getUsername())
                .name(viceDeanRequest.getName())
                .surname(viceDeanRequest.getSurname())
                .password(viceDeanRequest.getPassword())
                .ssn(viceDeanRequest.getSsn())
                .birthDay(viceDeanRequest.getBirthDay())
                .birthPlace(viceDeanRequest.getBirthPlace())
                .phoneNumber(viceDeanRequest.getPhoneNumber())
                .gender(viceDeanRequest.getGender())
                .build();
    }

    public ViceDeanResponse mapViceDeanToViceDeanResponse(ViceDean viceDean){
        return ViceDeanResponse.builder()
                .userId(viceDean.getId())
                .username(viceDean.getUsername())
                .name(viceDean.getName())
                .surname(viceDean.getSurname())
                .birthDay(viceDean.getBirthDay())
                .birthPlace(viceDean.getBirthPlace())
                .phoneNumber(viceDean.getPhoneNumber())
                .gender(viceDean.getGender())
                .ssn(viceDean.getSsn())
                .build();
    }

}
