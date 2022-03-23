package br.utfpr.edu.vinte_um.domain;

import br.utfpr.edu.vinte_um.domain.enums.GameStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

import static br.utfpr.edu.vinte_um.domain.enums.GameStatus.*;
import static java.util.stream.Collectors.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Game {
    Double odd;
    BigDecimal bet;
    List<Byte> rolls;
    GameStatus status;

    public byte sumRolls() {
        return rolls.stream().reduce((aByte, aByte2) -> Byte.parseByte((aByte + aByte2) + "")).orElse((byte) 0);
    }

    public String allRollsAsString() {
        return rolls.stream().map(String::valueOf).collect(joining(" "));
    }

    public void superOdd() {
        this.odd = 2.5;
        this.status = OCCURRING_SUPER_ODD;
    }
}
