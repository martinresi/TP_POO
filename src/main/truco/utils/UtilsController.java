package main.truco.utils;

import main.truco.models.Carta;
import main.truco.models.Jugador;
import main.truco.models.Mazo;
import main.truco.services.JugadorHumano;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.function.Consumer;

public class UtilsController {


    public void prepararNuevaMano(List<Jugador> jugadores) {
            Mazo mazo = new Mazo();
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
            for (int i = 0; i < mesa.size(); i++) {
                int v = mesa.get(i).getValorTruco();
                if (v > mejorVal) {
                    mejorVal = v;
                    idxGanador = i;
                }
            }

            System.out.println("→ Gana la mano: " + jugadores.get(idxGanador).getNombre());
            return idxGanador;
        }

    public void finalizarMano(Jugador ganador, int valorTruco, Consumer<Integer> asignarPuntos) {
        System.out.println("\n*** " + ganador.getNombre() + " gana la mano (valor truco = " + valorTruco + ") ***");
        asignarPuntos.accept(Integer.valueOf(valorTruco));
    }

}
