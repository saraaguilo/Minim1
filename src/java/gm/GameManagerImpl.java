package gm;

import org.apache.log4j.Logger;
import java.util.*;

public class GameManagerImpl implements GameManager {
    private List<Juego> juegosList;
    private HashMap<String, Usuario> usuarioHashMap;
    private List<Partida> partidasList;
    private List<Equipo> listaEquipos;
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
    /*
    public Juego crearJuego(String idEquipo, String idUsuario) {
        Partida j = buscarPartida(idUsuario);
        if(j==null){ //=null es que no lo hemos encontrado, asi que se puede crear
            j = new Partida(idEquipo,idUsuario);
            partidasList.add(j);
            logger.info("Juego creado con el equipo " + idEquipo + "añadido");
            return j.getJuego();
        }
        logger.warn("Ya existe un juego con el mismo id.");
        return null;
    }

     */
    public Juego crearJuego(int numEquipos, int dimension) {
        if (!juegosList.isEmpty()) {
            logger.warn("Ya hay un juego en marcha");
            return null;
        }
        Map<String, Equipo> equipos = new HashMap<>();
        for (int i = 0; i < numEquipos; i++) {
            equipos.put(String.valueOf(i), new Equipo(i, dimension));
        }
        Juego juego = new Juego(numEquipos, dimension, Juego.EN_PREPARACION);
        juegosList.add(juego);
        logger.info("Juego creado con " + numEquipos + " equipos de " + dimension + " personas cada uno");
        return juego;
    }

    public void addUser(String idUsuario, String nombreUsuario, String apellido) {
        // Crear un nuevo usuario
        Usuario user = new Usuario(idUsuario);
        // Añadir el usuario a la tabla hash

        usuarioHashMap.put(idUsuario, user);
    }




    //Inicio de una partida de un juego por parte de un usuario

    public Juego iniciarPartida(String idUsuario) {
        Juego juego = buscarJuego(idUsuario);

        if (juego == null) {
            logger.warn("No encuentro el juego " + idUsuario);
            return null;
        }

        if (juego.getEstado() == Juego.EN_FUNCIONAMIENTO) {
            logger.warn("El juego " + juego.getIdJuego() + " ya está en funcionamiento");
            return juego;
        }

        Usuario usuario = buscarUsuario(idUsuario);

        if (usuario == null) {
            logger.warn("No encontramos el usuario " + idUsuario);
            return null;
        }

        if (juego.getDimension()!=0) {
            logger.info("Se ha añadido una partida para el usuario " + idUsuario + " del juego " + juego.getIdJuego());
            return juego;
        }

        juego.setEstado(Juego.EN_FUNCIONAMIENTO);
        logger.info("El juego " + juego.getIdJuego() + " ha sido iniciado, en funcionamiento");
        return juego;
    }

    public int getListaPartidas(){
        return partidasList.size();
    }

    @Override
    public String consultarEstado(Juego juego) {
        if (juego.getEstado() == Juego.NO_INICIADO) {
            return "El juego " + juego.getIdJuego() + "no está iniciado.";
        } else if (juego.getEstado() == Juego.EN_PREPARACION) {
            return "El juego " + juego.getIdJuego() + "está en preparación.";
        } else if (juego.getEstado() == Juego.EN_FUNCIONAMIENTO) {
            return "El juego " + juego.getIdJuego() + "está en funcionamiento.";
        } else if (juego.getEstado() == Juego.FINALIZADO) {
            return "El juego " + juego.getIdJuego() + "ha finalizado.";
        } else {
            return "Estado desconocido para el juego " + juego.getIdJuego() + ".";
        }
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
    public Product buscarProduct(int identificadorProducto) { //PREGUNTAR TONI

        return listaProducts.get(identificadorProducto);
    }

    public Partida buscarPartida(String usuarioId) {
        for (Partida partida : partidasList) {
            if (partida.getUsuario().equals(usuarioId)) {
                return partida;
            }
        }
        return null;
    }
    public Equipo buscarEquipo(int id) { //PREGUNTAR TONI

        return listaEquipos.get(id);
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
    public Usuario añadirUsuario(String idUsuario, String nombre, String apellidos) {
        Usuario usuario = buscarUsuario(idUsuario);
        if (usuario != null) {
            logger.warn("Ya existe un usuario con el mismo identificador.");

        }

        usuario = new Usuario(idUsuario, nombre, apellidos, 25);
        usuarioHashMap.put(idUsuario, usuario);

        logger.info("Usuario " + idUsuario + " creado con éxito.");
        return null;
        // También se puede agregar aquí cualquier otra lógica adicional, como enviar un correo de bienvenida al usuario, por ejemplo.
    }




    @Override
    public void comprarProducto(String idProducto, String idUsuario) {
        Usuario usuario=usuarioHashMap.get(idUsuario);
        Product product=listaProducts.get(Integer.parseInt(idProducto));
        int numPuntos=usuario.getNumPuntos();
        if (idUsuario==idUsuario & numPuntos >= product.getPrecio()) {
            numPuntos = product.getPrecio();
            listaProducts.add(product);
        } else {
            logger.warn("Saldo insuficiente o usuario no existe");
        }

    }

    @Override

    public int consultarVida(String idUsuario) { //consultar vida usuario
        Usuario usuario = usuarioHashMap.get(idUsuario);
        if (usuario == null) {
            logger.warn("No existe el usuario con ID " + idUsuario);
            return -1;
        }
        if (usuario.getEquipo() == null) {
            logger.warn("El usuario " + idUsuario + " no está en una partida");
            return -1;
        }
        int vidaUsuario=usuario.getVida();
        int vidaActual = usuario.getVida();
        logger.info("Vida actual del usuario " + idUsuario + ": " + vidaActual);
        return vidaActual;
    }
    @Override
    public int consultarVidaEquipo(int idEquipo) {
        Equipo equipo = buscarEquipo(idEquipo);
        if (equipo == null) {
            logger.warn("No se ha encontrado el equipo " + idEquipo);

        }

        String vidaEquipo = equipo.getJugadores().get(consultarVidaEquipo(idEquipo));


        logger.info(String.format("El valor de vida del equipo %d es de %d", idEquipo, vidaEquipo));

        return Integer.parseInt(vidaEquipo);
    }
    public void actualizarVidaUsuario(String idUsuario, int cantidadVida) {
        Juego juego = buscarJuego(idUsuario);
        if (juego == null || juego.getEstado() != Juego.EN_FUNCIONAMIENTO) {
            logger.warn("No hay un juego en activo.");
            return;
        }

        Usuario usuario = usuarioHashMap.get(idUsuario);
        if (usuario == null) {
            logger.warn("No se encontró el usuario con el identificador " + idUsuario + ".");
            return;
        }

        int nuevaVida = usuario.getVida() - cantidadVida;
        if (nuevaVida <= 0) {
            nuevaVida = 0;
            logger.warn("El usuario " + usuario.getNombreUsuario() + " ha muerto.");
        }

        usuario.setVida(nuevaVida);
        logger.info("La vida del usuario " + usuario.getNombreUsuario() + " ha sido actualizada a " + nuevaVida + ".");
    }
    @Override
    public int finalizarJuego(String idjuego) {
        Juego juego=new Juego(idjuego);
        if (juego == null) {
            logger.warn("No se puede finalizar un juego nulo");
            return -1;
        }
        if (juego.getEstado() == Juego.FINALIZADO) {
            logger.warn("El juego " + juego.getIdJuego() + " ya ha sido finalizado");
            return -1;
        }
        juego.setEstado(Juego.FINALIZADO);
        logger.info("El juego " + juego.getIdJuego() + " ha sido finalizado");
        return 0;
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
    public int sizeProductos(){
        int ret = this.listaProducts.size();
        logger.info(ret + " producto en la lista");
        return ret;
    }

    public int sizeJuegos(){
        int ret = this.juegosList.size();
        logger.info(ret + " juegos en la lista");
        return ret;
    }

    public void añadirProducto(int idProducto, String descripcion, int precio) {
        Product producto = buscarProduct(idProducto);
        if (producto != null) {
            logger.warn("Ya existe un producto con el mismo identificador.");
            return;
        }

        producto = new Product(idProducto, descripcion, precio);
        listaProducts.add(producto);

        logger.info("Producto " + idProducto + " añadido con éxito.");

        // También se puede agregar aquí cualquier otra lógica adicional, como actualizar el catálogo de la tienda, por ejemplo.
    }




}