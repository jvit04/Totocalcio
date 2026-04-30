package utilities;

public class Partido {
    private String equipoLocal;
    private String equipoVisitante;
    private String rutaBanderaLocal;
    private String rutaBanderaVisitante;
    private String resultadoReal;
    private String tituloPartido;

    public Partido(String equipoLocal, String equipoVisitante, String rutaBanderaLocal, String rutaBanderaVisitante, String resultadoReal, String tituloPartido) {
        this.equipoLocal = equipoLocal;
        this.equipoVisitante = equipoVisitante;
        this.rutaBanderaLocal = rutaBanderaLocal;
        this.rutaBanderaVisitante = rutaBanderaVisitante;
        this.resultadoReal = resultadoReal;
        this.tituloPartido = tituloPartido;
    }

    public String getEquipoLocal() {
        return equipoLocal;
    }

    public void setEquipoLocal(String equipoLocal) {
        this.equipoLocal = equipoLocal;
    }

    public String getEquipoVisitante() {
        return equipoVisitante;
    }

    public void setEquipoVisitante(String equipoVisitante) {
        this.equipoVisitante = equipoVisitante;
    }

    public String getRutaBanderaLocal() {
        return rutaBanderaLocal;
    }

    public void setRutaBanderaLocal(String rutaBanderaLocal) {
        this.rutaBanderaLocal = rutaBanderaLocal;
    }

    public String getRutaBanderaVisitante() {
        return rutaBanderaVisitante;
    }

    public void setRutaBanderaVisitante(String rutaBanderaVisitante) {
        this.rutaBanderaVisitante = rutaBanderaVisitante;
    }

    public String getResultadoReal() {
        return resultadoReal;
    }

    public void setResultadoReal(String resultadoReal) {
        this.resultadoReal = resultadoReal;
    }

    public String getTituloPartido() {
        return tituloPartido;
    }

    public void setTituloPartido(String tituloPartido) {
        this.tituloPartido = tituloPartido;
    }
}
