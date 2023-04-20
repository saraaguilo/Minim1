package gm;

import java.util.List;

public class Juego{
    private String idJuego;
    private String descJuego;
    private int numNivell;
    private int puntosPorNivel;
    private List<Usuario> listaUsuario;
    private List<Equipo> listaequipos;
    private enum Estado {
        NO_INICIADO,
        INICIADO_EN_PREPARACION,
        INICIADO_EN_FUNCIONAMIENTO,
        FINALIZADO
    }

    private Estado estado;

    public Estado getEstado() {
        return estado;
    }

    public String consultarEstadoJuego(Juego juego) {
        if (juego == null) {
            return "No hay juego creado";
        } else {
            switch (juego.getEstado()) {
                case NO_INICIADO:
                    return "El juego aún no ha sido iniciado";
                case INICIADO_EN_PREPARACION:
                    return "El juego está en preparación";
                case INICIADO_EN_FUNCIONAMIENTO:
                    return "El juego está en funcionamiento";
                case FINALIZADO:
                    return "El juego ha finalizado";
                default:
                    return "Estado del juego desconocido";
            }
        }

    }

    public void setEstado() {
        this.estado = estado;
    }

    public Juego (String idJuego, String descJuego, int numNivell, int puntosPorNivel){
        this.idJuego = idJuego;
        this.descJuego = descJuego;
        this.numNivell = numNivell;
        this.puntosPorNivel = puntosPorNivel;
    }
    public Juego (String juego){
    }

    public Integer getPuntosPorNivel() {
        return puntosPorNivel;
    }



    public void setPuntosPorNivel(Integer puntosPorNivel) {
        this.puntosPorNivel = puntosPorNivel;
    }


    public String getIdJuego() {
        return idJuego;
    }

    public void setIdJuego(String idJuego) {
        this.idJuego = idJuego;
    }

    public String getDescJuego() {
        return descJuego;
    }

    public void setDescJuego(String descJuego) {
        this.descJuego = descJuego;
    }

    public Integer getNumNivell() {
        return numNivell;
    }

    public void setNumNivell(Integer numNivell) {
        this.numNivell = numNivell;
    }

}
