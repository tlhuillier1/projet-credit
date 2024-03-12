package fr.miage.banque.assembler;

import fr.miage.banque.controller.ClientController;
import fr.miage.banque.controller.LoanController;
import fr.miage.banque.domain.entity.Client;
import org.springframework.context.annotation.Configuration;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.hateoas.server.RepresentationModelAssembler;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Configuration
public class ClientAssembler implements RepresentationModelAssembler<Client, EntityModel<Client>> {
    @Override
    public EntityModel<Client> toModel(Client client) {
        EntityModel<Client> clientModel = EntityModel.of(client);
        clientModel.add(linkTo(methodOn(ClientController.class).getClient(client.getId())).withSelfRel());
        clientModel.add(linkTo(methodOn(ClientController.class).getClients()).withRel(IanaLinkRelations.COLLECTION));
        clientModel.add(linkTo(methodOn(LoanController.class).createLoan(null)).withRel("loans"));
        return clientModel;
    }
}
