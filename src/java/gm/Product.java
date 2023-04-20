package gm;

import java.util.ArrayList;
import java.util.List;

public class Product {

    private String idProducto; //1
    private String descripcion; //2
    private int precio; //3


    public String getIdProducto() {
        return idProducto;
    }

    public void setIdProducto(String idProducto) {
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
    public Product(String idProducto, String descripcion, int precio){
        this.idProducto = idProducto;
        this.descripcion = descripcion;
        this.precio = precio;

    }
    public Product(){
    }
}
