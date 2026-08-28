package com.ankit.portfolio.controller;

import com.ankit.portfolio.dto.ContactRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@RestController
@RequestMapping("/api/contact")
@CrossOrigin(origins = {"http://localhost:5173", "http://127.0.0.1:5173"})
public class ContactController {

    @Autowired
    private JavaMailSender mailSender;

    @Value("${portfolio.contact.to-email:ankitshukla9135@gmail.com}")
    private String toEmail;

    /**
     * POST /api/contact
     * Receives form submission, sends an email notification to Ankit.
     */
    @PostMapping
    public ResponseEntity<String> handleContact(@Valid @RequestBody ContactRequest req) {

        // ── 1. Send notification email to Ankit ──────────────────────
        SimpleMailMessage notification = new SimpleMailMessage();
        notification.setTo(toEmail);
        notification.setSubject("Portfolio Contact: " + req.getName());
        notification.setText(
            "New contact form submission received on " +
            LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd MMM yyyy, hh:mm a")) +
            "\n\n" +
            "──────────────────────────\n" +
            "Name    : " + req.getName()    + "\n" +
            "Email   : " + req.getEmail()   + "\n" +
            "Message : " + req.getMessage() + "\n" +
            "──────────────────────────\n\n" +
            "Reply directly to: " + req.getEmail()
        );
        notification.setReplyTo(req.getEmail());
        mailSender.send(notification);

        // ── 2. Send auto-reply to the sender ─────────────────────────
        SimpleMailMessage autoReply = new SimpleMailMessage();
        autoReply.setTo(req.getEmail());
        autoReply.setSubject("Thanks for reaching out, " + req.getName() + "!");
        autoReply.setText(
            "Hi " + req.getName() + ",\n\n" +
            "Thanks for getting in touch! I've received your message and will reply within 24 hours.\n\n" +
            "Your message:\n\"" + req.getMessage() + "\"\n\n" +
            "──────────────────────────\n" +
            "Ankit Shukla\n" +
            "Java Developer | B.Tech ECE\n" +
            "ankitshukla9135@gmail.com\n" +
            "github.com/Ankit-Shukla0"
        );
        mailSender.send(autoReply);

        return ResponseEntity.ok("Message sent successfully");
    }
}
