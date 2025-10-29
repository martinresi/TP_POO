package main.truco.services;

import main.truco.models.Carta;
import main.truco.models.Jugador;
import main.truco.utils.UtilsService;

import java.util.Scanner;

public class JugadorMaquina extends Jugador {
    public JugadorMaquina(String nombre) { super(nombre); }

    @Override
    public Carta jugarCarta(Scanner sc) {
        Carta carta = UtilsService.elegirCarta(mano);
        System.out.println(nombre + " jugó " + carta);
        return carta;
    }

    @Override
    public String decidirCantarEnvido(Scanner sc) {
        return UtilsService.decidirCantarEnvido(calcularEnvido());
    }

    @Override
    public String responderEnvido(Scanner sc, int stake, int cantoValor) {
        return UtilsService.responderEnvido(calcularEnvido(), stake, cantoValor);
    }

    @Override
    public String decidirCantarTruco(Scanner sc, int valorTrucoActual) {
        int max = mano.stream().mapToInt(Carta::getValorTruco).max().orElse(0);
        return UtilsService.decidirCantarTruco(max, valorTrucoActual);
    }

    @Override
    public String responderTruco(Scanner sc, int valorTrucoActual, String canto) {
        int max = mano.stream().mapToInt(Carta::getValorTruco).max().orElse(0);
        return UtilsService.responderTruco(max, valorTrucoActual);
    }
}