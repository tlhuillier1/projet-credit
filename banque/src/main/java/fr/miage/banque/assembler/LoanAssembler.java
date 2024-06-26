package fr.miage.banque.assembler;

import fr.miage.banque.controller.CreditController;
import fr.miage.banque.controller.EventController;
import fr.miage.banque.controller.LoanController;
import fr.miage.banque.domain.entity.LoanApplication;
import fr.miage.banque.domain.entity.LoanStatus;
import org.springframework.context.annotation.Configuration;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.hateoas.server.RepresentationModelAssembler;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Configuration
public class LoanAssembler implements RepresentationModelAssembler<LoanApplication, EntityModel<LoanApplication>> {
    @Override
    public EntityModel<LoanApplication> toModel(LoanApplication loanApplication) {
        EntityModel<LoanApplication> loanModel = EntityModel.of(loanApplication);
        loanModel.add(linkTo(methodOn(LoanController.class).getLoan(loanApplication.getId())).withSelfRel());
        loanModel.add(linkTo(methodOn(LoanController.class).getLoans(null)).withRel(IanaLinkRelations.COLLECTION));
        loanModel.add(linkTo(methodOn(EventController.class).getEvent(loanApplication.getId())).withRel("events"));
        switch (loanApplication.getStatus()) {
            case LoanStatus.DEBUT:
                loanModel.add(linkTo(methodOn(LoanController.class).applyForLoan(loanApplication.getId())).withRel("apply"));
                break;
            case LoanStatus.ETUDE:
                loanModel.add(linkTo(methodOn(LoanController.class).reviewLoan(loanApplication.getId(), null, null)).withRel("review"));
                break;
            case LoanStatus.ACCEPTATION:
                loanModel.add(linkTo(methodOn(LoanController.class).validateLoan(loanApplication.getId(), null, null)).withRel("validate"));
                break;
            case LoanStatus.VALIDATION:
                loanModel.add(linkTo(methodOn(CreditController.class).getCredit(null)).withRel("crédit"));
                loanModel.add(linkTo(methodOn(CreditController.class).getCredits()).withRel("crédit"));
                break;
        }
        return loanModel;
    }
}
