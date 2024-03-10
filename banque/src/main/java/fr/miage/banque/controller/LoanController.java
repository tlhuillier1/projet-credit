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
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/loans")
@AllArgsConstructor
public class LoanController {
    private final LoanService loanService;
    private static final Logger logger = LoggerFactory.getLogger(LoanService.class);

    private final ClientRepository clientRepository;

    @PostMapping("/create")
    public ResponseEntity<Loan> createLoan(@RequestBody LoanRequestDTO loanRequestDTO) {
        Client client = clientRepository.findById(loanRequestDTO.getClientId()).orElseThrow(() -> new RuntimeException("Client not found"));
        Loan requestDTOLoan = loanRequestDTO.getLoan();
        requestDTOLoan.setClient(client);
        Loan loan = loanService.createLoan(requestDTOLoan);
        loan.add(linkTo(methodOn(LoanController.class).getLoan(loan.getId())).withSelfRel());
        loan.add(linkTo(methodOn(LoanController.class).getLoans()).withRel(IanaLinkRelations.COLLECTION));

        return ResponseEntity.created(linkTo(methodOn(LoanController.class).getLoan(loan.getId())).toUri()).body(loan);
    }

    @GetMapping
    public ResponseEntity<CollectionModel<Loan>> getLoans() {
        List<Loan> loans = loanService.getLoans();
        for (Loan loan : loans) {
            loan.add(linkTo(methodOn(LoanController.class).getLoan(loan.getId())).withSelfRel());
            loan.add(linkTo(methodOn(LoanController.class).getLoans()).withRel(IanaLinkRelations.COLLECTION));
        }

        return ResponseEntity.ok(CollectionModel.of(loans, linkTo(methodOn(LoanController.class).getLoans()).withSelfRel()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Loan> getLoan(@PathVariable("id") Long id) {
        Loan loan = loanService.getLoan(id);
        loan.add(linkTo(methodOn(LoanController.class).getLoan(id)).withSelfRel());
        loan.add(linkTo(methodOn(LoanController.class).getLoans()).withRel(IanaLinkRelations.COLLECTION));
        return ResponseEntity.ok(loan);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Loan> modifyLoan(@PathVariable("id") Long id, @RequestBody Loan loan) {
        Loan modifiedLoan = loanService.modifyLoan(id, loan);
        modifiedLoan.add(linkTo(methodOn(LoanController.class).getLoan(id)).withSelfRel());
        modifiedLoan.add(linkTo(methodOn(LoanController.class).getLoans()).withRel(IanaLinkRelations.COLLECTION));
        return ResponseEntity.ok(modifiedLoan);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteLoan(@PathVariable("id") Long id) {
        return ResponseEntity.ok(loanService.deleteLoan(id));
    }

    @GetMapping("/status/{id}")
    public ResponseEntity<String> getStatus(@PathVariable("id") Long id) {
        return ResponseEntity.ok(loanService.getStatus(id));
    }
}
