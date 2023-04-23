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

        gm.añadirUsuario("1", "Sara", "Aguilo");
        gm.añadirUsuario("2", "Anna", "Sabater");

        gm.crearJuego(3, 6);
        gm.crearJuego(5,8);

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
        this.gm.crearJuego(3,6);
        Assert.assertEquals(3,this.gm.sizeJuegos());
    }
    @Test
    public void testAddUsuario(){
        logger.info("----- Empezamos con el test AddUsuario ------");
        logger.info("Condiciones iniciales: ");
        Assert.assertEquals(2,this.gm.sizeUsuarios());

        logger.info("Agregamos un usuario nuevo");
        this.gm.añadirUsuario("3", "Toni", "Pons");
        Assert.assertEquals(3,this.gm.sizeUsuarios());
    }
    @Test
    public void testAddProduct(){
        logger.info("----- Empezamos con el test AddProduct ------");
        logger.info("Condiciones iniciales: ");
        Assert.assertEquals(2,this.gm.sizeProductos());

        logger.info("Agregamos un producto nuevo");
        this.gm.añadirProducto(3, "Pan", 5);
        Assert.assertEquals(3,this.gm.sizeProductos());
    }


    @Test
    public int testFinalizarJuego() {
        // Crear un juego nuevo
        Juego juego3 = new Juego(6,8,1);
        juego3.getIdJuego();


        // Crear una partida para el usuario en el juego
        int i=gm.finalizarJuego(juego3.getIdJuego());

        return i;
    }
}
