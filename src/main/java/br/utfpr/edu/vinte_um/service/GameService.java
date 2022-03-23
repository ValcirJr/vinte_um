package br.utfpr.edu.vinte_um.service;

import java.math.BigDecimal;

public interface GameService {
    void newGame(BigDecimal bet);
    void continuePlaying();
    void stopPlaying();
}
