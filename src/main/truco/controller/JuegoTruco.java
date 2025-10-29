package main.truco.controller;

import main.truco.models.*;
import main.truco.services.*;
import main.truco.utils.UtilsController;

import java.util.*;

import static main.truco.utils.Decoration.anunciarComienzoPartida;

public class JuegoTruco {
    private final List<Jugador> jugadores = new ArrayList<>();
    private final Scanner sc = new Scanner(System.in);
    private int puntosMeta;
    private final Map<Integer, Integer> equipoPuntos = new HashMap<>();
    private final UtilsController utils = new UtilsController();
    private final Truco trucoService = new Truco();

    public void menu() {

        System.out.print("Elegí los puntos meta (15 o 30): ");
        puntosMeta = leerIntOpcional(new int[]{15, 30});

        System.out.print("Elegí cantidad de jugadores (2 o 4): ");
        int nJugadores = leerIntOpcional(new int[]{2, 4});

        for (int i = 1; i <= nJugadores; i++) {
            System.out.print("Jugador " + i + " es Humano (h) o Máquina (m)? ");
            String tipo = sc.nextLine().trim().toLowerCase();
            if (tipo.startsWith("h")) {
                System.out.print("Ingrese nombre del jugador: ");
                String nombre = sc.nextLine().trim();
                jugadores.add(new JugadorHumano(nombre.isEmpty() ? "Jugador " + i : nombre));
            } else {
                jugadores.add(new JugadorMaquina("Máquina " + i));
            }
        }

        equipoPuntos.clear();
        equipoPuntos.put(0, 0);
        equipoPuntos.put(1, 0);

        anunciarComienzoPartida();
        iniciarPartida();
    }

    private int leerIntOpcional(int[] permitidos) {
        while (true) {
            try {
                int v = Integer.parseInt(sc.nextLine().trim());
                for (int p : permitidos) if (p == v) return v;
            } catch (Exception ignored) {}
            System.out.print("Valor inválido. Ingresá " + Arrays.toString(permitidos) + ": ");
        }
    }

    private void iniciarPartida() {
        String reset = "\u001B[0m";
        String verde = "\u001B[32m";
        String rojo = "\u001B[31m";
        String azul = "\u001B[34m";
        String amarillo = "\u001B[33m";
        String morado = "\u001B[35m";

        int manoIndex = 0;

        System.out.println();
        System.out.println(azul + "═══════════════════════════════════════════════════════" + reset);
        System.out.println(amarillo + "             🂡 ¡QUE COMIENCE EL TRUCO! 🂱" + reset);
        System.out.println(azul + "═══════════════════════════════════════════════════════" + reset);
        System.out.println();

        while (true) {
            jugarMano();

            int equipoA = equipoPuntos.getOrDefault(0, 0);
            int equipoB = equipoPuntos.getOrDefault(1, 0);

            System.out.println(azul + "\n──────────────────────────────" + reset);
            System.out.println(morado + "🏆  PUNTUACIÓN ACTUAL" + reset);
            System.out.println(verde + "  ➤ Equipo A: " + equipoA + reset + "   " + rojo + "➤ Equipo B: " + equipoB + reset);
            System.out.println(azul + "──────────────────────────────" + reset);

            mostrarPuntajeIndividual();

            if (equipoA >= puntosMeta || equipoB >= puntosMeta) {
                System.out.println();
                System.out.println(azul + "═══════════════════════════════════════════════════════" + reset);
                System.out.println(amarillo + "🎉 FIN DE LA PARTIDA 🎉" + reset);
                System.out.println((equipoA >= puntosMeta ? verde + "🏅 ¡Ganó el Equipo A!" : rojo + "🏅 ¡Ganó el Equipo B!") + reset);
                System.out.println(azul + "═══════════════════════════════════════════════════════" + reset);
                System.out.println();
                break;
            }

            manoIndex = (manoIndex + 1) % jugadores.size();
            Collections.rotate(jugadores, -1);

            System.out.println(amarillo + "\n🔁 Preparando la próxima mano..." + reset);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }

        System.out.println();
        System.out.println(azul + "═══════════════════════════════════════════════════════" + reset);
        System.out.println(verde + "🙏 ¡GRACIAS POR JUGAR AL TRUCO DE POO!" + reset);
        System.out.println(azul + "═══════════════════════════════════════════════════════" + reset);
        System.out.println();
    }

    private void mostrarPuntajeIndividual() {
        String reset = "\u001B[0m";
        String verde = "\u001B[32m";
        String amarillo = "\u001B[33m";
        String azul = "\u001B[34m";
        String morado = "\u001B[35m";

        System.out.println(azul + "\n🧮 Puntaje individual:" + reset);
        System.out.println(morado + "────────────────────────────────────────" + reset);

        for (int i = 0; i < jugadores.size(); i++) {
            Jugador j = jugadores.get(i);
            String equipo = (jugadores.size() == 2) ? (i == 0 ? "Equipo A" : "Equipo B")
                    : (i % 2 == 0 ? "Equipo A" : "Equipo B");

            String color = equipo.equals("Equipo A") ? verde : amarillo;
            System.out.printf(color + "  ➤ %-15s" + reset + " | %-9s | %2d puntos%n",
                    j.getNombre(), equipo, j.getPuntaje());
        }

        System.out.println(morado + "────────────────────────────────────────" + reset);
    }

    private void jugarMano() {
        System.out.println("\nEl MANO es: " + jugadores.get(0).getNombre());
        utils.prepararNuevaMano(jugadores);

        Envido envido = new Envido(jugadores, sc);
        envido.gestionarEnvido();

        int valorTruco = 1;
        int[] bazasGanadas = new int[jugadores.size()];
        boolean trucoCantado = false;

        for (int baza = 1; baza <= 3; baza++) {
            System.out.println("\n>>> Mano " + baza);

            if (!trucoCantado || valorTruco < 4) {
                int nuevoValor = trucoService.gestionarTruco(jugadores, sc, valorTruco);

                if (nuevoValor == -1) {
                    int callerIdx = trucoService.getUltimoQueCantoIndex();
                    Jugador ganadorPorRechazo = jugadores.get(callerIdx);

                    System.out.println("¡El Truco fue rechazado! Gana quien lo cantó: "
                            + ganadorPorRechazo.getNombre());

                    finalizarMano(ganadorPorRechazo, valorTruco);
                    return;
                } else if (nuevoValor > valorTruco) {
                    valorTruco = nuevoValor;
                    trucoCantado = true;
                }
            }

            int ganadorIdx = utils.jugarBaza(jugadores, sc);
            bazasGanadas[ganadorIdx]++;

            if (bazasGanadas[ganadorIdx] == 2) {
                finalizarMano(jugadores.get(ganadorIdx), valorTruco);
                return;
            }

            if (trucoCantado) {
                finalizarMano(jugadores.get(ganadorIdx), valorTruco);
                return;
            }
        }

        finalizarMano(jugadores.get(0), valorTruco);
    }

    private void finalizarMano(Jugador ganador, int valorTruco) {
        utils.finalizarMano(ganador, valorTruco, puntos -> asignarPuntoEquipo(ganador, puntos));
    }

    private void asignarPuntoEquipo(Jugador jugador, int puntos) {
        String reset = "\u001B[0m";
        String verde = "\u001B[32m";
        String amarillo = "\u001B[33m";
        String azul = "\u001B[34m";

        int idx = jugadores.indexOf(jugador);
        int equipoId = (jugadores.size() == 2) ? idx : (idx % 2 == 0 ? 0 : 1);

        int total = equipoPuntos.getOrDefault(equipoId, 0);
        equipoPuntos.put(equipoId, total + puntos);

        String nombreEquipo = equipoId == 0 ? "Equipo A" : "Equipo B";
        String colorEquipo = equipoId == 0 ? verde : amarillo;

        System.out.println(azul + "──────────────────────────────" + reset);
        System.out.println(colorEquipo + "🏅 " + nombreEquipo + reset + " suma " + puntos + " punto(s).");
        System.out.println(colorEquipo + "💯 Total acumulado: " + (total + puntos) + " puntos." + reset);
        System.out.println(azul + "──────────────────────────────" + reset);
    }

}
