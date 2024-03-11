package fr.miage.banque.controller;

import com.fasterxml.jackson.annotation.JsonView;
import fr.miage.banque.domain.entity.Client;
import fr.miage.banque.service.ClientService;
import lombok.AllArgsConstructor;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/clients")
@AllArgsConstructor
public class ClientController {
    private final ClientService clientService;

    @PostMapping("/create")
    public ResponseEntity<Client> createClient(@RequestBody Client client) {
        Client newClient = clientService.createClient(client);
        newClient.add(linkTo(methodOn(ClientController.class).getClient(newClient.getId())).withSelfRel());
        newClient.add(linkTo(methodOn(ClientController.class).getClients()).withRel(IanaLinkRelations.COLLECTION));
        client.add(linkTo(methodOn(LoanController.class).createLoan(null)).withRel("loans"));

        return ResponseEntity.created(linkTo(methodOn(ClientController.class).getClient(newClient.getId())).toUri()).body(newClient);
    }

    @GetMapping
    public ResponseEntity<CollectionModel<Client>> getClients() {
        List<Client> clients = clientService.getClients();
        if (clients.isEmpty()) {
            return ResponseEntity.noContent().build();
        } else {
            for (Client client : clients) {
                client.add(linkTo(methodOn(ClientController.class).getClient(client.getId())).withSelfRel());
                client.add(linkTo(methodOn(ClientController.class).getClients()).withRel(IanaLinkRelations.COLLECTION));
            }

            return ResponseEntity.ok(CollectionModel.of(clients, linkTo(methodOn(ClientController.class).getClients()).withSelfRel()));
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Client> getClient(@PathVariable("id") Long id) {
        try {
            Client client = clientService.getClient(id);
            client.add(linkTo(methodOn(ClientController.class).getClient(id)).withSelfRel());
            client.add(linkTo(methodOn(ClientController.class).getClients()).withRel(IanaLinkRelations.COLLECTION));
            client.add(linkTo(methodOn(LoanController.class).createLoan(null)).withRel("loans"));
            return ResponseEntity.ok(client);
        } catch (NoSuchElementException e) {
            return ResponseEntity.notFound().build();
        }

    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Client> modifyClient(@PathVariable("id") Long id, @RequestBody Client client) {
        try {
            Client modifiedClient = clientService.modifyClient(id, client);
            modifiedClient.add(linkTo(methodOn(ClientController.class).getClient(id)).withSelfRel());
            modifiedClient.add(linkTo(methodOn(ClientController.class).getClients()).withRel(IanaLinkRelations.COLLECTION));
            client.add(linkTo(methodOn(LoanController.class).createLoan(null)).withRel("loans"));
            return ResponseEntity.ok(modifiedClient);
        } catch (NoSuchElementException e) {
            return ResponseEntity.notFound().build();
        }

    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteClient(@PathVariable("id") Long id) {
        return ResponseEntity.ok(clientService.deleteClient(id));
    }
}
