package main.truco.services;

import main.truco.models.Carta;
import main.truco.models.Jugador;
import java.util.Scanner;
import static main.truco.utils.UtilsService.preguntarYValidar;

public class JugadorHumano extends Jugador {

    public JugadorHumano(String nombre) {
        super(nombre);
    }

    @Override
    public Carta jugarCarta(Scanner sc) {
        String reset = "\u001B[0m";
        String azul = "\u001B[34m";
        String verde = "\u001B[32m";
        String amarillo = "\u001B[33m";

        System.out.println(azul + "\n──────────────────────────────────────────────" + reset);
        System.out.println(verde + "🃏 " + nombre + ", es tu turno para jugar una carta:" + reset);
        mostrarMano();

        int idx = -1;
        while (idx < 0 || idx >= mano.size()) {
            System.out.print(amarillo + "👉 Elegí el índice de carta a jugar (0-" + (mano.size() - 1) + "): " + reset);
            if (sc.hasNextInt()) idx = sc.nextInt();
            else {
                sc.next();
                System.out.println("⚠️  Ingresá un número válido.");
            }
        }
        sc.nextLine();

        Carta c = mano.remove(idx);
        System.out.println(verde + "\n✨ " + nombre + " jugó " + c + " ✨" + reset);
        System.out.println(azul + "──────────────────────────────────────────────" + reset);
        return c;
    }

    @Override
    public String decidirCantarEnvido(Scanner sc) {
        String reset = "\u001B[0m";
        String magenta = "\u001B[35m";
        String amarillo = "\u001B[33m";
        System.out.println(magenta + "\n🎶 Turno de " + nombre + " para decidir Envido:" + reset);
        return preguntarYValidar(
                sc,
                amarillo + "💬 ¿Cantar Envido (E), Real Envido (R), Falta (F) o No (N)? " + reset,
                "ERF",
                "N"
        );
    }

    @Override
    public String responderEnvido(Scanner sc, int stake, int cantoValor) {
        String reset = "\u001B[0m";
        String cyan = "\u001B[36m";
        String amarillo = "\u001B[33m";
        System.out.println(cyan + "\n🎯 Te cantaron Envido, " + nombre + "!" + reset);
        return preguntarYValidar(
                sc,
                amarillo + "💬 ¿Responder Envido? (Q=Quiero / N=No / S=Subir a Real si aplica): " + reset,
                "QNS",
                "N"
        );
    }

    @Override
    public String decidirCantarTruco(Scanner sc, int valorTrucoActual) {
        String reset = "\u001B[0m";
        String rojo = "\u001B[31m";
        String amarillo = "\u001B[33m";
        System.out.println(rojo + "\n🔥 " + nombre + ", momento de decidir el Truco!" + reset);
        return preguntarYValidar(
                sc,
                amarillo + "💬 ¿Cantar Truco (T) / Retruco (R) / Vale 4 (V) o No (N)? " + reset,
                "TRV",
                "N"
        );
    }

    @Override
    public String responderTruco(Scanner sc, int valorTrucoActual, String canto) {
        String reset = "\u001B[0m";
        String rojo = "\u001B[31m";
        String verde = "\u001B[32m";
        String amarillo = "\u001B[33m";

        System.out.println(rojo + "\n⚔️  " + nombre + ", te cantaron " + canto + "!" + reset);
        return preguntarYValidar(
                sc,
                amarillo + "💬 ¿Responder " + canto + "? (Q=Quiero / N=No / S=Subir): " + reset,
                "QNS",
                "N"
        );
    }
}
