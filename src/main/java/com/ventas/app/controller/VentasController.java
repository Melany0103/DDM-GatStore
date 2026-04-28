package com.ventas.app.controller;

import com.ventas.app.model.Producto;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.util.LinkedHashMap;
import java.util.Map;

@Controller
public class VentasController {

    @GetMapping("/")
    public String formulario(Model model) {
        model.addAttribute("producto", new Producto());
        model.addAttribute("catalogo", getCatalogo());
        return "index";
    }

    @PostMapping("/calcular")
    public String calcular(@ModelAttribute Producto producto, Model model) {
        // Asignamos el precio base según el producto seleccionado del catálogo
        Double precioBase = getCatalogo().get(producto.getNombre());
        producto.setPrecioBase(precioBase != null ? precioBase : 0.0);
        
        double total = producto.calcularTotal();
        
        model.addAttribute("total", total);
        model.addAttribute("precioUnitario", producto.getPrecioFinalUnitario());
        model.addAttribute("producto", producto);
        model.addAttribute("catalogo", getCatalogo());
        model.addAttribute("mensaje", "¡Orden de Compra DDM generada con éxito!");
        
        return "index";
    }

    // Catálogo especializado de accesorios para gatos
    private Map<String, Double> getCatalogo() {
        Map<String, Double> productos = new LinkedHashMap<>();
        productos.put("Rascador Torre Premium", 150.00);
        productos.put("Arena Sanitaria 10kg", 45.00);
        productos.put("Collar Antipulgas", 25.00);
        productos.put("Fuente de Agua LED", 85.00);
        productos.put("Cama Iglú Térmica", 75.00);
        productos.put("Pack Juguetes con Catnip", 35.00);
        return productos;
    }
}