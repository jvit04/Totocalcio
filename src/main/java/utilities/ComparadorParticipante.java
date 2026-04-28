package utilities;
import java.util.Comparator;

/**
 * Clase Comparador que permite (valga la redundancia) comparar los diferentes participantes, útil para el Heap
 */
public class ComparadorParticipante implements Comparator<Participante> {

    @Override
    public int compare(Participante p1, Participante p2) {
        // 1. Primero comparamos los puntos (Aciertos)
        int comparacionPuntos = Integer.compare(p1.getPuntos(), p2.getPuntos());

        // 2. Si los puntos son diferentes, retornamos el resultado normal
        if (comparacionPuntos != 0) {
            return comparacionPuntos;
        }

        // 3. DESEMPATE: Si tienen los mismos puntos, ordenamos alfabéticamente por el nombre.
        // Invertimos el compareTo multiplicando por -1 para que la 'A' tenga mayor prioridad que la 'Z' en el MaxHeap.
        return p1.getNombre().compareToIgnoreCase(p2.getNombre()) * -1;
    }
}