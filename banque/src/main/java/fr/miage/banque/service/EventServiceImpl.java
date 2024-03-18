package fr.miage.banque.service;

import fr.miage.banque.domain.entity.Event;
import fr.miage.banque.domain.entity.LoanApplication;
import fr.miage.banque.repository.EventRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class EventServiceImpl implements EventService {
    private final EventRepository eventRepository;

    @Override
    public Event createEvent(LoanApplication loanApplication) {
        Event event = new Event(loanApplication);
        return eventRepository.save(event);
    }
}
