package br.utfpr.edu.vinte_um.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
public class Bank {
    private BigDecimal currentValue;
}
