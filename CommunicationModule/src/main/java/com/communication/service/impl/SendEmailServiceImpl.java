package com.communication.service.impl;

import com.communication.dto.SendEmailDto;
import com.communication.service.SendEmailService;
import jakarta.activation.DataSource;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import jakarta.mail.util.ByteArrayDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
@Service
public class SendEmailServiceImpl implements SendEmailService {
    @Autowired
    private JavaMailSender javaMailSender;
    @Value("${spring.mail.username}")
    private String sender;
    @Override
    public void sendEmail(SendEmailDto sendEmailDto) {
        try {
            MimeMessage mimeMessage = javaMailSender.createMimeMessage();
            MimeMessageHelper helper=new MimeMessageHelper(mimeMessage, true);

            helper.setFrom(sender);
            helper.setTo(sendEmailDto.getRecipient());
            helper.setSubject(sendEmailDto.getSubject());
            helper.setText(sendEmailDto.getMessage(),false);


            if (sendEmailDto.getAttachment() != null && !sendEmailDto.getAttachment().isEmpty()) {
                DataSource dataSource = new ByteArrayDataSource(sendEmailDto.getAttachment().getInputStream(),
                        sendEmailDto.getAttachment().getContentType());
                helper.addAttachment(sendEmailDto.getAttachment().getOriginalFilename(), dataSource);
            }

            javaMailSender.send(mimeMessage);

        } catch (MessagingException e) {
            throw new RuntimeException(e.getMessage());
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
