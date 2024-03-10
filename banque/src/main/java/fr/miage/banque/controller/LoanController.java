package fr.miage.banque.controller;

import com.fasterxml.jackson.annotation.JsonView;
import fr.miage.banque.domain.dto.LoanRequestDTO;
import fr.miage.banque.domain.entity.Client;
import fr.miage.banque.domain.entity.Loan;
import fr.miage.banque.repository.ClientRepository;
import fr.miage.banque.service.LoanService;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/loans")
@AllArgsConstructor
public class LoanController {
    private final LoanService loanService;
    private static final Logger logger = LoggerFactory.getLogger(LoanService.class);

    private final ClientRepository clientRepository;

    @PostMapping("/create")
    public Loan createLoan(@RequestBody LoanRequestDTO loanRequestDTO) {
        logger.info("Loan: " + loanRequestDTO.toString());
        Client client = clientRepository.findById(loanRequestDTO.getClientId()).orElseThrow(() -> new RuntimeException("Client not found"));
        Loan loan = loanRequestDTO.getLoan();
        loan.setClient(client);
        return loanService.createLoan(loan);
    }

    @GetMapping
    public List<Loan> getLoans() {
        logger.info("Getting all loans");
        return loanService.getLoans();
    }

    @GetMapping("/{id}")
    public Loan getLoan(@PathVariable("id") Long id) {
        logger.info("Getting loan with id: " + id);
        return loanService.getLoan(id);
    }

    @PutMapping("/update/{id}")
    public Loan modifyLoan(@PathVariable("id") Long id, @RequestBody Loan loan) {
        return loanService.modifyLoan(id, loan);
    }

    @DeleteMapping("/delete/{id}")
    public String deleteLoan(@PathVariable("id") Long id) {
        return loanService.deleteLoan(id);
    }

    @GetMapping("/status/{id}")
    public String getStatus(@PathVariable("id") Long id) {
        return loanService.getStatus(id);
    }
}
