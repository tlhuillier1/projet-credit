package fr.miage.banque.controller;

import fr.miage.banque.assembler.CreditAssembler;
import fr.miage.banque.domain.entity.Credit;
import fr.miage.banque.service.CreditService;
import jakarta.websocket.server.PathParam;
import lombok.AllArgsConstructor;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.NoSuchElementException;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/credits")
@AllArgsConstructor
public class CreditController {
    private final CreditService creditService;
    private final CreditAssembler creditAssembler;

    @GetMapping
    public ResponseEntity<CollectionModel<EntityModel<Credit>>> getCredits() {
        List<Credit> credits = creditService.getCredits();

        if (credits.isEmpty()) {
            return ResponseEntity.notFound().build();
        } else {
            CollectionModel<EntityModel<Credit>> creditsModel = creditAssembler.toCollectionModel(credits);
            creditsModel.add(linkTo(methodOn(CreditController.class).getCredits()).withSelfRel());
            return ResponseEntity.ok(creditsModel);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<EntityModel<Credit>> getCredit(@PathVariable("id") Long id) {
        try {
            Credit credit = creditService.getCredit(id);
            EntityModel<Credit> creditModel = creditAssembler.toModel(credit);
            return ResponseEntity.ok(creditModel);
        } catch (NoSuchElementException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
