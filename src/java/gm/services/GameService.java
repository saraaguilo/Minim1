package gm.services;

import gm.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import gm.Juego;
import javax.ws.rs.*;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

import static gm.GameManagerImpl.logger;


@Api(value = "/game", description = "Endpoint to Game Service")
@Path("game")
public class GameService {
    private GameManager gm;
    public GameService() {
        this.gm = GameManagerImpl.getInstance();
        //Si no hay juegos, crea algunos
        if (gm.sizeJuegos()==0){
            this.gm.crearJuego(1,3);
            this.gm.crearJuego(4,5);
            this.gm.crearJuego(2,8);

        }
        //Si no hay usuarios, crea algunos
        if (gm.sizeUsuarios()==0){
            this.gm.añadirUsuario("5", "Miquel", "Campos");
            this.gm.añadirUsuario("7", "Marcos", "Cuevas");
        }
        //Inicia el juego "Parchis" para el usuario "Sara"
        this.gm.iniciarPartida("5");

        if (gm.sizeProductos() == 0) {
            this.gm.añadirProducto(1, "Llavero", 18);
            this.gm.añadirProducto(2, "Zapatillas", 20);
        }
    }
    @POST
    @ApiOperation(value = "Create Game", notes = "")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Game created", response = Juego.class),
            @ApiResponse(code = 400, message = "Invalid request"),
    })
    @Path("/juegos/crearJuego/{N}/{P}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response crearJuego(Juego request) {
        Juego juego = new Juego(request.getNumEquipos(), request.getDimension(), request.getEstado());
        Juego j = this.gm.crearJuego(juego.getNumEquipos() , juego.getDimension());
        if (j == null) {
            return Response.status(400).build();
        }
        return Response.status(201).entity(j).build();
    }

    @POST
    @ApiOperation(value = "Añadir usuario en el sistema", notes = "")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "All good", response = Usuario.class, responseContainer="List"),
            @ApiResponse(code = 404, message = "Something went wrong")
    })
    @Path("/usuario/{idUsuario}/{nombre}/{apellidos}/añadirUsuario")
    @Produces(MediaType.APPLICATION_JSON)
    public Response añadirUsuario(@PathParam("idUsuario") String idUsuario, @PathParam("nombre") String nombre, @PathParam("apellidos") String apellidos) {
        this.gm.añadirUsuario(idUsuario,nombre,apellidos);
        Usuario u = this.gm.buscarUsuario(idUsuario);
        if (u!=null){
            return Response.status(201).build();
        }
        return Response.status(404).build();

    }
    @POST
    @ApiOperation(value = "Añadir producto en el sistema", notes = "")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "All good", response = Product.class, responseContainer="List"),
            @ApiResponse(code = 404, message = "Something went wrong")
    })
    @Path("/usuario/{idUsuario}/{idProducto}/{descripcion}/{precio}/añadirProducto")
    @Produces(MediaType.APPLICATION_JSON)
    public Response añadirProducto(@PathParam("idProducto") int idProducto, @PathParam("descripcion") String descripcion, @PathParam("precio") int precio) {
        this.gm.añadirProducto(idProducto,descripcion,precio);
        Product u = this.gm.buscarProduct(idProducto);
        if (u!=null){
            return Response.status(201).build();
        }
        return Response.status(404).build();

    }

    @PUT
    @ApiOperation(value = "Iniciar partida", notes = "Start a game")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "All good", response = Juego.class),
            @ApiResponse(code = 404, message = "Game does not exists"),
    })
    @Path("/usuario/{idUsuario}/inicioPartida")
    public Response iniciarPartida(@PathParam("idUsuario") String idUsuario) {
        Juego j = this.gm.iniciarPartida(idUsuario);
        if(j!=null){
            return Response.status(201).entity(j).build();
        }
        return Response.status(404).build();
    }

    @PUT
    @ApiOperation(value = "Comprar producto", notes = "")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "All good", response = Product.class),
            @ApiResponse(code = 404, message = "Product does not exists"),
    })
    @Path("/usuario/{idUsuario}/{idProducto}/ComprarProducto")
    public Response comprarProducto(@PathParam("idProducto") String idProducto, @PathParam("idUsuario") String idUsuario) {
        Product j = this.gm.buscarProduct(Integer.parseInt(idProducto));
        if(j!=null){
            return Response.status(201).entity(j).build();
        }

        Usuario usuario=this.gm.buscarUsuario(idUsuario);

        Product producto=this.gm.buscarProduct(Integer.parseInt(idProducto));
        int price=producto.getPrecio();
        int numPuntos=usuario.getNumPuntos();
        if (usuario.getNumPuntos()>price){
            usuario.setNumPuntos(numPuntos-price);
            return Response.status(200).build();
        }
        return Response.status(404).build();
    }

    @PUT
    @ApiOperation(value = "End a match", notes = "End a match")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Match ended"),
            @ApiResponse(code = 404, message = "Player/match does not exist"),
    })
    @Path("/usuario/{idJuego}/finalizarPartida")
    @Produces(MediaType.APPLICATION_JSON)
    public Response finalizarJuego(@PathParam("idJuego") String idJuego) {
        Juego juego=this.gm.buscarJuego(idJuego);
        int finalizar=this.gm.finalizarJuego(idJuego);
        if (finalizar!=-1){
            return Response.status(201).build();
        }
        return Response.status(404).build();
    }


    @GET
    @ApiOperation(value = "Get player's match", notes = "")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "All good", response = Partida.class, responseContainer="List"),
            @ApiResponse(code = 404, message = "Something went wrong")
    })
    @Path("/usuario/{nomUsuario}/partidaUsuario")
    @Produces(MediaType.APPLICATION_JSON)
    public Response partidaUsuario(@PathParam("nomUsuario") String id) {
        List<Partida> matches = this.gm.partidaUsuario(id);
        if(matches.size()!=0){
            GenericEntity<List<Partida>> entity = new GenericEntity<List<Partida>>(matches){};

            return Response.status(201).entity(entity).build();
        }
        return Response.status(404).build();
    }
    @PUT
    @ApiOperation(value = "Actualizar vida usuario", notes = " ")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 404, message = "NOT_FOUND")
    })
    @Path("/usuario/ActualizarVidaUsuario/{idUsuario}/{cantidad}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response ActualizarVidaUsuario(@PathParam("idUsuario") String idUsuario, @PathParam("cantidad") int cantidad) {
        Usuario usuario= this.gm.buscarUsuario(idUsuario);
        if (usuario == null) {
            logger.warn("No existe el usuario " + idUsuario);
            return Response.status(404).build();
        }
        this.gm.actualizarVidaUsuario(idUsuario, cantidad);
        int actualizoVida=usuario.getVida()+cantidad;

        usuario.setVida(actualizoVida);
        logger.info("Vida usuario " +usuario.getNombreUsuario()+"actualizada" +actualizoVida);

        return Response.status(200).build();
    }


    @GET
    @ApiOperation(value = "Get el estado del juego", notes = "Estado")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "All good", response = Juego.class),
            @ApiResponse(code = 404, message = "Player or game does not exists"),
    })
    @Path("/usuario/{juego}/consultarEstadoJuego")
    @Produces(MediaType.APPLICATION_JSON)
    public Response consultarEstado(@PathParam("juego") Juego juego) {
        int estado = Integer.parseInt(this.gm.consultarEstado(juego));
        if (estado >=0){
            return Response.status(201).entity(estado).build();
        }
        return Response.status(404).build();
    }
    @GET
    @ApiOperation(value = "Consultar vida de usuario", notes = " ")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Usuario encontrado"),
            @ApiResponse(code = 404, message = "NOT_FOUND")
    })
    @Path("/usuario/consultarVida/{idUsuario}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response consultarVida(@PathParam("idUsuario") String idUsuario) {
        int vida = this.gm.consultarVida(idUsuario);
        if (vida == -1) {//
            return Response.status(404).build();
        }
        return Response.status(200).entity(vida).build();
    }
    @GET
    @ApiOperation(value = "Consultar vida equipo", notes = " ")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Usuario encontrado"),
            @ApiResponse(code = 404, message = "NOT_FOUND")
    })
    @Path("/usuario/consultarVidaEquipo/{idEquipo}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response consultarVidaEquipo(@PathParam("idEquipo") int idEquipo) {
        int vidaEquipo = this.gm.consultarVidaEquipo(idEquipo);
        if (vidaEquipo == -1) {// si no existe NOTFOUND
            return Response.status(404).build();
        }
        return Response.status(200).entity(vidaEquipo).build();
    }




}






