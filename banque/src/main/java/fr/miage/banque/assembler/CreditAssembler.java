package fr.miage.banque.assembler;

import fr.miage.banque.controller.CreditController;
import fr.miage.banque.domain.entity.Credit;
import fr.miage.banque.domain.entity.LoanApplication;
import org.springframework.context.annotation.Configuration;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.hateoas.server.RepresentationModelAssembler;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Configuration

public class CreditAssembler implements RepresentationModelAssembler<Credit, EntityModel<Credit>> {
    @Override
    public EntityModel<Credit> toModel(Credit credit) {
        EntityModel<Credit> creditModel = EntityModel.of(credit);
        creditModel.add(linkTo(methodOn(CreditController.class).getCredit(credit.getId())).withSelfRel());
        creditModel.add(linkTo(methodOn(CreditController.class).getCredits()).withRel(IanaLinkRelations.COLLECTION));
        return creditModel;
    }
}
