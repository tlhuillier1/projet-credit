package fr.miage.banque.assembler;

import fr.miage.banque.controller.EventController;
import fr.miage.banque.domain.entity.Credit;
import fr.miage.banque.domain.entity.Event;
import org.springframework.context.annotation.Configuration;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.hateoas.server.RepresentationModelAssembler;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Configuration
public class EventAssembler implements RepresentationModelAssembler<Event, EntityModel<Event>> {
    @Override
    public EntityModel<Event> toModel(Event event) {
        EntityModel<Event> eventModel = EntityModel.of(event);
        eventModel.add(linkTo(methodOn(EventController.class).getEvent(event.getLoanApplication().getId())).withSelfRel());
        eventModel.add(linkTo(methodOn(EventController.class).getEvents()).withRel(IanaLinkRelations.COLLECTION));
        return eventModel;
    }
}
