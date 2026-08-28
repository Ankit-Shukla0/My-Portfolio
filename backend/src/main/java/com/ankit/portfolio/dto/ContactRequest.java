package com.ankit.portfolio.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class ContactRequest {

    @NotBlank(message = "Name is required")
    @Size(max = 120, message = "Name too long")
    private String name;

    @NotBlank(message = "Email is required")
    @Email(message = "Invalid email address")
    private String email;

    @NotBlank(message = "Message is required")
    @Size(max = 2000, message = "Message too long")
    private String message;

    // Getters & Setters
    public String getName()    { return name; }
    public void setName(String name) { this.name = name; }

    public String getEmail()   { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }
}
