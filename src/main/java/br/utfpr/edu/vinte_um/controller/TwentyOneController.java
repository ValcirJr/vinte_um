package br.utfpr.edu.vinte_um.controller;

import br.utfpr.edu.vinte_um.exception.BankException;
import br.utfpr.edu.vinte_um.exception.GameException;
import br.utfpr.edu.vinte_um.provider.TwentyOneProvider;
import br.utfpr.edu.vinte_um.service.BankService;
import br.utfpr.edu.vinte_um.service.GameService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

import static br.utfpr.edu.vinte_um.message.ReturnMessages.*;

@RestController
@RequestMapping("play")
@RequiredArgsConstructor
public class TwentyOneController {

    private final BankService bankService;
    private final GameService gameService;

    private final TwentyOneProvider provider;

    @GetMapping("new-bank/{value}")
    public ResponseEntity<String> newPlayer(@PathVariable BigDecimal value) {
        try {
            bankService.initialize(value);
            return ResponseEntity.ok()
                    .body(
                            String.format(NEW_BANK_CREATED, provider.bank.getCurrentValue())
                    );
        } catch (BankException ex) {
            return ResponseEntity.status(ex.getStatus()).body(ex.getMessage());
        }
    }

    @GetMapping("new-game/{bet}")
    public ResponseEntity<String> newGame(@PathVariable BigDecimal bet){
        try {
            gameService.newGame(bet);
            return ResponseEntity.ok()
                    .body(
                            String.format(NEW_GAME_CREATED, provider.game.getRolls().get(0))
                    );
        } catch (GameException ex) {
            return ResponseEntity.status(ex.getStatus()).body(ex.getMessage());
        }
    }

    @GetMapping("/continue")
    public ResponseEntity<String> continueGame() {
        try {
            gameService.continuePlaying();
        } catch (GameException ex) {
            return ResponseEntity.status(ex.getStatus()).body(ex.getMessage());
        }

        switch (provider.game.getStatus()) {
            case FINISHED -> {
                return ResponseEntity.ok()
                        .body(
                                String.format(GAME_LOSE, provider.game.allRollsAsString(), provider.bank.getCurrentValue())
                        );
            }
            case OCCURRING -> {
                return ResponseEntity.ok()
                        .body(
                                String.format(GOOD_PLAY, provider.game.allRollsAsString())
                        );
            }
            case OCCURRING_SUPER_ODD -> {
                return ResponseEntity.ok()
                        .body(
                                String.format(SUPER_ODD, provider.game.allRollsAsString())
                        );
            }
            default -> {
                return ResponseEntity.ok("");
            }
        }
    }

    @GetMapping("/stop")
    public ResponseEntity<String> stopPlaying() {
        try {
            gameService.stopPlaying();
            return ResponseEntity.ok()
                    .body(
                            String.format(STOPPED, provider.game.allRollsAsString(), provider.bank.getCurrentValue())
                    );
        } catch (GameException ex) {
            return ResponseEntity.status(ex.getStatus()).body(ex.getMessage());
        }
    }

}