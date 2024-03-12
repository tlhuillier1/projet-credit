package fr.miage.banque.assembler;

import fr.miage.banque.controller.LoanController;
import fr.miage.banque.domain.entity.Loan;
import fr.miage.banque.domain.entity.LoanStatus;
import org.springframework.context.annotation.Configuration;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.hateoas.server.RepresentationModelAssembler;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Configuration
public class LoanAssembler implements RepresentationModelAssembler<Loan, EntityModel<Loan>> {
    @Override
    public EntityModel<Loan> toModel(Loan loan) {
        EntityModel<Loan> loanModel = EntityModel.of(loan);
        loanModel.add(linkTo(methodOn(LoanController.class).getLoan(loan.getId())).withSelfRel());
        loanModel.add(linkTo(methodOn(LoanController.class).getLoans(null)).withRel(IanaLinkRelations.COLLECTION));
        switch (loan.getStatus()) {
            case LoanStatus.DEBUT:
                loanModel.add(linkTo(methodOn(LoanController.class).applyForLoan(loan.getId())).withRel("apply"));
                break;
            case LoanStatus.ETUDE:
                loanModel.add(linkTo(methodOn(LoanController.class).reviewLoan(loan.getId(), null, null)).withRel("review"));
                break;
        }
        return loanModel;
    }
}
