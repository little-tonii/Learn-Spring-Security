package spring.security.learn.controllers;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ContactController {
    
    @PostMapping("/contact")
    public String saveContactInquiryDetails() {
        return "Inquiry details are saved to the DB.";
    }
}
