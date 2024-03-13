package fr.miage.finance.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/finance")
@AllArgsConstructor
public class ApiFinanceController {
    @GetMapping("/{income}")
    public ResponseEntity<String> getFinance(@PathVariable Double income) {
        if (income == null) {
            return ResponseEntity.badRequest().build();
        } else if (income > 50000) {
            return ResponseEntity.ok("You are eligible for a loan");
        } else {
            return ResponseEntity.ok("You are not eligible for a loan");
        }
    }
}
