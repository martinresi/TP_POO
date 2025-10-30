package test;

import main.truco.models.Jugador;
import main.truco.models.Carta;
import main.truco.models.Palo;
import main.truco.services.Envido;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.List;
import java.util.Scanner;
import java.io.ByteArrayInputStream;
import static org.junit.jupiter.api.Assertions.*;

class EnvidoTest {

    private Jugador jugador1;
    private Jugador jugador2;

    @BeforeEach
    void setUp() {
        jugador1 = new main.truco.services.JugadorMaquina("Jugador1");
        jugador2 = new main.truco.services.JugadorHumano("Jugador2");
    }

    private Scanner crearScannerConEntrada(String entrada) {
        ByteArrayInputStream input = new ByteArrayInputStream(entrada.getBytes());
        return new Scanner(input);
    }

    @Test
    void testGestionarEnvido_cantorCantaYElOtroNoQuiere() {

        darCartasConEnvido(jugador1, 25);
        darCartasConEnvido(jugador2, 20);
        Scanner scanner = crearScannerConEntrada("E\nN\n");

        Envido envido = new Envido(List.of(jugador1, jugador2), scanner);
        Envido.EnvidoState result = envido.gestionarEnvido();

        assertNotNull(result);
        assertEquals("Jugador1", result.cantor);
        assertEquals("Jugador1", result.ganador);
        assertEquals(1, result.puntosGanados);
        assertEquals(2, result.stake);

        assertTrue(jugador1.getPuntaje() > 0);
    }

    @Test
    void testGestionarEnvido_cantorCantaYElOtroQuiere() {
        darCartasConEnvido(jugador1, 25);
        darCartasConEnvido(jugador2, 30);

        // jugador1 canta "E", jugador2 acepta "Q"
        Scanner scanner = crearScannerConEntrada("E\nQ\n");

        Envido envido = new Envido(List.of(jugador1, jugador2), scanner);
        Envido.EnvidoState result = envido.gestionarEnvido();

        assertNotNull(result);
        assertEquals("Jugador1", result.cantor);
        assertEquals("Jugador2", result.ganador);
        assertEquals(2, result.puntosGanados);
    }

    @Test
    void testGestionarEnvido_nadieCanta() {

        darCartasConEnvido(jugador1, 20);
        darCartasConEnvido(jugador2, 15);

        Scanner scanner = crearScannerConEntrada("P\nP\n");

        Envido envido = new Envido(List.of(jugador1, jugador2), scanner);
        Envido.EnvidoState result = envido.gestionarEnvido();

        assertNull(result.cantor);
        assertNull(result.ganador);
        assertEquals(0, result.puntosGanados);
    }

    @Test
    void testCalcularEnvido() {

        darCartasConEnvido(jugador1, 33);
        int envido = jugador1.calcularEnvido();
        assertEquals(33, envido);
    }

    private void darCartasConEnvido(Jugador jugador, int envidoDeseado) {
        if (envidoDeseado == 25) {
            jugador.recibirCarta(new Carta(5, Palo.ORO));
            jugador.recibirCarta(new Carta(7, Palo.ORO));
            jugador.recibirCarta(new Carta(2, Palo.ESPADA));
        } else if (envidoDeseado == 20) {
            jugador.recibirCarta(new Carta(3, Palo.COPA));
            jugador.recibirCarta(new Carta(7, Palo.COPA));
            jugador.recibirCarta(new Carta(1, Palo.BASTO));
        } else if (envidoDeseado == 30) {
            jugador.recibirCarta(new Carta(6, Palo.ESPADA));
            jugador.recibirCarta(new Carta(4, Palo.ESPADA));
            jugador.recibirCarta(new Carta(1, Palo.ORO));
        } else if (envidoDeseado == 33) {
            jugador.recibirCarta(new Carta(7, Palo.BASTO));
            jugador.recibirCarta(new Carta(6, Palo.BASTO));
            jugador.recibirCarta(new Carta(1, Palo.COPA));
        } else {
            jugador.recibirCarta(new Carta(1, Palo.ORO));
            jugador.recibirCarta(new Carta(2, Palo.COPA));
            jugador.recibirCarta(new Carta(3, Palo.BASTO));
        }
    }
}