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

/**
 * Root resource (exposed at "myresource" path)
 */
@Api(value = "/game", description = "Endpoint to Game Service")
@Path("game")
public class GameService {
    private GameManager gm;
    public GameService() {
        this.gm = GameManagerImpl.getInstance();
        //Si no hay juegos, crea algunos
        if (gm.sizeJuegos()==0){
            this.gm.crearJuego("1", "parchis", 1, 5);
            this.gm.crearJuego("2", "uno", 2, 3);
            this.gm.crearJuego("3", "ajedrez", 1, 20);

        }
        //Si no hay usuarios, crea algunos
        if (gm.sizeUsuarios()==0){
            this.gm.addUsuario("Sara");
            this.gm.addUsuario("Anna");
        }
        //Inicia el juego "Parchis" para el usuario "Sara"
        this.gm.inicioPartida("1","Sara");
    }
    @POST
    @ApiOperation(value = "Create Game", notes = "")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Game created", response = Juego.class),
            @ApiResponse(code = 400, message = "Invalid request"),
    })
    @Path("/juegos")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response crearJuego(Juego request) {
        Juego juego = new Juego(request.getIdJuego(), request.getDescJuego(), request.getNumNivell(), request.getPuntosPorNivel());
        Juego j = this.gm.crearJuego(juego.getIdJuego(), juego.getDescJuego(), juego.getNumNivell(), juego.getPuntosPorNivel());
        if (j == null) {
            return Response.status(400).build();
        }
        return Response.status(201).entity(j).build();
    }

    @GET
    @ApiOperation(value = "get actual level", notes = "Get the actual lvl of a player")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "All good", response = Juego.class),
            @ApiResponse(code = 404, message = "Player or game does not exists"),
    })
    @Path("/usuario/{idU}/NumNivellActual")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getNumNivellActual(@PathParam("idU") String idU) {
        int lvl = this.gm.getNumNivellActual(idU);
        if (lvl>=0){
            return Response.status(201).entity(lvl).build();
        }
        return Response.status(404).build();
    }

    @PUT
    @ApiOperation(value = "Start a game", notes = "Start a game?")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "All good", response = Juego.class),
            @ApiResponse(code = 404, message = "Game does not exists"),
    })
    @Path("/usuario/{idU}/inicioPartida/{idJ}")
    public Response inicioPartida(@PathParam("idJ")String idJ,@PathParam("idU") String idU) {
        Juego j = this.gm.inicioPartida(idJ,idU);
        if(j!=null){
            return Response.status(201).entity(j).build();
        }
        return Response.status(404).build();
    }

    @PUT
    @ApiOperation(value = "End a match", notes = "End a match")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Match ended"),
            @ApiResponse(code = 404, message = "Player/match does not exist"),
    })
    @Path("/usuario/{idU}/finalizarPartida")
    @Produces(MediaType.APPLICATION_JSON)
    public Response finalizarPartida(@PathParam("idU") String id) {
        Usuario u = this.gm.finalizarPartida(id);
        if (u!=null){
            return Response.status(201).build();
        }
        return Response.status(404).build();
    }

    @GET
    @ApiOperation(value = "get users by game", notes = "Get the users that have played a specific game")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "All good", response = Usuario.class, responseContainer="List"),
            @ApiResponse(code = 404, message = "Game does not exists"),
    })
    @Path("/juego/{juego}/usuarios")
    @Produces(MediaType.APPLICATION_JSON)
    public Response consultarUsuariosPorJuego(@PathParam("juego") String juego) {
        Juego juegoObj = new Juego(juego); // create the game object from the string parameter
        List<Usuario> usuarios = (List<Usuario>) this.gm.consultarUsuariosPorJuego(juegoObj);
        if (usuarios == null) {
            return Response.status(404).build();
        }
        return Response.status(201).entity(usuarios).build();
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
    @POST
    @ApiOperation(value = "Añadir usuario en el sistema", notes = "")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "All good", response = Usuario.class, responseContainer="List"),
            @ApiResponse(code = 404, message = "Something went wrong")
    })
    @Path("/usuario/{idUsuario}/addUsuario")
    @Produces(MediaType.APPLICATION_JSON)
    public Response addUsuario(@PathParam("idUsuario") String idUser) {
        Usuario u = this.gm.finalizarPartida(idUser);
        if (u!=null){
            return Response.status(201).build();
        }
        return Response.status(404).build();

    }
    @POST
    @ApiOperation(value = "Añadir producto en la tienda", notes = "")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "All good", response = Usuario.class, responseContainer="List"),
            @ApiResponse(code = 404, message = "Something went wrong")
    })
    @Path("/usuario/{idProduct}/addProducto")
    @Produces(MediaType.APPLICATION_JSON)
    public Response addProduct(@PathParam("idProduct") String idProduct) {
        Product u = this.gm.addProduct(idProduct);
        if (u!=null){
            return Response.status(201).build();
        }
        return Response.status(404).build();

    }
    @GET
    @ApiOperation(value = "Get el estado del juego", notes = "Estado")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "All good", response = Juego.class),
            @ApiResponse(code = 404, message = "Player or game does not exists"),
    })
    @Path("/usuario/{juego}/consultarEstadoJuego")
    @Produces(MediaType.APPLICATION_JSON)
    public Response consultarEstadoJuego(@PathParam("juego") Juego juego) {
        int estado = Integer.parseInt(this.gm.consultarEstadoJuego(juego));
        if (estado >=0){
            return Response.status(201).entity(estado).build();
        }
        return Response.status(404).build();
    }






}