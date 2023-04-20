package gm;

import java.util.List;

public interface GameManager {
  //  public void addJuego (Juego juego);
    public Juego crearJuego(String idJuego, String descJuego, int nivelActual, int puntosPorNivel);
    public Juego inicioPartida(String idJ, String idU);
    public int getNumNivellActual(String idU);

    public void comprarProducto(Product producto, String idUsuario, int numPuntos);

  public String getNumPuntos(String idU);

    public Usuario finalizarPartida(String user);
    // Consulta de usuarios que han participado en un juego ordenado por puntuación (descendente)
    public Usuario consultarUsuariosPorJuego(Juego juego);
    public List<Partida> partidaUsuario(String nomUsuario);

    public Usuario addUserToTeam(String idUsuario, int teamId);

    /////////////////////////////////////////////////////////////////

    public Juego buscarJuego(String identificadorJuego);
    public Usuario buscarUsuario(String identificadorUsuario);
    public Partida buscarPartida(String usuarioId);
    public Usuario getUser(String idUser);
    public Usuario addUsuario(String idUser);
    public int sizeUsuarios();
    public int sizeJuegos();
    public int getListaPartidas();
    public String consultarEstadoJuego(Juego juego);

  //proporcionar información sobre la actividad de un usuario en un juego en particular, incluyendo detalles
  // como el nivel actual, los puntos acumulados y la fecha de inicio de las partidas


  public void clear();
    public Product addProduct(String idProduct);
}