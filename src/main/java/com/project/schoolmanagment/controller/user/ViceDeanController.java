package com.project.schoolmanagment.controller.user;

import com.project.schoolmanagment.payload.request.user.ViceDeanRequest;
import com.project.schoolmanagment.payload.response.message.ResponseMessage;
import com.project.schoolmanagment.payload.response.user.ViceDeanResponse;
import com.project.schoolmanagment.service.user.ViceDeanService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/viceDean")
@RequiredArgsConstructor
@PreAuthorize("hasAnyAuthority('ADMIN','MANAGER','ASSISTANT_MANAGER')")

public class ViceDeanController {

    public final ViceDeanService viceDeanService;



    @PostMapping("/save")
    public ResponseMessage<ViceDeanResponse> saveViceDean(@Valid @RequestBody ViceDeanRequest viceDeanRequest){
        return viceDeanService.saveViceDean(viceDeanRequest);
    }

}
