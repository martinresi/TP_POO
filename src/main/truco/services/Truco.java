package main.truco.services;

import main.truco.models.Jugador;

import java.util.List;
import java.util.Scanner;

public class Truco {

    private int ultimoQueCantoIndex = -1;

    public int gestionarTruco(List<Jugador> jugadores, Scanner sc, int valorActual) {
        String reset = "\u001B[0m";
        String rojo = "\u001B[31m";
        String verde = "\u001B[32m";
        String azul = "\u001B[34m";
        String amarillo = "\u001B[33m";
        String magenta = "\u001B[35m";

        Jugador jugadorActual = jugadores.get(0);
        boolean esMaquina = jugadorActual instanceof JugadorMaquina;

        String canto;

        System.out.println(azul + "\n──────────────────────────────────────────────" + reset);
        System.out.println(magenta + "🎙️  Turno de " + jugadorActual.getNombre() + reset);
        System.out.println(azul + "──────────────────────────────────────────────" + reset);

        if (esMaquina) {
            if (valorActual < 2) canto = "T";
            else if (valorActual == 2) canto = "R";
            else canto = "V";

            String cantoTexto = cantoTrucoToString(calcularNuevoValorTruco(canto, valorActual));
            System.out.println(rojo + "🤖 " + jugadorActual.getNombre() + " canta " + cantoTexto + "!" + reset);
        } else {
            System.out.println(amarillo + "🎴 Opciones para cantar truco:" + reset);
            System.out.println(verde + "  [T] Truco" + reset);
            System.out.println(verde + "  [R] Retruco" + reset);
            System.out.println(verde + "  [V] Vale Cuatro" + reset);
            System.out.println(rojo + "  [N] No quiero cantar" + reset);
            System.out.print(azul + "\n👉 Elegí tu canto: " + reset);
            canto = sc.nextLine().trim().toUpperCase();
        }

        int nuevoValor = calcularNuevoValorTruco(canto, valorActual);

        if (nuevoValor == valorActual) {
            System.out.println(amarillo + "\n🙅 No se cantó nada, continúa la mano..." + reset);
            return valorActual;
        }

        ultimoQueCantoIndex = 0;
        String cantoTexto = cantoTrucoToString(nuevoValor);
        System.out.println(rojo + "\n🔥 " + jugadorActual.getNombre() + " canta " + cantoTexto + "!" + reset);

        return manejarRespuestasTruco(jugadores, sc, ultimoQueCantoIndex, nuevoValor, jugadorActual, valorActual);
    }



    public int getUltimoQueCantoIndex() {
        return ultimoQueCantoIndex;
    }

    public void setUltimoQueCantoIndex(int index) {
        this.ultimoQueCantoIndex = index;
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

            String respuesta;
            if (oponente instanceof JugadorMaquina) {
                if (nuevoValor == 2) respuesta = "S";
                else if (nuevoValor == 3) respuesta = "S";
                else respuesta = "N";
                System.out.println(oponente.getNombre() + " responde automáticamente: " + respuesta);
            } else {

                System.out.print(oponente.getNombre() + ", ¿acepta? (s = quiero / n = no quiero / r = retruco / v = vale cuatro): ");
                respuesta = sc.nextLine().trim().toUpperCase();
            }

            switch (respuesta) {
                case "N" -> {
                    System.out.println(oponente.getNombre() + " no quiso. " + cantor.getNombre() + " gana 1 punto.");
                    return -1;
                }
                case "S" -> {
                    System.out.println(oponente.getNombre() + " quiere!");
                    return nuevoValor;
                }
                case "R" -> {
                    if (nuevoValor < 3) {
                        System.out.println(oponente.getNombre() + " canta RETRUCO!");
                        setUltimoQueCantoIndex(idx);
                        return manejarRespuestasTruco(jugadores, sc, idx, 3, oponente, nuevoValor);
                    }
                }
                case "V" -> {
                    if (nuevoValor < 4) {
                        System.out.println(oponente.getNombre() + " canta VALE CUATRO!");
                        setUltimoQueCantoIndex(idx);
                        return manejarRespuestasTruco(jugadores, sc, idx, 4, oponente, nuevoValor);
                    }
                }
            }
        }
        return nuevoValor;
    }


    private String cantoTrucoToString(int valor) {
        return switch (valor) {
            case 2 -> "TRUCO";
            case 3 -> "RETRUCO";
            case 4 -> "VALE CUATRO";
            default -> "SIN CANTAR";
        };
    }
    private int calcularNuevoValorTruco(String canto, int actual) {
        return switch (canto.toUpperCase()) {
            case "T" -> Math.max(actual, 2);
            case "R" -> Math.max(actual, 3);
            case "V" -> Math.max(actual, 4);
            default -> actual;
        };
    }


}
