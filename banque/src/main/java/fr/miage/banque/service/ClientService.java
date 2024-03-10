package fr.miage.banque.service;

import fr.miage.banque.domain.entity.Client;

import java.util.List;

public interface ClientService {
    Client createClient(Client client);

    List<Client> getClients();

    Client getClient(Long id);

    Client modifyClient(Long id, Client client);

    String deleteClient(Long id);
}
