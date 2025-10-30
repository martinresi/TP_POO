package test;

import main.truco.models.Jugador;
import main.truco.models.Palo;
import main.truco.services.JugadorMaquina;
import main.truco.utils.UtilsController;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Consumer;

import static org.junit.jupiter.api.Assertions.*;

class UtilsControllerTest {

    private UtilsController utilsController = new UtilsController();

    @Test
    void testJugarBazaConUnSoloJugador() {

        List<Jugador> jugadores = new ArrayList<>();
        JugadorMaquina jugador = new JugadorMaquina("Solitario");
        jugador.recibirCarta(new main.truco.models.Carta(5, Palo.ESPADA));
        jugadores.add(jugador);

        Scanner sc = new Scanner(System.in);
        int ganadorIndex = utilsController.jugarBaza(jugadores, sc);
        assertEquals(0, ganadorIndex,
                "Con un solo jugador siempre debe ganar él");
    }

    @Test
    void testConsumerQueLanzaExcepcion() {

        Jugador ganador = new JugadorMaquina("Test");
        Consumer<Integer> consumerConError = puntos -> {
            throw new RuntimeException("Error en consumer");
        };
        assertThrows(RuntimeException.class, () ->
                utilsController.finalizarMano(ganador, 2, consumerConError));
    }
}