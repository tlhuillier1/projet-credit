package fr.miage.banque.controller;

import fr.miage.banque.domain.dto.LoanRequestDTO;
import fr.miage.banque.domain.entity.Client;
import fr.miage.banque.domain.entity.Loan;
import fr.miage.banque.repository.ClientRepository;
import fr.miage.banque.service.LoanService;
import lombok.AllArgsConstructor;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/loans")
@AllArgsConstructor
public class LoanController {
    private final LoanService loanService;
    private final ClientRepository clientRepository;

    @PostMapping("/create")
    public ResponseEntity<Loan> createLoan(@RequestBody LoanRequestDTO loanRequestDTO) {
        try {
            Client client = clientRepository.findById(loanRequestDTO.getClientId()).orElseThrow(() -> new NoSuchElementException("Client not found"));
            Loan requestDTOLoan = loanRequestDTO.getLoan();
            requestDTOLoan.setClient(client);
            Loan loan = loanService.createLoan(requestDTOLoan);
            loan.add(linkTo(methodOn(LoanController.class).getLoan(loan.getId())).withSelfRel());
            loan.add(linkTo(methodOn(LoanController.class).getLoans()).withRel(IanaLinkRelations.COLLECTION));

            return ResponseEntity.created(linkTo(methodOn(LoanController.class).getLoan(loan.getId())).toUri()).body(loan);
        } catch (NoSuchElementException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping
    public ResponseEntity<CollectionModel<Loan>> getLoans() {
        List<Loan> loans = loanService.getLoans();
        if (loans.isEmpty()) {
            return ResponseEntity.noContent().build();
        } else {
            for (Loan loan : loans) {
                loan.add(linkTo(methodOn(LoanController.class).getLoan(loan.getId())).withSelfRel());
                loan.add(linkTo(methodOn(LoanController.class).getLoans()).withRel(IanaLinkRelations.COLLECTION));
            }

            return ResponseEntity.ok(CollectionModel.of(loans, linkTo(methodOn(LoanController.class).getLoans()).withSelfRel()));
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Loan> getLoan(@PathVariable("id") Long id) {
        try {
            Loan loan = loanService.getLoan(id);
            loan.add(linkTo(methodOn(LoanController.class).getLoan(id)).withSelfRel());
            loan.add(linkTo(methodOn(LoanController.class).getLoans()).withRel(IanaLinkRelations.COLLECTION));
            return ResponseEntity.ok(loan);
        } catch (NoSuchElementException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Loan> modifyLoan(@PathVariable("id") Long id, @RequestBody Loan loan) {
        try {
            Loan modifiedLoan = loanService.modifyLoan(id, loan);
            modifiedLoan.add(linkTo(methodOn(LoanController.class).getLoan(id)).withSelfRel());
            modifiedLoan.add(linkTo(methodOn(LoanController.class).getLoans()).withRel(IanaLinkRelations.COLLECTION));
            return ResponseEntity.ok(modifiedLoan);
        } catch (NoSuchElementException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteLoan(@PathVariable("id") Long id) {
        return ResponseEntity.ok(loanService.deleteLoan(id));
    }

    @GetMapping("/status/{id}")
    public ResponseEntity<String> getStatus(@PathVariable("id") Long id) {
        try {
            return ResponseEntity.ok(loanService.getStatus(id));
        } catch (NoSuchElementException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
