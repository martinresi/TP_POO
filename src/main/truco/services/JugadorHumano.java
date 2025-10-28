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
        mostrarMano();
        int idx = -1;
        while (idx < 0 || idx >= mano.size()) {
            System.out.print(nombre + " - Elegí índice de carta a jugar (0-" + (mano.size()-1) + "): ");
            if (sc.hasNextInt()) idx = sc.nextInt();
            else {
                sc.next();
            }
        }
        sc.nextLine();
        Carta c = mano.remove(idx);
        System.out.println(nombre + " jugó " + c);
        return c;
    }

    @Override
    public String decidirCantarEnvido(Scanner sc) {
        return preguntarYValidar(
                sc,
                nombre + " ¿Cantar Envido (E), Real Envido (R), Falta (F) o No (N)? ",
                "ERF",
                "N"
        );
    }

    @Override
    public String responderEnvido(Scanner sc, int stake, int cantoValor) {
        return preguntarYValidar(
                sc,
                nombre + " ¿Responder Envido? (Q=Quiero / N=No / S=Subir a Real si aplica): ",
                "QNS",
                "N"
        );
    }

    @Override
    public String decidirCantarTruco(Scanner sc, int valorTrucoActual) {
        return preguntarYValidar(
                sc,
                nombre + " ¿Cantar Truco (T) / Retruco (R) / Vale4 (V) o No (N)? ",
                "TRV",
                "N"
        );
    }

    @Override
    public String responderTruco(Scanner sc, int valorTrucoActual, String canto) {
        return preguntarYValidar(
                sc,
                nombre + " ¿Responder " + canto + "? (Q=Quiero / N=No / S=Subir): ",
                "QNS",
                "N"
        );
    }

}
