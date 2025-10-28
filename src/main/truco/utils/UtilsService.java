package main.truco.utils;

import main.truco.models.Carta;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class UtilsService {

    private static final Random rnd = new Random();

    public static String preguntarYValidar(
            Scanner sc,
            String mensaje,
            String opcionesValidas,
            String defecto
    ) {
        System.out.print(mensaje);
        String r = sc.nextLine().trim().toUpperCase();
        return opcionesValidas.contains(r) ? r : defecto;
    }

    public static Carta elegirCarta(List<Carta> mano) {

        int mejorIdx = 0;
        int mejorValor = -1;
        for (int i = 0; i < mano.size(); i++) {
            int v = mano.get(i).getValorTruco();
            if (v > mejorValor) {
                mejorValor = v;
                mejorIdx = i;
            }
        }
        int idx = (rnd.nextDouble() < 0.7) ? mejorIdx : rnd.nextInt(mano.size());
        return mano.remove(idx);
    }

    // --- ENVIDO ---
    public static String decidirCantarEnvido(int envido) {

        if (envido >= 31) return "F";
        if (envido >= 28) return "R";
        if (envido >= 24 && rnd.nextDouble() < 0.6) return "E";
        return "N";
    }

    public static String responderEnvido(int envidoPropio, int stake, int cantoValor) {

        if (envidoPropio >= cantoValor || envidoPropio >= 28) return "Q";
        if (envidoPropio >= 24 && stake <= 2 && rnd.nextDouble() < 0.6) return "Q";
        return "N";
    }

    // --- TRUCO ---
    public static String decidirCantarTruco(int maxCarta, int valorTrucoActual) {

        if (maxCarta >= 12 && valorTrucoActual < 2) return "T";
        if (maxCarta >= 13 && valorTrucoActual < 3 && rnd.nextDouble() < 0.5) return "R";
        return "N";
    }

    public static String responderTruco(int maxCarta, int valorTrucoActual) {

        if (maxCarta >= 13 && valorTrucoActual < 3) return "S";
        if (maxCarta >= 10) return "Q";
        return rnd.nextDouble() < 0.5 ? "Q" : "N";
    }
}
