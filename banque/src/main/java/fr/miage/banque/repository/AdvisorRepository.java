package fr.miage.banque.repository;

import fr.miage.banque.domain.entity.Advisor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdvisorRepository extends JpaRepository<Advisor, Long> {
}
