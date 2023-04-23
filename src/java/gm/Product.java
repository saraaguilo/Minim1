package gm;

import java.util.ArrayList;
import java.util.List;

public class Product {

    private int idProducto; //1
    private String descripcion; //2
    private int precio; //33


    public int getIdProducto() {
        return idProducto;
    }

    public void setIdProducto(int idProducto) {
        this.idProducto = idProducto;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public int getPrecio() {
        return precio;
    }

    public void setPrecio(int precio) {
        this.precio = precio;
    }
    public Product(int idProducto, String descripcion, int precio){
        this.idProducto = idProducto;
        this.descripcion = descripcion;
        this.precio = precio;

    }
    public Product(){
    }
}
