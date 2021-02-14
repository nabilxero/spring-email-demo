package com.practice.springemaildemo;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.Email;
import javax.xml.bind.ValidationException;

@RestController
@RequestMapping("/feedback")
public class FeedbackController {

    private EmailConfig emailConfig;

    public FeedbackController(EmailConfig emailConfig) {
        this.emailConfig = emailConfig;
    }

    @PostMapping
    public void sendFeedback(@RequestBody Feedback feedback, BindingResult bindingResult) throws ValidationException {
        if(bindingResult.hasErrors()){
            throw new ValidationException("Feedback is not valid");
        }

//        Create mail sender
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setHost(this.emailConfig.getHost());
        mailSender.setPort((this.emailConfig.getPort()));
        mailSender.setUsername(this.emailConfig.getUsername());
        mailSender.setPassword(this.emailConfig.getPassword());

//        Create an email instance
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setFrom(feedback.getEmail());
        mailMessage.setTo("demo@gmail.com");
        mailMessage.setSubject("New feedback from "+feedback.getName());
        mailMessage.setText(feedback.getFeedback());

//        Send mail
        mailSender.send(mailMessage);;

    }
}
