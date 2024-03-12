package fr.miage.banque.controller;

import fr.miage.banque.assembler.ClientAssembler;
import fr.miage.banque.domain.entity.Client;
import fr.miage.banque.service.ClientService;
import lombok.AllArgsConstructor;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/clients")
@AllArgsConstructor
public class ClientController {
    private final ClientService clientService;
    private ClientAssembler assembler;

    @PostMapping
    public ResponseEntity<EntityModel<Client>> createClient(@RequestBody Client client) {
        Client newClient = clientService.createClient(client);
        EntityModel<Client> clientModel = assembler.toModel(newClient);
        return ResponseEntity.created(linkTo(methodOn(ClientController.class)
                .getClient(newClient.getId())).toUri())
                .body(clientModel);
    }

    // problème avec HATEOAS
    @GetMapping
    public ResponseEntity<CollectionModel<Client>> getClients() {
        List<Client> clients = clientService.getClients();
        if (clients.isEmpty()) {
            return ResponseEntity.noContent().build();
        } else {
            clients.stream().map(assembler::toModel).collect(Collectors.toList());
            CollectionModel<Client> model = CollectionModel.of(clients);
            model.add(linkTo(methodOn(ClientController.class).getClients()).withSelfRel());
            return ResponseEntity.ok(model);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<EntityModel<Client>> getClient(@PathVariable("id") Long id) {
        try {
            Client client = clientService.getClient(id);
            EntityModel<Client> clientModel = assembler.toModel(client);
            return ResponseEntity.ok(clientModel);
        } catch (NoSuchElementException e) {
            return ResponseEntity.notFound().build();
        }

    }

    @PutMapping("/{id}")
    public ResponseEntity<EntityModel<Client>> modifyClient(@PathVariable("id") Long id, @RequestBody Client client) {
        try {
            Client modifiedClient = clientService.modifyClient(id, client);
            EntityModel<Client> clientModel = assembler.toModel(modifiedClient);
            return ResponseEntity.ok(clientModel);
        } catch (NoSuchElementException e) {
            return ResponseEntity.notFound().build();
        }

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteClient(@PathVariable("id") Long id) {
        return ResponseEntity.ok(clientService.deleteClient(id));
    }
}
