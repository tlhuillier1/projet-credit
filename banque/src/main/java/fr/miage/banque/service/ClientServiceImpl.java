package fr.miage.banque.service;

import fr.miage.banque.domain.entity.Client;
import fr.miage.banque.repository.ClientRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

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
        return clientRepository.findById(id).orElseThrow(() -> new RuntimeException("Client not found"));
    }

    @Override
    public Client modifyClient(Long id, Client client) {
        return clientRepository.findById(id)
                .map(c -> {
                    c.setFirstName(client.getFirstName());
                    c.setLastName(client.getLastName());
                    c.setEmail(client.getEmail());
                    c.setAddress(client.getAddress());
                    c.setJob(client.getJob());
                    c.setSalary3years(client.getSalary3years());
                    return clientRepository.save(c);
                })
                .orElseThrow(() -> new RuntimeException("Client not found"));
    }

    @Override
    public String deleteClient(Long id) {
        clientRepository.deleteById(id);
        return "Client deleted";
    }
}
