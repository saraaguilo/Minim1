package gm;

import java.util.ArrayList;
import java.util.List;

public class Usuario {
    private String idUsuario;
    private String nombreUsuario;
    private String idPartidaActual;
    private int numPuntos;
    private List<Partida> partidasJugadasList;
//    private List<Usuario> listaUsuarios;
    private boolean jugando;

    public Usuario(String idUsuario, String nombreUsuario, String idPartidaActual, int numPuntos){
        this.idUsuario = idUsuario;
        this.nombreUsuario = nombreUsuario;
        this.idPartidaActual = idPartidaActual;
        this.numPuntos = numPuntos;
        partidasJugadasList = new ArrayList<>();
       // listaUsuarios = new ArrayList<>();

    }
    public Usuario(){
    }

    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }



    public String getIdPartidaActual() {
        return idPartidaActual;
    }

    public void setIdPartidaActual(String idPartidaActual) {
        this.idPartidaActual = idPartidaActual;
    }

    public List<Partida> getPartidasJugadasList() {
        return partidasJugadasList;
    }

    public void setPartidasJugadasList(List<Partida> partidasJugadasList) {
        this.partidasJugadasList = partidasJugadasList;
    }

   /* public List<Usuario> getListaUsuarios() {
        return listaUsuarios;
    }
*/
    public String getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(String idUsuario) {
        this.idUsuario = idUsuario;
    }

    public int getNumPuntos() {
        return numPuntos;
    }

    public void setNumPuntos(int numPuntos) {
        this.numPuntos = numPuntos;
    }


   /* public void setListaUsuarios(List<Usuario> listaUsuarios) {
        this.listaUsuarios = listaUsuarios;
    }*/
    public List<Partida> getPartidas() {
        List<Partida> partidasUsuario = new ArrayList<>();
        for (Partida partida : partidasJugadasList) {
            if (partida.getUsuario().equals(this)) {
                partidasUsuario.add(partida);
            }
        }
        return partidasUsuario;
    }
    public boolean getJugando() {
        return jugando;
    }

    public void setJugando(boolean jugando) {
        this.jugando = jugando;
    }

    public void addPartida(Partida partida){
        this.partidasJugadasList.add(Integer.parseInt(partida.getId()), partida);
        this.jugando=true;
    }
}
