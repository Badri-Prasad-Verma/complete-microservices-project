package com.communication.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SendEmailDto {

    private String recipient;
    private String subject;
    private String message;
    private MultipartFile attachment;

}
