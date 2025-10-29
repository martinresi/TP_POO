package main.truco.utils;

public class Decoration {

    public static void anunciarComienzoPartida() {
        String reset = "\u001B[0m";
        String rojo = "\u001B[31m";
        String amarillo = "\u001B[33m";
        String azul = "\u001B[34m";
        String verde = "\u001B[32m";

        System.out.println();
        System.out.println(azul + "═══════════════════════════════════════════════════════" + reset);
        System.out.println(amarillo + "             🃏  ¡COMIENZA LA PARTIDA!  🃏" + reset);
        System.out.println(azul + "═══════════════════════════════════════════════════════" + reset);
        System.out.println(verde + "      Que ruede el mate, se repartan las cartas..." + reset);
        System.out.println(rojo + "           ¡Y que gane el más criollo del truco! 💪" + reset);
        System.out.println(azul + "═══════════════════════════════════════════════════════" + reset);
        System.out.println();
    }

    public static void mostrarBienvenida() {
        String reset = "\u001B[0m";
        String rojo = "\u001B[31m";
        String verde = "\u001B[32m";
        String amarillo = "\u001B[33m";
        String azul = "\u001B[34m";
        String morado = "\u001B[35m";
        String cian = "\u001B[36m";

        System.out.println();
        System.out.println(azul + "═══════════════════════════════════════════════════════" + reset);
        System.out.println(amarillo + "              ♠♦ BIENVENIDO AL TRUCO DE POO ♣♥" + reset);
        System.out.println(azul + "═══════════════════════════════════════════════════════" + reset);
        System.out.println();
        System.out.println(verde + "        ¡Prepará el mate y las cartas!" + reset);
        System.out.println(morado + "        Envido, Truco, Retruco y Vale Cuatro te esperan." + reset);
        System.out.println(cian + "        Que gane el mejor... o el más chamuyero 😏" + reset);
        System.out.println();
        System.out.println(azul + "═══════════════════════════════════════════════════════" + reset);
        System.out.println(rojo + "       © 2025 - Programación Orientada a Objetos" + reset);
        System.out.println(azul + "═══════════════════════════════════════════════════════" + reset);
        System.out.println();
    }

}
