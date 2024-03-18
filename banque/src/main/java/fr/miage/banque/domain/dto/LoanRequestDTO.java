package fr.miage.banque.domain.dto;

import fr.miage.banque.domain.entity.LoanApplication;

public class LoanRequestDTO {
    public LoanApplication loanApplication;
    public Long clientId;

    public LoanRequestDTO(LoanApplication loanApplication, Long clientId) {
        this.loanApplication = loanApplication;
        this.clientId = clientId;
    }

    public LoanApplication getLoan() {
        return loanApplication;
    }

    public void setLoan(LoanApplication loanApplication) {
        this.loanApplication = loanApplication;
    }

    public Long getClientId() {
        return clientId;
    }

    public void setClientId(Long clientId) {
        this.clientId = clientId;
    }
}
