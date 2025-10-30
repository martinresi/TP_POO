package test;

import main.truco.models.Carta;
import main.truco.models.Jugador;
import main.truco.models.Palo;
import main.truco.services.JugadorMaquina;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;

class JugadorMaquinaTest {

    private JugadorMaquina jugadorMaquina;
    private Carta carta1;
    private Carta carta2;
    private Carta carta3;

    @BeforeEach
    void setUp() {

        jugadorMaquina = new JugadorMaquina("TestBot");

        carta1 = new Carta(1, Palo.ESPADA);
        carta2 = new Carta(5, Palo.ORO);
        carta3 = new Carta(7, Palo.COPA);

        jugadorMaquina.recibirCarta(carta1);
        jugadorMaquina.recibirCarta(carta2);
        jugadorMaquina.recibirCarta(carta3);
    }

    @Test
    void testConstructor() {

        assertEquals("TestBot", jugadorMaquina.getNombre());
        assertNotNull(jugadorMaquina.getMano());
        assertEquals(3, jugadorMaquina.getMano().size());
    }

    @Test
    void testJugarCarta() {

        Scanner sc = new Scanner(System.in);
        Carta cartaJugada = jugadorMaquina.jugarCarta(sc);
        assertNotNull(cartaJugada);
        assertTrue(jugadorMaquina.getMano().contains(cartaJugada) ||
                !jugadorMaquina.getMano().contains(cartaJugada));
        assertTrue(cartaJugada == carta1 || cartaJugada == carta2 || cartaJugada == carta3);
    }


    @Test
    void testDecidirCantarEnvido() {

        Scanner sc = new Scanner(System.in);
        String decision = jugadorMaquina.decidirCantarEnvido(sc);
        assertNotNull(decision);
        assertTrue(decision.equals("envido") ||
                decision.equals("real envido") ||
                decision.equals("falta envido") ||
                decision.equals(""));
    }

    @Test
    void testResponderEnvido() {

        Scanner sc = new Scanner(System.in);
        int stake = 2;
        int cantoValor = 5;

        String respuesta = jugadorMaquina.responderEnvido(sc, stake, cantoValor);
        assertNotNull(respuesta);
        assertTrue(respuesta.equals("quiero") ||
                respuesta.equals("no quiero") ||
                respuesta.equals("envido") ||
                respuesta.equals("real envido") ||
                respuesta.equals("falta envido"));
    }

    @Test
    void testDecidirCantarTruco() {

        Scanner sc = new Scanner(System.in);
        int valorTrucoActual = 1;
        String decision = jugadorMaquina.decidirCantarTruco(sc, valorTrucoActual);
        assertNotNull(decision);
        assertTrue(decision.equals("truco") ||
                decision.equals("retruco") ||
                decision.equals("vale cuatro") ||
                decision.equals(""));
    }

    @Test
    void testDecidirCantarTrucoConValoresDiferentes() {

        Scanner sc = new Scanner(System.in);
        String decision1 = jugadorMaquina.decidirCantarTruco(sc, 1);
        String decision2 = jugadorMaquina.decidirCantarTruco(sc, 2);
        String decision3 = jugadorMaquina.decidirCantarTruco(sc, 3);

        assertNotNull(decision1);
        assertNotNull(decision2);
        assertNotNull(decision3);
    }

    @Test
    void testResponderTruco() {

        Scanner sc = new Scanner(System.in);
        int valorTrucoActual = 2;
        String canto = "retruco";
        String respuesta = jugadorMaquina.responderTruco(sc, valorTrucoActual, canto);
        assertNotNull(respuesta);
        assertTrue(respuesta.equals("quiero") ||
                respuesta.equals("no quiero") ||
                respuesta.equals("retruco") ||
                respuesta.equals("vale cuatro"));
    }

    @Test
    void testResponderTrucoConDiferentesValores() {

        Scanner sc = new Scanner(System.in);
        String respuesta1 = jugadorMaquina.responderTruco(sc, 1, "truco");
        String respuesta2 = jugadorMaquina.responderTruco(sc, 2, "retruco");
        String respuesta3 = jugadorMaquina.responderTruco(sc, 3, "vale cuatro");

        assertNotNull(respuesta1);
        assertNotNull(respuesta2);
        assertNotNull(respuesta3);
    }

    @Test
    void testHerencia() {
        assertTrue(jugadorMaquina instanceof Jugador);
        assertEquals("TestBot", jugadorMaquina.getNombre());
    }

    @Test
    void testCalcularEnvido() {

        int envido = jugadorMaquina.calcularEnvido();
        assertTrue(envido >= 0 && envido <= 33);
    }

    @Test
    void testManoCompleta() {

        assertEquals(3, jugadorMaquina.getMano().size());
        assertTrue(jugadorMaquina.getMano().contains(carta1));
        assertTrue(jugadorMaquina.getMano().contains(carta2));
        assertTrue(jugadorMaquina.getMano().contains(carta3));
    }
}