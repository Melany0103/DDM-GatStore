package com.ventas.app.model;
import java.time.LocalDateTime;
import java.util.UUID;

public class Producto {
    private String id;
    private String cliente;
    private String nombre;
    private double precioBase;
    private int cantidad;
    private String tipoVenta;
    private LocalDateTime fecha;

    public Producto() {
        this.id = UUID.randomUUID().toString().substring(0, 5).toUpperCase();
        this.fecha = LocalDateTime.now();
    }

    public double getPrecioFinalUnitario() {
        return "Mayor".equals(tipoVenta) ? precioBase * 0.80 : precioBase;
    }

    public double calcularTotal() {
        return getPrecioFinalUnitario() * this.cantidad;
    }

    // Getters y Setters
    public String getId() { return id; }
    public String getCliente() { return cliente; }
    public void setCliente(String cliente) { this.cliente = cliente; }
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public double getPrecioBase() { return precioBase; }
    public void setPrecioBase(double precioBase) { this.precioBase = precioBase; }
    public int getCantidad() { return cantidad; }
    public void setCantidad(int cantidad) { this.cantidad = cantidad; }
    public String getTipoVenta() { return tipoVenta; }
    public void setTipoVenta(String tipoVenta) { this.tipoVenta = tipoVenta; }
    public LocalDateTime getFecha() { return fecha; }
}