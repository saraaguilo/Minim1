import gm.GameManager;
import gm.GameManagerImpl;
import gm.Juego;
import gm.Usuario;
import org.apache.log4j.Logger;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;


public class GameManagerTest {
    private GameManager gm;
    public static final Logger logger = Logger.getLogger(GameManager.class);


    @Before
    public void setUp() {
        //Inicialización de variables antes de cada Test
        gm = GameManagerImpl.getInstance();

        gm.addUsuario(String.valueOf(new Usuario("1", "Sara", "1", 15)));
        gm.addUsuario(String.valueOf(new Usuario("2", "Anna", "2", 10)));

        gm.crearJuego("1", "parchis", 1, 5);
        gm.crearJuego("2", "uno", 2, 3);

    }
    @After
    public void tearDown() {
        //Tareas a realizar después de cada test
        gm.clear();
        gm = null;
    }
    @Test
    public void testAddJuego(){
        logger.info("----- Empezamos con el test AddJuego ------");
        logger.info("Condiciones iniciales: ");
        Assert.assertEquals(2,this.gm.sizeJuegos());

        logger.info("Agregamos un juego nuevo");
        this.gm.crearJuego("3", "ajedrez", 8, 5);
        Assert.assertEquals(3,this.gm.sizeJuegos());
    }
    @Test
    public void testAddUsuario(){
        logger.info("----- Empezamos con el test AddUsuario ------");
        logger.info("Condiciones iniciales: ");
        Assert.assertEquals(2,this.gm.sizeUsuarios());

        logger.info("Agregamos un usuario nuevo");
        this.gm.addUsuario(String.valueOf(new Usuario("3", "Toni", null, 22)));
        Assert.assertEquals(3,this.gm.sizeUsuarios());
    }

    @Test
    public void testInicioPartida(){
        // Crear un juego nuevo
        Juego juego3 = new Juego("3", "ajedrez", 1, 20);
        StringBuilder sb = new StringBuilder();
        sb.append(juego3).append(",");
        String cadenaJ3 = sb.toString();

        // Crear un usuario nuevo
        Usuario usuario3 = new Usuario("3", "Toni", null, 0);
        StringBuilder sp = new StringBuilder();
        sp.append(juego3).append(",");
        String cadenaU3 = sp.toString();

        // Crear una partida para el usuario en el juego
        gm.inicioPartida(cadenaJ3, cadenaU3);
        // Verificar que la partida ha sido creada
        Assert.assertEquals(1, gm.getListaPartidas());

        // Crear otra partida para el mismo usuario en el mismo juego
        gm.inicioPartida(cadenaJ3, cadenaU3);
        // Verificar que no se ha creado otra partida
        Assert.assertEquals(1, gm.getListaPartidas());

        // Crear una partida para un usuario inexistente
        gm.inicioPartida(cadenaU3, "4");
        // Verificar que no se ha creado otra partida
        Assert.assertEquals(1, gm.getListaPartidas());

        // Crear una partida para un juego inexistente
        gm.inicioPartida("4", cadenaJ3);
        // Verificar que no se ha creado otra partida
        Assert.assertEquals(1, gm.getListaPartidas());
    }

    @Test
    public void testFinalizarPartida() {
        // Crear un juego nuevo
        Juego juego3 = new Juego("3", "ajedrez", 1, 20);
        StringBuilder sb = new StringBuilder();
        sb.append(juego3).append(",");
        String cadenaJ3 = sb.toString();

        // Crear un usuario nuevo
        Usuario usuario3 = new Usuario("3", "Toni", null, 0);
        StringBuilder sp = new StringBuilder();
        sp.append(juego3).append(",");
        String cadenaU3 = sp.toString();

        // Crear una partida para el usuario en el juego
        gm.inicioPartida(cadenaJ3, cadenaU3);
    }
}

