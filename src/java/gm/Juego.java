package gm;

public class Juego{
    public static final int NO_INICIADO=0;
    public static final int EN_PREPARACION=1;
    public static final int EN_FUNCIONAMIENTO=2;
    public static final int FINALIZADO=3;


    private String idJuego;
    //private String descJuego;
    private int numNivell;
    //private int puntosPorNivel;

    private int estado;
    private int numEquipos;
    private int dimension;


    public int getEstado() {
        return estado;
    }

    public void setEstado(int estado) {
        this.estado = estado;
    }

    public String consultarEstadoJuego(Juego juego) {
        if (juego == null) {
            return "No hay juego creado";
        } else {
            switch (juego.getEstado()) {
                case NO_INICIADO:
                    return "El juego aún no ha sido iniciado";
                case EN_PREPARACION:
                    return "El juego está en preparación";
                case EN_FUNCIONAMIENTO:
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

    public Juego (int numEquipos, int dimension, int estado){
        this.idJuego = idJuego;
        this.numEquipos = numEquipos;
        this.dimension = dimension;
        this.estado = NO_INICIADO;
    }
    public Juego (String idjuego){
    }


    public String getIdJuego() {
        return idJuego;
    }

    public void setIdJuego(String idJuego) {
        this.idJuego = idJuego;
    }

    public int getNumNivell() {
        return numNivell;
    }

    public void setNumNivell(int numNivell) {
        this.numNivell = numNivell;
    }

    public int getNumEquipos() {
        return numEquipos;
    }

    public void setNumEquipos(int numEquipos) {
        this.numEquipos = numEquipos;
    }

    public int getDimension() {
        return dimension;
    }

    public void setDimension(int dimension) {
        this.dimension = dimension;
    }
}
