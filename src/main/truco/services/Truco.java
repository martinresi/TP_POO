package main.truco.services;

import main.truco.models.Jugador;

import java.util.List;
import java.util.Scanner;

public class Truco {

    public int gestionarTruco(List<Jugador> jugadores, Scanner sc, int valorTrucoActual) {

        for (int i = 0; i < jugadores.size(); i++) {
            Jugador j = jugadores.get(i);

            if (j instanceof main.truco.services.JugadorHumano) {
                j.mostrarMano();
            }

            String canto = j.decidirCantarTruco(sc, valorTrucoActual);
            if (!"N".equalsIgnoreCase(canto)) {
                int resultado = procesarCantoTruco(jugadores, sc, i, canto, valorTrucoActual);
                if (resultado == -1) return valorTrucoActual;
                valorTrucoActual = resultado;
            }
        }
        return valorTrucoActual;
    }

    private int procesarCantoTruco(List<Jugador> jugadores, Scanner sc, int jugadorIndex, String canto, int valorTrucoActual) {
        Jugador cantor = jugadores.get(jugadorIndex);
        int nuevoValor = calcularNuevoValorTruco(canto, valorTrucoActual);

        if (nuevoValor == valorTrucoActual)
            return valorTrucoActual;

        System.out.println(cantor.getNombre() + " canta " + cantoTrucoToString(nuevoValor) + "!");
        return manejarRespuestasTruco(jugadores, sc, jugadorIndex, nuevoValor, cantor, valorTrucoActual);
    }

    private int calcularNuevoValorTruco(String canto, int actual) {
        return switch (canto.toUpperCase()) {
            case "T" -> Math.max(actual, 2);
            case "R" -> Math.max(actual, 3);
            case "V" -> Math.max(actual, 4);
            default -> actual;
        };
    }

    private String cantoTrucoToString(int valor) {
        return switch (valor) {
            case 2 -> "TRUCO";
            case 3 -> "RETRUCO";
            case 4 -> "VALE CUATRO";
            default -> "TRUCO";
        };
    }

    private int manejarRespuestasTruco(
            List<Jugador> jugadores,
            Scanner sc,
            int indexCantor,
            int nuevoValor,
            Jugador cantor,
            int valorTrucoActual
    ) {
        for (int r = 1; r < jugadores.size(); r++) {
            int idx = (indexCantor + r) % jugadores.size();
            Jugador oponente = jugadores.get(idx);

            if (jugadores.size() == 4 && (idx % 2 == indexCantor % 2)) continue;

            String respuesta = oponente.responderTruco(sc, valorTrucoActual, cantoTrucoToString(nuevoValor));

            if ("N".equalsIgnoreCase(respuesta)) {
                System.out.println(oponente.getNombre() + " no quiso. " + cantor.getNombre() + " gana 1 punto.");
                return -1;
            }

            if ("S".equalsIgnoreCase(respuesta)) {
                System.out.println(oponente.getNombre() + " sube la apuesta!");
                return nuevoValor;
            }

            System.out.println(oponente.getNombre() + " quiere.");
        }

        return nuevoValor;
    }
}
