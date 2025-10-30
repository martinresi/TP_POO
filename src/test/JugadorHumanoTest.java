package test;

import main.truco.services.JugadorHumano;
import main.truco.models.Carta;
import main.truco.models.Palo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.Scanner;
import java.io.ByteArrayInputStream;
import static org.junit.jupiter.api.Assertions.*;

class JugadorHumanoTest {

    private JugadorHumano jugador;

    @BeforeEach
    void setUp() {
        jugador = new JugadorHumano("TestPlayer");
    }

    private Scanner crearScannerConEntrada(String entrada) {
        ByteArrayInputStream input = new ByteArrayInputStream(entrada.getBytes());
        return new Scanner(input);
    }

    @Test
    void testConstructor() {
        JugadorHumano nuevoJugador = new JugadorHumano("Juan");
        assertEquals("Juan", nuevoJugador.getNombre());
        assertEquals(0, nuevoJugador.getPuntaje());
        assertTrue(nuevoJugador.getMano().isEmpty());
    }

    @Test
    void testJugarCarta_primeraCartaValida() {
        jugador.recibirCarta(new Carta(1, Palo.ORO));
        jugador.recibirCarta(new Carta(2, Palo.ESPADA));
        jugador.recibirCarta(new Carta(3, Palo.COPA));

        Scanner scanner = crearScannerConEntrada("0\n");
        Carta cartaJugada = jugador.jugarCarta(scanner);

        assertNotNull(cartaJugada);
        assertEquals(1, cartaJugada.getNumero());
        assertEquals(Palo.ORO, cartaJugada.getPalo());
        assertEquals(2, jugador.getMano().size());
    }

    @Test
    void testJugarCarta_segundaCartaValida() {
        jugador.recibirCarta(new Carta(1, Palo.ORO));
        jugador.recibirCarta(new Carta(7, Palo.ESPADA));
        jugador.recibirCarta(new Carta(3, Palo.COPA));

        Scanner scanner = crearScannerConEntrada("1\n");
        Carta cartaJugada = jugador.jugarCarta(scanner);

        assertNotNull(cartaJugada);
        assertEquals(7, cartaJugada.getNumero());
        assertEquals(Palo.ESPADA, cartaJugada.getPalo());
        assertEquals(2, jugador.getMano().size());
    }

    @Test
    void testJugarCarta_terceraCartaValida() {
        jugador.recibirCarta(new Carta(1, Palo.ORO));
        jugador.recibirCarta(new Carta(2, Palo.ESPADA));
        jugador.recibirCarta(new Carta(12, Palo.COPA));

        Scanner scanner = crearScannerConEntrada("2\n");
        Carta cartaJugada = jugador.jugarCarta(scanner);

        assertNotNull(cartaJugada);
        assertEquals(12, cartaJugada.getNumero());
        assertEquals(Palo.COPA, cartaJugada.getPalo());
        assertEquals(2, jugador.getMano().size());
    }

    @Test
    void testJugarCarta_indiceInvalidoLuegoValido() {

        jugador.recibirCarta(new Carta(1, Palo.ORO));
        jugador.recibirCarta(new Carta(2, Palo.ESPADA));
        Scanner scanner = crearScannerConEntrada("5\n0\n");
        Carta cartaJugada = jugador.jugarCarta(scanner);

        assertNotNull(cartaJugada);
        assertEquals(1, cartaJugada.getNumero());
    }

    @Test
    void testJugarCarta_textoLuegoNumeroValido() {

        jugador.recibirCarta(new Carta(5, Palo.BASTO));
        jugador.recibirCarta(new Carta(6, Palo.ORO));
        Scanner scanner = crearScannerConEntrada("abc\n1\n");
        Carta cartaJugada = jugador.jugarCarta(scanner);

        assertNotNull(cartaJugada);
        assertEquals(6, cartaJugada.getNumero());
    }

    @Test
    void testJugarCarta_remueveCartaDeLaMano() {

        jugador.recibirCarta(new Carta(1, Palo.ORO));
        jugador.recibirCarta(new Carta(2, Palo.ESPADA));
        jugador.recibirCarta(new Carta(3, Palo.COPA));

        assertEquals(3, jugador.getMano().size());

        Scanner scanner = crearScannerConEntrada("1\n");
        jugador.jugarCarta(scanner);

        assertEquals(2, jugador.getMano().size());
        assertEquals(1, jugador.getMano().get(0).getNumero());
        assertEquals(3, jugador.getMano().get(1).getNumero());
    }

    @Test
    void testDecidirCantarEnvido_eligeEnvido() {

        Scanner scanner = crearScannerConEntrada("E\n");
        String resultado = jugador.decidirCantarEnvido(scanner);
        assertEquals("E", resultado);
    }

    @Test
    void testDecidirCantarEnvido_eligeRealEnvido() {

        Scanner scanner = crearScannerConEntrada("R\n");
        String resultado = jugador.decidirCantarEnvido(scanner);
        assertEquals("R", resultado);
    }

    @Test
    void testDecidirCantarEnvido_eligeFalta() {

        Scanner scanner = crearScannerConEntrada("F\n");
        String resultado = jugador.decidirCantarEnvido(scanner);
        assertEquals("F", resultado);
    }

    @Test
    void testDecidirCantarEnvido_eligeNo() {

        Scanner scanner = crearScannerConEntrada("N\n");
        String resultado = jugador.decidirCantarEnvido(scanner);
        assertEquals("N", resultado);
    }

    @Test
    void testDecidirCantarEnvido_entradaInvalidaLuegoValida() {

        Scanner scanner = crearScannerConEntrada("X\nE\n");
        String resultado = jugador.decidirCantarEnvido(scanner);
        assertEquals("E", resultado);
    }

    @Test
    void testDecidirCantarEnvido_minusculas() {

        Scanner scanner = crearScannerConEntrada("e\n");
        String resultado = jugador.decidirCantarEnvido(scanner);
        assertEquals("E", resultado);
    }

    @Test
    void testResponderEnvido_eligeQuiero() {

        Scanner scanner = crearScannerConEntrada("Q\n");
        String resultado = jugador.responderEnvido(scanner, 2, 25);
        assertEquals("Q", resultado);
    }

    @Test
    void testResponderEnvido_eligeNo() {

        Scanner scanner = crearScannerConEntrada("N\n");
        String resultado = jugador.responderEnvido(scanner, 2, 25);
        assertEquals("N", resultado);
    }

    @Test
    void testResponderEnvido_eligeSubir() {

        Scanner scanner = crearScannerConEntrada("S\n");
        String resultado = jugador.responderEnvido(scanner, 2, 25);
        assertEquals("S", resultado);
    }

    @Test
    void testResponderEnvido_entradaInvalidaLuegoValida() {

        Scanner scanner = crearScannerConEntrada("Z\nQ\n");
        String resultado = jugador.responderEnvido(scanner, 2, 25);
        assertEquals("Q", resultado);
    }

    @Test
    void testResponderEnvido_minusculas() {

        Scanner scanner = crearScannerConEntrada("q\n");
        String resultado = jugador.responderEnvido(scanner, 2, 25);
        assertEquals("Q", resultado);
    }

    @Test
    void testDecidirCantarTruco_eligeTruco() {

        Scanner scanner = crearScannerConEntrada("T\n");
        String resultado = jugador.decidirCantarTruco(scanner, 1);
        assertEquals("T", resultado);
    }

    @Test
    void testDecidirCantarTruco_eligeRetruco() {

        Scanner scanner = crearScannerConEntrada("R\n");
        String resultado = jugador.decidirCantarTruco(scanner, 2);
        assertEquals("R", resultado);
    }

    @Test
    void testDecidirCantarTruco_eligeValeCuatro() {

        Scanner scanner = crearScannerConEntrada("V\n");
        String resultado = jugador.decidirCantarTruco(scanner, 3);
        assertEquals("V", resultado);
    }

    @Test
    void testDecidirCantarTruco_eligeNo() {

        Scanner scanner = crearScannerConEntrada("N\n");
        String resultado = jugador.decidirCantarTruco(scanner, 1);
        assertEquals("N", resultado);
    }

    @Test
    void testDecidirCantarTruco_entradaInvalidaLuegoValida() {

        Scanner scanner = crearScannerConEntrada("X\nT\n");
        String resultado = jugador.decidirCantarTruco(scanner, 1);
        assertEquals("T", resultado);
    }

    @Test
    void testDecidirCantarTruco_minusculas() {

        Scanner scanner = crearScannerConEntrada("t\n");
        String resultado = jugador.decidirCantarTruco(scanner, 1);
        assertEquals("T", resultado);
    }

    @Test
    void testResponderTruco_eligeQuiero() {

        Scanner scanner = crearScannerConEntrada("Q\n");
        String resultado = jugador.responderTruco(scanner, 2, "TRUCO");
        assertEquals("Q", resultado);
    }

    @Test
    void testResponderTruco_eligeNo() {

        Scanner scanner = crearScannerConEntrada("N\n");
        String resultado = jugador.responderTruco(scanner, 2, "TRUCO");
        assertEquals("N", resultado);
    }

    @Test
    void testResponderTruco_eligeSubir() {

        Scanner scanner = crearScannerConEntrada("S\n");
        String resultado = jugador.responderTruco(scanner, 2, "TRUCO");
        assertEquals("S", resultado);
    }

    @Test
    void testResponderTruco_entradaInvalidaLuegoValida() {

        Scanner scanner = crearScannerConEntrada("X\nQ\n");
        String resultado = jugador.responderTruco(scanner, 2, "TRUCO");
        assertEquals("Q", resultado);
    }

    @Test
    void testResponderTruco_minusculas() {

        Scanner scanner = crearScannerConEntrada("q\n");
        String resultado = jugador.responderTruco(scanner, 2, "TRUCO");
        assertEquals("Q", resultado);
    }

    @Test
    void testResponderTruco_conDiferentesCantosString() {

        Scanner scanner1 = crearScannerConEntrada("Q\n");
        String resultado1 = jugador.responderTruco(scanner1, 2, "TRUCO");
        assertEquals("Q", resultado1);

        Scanner scanner2 = crearScannerConEntrada("Q\n");
        String resultado2 = jugador.responderTruco(scanner2, 3, "RETRUCO");
        assertEquals("Q", resultado2);

        Scanner scanner3 = crearScannerConEntrada("Q\n");
        String resultado3 = jugador.responderTruco(scanner3, 4, "VALE CUATRO");
        assertEquals("Q", resultado3);
    }

    @Test
    void testJugarVariasCartas() {

        jugador.recibirCarta(new Carta(1, Palo.ORO));
        jugador.recibirCarta(new Carta(2, Palo.ESPADA));
        jugador.recibirCarta(new Carta(3, Palo.COPA));

        Scanner scanner1 = crearScannerConEntrada("0\n");
        Carta carta1 = jugador.jugarCarta(scanner1);
        assertEquals(1, carta1.getNumero());
        assertEquals(2, jugador.getMano().size());

        Scanner scanner2 = crearScannerConEntrada("0\n");
        Carta carta2 = jugador.jugarCarta(scanner2);
        assertEquals(2, carta2.getNumero());
        assertEquals(1, jugador.getMano().size());

        Scanner scanner3 = crearScannerConEntrada("0\n");
        Carta carta3 = jugador.jugarCarta(scanner3);
        assertEquals(3, carta3.getNumero());
        assertEquals(0, jugador.getMano().size());
    }

    @Test
    void testHeredaDeFuncionesDeJugador() {

        jugador.sumarPuntos(5);
        assertEquals(5, jugador.getPuntaje());
        jugador.recibirCarta(new Carta(7, Palo.ORO));
        jugador.recibirCarta(new Carta(6, Palo.ORO));
        jugador.recibirCarta(new Carta(1, Palo.ESPADA));

        int envido = jugador.calcularEnvido();
        assertEquals(33, envido);

        jugador.limpiarMano();
        assertTrue(jugador.getMano().isEmpty());
    }
}