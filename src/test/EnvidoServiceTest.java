package test;

import main.truco.models.*;
import main.truco.services.JugadorHumano;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class EnvidoServiceTest {

    @Test
    void testComparacionPuntosEnvido() {
        JugadorHumano j1 = new JugadorHumano("A");
        JugadorHumano j2 = new JugadorHumano("B");
        j1.recibirCarta(new Carta(7, Palo.ESPADA));
        j1.recibirCarta(new Carta(5, Palo.ESPADA));
        j1.recibirCarta(new Carta(11, Palo.BASTO));
        j2.recibirCarta(new Carta(4, Palo.ORO));
        j2.recibirCarta(new Carta(6, Palo.ORO));
        j2.recibirCarta(new Carta(12, Palo.BASTO));
        assertTrue(j1.calcularEnvido() > j2.calcularEnvido());
    }
}