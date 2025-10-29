package main.truco.models;

public enum Palo {
    ESPADA, BASTO, ORO, COPA;

    @Override
    public String toString() {
        String s = super.toString().toLowerCase();
        return Character.toUpperCase(s.charAt(0)) + s.substring(1);
    }
}