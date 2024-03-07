package fr.miage.banque.domain.dto;

import fr.miage.banque.domain.entity.Loan;

public class LoanRequestDTO {
    public Loan loan;
    public Long clientId;

    public LoanRequestDTO(Loan loan, Long clientId) {
        this.loan = loan;
        this.clientId = clientId;
    }

    public Loan getLoan() {
        return loan;
    }

    public void setLoan(Loan loan) {
        this.loan = loan;
    }

    public Long getClientId() {
        return clientId;
    }

    public void setClientId(Long clientId) {
        this.clientId = clientId;
    }
}
