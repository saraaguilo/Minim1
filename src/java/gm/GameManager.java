package gm;

import java.util.List;

public interface GameManager {

  public Juego crearJuego(int numEquipos, int dimension);
  public Juego iniciarPartida(String idUsuario);


  void comprarProducto(String idProducto, String idUsuario);


  public List<Partida> partidaUsuario(String nomUsuario);

  //public Usuario addUserToTeam(String idUsuario, int teamId);

    /////////////////////////////////////////////////////////////////

    public Juego buscarJuego(String identificadorJuego);
    public Usuario buscarUsuario(String identificadorUsuario);
    public Partida buscarPartida(String usuarioId);
    public void añadirProducto(int idProducto, String descripcion, int precio);
    public Product buscarProduct(int identificadorProducto);
    public Equipo buscarEquipo(int id);
    public void actualizarVidaUsuario(String idUsuario, int cantidadVida);
    public int finalizarJuego(String idJuego);

    public int consultarVida(String idUsuario);
    public int consultarVidaEquipo(int idEquipo);

    public Usuario añadirUsuario(String idUsuario, String nombre, String apellidos);
    public int sizeProductos();
    public int sizeUsuarios();
    public int sizeJuegos();

    public String consultarEstado(Juego juego);


  public void clear();
}
