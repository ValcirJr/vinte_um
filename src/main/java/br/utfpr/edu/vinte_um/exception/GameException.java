package br.utfpr.edu.vinte_um.exception;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;

import java.io.Serial;

@Getter
@Slf4j
public class GameException extends RuntimeException{

    @Serial
    private static final long serialVersionUID = 1211880055276023455L;

    private final String message;
    private final HttpStatus status;

    public GameException( String message, HttpStatus status) {
        log.error("Erro ao manipular jogo " + message);
        this.message = message;
        this.status = status;
    }
}
