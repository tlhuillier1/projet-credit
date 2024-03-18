package fr.miage.banque.controller;

import fr.miage.banque.assembler.EventAssembler;
import fr.miage.banque.domain.entity.Credit;
import fr.miage.banque.domain.entity.Event;
import fr.miage.banque.domain.entity.LoanApplication;
import fr.miage.banque.repository.LoanRepository;
import fr.miage.banque.service.EventService;
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
@RequestMapping("/events")
@AllArgsConstructor
public class EventController {

    private final EventService eventService;
    private final LoanRepository loanRepository;
    private final EventAssembler eventAssembler;

    @GetMapping
    public ResponseEntity<CollectionModel<EntityModel<Event>>> getEvents() {
        List<Event> events = eventService.getEvents();

        if (events.isEmpty()) {
            return ResponseEntity.notFound().build();
        } else {
            CollectionModel<EntityModel<Event>> eventsModel = eventAssembler.toCollectionModel(events);
            eventsModel.add(linkTo(methodOn(EventController.class).getEvents()).withSelfRel());
            return ResponseEntity.ok(eventsModel);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<CollectionModel<EntityModel<Event>>> getEvent(@PathVariable("id") Long loanId) {
        try {
            LoanApplication loanApplication = loanRepository.findById(loanId).get();
            List<Event> events = eventService.getEventsByLoanApplication(loanApplication);
            CollectionModel<EntityModel<Event>> eventsModel = eventAssembler.toCollectionModel(events);
            eventsModel.add(linkTo(methodOn(EventController.class).getEvents()).withSelfRel());
            return ResponseEntity.ok(eventsModel);
        } catch (NoSuchElementException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
