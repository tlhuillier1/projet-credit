package fr.miage.banque.controller;

import com.fasterxml.jackson.annotation.JsonView;
import fr.miage.banque.domain.entity.Client;
import fr.miage.banque.service.ClientService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/clients")
@AllArgsConstructor
public class ClientController {
    private final ClientService clientService;

    @PostMapping("/create")
    public Client createClient(@RequestBody Client client) {
        return clientService.createClient(client);
    }

    @GetMapping
    public List<Client> getClients() {
        return clientService.getClients();
    }

    @PutMapping("/update/{id}")
    public Client modifyClient(@PathVariable("id") Long id, @RequestBody Client client) {
        return clientService.modifyClient(id, client);
    }

    @DeleteMapping("/delete/{id}")
    public String deleteClient(@PathVariable("id") Long id) {
        return clientService.deleteClient(id);
    }
}
