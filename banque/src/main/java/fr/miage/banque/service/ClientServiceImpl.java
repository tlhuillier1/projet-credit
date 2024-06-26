package fr.miage.banque.service;

import fr.miage.banque.domain.entity.Client;
import fr.miage.banque.repository.ClientRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
@AllArgsConstructor
public class ClientServiceImpl implements ClientService {

    private final ClientRepository clientRepository;

    @Override
    public Client createClient(Client client) {
        return clientRepository.save(client);
    }

    @Override
    public List<Client> getClients() {
        return clientRepository.findAll();
    }

    @Override
    public Client getClient(Long id) {
        return clientRepository.findById(id).orElseThrow(() -> new NoSuchElementException("Client not found"));
    }

    @Override
    public Client modifyClient(Long id, Client client) {
        return clientRepository.findById(id)
                .map(c -> {
                    c.setFirstname(client.getFirstname());
                    c.setLastname(client.getLastname());
                    c.setEmail(client.getEmail());
                    c.setAddress(client.getAddress());
                    c.setJob(client.getJob());
                    c.setSalary3years(client.getSalary3years());
                    return clientRepository.save(c);
                })
                .orElseThrow(() -> new NoSuchElementException("Client not found"));
    }

    @Override
    public String deleteClient(Long id) {
        clientRepository.deleteById(id);
        return "Client deleted";
    }
}
