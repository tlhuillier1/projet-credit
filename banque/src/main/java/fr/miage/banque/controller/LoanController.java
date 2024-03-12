package fr.miage.banque.controller;

import fr.miage.banque.assembler.LoanAssembler;
import fr.miage.banque.domain.dto.LoanRequestDTO;
import fr.miage.banque.domain.entity.Advisor;
import fr.miage.banque.domain.entity.Client;
import fr.miage.banque.domain.entity.Loan;
import fr.miage.banque.repository.ClientRepository;
import fr.miage.banque.repository.AdvisorRepository;
import fr.miage.banque.service.EventService;
import fr.miage.banque.service.LoanService;
import lombok.AllArgsConstructor;
import org.springframework.data.repository.query.Param;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
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
    private final AdvisorRepository advisorRepository;
    private final EventService eventService;
    private final LoanAssembler loanAssembler;

    @PostMapping
    public ResponseEntity<EntityModel<Loan>> createLoan(@RequestBody LoanRequestDTO loanRequestDTO) {
        try {
            Client client = clientRepository.findById(loanRequestDTO.getClientId()).orElseThrow(() -> new NoSuchElementException("Client not found"));
            Loan requestDTOLoan = loanRequestDTO.getLoan();
            requestDTOLoan.setClient(client);
            Loan loan = loanService.createLoan(requestDTOLoan);

            eventService.createEvent(loan);

            EntityModel<Loan> loanModel = loanAssembler.toModel(loan);
            return ResponseEntity.created(linkTo(methodOn(LoanController.class)
                    .getLoan(loan.getId())).toUri())
                    .body(loanModel);
        } catch (NoSuchElementException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping
    public ResponseEntity<CollectionModel<EntityModel<Loan>>> getLoans(@Param("status") String status) {
        List<Loan> loans = loanService.getLoans(status);
        if (loans.isEmpty()) {
            return ResponseEntity.noContent().build();
        } else {
            CollectionModel<EntityModel<Loan>> collectionModel = loanAssembler.toCollectionModel(loans);
            collectionModel.add(linkTo(methodOn(LoanController.class).getLoans(null)).withSelfRel());
            return ResponseEntity.ok(collectionModel);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<EntityModel<Loan>> getLoan(@PathVariable("id") Long id) {
        try {
            Loan loan = loanService.getLoan(id);
            EntityModel<Loan> loanModel = loanAssembler.toModel(loan);
            return ResponseEntity.ok(loanModel);
        } catch (NoSuchElementException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<EntityModel<Loan>> modifyLoan(@PathVariable("id") Long id, @RequestBody Loan loan) {
        try {
            Loan modifiedLoan = loanService.modifyLoan(id, loan);
            EntityModel<Loan> loanModel = loanAssembler.toModel(modifiedLoan);
            return ResponseEntity.ok(loanModel);
        } catch (NoSuchElementException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteLoan(@PathVariable("id") Long id) {
        return ResponseEntity.ok(loanService.deleteLoan(id));
    }

    @GetMapping("/{id}/status")
    public ResponseEntity<String> getStatus(@PathVariable("id") Long id) {
        try {
            return ResponseEntity.ok(loanService.getStatus(id));
        } catch (NoSuchElementException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{id}/apply")
    public ResponseEntity<EntityModel<Loan>> applyForLoan(@PathVariable("id") Long id) {
        try {
            Loan loan = loanService.applyForLoan(id);

            eventService.createEvent(loan);
            EntityModel<Loan> loanModel = loanAssembler.toModel(loan);
            return ResponseEntity.ok(loanModel);
        } catch (NoSuchElementException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{id}/review")
    public ResponseEntity<EntityModel<Loan>> reviewLoan(@PathVariable("id") Long id, @RequestParam String decision, @RequestParam Long advisorId) {
        try {
            Advisor advisor = advisorRepository.findById(advisorId).orElseThrow(() -> new NoSuchElementException("Advisor not found"));
            Loan loan = loanService.reviewLoan(id, decision, advisor);
            eventService.createEvent(loan);
            EntityModel<Loan> loanModel = loanAssembler.toModel(loan);
            return ResponseEntity.ok(loanModel);
        } catch (NoSuchElementException e) {
            return ResponseEntity.notFound().build();
        } catch  (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }

}
