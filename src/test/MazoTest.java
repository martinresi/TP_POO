package test;

import main.truco.models.Mazo;
import main.truco.models.Carta;
import main.truco.models.Palo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.HashSet;
import java.util.Set;
import static org.junit.jupiter.api.Assertions.*;

class MazoTest {

    private Mazo mazo;

    @BeforeEach
    void setUp() {
        mazo = new Mazo();
    }

    @Test
    void testConstructor_crea40Cartas() {
        int contador = 0;
        while (mazo.robar() != null) {
            contador++;
        }
        assertEquals(40, contador);
    }

    @Test
    void testConstructor_noTiene8Ni9() {
        Mazo mazoNuevo = new Mazo();
        Set<String> cartasRobadas = new HashSet<>();

        Carta carta;
        while ((carta = mazoNuevo.robar()) != null) {
            cartasRobadas.add(carta.getNumero() + "-" + carta.getPalo());
            assertNotEquals(8, carta.getNumero());
            assertNotEquals(9, carta.getNumero());
        }

        assertEquals(40, cartasRobadas.size());
    }

    @Test
    void testConstructor_tieneTodosLosPalos() {
        Mazo mazoNuevo = new Mazo();
        Set<Palo> palosEncontrados = new HashSet<>();

        Carta carta;
        while ((carta = mazoNuevo.robar()) != null) {
            palosEncontrados.add(carta.getPalo());
        }

        assertEquals(4, palosEncontrados.size());
        assertTrue(palosEncontrados.contains(Palo.ORO));
        assertTrue(palosEncontrados.contains(Palo.ESPADA));
        assertTrue(palosEncontrados.contains(Palo.COPA));
        assertTrue(palosEncontrados.contains(Palo.BASTO));
    }

    @Test
    void testConstructor_tieneTodosLosNumeros() {
        Mazo mazoNuevo = new Mazo();
        Set<Integer> numerosEncontrados = new HashSet<>();

        Carta carta;
        while ((carta = mazoNuevo.robar()) != null) {
            numerosEncontrados.add(carta.getNumero());
        }

        assertEquals(10, numerosEncontrados.size());
        for (int i = 1; i <= 7; i++) {
            assertTrue(numerosEncontrados.contains(i));
        }
        assertTrue(numerosEncontrados.contains(10));
        assertTrue(numerosEncontrados.contains(11));
        assertTrue(numerosEncontrados.contains(12));

        assertFalse(numerosEncontrados.contains(8));
        assertFalse(numerosEncontrados.contains(9));
    }

    @Test
    void testRobar_devuelveCartaValida() {
        Carta carta = mazo.robar();
        assertNotNull(carta);
        assertTrue(carta.getNumero() >= 1 && carta.getNumero() <= 12);
        assertNotEquals(8, carta.getNumero());
        assertNotEquals(9, carta.getNumero());
    }

    @Test
    void testRobar_reduceTamanio() {

        mazo.robar();
        int contador = 0;
        while (mazo.robar() != null) {
            contador++;
        }
        assertEquals(39, contador);
    }

    @Test
    void testRobar_mazoVacioDevuelveNull() {

        for (int i = 0; i < 40; i++) {
            assertNotNull(mazo.robar());
        }
        assertNull(mazo.robar());
    }

    @Test
    void testRobar_variasVecesEnMazoVacio() {

        for (int i = 0; i < 40; i++) {
            mazo.robar();
        }
        assertNull(mazo.robar());
        assertNull(mazo.robar());
        assertNull(mazo.robar());
    }

    @Test
    void testBarajar_cambiaOrden() {

        Mazo mazo1 = new Mazo();
        Mazo mazo2 = new Mazo();
        boolean algunaDiferente = false;
        for (int i = 0; i < 5; i++) {
            Carta c1 = mazo1.robar();
            Carta c2 = mazo2.robar();

            if (c1.getNumero() != c2.getNumero() || c1.getPalo() != c2.getPalo()) {
                algunaDiferente = true;
                break;
            }
        }
        assertTrue(algunaDiferente);
    }

    @Test
    void testBarajar_mantieneCantidadDeCartas() {
        mazo.barajar();

        int contador = 0;
        while (mazo.robar() != null) {
            contador++;
        }
        assertEquals(40, contador);
    }

    @Test
    void testBarajar_despuesDeRobarAlgunas() {

        for (int i = 0; i < 10; i++) {
            mazo.robar();
        }

        mazo.barajar();
        int contador = 0;
        while (mazo.robar() != null) {
            contador++;
        }
        assertEquals(30, contador);
    }

    @Test
    void testSinCartasDuplicadas() {

        Mazo mazoNuevo = new Mazo();
        Set<String> cartasUnicas = new HashSet<>();

        Carta carta;
        while ((carta = mazoNuevo.robar()) != null) {
            String id = carta.getNumero() + "-" + carta.getPalo();
            assertTrue(cartasUnicas.add(id), "Carta duplicada encontrada: " + id);
        }

        assertEquals(40, cartasUnicas.size());
    }

    @Test
    void testCada10CartasPorPalo() {
        Mazo mazoNuevo = new Mazo();
        int[] contadorPorPalo = new int[4]; // ORO, COPA, ESPADA, BASTO

        Carta carta;
        while ((carta = mazoNuevo.robar()) != null) {
            contadorPorPalo[carta.getPalo().ordinal()]++;
        }

        for (int i = 0; i < 4; i++) {
            assertEquals(10, contadorPorPalo[i],
                    "El palo " + Palo.values()[i] + " no tiene 10 cartas");
        }
    }
}