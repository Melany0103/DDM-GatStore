package com.ventas.app.model;

public class Producto {
    private String cliente;
    private String nombre;
    private double precioBase;
    private int cantidad;
    private String tipoVenta; // "Menor" o "Mayor"

    public Producto() {}

    // Lógica POO: El objeto determina el precio unitario final
    public double getPrecioFinalUnitario() {
        if ("Mayor".equals(tipoVenta)) {
            return precioBase * 0.80; // 20% de descuento automático
        }
        return precioBase;
    }

    public double calcularTotal() {
        return getPrecioFinalUnitario() * this.cantidad;
    }

    // Getters y Setters
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
}
