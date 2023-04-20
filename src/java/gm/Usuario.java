package gm;

import java.util.ArrayList;
import java.util.List;

import static gm.GameManagerImpl.logger;

public class Usuario {
    private String idUsuario; //1
    private String nombreUsuario; //2
    private String apellido; //3
    private String idPartidaActual;
    private int numPuntos; //4
    private List<Partida> partidasJugadasList;
//    private List<Usuario> listaUsuarios;
    private boolean jugando;
    private int Vida;

    public int getVida() {
        return Vida;
    }

    public void setVida(int vida) {
        Vida = vida;
    }

    private List<Product> listaProducts;

    public List<Product> getListaProducts() {
        return listaProducts;
    }

    public void setListaProducts(List<Product> listaProducts) {
        this.listaProducts = listaProducts;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public Usuario(String idUsuario, String nombreUsuario, String apellido, int numPuntos){
        this.idUsuario = idUsuario;
        this.nombreUsuario = nombreUsuario;
        this.apellido = apellido;
        this.numPuntos = 25; //saldo
        partidasJugadasList = new ArrayList<>();
       // listaUsuarios = new ArrayList<>();

    }
    public Usuario(String idUsuario, String nombreUsuario, String apellido){
    }
    public Product addProduct(String idUser, Product product){
        if(idUser !=idUsuario){
            logger.warn("El usuario no existe, lo añadimos");

        }

        // Añadir el producto a la lista de productos
        listaProducts.add(product);
        return null;

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
    public void comprarProducto(Product producto,String idUsuario) {
        if (idUsuario==idUsuario & numPuntos >= producto.getPrecio()) {
            numPuntos = producto.getPrecio();
            listaProducts.add(producto);
        } else {
            System.out.println("Error: Saldo insuficiente o usuario no existe.");
        }
    }
    public void addUserToTeam(String idUsuario) {
        Equipo equipo =new Equipo();
        equipo.addJugador(idUsuario);

        System.out.println("El usuario con id " + idUsuario + " fue agregado al equipo ");
    }
}
