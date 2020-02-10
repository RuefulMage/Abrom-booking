package ru.kpfu.itis.Utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class MailSender {

    private JavaMailSender emailSender;

    @Autowired
    public MailSender(JavaMailSender emailSender) {
        this.emailSender = emailSender;
    }

    public void sendMail(String[] emails, String text, String subject){
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(emails);
        message.setSubject(subject);
        message.setText(text);
        this.emailSender.send(message);
    }

    public void sendMail(String email, String text, String subject){
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(email);
        message.setSubject(subject);
        message.setText(text);
        this.emailSender.send(message);
    }
}
