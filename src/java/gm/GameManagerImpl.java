package gm;

import org.apache.log4j.Logger;
import java.util.*;

public class GameManagerImpl implements GameManager {
    private List<Juego> juegosList;
    private HashMap<String, Usuario> usuarioHashMap;
    private List<Partida> partidasList;
    private List<Product> listaProducts;
    public static GameManagerImpl instance = null;
    public static final Logger logger = Logger.getLogger(GameManagerImpl.class);

    private GameManagerImpl() {
        juegosList = new ArrayList<>();
        usuarioHashMap = new HashMap<>();
        partidasList = new ArrayList<>();
    }

    public static GameManagerImpl getInstance() {
        if (instance == null) {
            instance = new GameManagerImpl();
        }
        return instance;
    }

    public static void setInstance(GameManagerImpl instance) {
        GameManagerImpl.instance = instance;
    }


    //Creación de un juego
    public Juego crearJuego(String idJuego, String descJuego, int nivelActual, int puntosPorNivel) {
        Juego j = buscarJuego(idJuego);
        if(j==null){ //=null es que no lo hemos encontrado, asi que se puede crear
            j = new Juego(idJuego,descJuego,nivelActual,puntosPorNivel);
            juegosList.add(j);
            logger.info("Juego " + idJuego + "añadido");
            return j;
        }
        logger.warn("Ya existe un juego con el mismo id.");
        return null;
    }
    public void addUser(String idUsuario, String nombreUsuario, String apellido) {
        // Crear un nuevo usuario
        Usuario user = new Usuario(idUsuario, nombreUsuario, apellido);
        // Añadir el usuario a la tabla hash

        usuarioHashMap.put(idUsuario, user);
    }



    //Inicio de una partida de un juego por parte de un usuario
    @Override
    public Juego inicioPartida(String idJ, String idU) {
        Juego juego = buscarJuego(idJ);
        if (juego == null) {
            /*logger.info("No existe este juego");
            return juego;*/
            logger.warn("No se ha encontrado el juego " + idJ);
            return null;
        }
        Usuario usuario = buscarUsuario(idU);
        if (usuario == null) {
            /*logger.info("No existe este usuario");
            return juego;*/
            logger.warn("No se ha encontrado el usuario " + idU);
            return null;
        }

        for (Partida partida : usuario.getPartidas()) {
            if (partida.getJuego().getIdJuego().equals(idJ)) {
                logger.warn("El usuario " + usuario.getIdUsuario() + " ya tiene una partida en curso para este juego.");
                return null;
            }
        }

        Partida partida = new Partida(juego, usuario);
        //partida.setPuntosAcumulados(50);// comença amb 50 punts
        //partida.setNivelActual(1); // comença al nivell 1
        partidasList.add(partida);
        usuario.addPartida(partida);
        usuario.addUserToTeam(idU);

        logger.info("Se ha añadido una partida para el usuario " + usuario.getIdUsuario() + " en el juego " + juego.getIdJuego() + "con el estado" + juego.getEstado() );

        return juego;
        /*logger.info("Añadiendo partida para el usuario: "+ usuario.getIdUsuario());
        this.usuarioHashMap.get(idU).addPartida(partida);
        logger.info("La partida ha comenzado para el usuario " + idU + " en el juego " + idJ);
        return juego;*/
    }

    public int getListaPartidas(){
        return partidasList.size();
    }

    @Override
    public String consultarEstadoJuego(Juego juego) {
        return null;
    }

    @Override


    public Juego buscarJuego(String identificadorJuego) {
        for (Juego juego : juegosList) {
            if (juego.getIdJuego().equals(identificadorJuego)) {
                return juego;
            }
        }
        return null; // Si no se encuentra el juego, se devuelve null
    }

    public Usuario buscarUsuario(String identificadorUsuario) { //PREGUNTAR TONI

        return usuarioHashMap.get(identificadorUsuario);
    }

    public Partida buscarPartida(String usuarioId) {
        for (Partida partida : partidasList) {
            if (partida.getUsuario().equals(usuarioId)) {
                return partida;
            }
        }
        return null;
    }
    public Usuario getUser(String idUser) {
        logger.info("Buscando un jugador con el siguiente id: " + idUser);
        if(this.usuarioHashMap.get(idUser)==null){
            logger.warn("Jugador no encontrado");
            return null;
        }
        return this.usuarioHashMap.get(idUser);
    }

    @Override
    public Usuario addUsuario(String idUser) {
        return null;
    }


    @Override
    public int getNumNivellActual(String idU) {
        Partida partida = buscarPartida(idU);
        if (partida == null) {
            logger.warn("No existe la partida");
            return 0;
        }
        logger.info("Usuario " + idU + " en partida " + partida.getId() + " en nivel " + partida.getNivelActual());
        //Si no es passesin parametres seria de la seguent manera:
        /*public int getNumNivellActual() {
            int numNivell = juegosList.size();
            logger.info("Numero de niveles:" + numNivell);
        return numNivell;*/
        return partida.getNivelActual();
    }


    @Override
    public void comprarProducto(Product producto, String idUsuario, int numPuntos) {
        if (idUsuario==idUsuario & numPuntos >= producto.getPrecio()) {
            numPuntos = producto.getPrecio();
            listaProducts.add(producto);
        } else {
            System.out.println("Error: Saldo insuficiente o usuario no existe.");
        }

    }

    @Override
    public String getNumPuntos(String idU) {
        Partida partida = buscarPartida(idU);
        if (partida == null) {
            logger.warn("No existe la partida");
            return idU;
        }
        logger.info("Usuario " + idU + " en partida " + partida.getId() + " con los puntos " + partida.getPuntosAcumulados());
        //return idU;
        return Integer.toString(partida.getPuntosAcumulados());
    }




    //Si no es passesin parametres seria de la seguent manera:
    /*public int getNumPuntos() {
        int numPuntos = usuarioHashMap.size();
        logger.info("Numero de niveles:" + numPuntos);
        return numPuntos;
    }*/



    @Override
    public Usuario finalizarPartida(String user) {
        Partida partida = buscarPartida(user);
        if (partida == null) {
            logger.warn("No se encontró una partida en curso para el usuario especificado");
             return null;
        }
        Usuario usuario = partida.getUsuario();
        Juego juego = partida.getJuego();
        //int puntuacionActual = partida.getPuntosAcumulados();
        partidasList.remove(partida);
        logger.info("Partida finalizada para el usuario " + user);
        juego.getEstado();
        return null;
    }

    // Consulta de usuarios que han participado en un juego ordenado por puntuación (descendente)
    public Usuario consultarUsuariosPorJuego(Juego juego){
        List<Usuario> usuarios = new ArrayList<>();
        if (!usuarioHashMap.containsKey(juego)) {
            logger.warn("El juego no existe");
            return null;
        }
        List<Partida> partidas = new ArrayList<>();
        for (Partida partida : partidasList) {
            if (partida.getJuego().equals(juego)) {
                partidas.add(partida);
            }
        }
        // Ordenar las partidas por puntuación descendente utilizando el comparador
        //Collections.sort(partidas, (p1, p2) -> p2.getPuntosAcumulados().compareTo(p1.getPuntosAcumulados()));
        Collections.sort(partidas, new Comparator<Partida>() {
            @Override
            public int compare(Partida p1, Partida p2) {
                return Integer.compare(p2.getPuntosAcumulados(), p1.getPuntosAcumulados());
            }
        });
        return null;
    }

    //devuelve una lista de todas las partidas registradas en el sistema para un usuario dado
    @Override
    public List<Partida> partidaUsuario(String nomUsuario) {
        List<Partida> partidasUsuario = new ArrayList<>();
        //busca el objeto Usuario en la HashMap usando el nombre de usuario proporcionado como clave
        if (!usuarioHashMap.containsKey(nomUsuario)) {
            logger.warn("El usuario no existe");
            return null;
        }
        return usuarioHashMap.get(nomUsuario).getPartidas();
    }

    //proporcionar información sobre la actividad de un usuario en un juego en particular, incluyendo detalles
    // como el nivel actual, los puntos acumulados y la fecha de inicio de las partidas

    public void clear(){
        partidasList.clear();
        usuarioHashMap.clear();
        juegosList.clear();
    }
    public int sizeUsuarios(){
        int ret = this.usuarioHashMap.size();
        logger.info(ret + " usuario en la lista");
        return ret;
    }

    public int sizeJuegos(){
        int ret = this.juegosList.size();
        logger.info(ret + " juegos en la lista");
        return ret;
    }
    @Override
    public Usuario addUserToTeam(String idUsuario, int teamId) {
        Equipo equipo =new Equipo();
        equipo.addJugador(idUsuario);

        System.out.println("El usuario con id " + idUsuario + " fue agregado al equipo " + teamId + ".");
        return null;
    }
    public Product addProduct(String idProduct) {
        Product product= new Product();
        listaProducts.add(product);

        System.out.println("El product con id " + idProduct + " fue agregado");
    return null;
    }




}