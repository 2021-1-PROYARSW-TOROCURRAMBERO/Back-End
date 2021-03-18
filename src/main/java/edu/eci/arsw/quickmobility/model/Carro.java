package edu.eci.arsw.quickmobility.model;

import org.springframework.data.annotation.Id;

public class Carro {
    @Id
    public String id;
    public String placa;
    public String marca;
    public String color;
    public String modelo;

    public Carro(){

    }

    public Carro(String placa, String marca, String color, String modelo){
        this.placa = placa;
        this.marca = marca;
        this.color = color;
        this.modelo = modelo;
    }

    public String getPlaca() {
        return placa;
    }

    public void setPlaca(String placa) {
        this.placa = placa;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }
}
