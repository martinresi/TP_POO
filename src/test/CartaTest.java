package test;

import main.truco.models.Carta;
import main.truco.models.Palo;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class CartaTest {

    @Test
    void testGetNumeroYPalo() {
        Carta carta = new Carta(5, Palo.BASTO);
        assertEquals(5, carta.getNumero());
        assertEquals(Palo.BASTO, carta.getPalo());
    }

    @Test
    void testGetValorTruco() {
        assertEquals(14, new Carta(1, Palo.ESPADA).getValorTruco());
        assertEquals(13, new Carta(1, Palo.BASTO).getValorTruco());
        assertEquals(12, new Carta(7, Palo.ESPADA).getValorTruco());
        assertEquals(11, new Carta(7, Palo.ORO).getValorTruco());
        assertEquals(10, new Carta(3, Palo.COPA).getValorTruco());
        assertEquals(9, new Carta(2, Palo.BASTO).getValorTruco());
        assertEquals(8, new Carta(1, Palo.COPA).getValorTruco());
        assertEquals(7, new Carta(12, Palo.BASTO).getValorTruco());
        assertEquals(6, new Carta(11, Palo.ESPADA).getValorTruco());
        assertEquals(5, new Carta(10, Palo.ORO).getValorTruco());
        assertEquals(4, new Carta(7, Palo.COPA).getValorTruco());
        assertEquals(3, new Carta(6, Palo.ESPADA).getValorTruco());
        assertEquals(2, new Carta(5, Palo.ORO).getValorTruco());
        assertEquals(1, new Carta(4, Palo.BASTO).getValorTruco());
        assertEquals(0, new Carta(8, Palo.BASTO).getValorTruco());
    }

    @Test
    void testToString() {
        Carta c = new Carta(4, Palo.ESPADA);
        assertEquals("4 de Espada", c.toString());
    }
}