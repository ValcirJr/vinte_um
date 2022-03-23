package br.utfpr.edu.vinte_um.exception;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;

import java.io.Serial;

@Slf4j
@Getter
public class BankException extends RuntimeException{

    @Serial
    private static final long serialVersionUID = -1298371928372831L;

    private final String message;
    private final HttpStatus status;

    public BankException( String message, HttpStatus status) {
        log.error("Erro ao manipular banca " + message);
        this.message = message;
        this.status = status;
    }
}
