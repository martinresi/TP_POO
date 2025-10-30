package test;

import main.truco.services.Truco;
import main.truco.models.Jugador;
import main.truco.models.Carta;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.List;
import java.util.ArrayList;
import java.util.Scanner;
import java.io.ByteArrayInputStream;
import static org.junit.jupiter.api.Assertions.*;

class TrucoTest {

    private static class JugadorConcreto extends Jugador {
        public JugadorConcreto(String nombre) {
            super(nombre);
        }

        @Override
        public Carta jugarCarta(Scanner sc) {
            return mano.isEmpty() ? null : mano.get(0);
        }

        @Override
        public String decidirCantarEnvido(Scanner sc) {
            return "P";
        }

        @Override
        public String responderEnvido(Scanner sc, int stake, int cantoValor) {
            return "N";
        }

        @Override
        public String decidirCantarTruco(Scanner sc, int valorTrucoActual) {
            return "T";
        }

        @Override
        public String responderTruco(Scanner sc, int valorTrucoActual, String canto) {
            return "S";
        }
    }

    private Truco truco;
    private List<Jugador> jugadores;

    @BeforeEach
    void setUp() {
        truco = new Truco();
        jugadores = new ArrayList<>();
        jugadores.add(new JugadorConcreto("Jugador1"));
        jugadores.add(new JugadorConcreto("Jugador2"));
    }

    private Scanner crearScannerConEntrada(String entrada) {
        ByteArrayInputStream input = new ByteArrayInputStream(entrada.getBytes());
        return new Scanner(input);
    }

    @Test
    void testConstructor() {
        Truco trucoNuevo = new Truco();
        assertEquals(-1, trucoNuevo.getUltimoQueCantoIndex());
    }

    @Test
    void testGetSetUltimoQueCantoIndex() {
        assertEquals(-1, truco.getUltimoQueCantoIndex());

        truco.setUltimoQueCantoIndex(0);
        assertEquals(0, truco.getUltimoQueCantoIndex());

        truco.setUltimoQueCantoIndex(1);
        assertEquals(1, truco.getUltimoQueCantoIndex());

        truco.setUltimoQueCantoIndex(-1);
        assertEquals(-1, truco.getUltimoQueCantoIndex());
    }

    @Test
    void testGestionarTruco_jugadorNoQuiereCantarNada() {

        Scanner scanner = crearScannerConEntrada("N\n");
        int resultado = truco.gestionarTruco(jugadores, scanner, 1);
        assertEquals(1, resultado);
    }

    @Test
    void testGestionarTruco_jugadorCantaTrucoYOponenteQuiere() {

        Scanner scanner = crearScannerConEntrada("T\nS\n");
        int resultado = truco.gestionarTruco(jugadores, scanner, 1);

        assertEquals(2, resultado);
        assertEquals(0, truco.getUltimoQueCantoIndex());
    }

    @Test
    void testGestionarTruco_jugadorCantaTrucoYOponenteNoQuiere() {

        Scanner scanner = crearScannerConEntrada("T\nN\n");
        int resultado = truco.gestionarTruco(jugadores, scanner, 1);

        assertEquals(-1, resultado);
    }

    @Test
    void testGestionarTruco_jugadorCantaTrucoYOponenteCantaRetruco() {

        Scanner scanner = crearScannerConEntrada("T\nR\nS\n");
        int resultado = truco.gestionarTruco(jugadores, scanner, 1);

        assertEquals(3, resultado);
    }

    @Test
    void testGestionarTruco_jugadorCantaTrucoYOponenteCantaRetrucoYNoQuiere() {

        Scanner scanner = crearScannerConEntrada("T\nR\nN\n");
        int resultado = truco.gestionarTruco(jugadores, scanner, 1);

        assertEquals(-1, resultado);
    }

    @Test
    void testGestionarTruco_cantarRetrucoDirectamente() {
        Scanner scanner = crearScannerConEntrada("R\nS\n");
        int resultado = truco.gestionarTruco(jugadores, scanner, 1);

        assertEquals(3, resultado);
    }

    @Test
    void testGestionarTruco_cantarValeCuatroDirectamente() {
        Scanner scanner = crearScannerConEntrada("V\nS\n");
        int resultado = truco.gestionarTruco(jugadores, scanner, 1);

        assertEquals(4, resultado);
    }

    @Test
    void testGestionarTruco_retrucoAValeCuatro() {

        Scanner scanner = crearScannerConEntrada("R\nV\nS\n");
        int resultado = truco.gestionarTruco(jugadores, scanner, 1);

        assertEquals(4, resultado);
    }

    @Test
    void testGestionarTruco_trucoYaEnValor2() {

        Scanner scanner = crearScannerConEntrada("T\n");
        int resultado = truco.gestionarTruco(jugadores, scanner, 2);

        assertEquals(2, resultado);
    }

    @Test
    void testGestionarTruco_retrucoYaEnValor3() {

        Scanner scanner = crearScannerConEntrada("R\n");
        int resultado = truco.gestionarTruco(jugadores, scanner, 3);

        assertEquals(3, resultado);
    }

    @Test
    void testGestionarTruco_valeCuatroYaEnValor4() {

        Scanner scanner = crearScannerConEntrada("V\n");
        int resultado = truco.gestionarTruco(jugadores, scanner, 4);

        assertEquals(4, resultado);
    }

    @Test
    void testGestionarTruco_escalarDeTrucoARetrucoAValeCuatro() {

        Scanner scanner1 = crearScannerConEntrada("T\nS\n");
        int resultado1 = truco.gestionarTruco(jugadores, scanner1, 1);
        assertEquals(2, resultado1);

        Scanner scanner2 = crearScannerConEntrada("R\nS\n");
        int resultado2 = truco.gestionarTruco(jugadores, scanner2, resultado1);
        assertEquals(3, resultado2);

        Scanner scanner3 = crearScannerConEntrada("V\nS\n");
        int resultado3 = truco.gestionarTruco(jugadores, scanner3, resultado2);
        assertEquals(4, resultado3);
    }

    @Test
    void testGestionarTruco_entradaInvalidaNoHaceNada() {

        Scanner scanner = crearScannerConEntrada("X\n");
        int resultado = truco.gestionarTruco(jugadores, scanner, 1);

        assertEquals(1, resultado);
    }

    @Test
    void testGestionarTruco_minusculasFuncionan() {

        Scanner scanner = crearScannerConEntrada("t\ns\n");
        int resultado = truco.gestionarTruco(jugadores, scanner, 1);

        assertEquals(2, resultado);
    }

    @Test
    void testGestionarTruco_conEspacios() {

        Scanner scanner = crearScannerConEntrada("  T  \n  S  \n");
        int resultado = truco.gestionarTruco(jugadores, scanner, 1);

        assertEquals(2, resultado);
    }

    @Test
    void testGestionarTruco_actualizaUltimoQueCantoIndex() {

        Scanner scanner = crearScannerConEntrada("T\nS\n");
        truco.gestionarTruco(jugadores, scanner, 1);

        assertEquals(0, truco.getUltimoQueCantoIndex());
    }

    @Test
    void testGestionarTruco_conTresJugadores() {

        jugadores.add(new JugadorConcreto("Jugador3"));
        Scanner scanner = crearScannerConEntrada("T\nS\nS\n");
        int resultado = truco.gestionarTruco(jugadores, scanner, 1);

        assertEquals(2, resultado);
    }

    @Test
    void testGestionarTruco_segundoJugadorNoQuiere() {

        jugadores.add(new JugadorConcreto("Jugador3"));
        Scanner scanner = crearScannerConEntrada("T\nN\n");
        int resultado = truco.gestionarTruco(jugadores, scanner, 1);

        assertEquals(-1, resultado);
    }

    @Test
    void testGestionarTruco_valorMaximo4() {

        Scanner scanner = crearScannerConEntrada("V\nV\n");
        int resultado = truco.gestionarTruco(jugadores, scanner, 4);

        assertEquals(4, resultado);
    }
}