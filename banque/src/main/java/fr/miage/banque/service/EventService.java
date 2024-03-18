package fr.miage.banque.service;

import fr.miage.banque.domain.entity.Event;
import fr.miage.banque.domain.entity.LoanApplication;

public interface EventService {
    Event createEvent(LoanApplication loanApplication);
}
