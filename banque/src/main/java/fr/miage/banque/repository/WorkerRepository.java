package fr.miage.banque.repository;

import fr.miage.banque.domain.entity.Worker;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WorkerRepository extends JpaRepository<Worker, Long> {
}
