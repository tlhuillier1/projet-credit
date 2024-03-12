package fr.miage.banque.service;

import fr.miage.banque.domain.entity.Event;
import fr.miage.banque.domain.entity.Loan;

public interface EventService {
    Event createEvent(Loan loan);
}
