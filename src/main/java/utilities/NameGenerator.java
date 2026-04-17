package utilities;

import java.util.Random;

public class NameGenerator {
    private static final String[] ADJETIVOS = {
            "Mágico", "Táctico", "Férreo", "Veloz", "Estratégico",
            "Implacable", "Legendario", "Invencible", "Fantástico", "Astro"
    };

    private static final String[] LEYENDAS = {
            "Mbappé", "Vinícius", "Zidane", "Pelé", "Maradona",
            "Baggio", "Ronaldo", "Buffon", "Totti", "Messi"
    };

    public static String generarNombreAleatorio(){
        Random random = new Random();
        String adjetivo = ADJETIVOS[random.nextInt(ADJETIVOS.length)];
        String leyenda = LEYENDAS[random.nextInt(LEYENDAS.length)];

        return adjetivo + " " + leyenda;
    }
}
