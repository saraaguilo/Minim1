package gm;
import java.util.*;

public class Equipo {
    private int id;
    private int dimension;
    private ArrayList<String> jugadores;
    private int vida;

    public int getVida() {
        return vida;
    }

    public void setVida(int vida) {
        vida = vida;
    }

    public Equipo(int id, int dimension) {
        this.id = id;
        this.dimension = dimension;
        this.jugadores = new ArrayList<String>();
        this.vida=vida;
    }
    public Equipo(){

    }


    public int getId() {
        return id;
    }

    public int getDimension() {
        return dimension;
    }

    public ArrayList<String> getJugadores() {
        return jugadores;
    }

    public void addJugador(String jugador) {
        if (jugadores.size() < dimension) {
            jugadores.add(jugador);
        }
    }



}