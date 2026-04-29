package utilities;

import java.util.Random;

/**
 * Clase para generar los nombres aleatorios para los jugadores ADJETIVO + LEYENDA
 */
public class NameGenerator {
    private static final String[] ADJETIVOS = {
            "Mágico", "Táctico", "Férreo", "Veloz", "Estratégico",
            "Implacable", "Legendario", "Invencible", "Fantástico", "Astro",
            "Colosal", "Supremo", "Épico", "Mítico", "Audaz",
            "Maestro", "Formidable", "Intratable", "Inmortal", "Glorioso"
    };

    private static final String[] LEYENDAS = {
            "Baggio", "Buffon", "Totti", "Maldini", "Pirlo",
            "Del Piero", "Baresi", "Cannavaro", "Meazza", "Rossi",
            "Zoff", "Riva", "Scirea", "Nesta", "Rivera",
            "Vieri", "Mazzola", "Tardelli", "Facchetti", "Gattuso"
    };

    /**
     * Se encarga de generar el nombre con el formato mencionado.
     * @return ADJETIVO + LEYENDA
     */
    public static String generarNombreAleatorio(){
        Random random = new Random();
        String adjetivo = ADJETIVOS[random.nextInt(ADJETIVOS.length)];
        String leyenda = LEYENDAS[random.nextInt(LEYENDAS.length)];

        return adjetivo + " " + leyenda;
    }
}
