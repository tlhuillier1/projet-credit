package fr.miage.banque.service;

import fr.miage.banque.domain.entity.Event;
import fr.miage.banque.domain.entity.Loan;
import fr.miage.banque.repository.EventRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class EventServiceImpl implements EventService {
    private final EventRepository eventRepository;

    @Override
    public Event createEvent(Loan loan) {
        Event event = new Event(loan);
        return eventRepository.save(event);
    }
}
