package alsofthome.be.controller;

import alsofthome.be.entities.Inscriere;
import alsofthome.be.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:3000")
public class InscriereController {

    private final EmailService emailService;

    public InscriereController(EmailService emailService) {
        this.emailService = emailService;
    }

    @PostMapping("/inscriere")
    public ResponseEntity<?> primesteInscriere(@RequestBody Inscriere inscriere) {
        try {
            emailService.trimiteEmail(inscriere);
            return ResponseEntity.ok("Trimis cu succes!");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("{\"message\":\"" + e.getMessage() + "\"}");
        }
    }
}