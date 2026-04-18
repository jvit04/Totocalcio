package utilities;

/**
 * Clase Participante cuenta con los atributos nombre y puntos
 * El nombre es generado automaticamente por la clase NameGenerator
 */
public class Participante {
    private String nombre;
    private int puntos;

    public Participante(String nombre, int puntos) {
        this.nombre = nombre;
        this.puntos = puntos;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getPuntos() {
        return puntos;
    }

    public void setPuntos(int puntos) {
        this.puntos = puntos;
    }

    @Override
    public String toString() {
        return nombre + " - " + puntos + " pts";
    }
}