package fr.miage.banque.controller;

import fr.miage.banque.assembler.LoanAssembler;
import fr.miage.banque.domain.dto.LoanRequestDTO;
import fr.miage.banque.domain.entity.BankJob;
import fr.miage.banque.domain.entity.Client;
import fr.miage.banque.domain.entity.LoanApplication;
import fr.miage.banque.domain.entity.Worker;
import fr.miage.banque.repository.ClientRepository;
import fr.miage.banque.repository.WorkerRepository;
import fr.miage.banque.service.CreditService;
import fr.miage.banque.service.EventService;
import fr.miage.banque.service.LoanService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import lombok.AllArgsConstructor;
import org.springframework.data.repository.query.Param;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

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
    private final WorkerRepository workerRepository;
    private final EventService eventService;
    private final CreditService creditService;
    private final LoanAssembler loanAssembler;
    protected RestTemplate restTemplate;


    @PostMapping
    public ResponseEntity<EntityModel<LoanApplication>> createLoan(@RequestBody LoanRequestDTO loanRequestDTO) {
        try {
            Client client = clientRepository.findById(loanRequestDTO.getClientId()).orElseThrow(() -> new NoSuchElementException("Client not found"));
            LoanApplication requestDTOLoanApplication = loanRequestDTO.getLoan();
            requestDTOLoanApplication.setClient(client);
            LoanApplication loanApplication = loanService.createLoan(requestDTOLoanApplication);

            eventService.createEvent(loanApplication);

            EntityModel<LoanApplication> loanModel = loanAssembler.toModel(loanApplication);
            return ResponseEntity.created(linkTo(methodOn(LoanController.class)
                    .getLoan(loanApplication.getId())).toUri())
                    .body(loanModel);
        } catch (NoSuchElementException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping
    public ResponseEntity<CollectionModel<EntityModel<LoanApplication>>> getLoans(@Param("status") String status) {
        List<LoanApplication> loanApplications = loanService.getLoans(status);
        if (loanApplications.isEmpty()) {
            return ResponseEntity.noContent().build();
        } else {
            CollectionModel<EntityModel<LoanApplication>> collectionModel = loanAssembler.toCollectionModel(loanApplications);
            collectionModel.add(linkTo(methodOn(LoanController.class).getLoans(null)).withSelfRel());
            return ResponseEntity.ok(collectionModel);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<EntityModel<LoanApplication>> getLoan(@PathVariable("id") Long id) {
        try {
            LoanApplication loanApplication = loanService.getLoan(id);
            EntityModel<LoanApplication> loanModel = loanAssembler.toModel(loanApplication);
            return ResponseEntity.ok(loanModel);
        } catch (NoSuchElementException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<EntityModel<LoanApplication>> modifyLoan(@PathVariable("id") Long id, @RequestBody LoanApplication loanApplication) {
        try {
            LoanApplication modifiedLoanApplication = loanService.modifyLoan(id, loanApplication);
            EntityModel<LoanApplication> loanModel = loanAssembler.toModel(modifiedLoanApplication);
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
    public ResponseEntity<EntityModel<LoanApplication>> applyForLoan(@PathVariable("id") Long id) {
        try {
            LoanApplication loanApplication = loanService.applyForLoan(id);

            eventService.createEvent(loanApplication);
            EntityModel<LoanApplication> loanModel = loanAssembler.toModel(loanApplication);
            return ResponseEntity.ok(loanModel);
        } catch (NoSuchElementException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{id}/review")
    @CircuitBreaker(name = "finance", fallbackMethod = "reviewLoanFallback")
    @Retry(name = "finance")
    public ResponseEntity<EntityModel<LoanApplication>> reviewLoan(@PathVariable("id") Long id, @RequestParam String decision, @RequestParam Long advisorId) {
        try {
            Worker advisor = workerRepository.findById(advisorId).orElseThrow(() -> new NoSuchElementException("Advisor not found"));
            if (advisor.getJob() != BankJob.ADVISOR) {
                throw new IllegalArgumentException("Worker is not an advisor");
            }

            String url = "http://localhost:8180/finance/" + loanService.getLoan(id).getClient().getSalary3years();

            // Appel API Finance
            String responseFinance = restTemplate.getForObject(url, String.class);

            if (responseFinance.equals("You are not eligible for a loan")) {
                //traitement à faire
                throw new IllegalArgumentException("Client is not eligible for a loan");
            } else {
                LoanApplication loanApplication = loanService.reviewLoan(id, decision, advisor);
                eventService.createEvent(loanApplication);
                EntityModel<LoanApplication> loanModel = loanAssembler.toModel(loanApplication);
                return ResponseEntity.ok(loanModel);
            }
        } catch (NoSuchElementException e) {
            return ResponseEntity.notFound().build();
        } catch  (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    public ResponseEntity<String> reviewLoanFallback(RuntimeException re) {
        return ResponseEntity.status(503).body("Service unavailable");
    }

    @PutMapping("/{id}/validate")
    public ResponseEntity<EntityModel<LoanApplication>> validateLoan(@PathVariable("id") Long id, @RequestParam Long advisorId, @RequestParam String decision) {
        try {
            Worker advisor = workerRepository.findById(advisorId).orElseThrow(() -> new NoSuchElementException("Advisor not found"));
            if (advisor.getJob() != BankJob.CREDIT_MANAGER) {
                throw new IllegalArgumentException("Worker is not an advisor");
            }
            LoanApplication loanApplication = loanService.validateLoan(id, advisor, decision);
            eventService.createEvent(loanApplication);
            creditService.createCredit(loanApplication);
            EntityModel<LoanApplication> loanModel = loanAssembler.toModel(loanApplication);
            return ResponseEntity.ok(loanModel);
        } catch (NoSuchElementException e) {
            return ResponseEntity.notFound().build();
        } catch  (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }

}
