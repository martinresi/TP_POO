package main.truco.services;

import main.truco.models.Jugador;

import java.util.List;
import java.util.Scanner;

public class Envido {
    private final List<Jugador> jugadores;
    private final Scanner sc;

    public Envido(List<Jugador> jugadores, Scanner sc) {
        this.jugadores = jugadores;
        this.sc = sc;
    }

    public static class EnvidoState {
        public String cantor;
        public int stake;
        public String ganador;
        public int puntosGanados;
    }

    public EnvidoState gestionarEnvido() {
        for (int i = 0; i < jugadores.size(); i++) {
            Jugador j = jugadores.get(i);
            String decision = j.decidirCantarEnvido(sc);
            if (!"N".equalsIgnoreCase(decision))
                return procesarCantoEnvido(i, decision);
        }
        System.out.println("Nadie cantó Envido.");
        return null;
    }

    private EnvidoState procesarCantoEnvido(int idxCantor, String decision) {
        Jugador cantor = jugadores.get(idxCantor);

        int stake = switch (decision.toUpperCase()) {
            case "E" -> 2;
            case "R" -> 3;
            case "F" -> 4;
            default -> 0;
        };

        System.out.println(cantor.getNombre() + " canta " + decision);

        for (int r = 1; r < jugadores.size(); r++) {
            int idx = (idxCantor + r) % jugadores.size();
            if (jugadores.size() == 4 && (idx % 2 == idxCantor % 2)) continue;
            Jugador oponente = jugadores.get(idx);
            String respuesta = oponente.responderEnvido(sc, stake, oponente.calcularEnvido());
            return manejarRespuestaEnvido(cantor, oponente, respuesta, stake);
        }
        return null;
    }

    private EnvidoState manejarRespuestaEnvido(Jugador cantor, Jugador oponente, String respuesta, int stake) {
        EnvidoState state = new EnvidoState();
        state.cantor = cantor.getNombre();
        state.stake = stake;

        if ("N".equalsIgnoreCase(respuesta)) {
            asignarPuntoEquipo(cantor, 1);
            state.ganador = cantor.getNombre();
            state.puntosGanados = 1;
            System.out.println(oponente.getNombre() + " no quiso. Gana 1 punto " + cantor.getNombre());
            return state;
        }

        if ("S".equalsIgnoreCase(respuesta) && stake == 2) {
            stake = 3;
            String resp2 = cantor.responderEnvido(sc, stake, cantor.calcularEnvido());
            if ("N".equalsIgnoreCase(resp2)) {
                asignarPuntoEquipo(oponente, 1);
                state.ganador = oponente.getNombre();
                state.puntosGanados = 1;
                System.out.println(cantor.getNombre() + " no quiso el Real Envido. Gana 1 punto " + oponente.getNombre());
                return state;
            }
        }

        return resolverEnvidoComparacion(cantor, oponente, stake);
    }

    private EnvidoState resolverEnvidoComparacion(Jugador cantor, Jugador oponente, int stake) {
        int valorCantor = cantor.calcularEnvido();
        int valorOponente = oponente.calcularEnvido();

        System.out.println("→ " + cantor.getNombre() + " tiene " + valorCantor
                + " puntos de envido, " + oponente.getNombre() + " tiene " + valorOponente + " puntos de envido.");

        EnvidoState result = new EnvidoState();
        result.cantor = cantor.getNombre();
        result.stake = stake;

        if (valorCantor > valorOponente) {
            asignarPuntoEquipo(cantor, stake);
            result.ganador = cantor.getNombre();
            result.puntosGanados = stake;
            System.out.println("→ Gana el Envido " + cantor.getNombre() + " (" + stake + " puntos)");
        } else if (valorOponente > valorCantor) {
            asignarPuntoEquipo(oponente, stake);
            result.ganador = oponente.getNombre();
            result.puntosGanados = stake;
            System.out.println("→ Gana el Envido " + oponente.getNombre() + " (" + stake + " puntos)");
        } else {
            Jugador mano = jugadores.get(0);
            asignarPuntoEquipo(mano, stake);
            result.ganador = mano.getNombre();
            result.puntosGanados = stake;
            System.out.println("→ Empate de envido, gana el mano (" + mano.getNombre() + ")");
        }
        return result;
    }

    private void asignarPuntoEquipo(Jugador jugador, int puntos) {
        jugador.sumarPuntos(puntos);
        System.out.println("→ " + jugador.getNombre() + " suma " + puntos + " puntos de Envido (total: " + jugador.getPuntaje() + ")");
    }
}