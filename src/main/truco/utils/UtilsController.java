package main.truco.utils;

import main.truco.models.Carta;
import main.truco.models.Jugador;
import main.truco.services.JugadorHumano;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.function.Consumer;

public class UtilsController {
    public void prepararNuevaMano(List<Jugador> jugadores) {
        main.truco.models.Mazo mazo = new main.truco.models.Mazo();
        mazo.barajar();
        jugadores.forEach(Jugador::limpiarMano);
        for (int r = 0; r < 3; r++)
            jugadores.forEach(j -> j.recibirCarta(mazo.robar()));

        System.out.println("\n--- Nueva mano ---");
        jugadores.stream()
                .filter(j -> j instanceof JugadorHumano)
                .forEach(Jugador::mostrarMano);
    }

    public int jugarBaza(List<Jugador> jugadores, Scanner sc) {
        List<Carta> mesa = new ArrayList<>();
        for (Jugador j : jugadores)
            mesa.add(j.jugarCarta(sc));

        int idxGanador = 0, mejorVal = -1;
        boolean empate = false;
        for (int i = 0; i < mesa.size(); i++) {
            int v = mesa.get(i).getValorTruco();
            if (v > mejorVal) {
                mejorVal = v;
                idxGanador = i;
                empate = false;
            } else if (v == mejorVal && i != idxGanador) {
                empate = true;
            }
        }
        if (empate) {
            System.out.println("→ Empate en la baza, gana el mano: " + jugadores.get(0).getNombre());
            idxGanador = 0;
        } else {
            System.out.println("→ Gana la mano: " + jugadores.get(idxGanador).getNombre());
        }
        return idxGanador;
    }

    public void finalizarMano(Jugador ganador, int valorTruco, Consumer<Integer> asignarPuntos) {
        System.out.println("\n*** " + ganador.getNombre() + " gana la mano (valor truco = " + valorTruco + ") ***");
        asignarPuntos.accept(valorTruco);
        ganador.sumarPuntos(valorTruco);
    }
}