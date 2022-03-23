package br.utfpr.edu.vinte_um.service;

import java.math.BigDecimal;

public interface BankService {
    void initialize(BigDecimal value);
    void finish();
}
