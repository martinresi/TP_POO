package main.truco.controller;

import main.truco.models.Jugador;
import main.truco.services.*;
import main.truco.utils.UtilsController;

import java.util.*;

public class JuegoTruco {

    private final List<Jugador> jugadores = new ArrayList<>();
    private final Scanner sc = new Scanner(System.in);
    private int puntosMeta;
    private final Map<Integer, Integer> equipoPuntos = new HashMap<>();
    private final UtilsController utils = new UtilsController();
    private final Truco trucoService = new Truco();

    public void menu() {

        System.out.println("=== TRUCO ARGENTINO ===");

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
        equipoPuntos.put(Integer.valueOf(0), Integer.valueOf(0));
        equipoPuntos.put(Integer.valueOf(1), Integer.valueOf(0));

        System.out.println("\nComienza la partida!");
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

        while (true) {
            jugarMano();
            int equipoA = equipoPuntos.getOrDefault(Integer.valueOf(0), Integer.valueOf(0));
            int equipoB = equipoPuntos.getOrDefault(Integer.valueOf(1), Integer.valueOf(0));


            System.out.println("\nPuntos — Equipo A: " + equipoA + "  |  Equipo B: " + equipoB);

            if (equipoA >= puntosMeta || equipoB >= puntosMeta) {
                System.out.println("\nFin de la partida. Ganó " + (equipoA >= puntosMeta ? "Equipo A" : "Equipo B"));
                break;
            }
        }
        System.out.println("Gracias por jugar!");
    }

    private void jugarMano() {
        utils.prepararNuevaMano(jugadores);

        Envido envido = new Envido(jugadores, sc);
        Envido.EnvidoState envidoState = envido.gestionarEnvido();

        int valorTruco = 1;
        int[] bazasGanadas = new int[jugadores.size()];

        for (int baza = 1; baza <= 3; baza++) {
            System.out.println("\n>>> Mano " + baza);
            valorTruco = gestionarTruco(jugadores, sc, valorTruco);
            int ganadorIdx = utils.jugarBaza(jugadores, sc);
            bazasGanadas[ganadorIdx]++;

            if (bazasGanadas[ganadorIdx] == 2) {
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

        int idx = jugadores.indexOf(jugador);
        int equipoId = (jugadores.size() == 2) ? idx : (idx % 2 == 0 ? 0 : 1);

        Integer equipoKey = Integer.valueOf(equipoId);
        Integer total = equipoPuntos.getOrDefault(equipoKey, Integer.valueOf(0));
        equipoPuntos.put(equipoKey, Integer.valueOf(total + puntos));

        System.out.println("Equipo " + (equipoId == 0 ? "A" : "B") + " suma " + puntos
                + " punto(s). Total: " + equipoPuntos.get(equipoKey));
    }

    private int gestionarTruco(List<Jugador> jugadores, Scanner sc, int valorTrucoActual) {
        int[] bazasGanadas = new int[jugadores.size()];

        for (int baza = 1; baza <= 3; baza++) {
            System.out.println("\n>>> Mano " + baza);

            valorTrucoActual = trucoService.gestionarTruco(jugadores, sc, valorTrucoActual);

            int ganadorIdx = utils.jugarBaza(jugadores, sc);
            bazasGanadas[ganadorIdx]++;

            if (bazasGanadas[ganadorIdx] == 2) {
                finalizarMano(jugadores.get(ganadorIdx), valorTrucoActual);
                return valorTrucoActual;
            }
        }

        finalizarMano(jugadores.get(0), valorTrucoActual);
        return valorTrucoActual;
    }


}
