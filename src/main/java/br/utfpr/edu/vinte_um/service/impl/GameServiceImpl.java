package br.utfpr.edu.vinte_um.service.impl;

import br.utfpr.edu.vinte_um.domain.Game;
import br.utfpr.edu.vinte_um.exception.GameException;
import br.utfpr.edu.vinte_um.provider.TwentyOneProvider;
import br.utfpr.edu.vinte_um.service.GameService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;

import static br.utfpr.edu.vinte_um.domain.enums.GameStatus.*;
import static java.util.Objects.*;
import static org.springframework.http.HttpStatus.*;

@Service
@RequiredArgsConstructor
@Slf4j
public class GameServiceImpl implements GameService {

    private final TwentyOneProvider provider;

    @Override
    public void newGame(BigDecimal bet) {
        log.info("Criando novo jogo...");
        if(isNull(provider.bank))
            throw new GameException("Precisa abrir uma banca", NOT_ACCEPTABLE);
        if(nonNull(provider.game) && provider.game.getStatus().equals(OCCURRING))
            throw new GameException("Já possuí um jogo", NOT_ACCEPTABLE);
        if(provider.bank.getCurrentValue().compareTo(bet) < 0)
            throw new GameException("Não possui esse dinheiro na banca", NOT_ACCEPTABLE);
        initializeGameWithOneRoll(bet);
    }

    @Override
    public void continuePlaying() {
        if(isNull(provider.game))
            throw new GameException("Inicie seu primeiro jogo", NOT_ACCEPTABLE);
        if(provider.game.getStatus().equals(FINISHED))
            throw new GameException("Jogo já finalizado, inicialize um novo!!", NOT_ACCEPTABLE);
        provider.game.getRolls().add(newRoll());
        if(provider.game.sumRolls() >= 21)
            lose();
        else if(provider.game.sumRolls() < 21 && provider.game.sumRolls() >= 18)
            provider.game.superOdd();
    }

    @Override
    public void stopPlaying() {
        if(isNull(provider.game))
            throw new GameException("Inicie seu primeiro jogo", NOT_ACCEPTABLE);
        if(provider.game.getStatus().equals(FINISHED))
            throw new GameException("Jogo já finalizado, inicialize um novo!!", NOT_ACCEPTABLE);
        provider.game.getRolls().add(newRoll());
        if(provider.game.sumRolls() <= 21)
            lose();
        else
            win();
    }

    private void initializeGameWithOneRoll(BigDecimal bet) {
        var rolls = new ArrayList<Byte>();
        rolls.add(newRoll());
        provider.game = new Game(2.0, bet, rolls, OCCURRING);
    }

    private byte newRoll() {
        return (byte) ((Math.random() * 13) + 1);
    }

    private void win() {
        provider.game.setStatus(FINISHED);
        provider.bank.setCurrentValue(
                provider.bank.getCurrentValue()
                        .add(provider.game.getBet()
                            .multiply(BigDecimal.valueOf(provider.game.getOdd())))
        );
    }

    private void lose() {
        provider.game.setStatus(FINISHED);
        provider.bank.setCurrentValue(
                provider.bank.getCurrentValue()
                        .subtract(provider.game.getBet())
        );
    }
}
