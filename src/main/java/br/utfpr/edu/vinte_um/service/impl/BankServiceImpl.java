package br.utfpr.edu.vinte_um.service.impl;

import br.utfpr.edu.vinte_um.domain.Bank;
import br.utfpr.edu.vinte_um.exception.BankException;
import br.utfpr.edu.vinte_um.provider.TwentyOneProvider;
import br.utfpr.edu.vinte_um.service.BankService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

import static java.util.Objects.*;
import static org.springframework.http.HttpStatus.*;

@Service
@RequiredArgsConstructor
public class BankServiceImpl implements BankService {

    private final TwentyOneProvider provider;

    @Override
    public void initialize(BigDecimal value) {
        if(nonNull(provider.bank))
            throw new BankException("Já possuí banca aberta", NOT_ACCEPTABLE);
        provider.bank = new Bank(value);
    }

    @Override
    public void finish() {

    }
}
