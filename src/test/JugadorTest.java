package test;

import main.truco.models.Jugador;
import main.truco.models.Carta;
import main.truco.models.Palo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.Scanner;
import static org.junit.jupiter.api.Assertions.*;

class JugadorTest {

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
            return "P";
        }

        @Override
        public String responderTruco(Scanner sc, int valorTrucoActual, String canto) {
            return "N";
        }
    }

    private Jugador jugador;

    @BeforeEach
    void setUp() {
        jugador = new JugadorConcreto("TestPlayer");
    }

    @Test
    void testConstructor() {
        assertEquals("TestPlayer", jugador.getNombre());
        assertEquals(0, jugador.getPuntaje());
        assertTrue(jugador.getMano().isEmpty());
    }

    @Test
    void testRecibirCarta() {
        Carta carta = new Carta(1, Palo.ORO);
        jugador.recibirCarta(carta);

        assertEquals(1, jugador.getMano().size());
        assertEquals(carta, jugador.getMano().get(0));
    }

    @Test
    void testRecibirCartaNull() {
        jugador.recibirCarta(null);
        assertTrue(jugador.getMano().isEmpty());
    }

    @Test
    void testLimpiarMano() {
        jugador.recibirCarta(new Carta(1, Palo.ORO));
        jugador.recibirCarta(new Carta(2, Palo.ESPADA));
        jugador.recibirCarta(new Carta(3, Palo.COPA));

        assertEquals(3, jugador.getMano().size());

        jugador.limpiarMano();
        assertTrue(jugador.getMano().isEmpty());
    }

    @Test
    void testSumarPuntos() {
        assertEquals(0, jugador.getPuntaje());

        jugador.sumarPuntos(5);
        assertEquals(5, jugador.getPuntaje());

        jugador.sumarPuntos(3);
        assertEquals(8, jugador.getPuntaje());
    }

    @Test
    void testCalcularEnvido_dosMismasPalo() {

        jugador.recibirCarta(new Carta(7, Palo.ORO));
        jugador.recibirCarta(new Carta(6, Palo.ORO));
        jugador.recibirCarta(new Carta(1, Palo.ESPADA));

        assertEquals(33, jugador.calcularEnvido());
    }

    @Test
    void testCalcularEnvido_tresMismasPalo() {

        jugador.recibirCarta(new Carta(7, Palo.ORO));
        jugador.recibirCarta(new Carta(5, Palo.ORO));
        jugador.recibirCarta(new Carta(3, Palo.ORO));

        assertEquals(32, jugador.calcularEnvido());
    }

    @Test
    void testCalcularEnvido_todasDistintasPalo() {

        jugador.recibirCarta(new Carta(7, Palo.ORO));
        jugador.recibirCarta(new Carta(5, Palo.ESPADA));
        jugador.recibirCarta(new Carta(3, Palo.COPA));

        assertEquals(7, jugador.calcularEnvido());
    }

    @Test
    void testCalcularEnvido_conFiguras() {

        jugador.recibirCarta(new Carta(10, Palo.ORO));
        jugador.recibirCarta(new Carta(7, Palo.ORO));
        jugador.recibirCarta(new Carta(1, Palo.ESPADA));

        assertEquals(27, jugador.calcularEnvido());
    }

    @Test
    void testCalcularEnvido_soloFiguras() {

        jugador.recibirCarta(new Carta(10, Palo.ORO));
        jugador.recibirCarta(new Carta(11, Palo.ORO));
        jugador.recibirCarta(new Carta(12, Palo.ESPADA));

        assertEquals(20, jugador.calcularEnvido());
    }

    @Test
    void testCalcularEnvido_dosParejasMismoPalo() {

        jugador.recibirCarta(new Carta(7, Palo.ORO));
        jugador.recibirCarta(new Carta(6, Palo.ORO));
        jugador.recibirCarta(new Carta(5, Palo.ESPADA));

        assertEquals(33, jugador.calcularEnvido());
    }

    @Test
    void testCalcularEnvido_envidoBueno() {

        jugador.recibirCarta(new Carta(6, Palo.ORO));
        jugador.recibirCarta(new Carta(5, Palo.ORO));
        jugador.recibirCarta(new Carta(2, Palo.COPA));

        assertEquals(31, jugador.calcularEnvido());
    }

    @Test
    void testCalcularEnvido_manoVacia() {
        assertEquals(0, jugador.calcularEnvido());
    }

    @Test
    void testCalcularEnvido_unaSolaCarta() {
        jugador.recibirCarta(new Carta(7, Palo.ORO));
        assertEquals(7, jugador.calcularEnvido());
    }

    @Test
    void testGetMano_noModificable() {
        jugador.recibirCarta(new Carta(1, Palo.ORO));

        var mano = jugador.getMano();
        assertEquals(1, mano.size());
        mano.add(new Carta(2, Palo.ESPADA));
        assertEquals(2, jugador.getMano().size());
    }
}