package fr.miage.banque.domain.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.Data;

import java.util.List;

@Entity
@Data
public class Client {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;

    public String firstName;

    public String lastName;

    @Email
    public String email;

    public String address;

    public String job;

    public double salary3years;

    @OneToMany(mappedBy = "client")
    public List<Loan> loans;

}
