package com.communication.controller;

import com.communication.dto.ApiResponse;
import com.communication.dto.SendEmailDto;
import com.communication.service.SendEmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/mail")
public class SendMailController {
    @Autowired
    private SendEmailService service;
    @PostMapping("/send")
    public ResponseEntity<ApiResponse> sendEmailId(
            @RequestBody SendEmailDto sendEmailDto
//            @RequestParam(value = "recipient",required = true) String recipient,
//            @RequestParam(value = "subject",required = true) String subject,
//            @RequestParam(value = "message",required = true) String message,
//            @RequestParam(value = "attachment",required = false) MultipartFile attachment
           ){
       try {
          // System.out.println(recipient);
         //  SendEmailDto sendEmailDto=new SendEmailDto(recipient,subject,message,attachment);
           service.sendEmail(sendEmailDto);

           ApiResponse response = ApiResponse.builder().httpStatus(HttpStatus.ACCEPTED)
                   .message("Email Send Successfully").build();

           return ResponseEntity.ok(response);
       }catch (Exception e){
           ApiResponse response = ApiResponse.builder().httpStatus(HttpStatus.ACCEPTED)
                   .message("Email Send Successfully").build();
           return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
       }

    }

}
