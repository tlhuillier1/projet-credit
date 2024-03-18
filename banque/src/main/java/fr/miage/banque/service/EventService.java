package fr.miage.banque.service;

import fr.miage.banque.domain.entity.Event;
import fr.miage.banque.domain.entity.LoanApplication;

import java.util.List;

public interface EventService {
    Event createEvent(LoanApplication loanApplication);

    List<Event> getEvents();

    List<Event> getEventsByLoanApplication(LoanApplication loanApplication);
}
