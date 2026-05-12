package com.ventas.app.controller;

import com.ventas.app.model.Producto;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import jakarta.servlet.http.HttpSession;
import java.util.*;

@Controller
public class VentasController {

    private static List<Producto> historialVentas = new ArrayList<>();
    private static Map<String, Integer> inventario = new HashMap<>();

    public VentasController() {
        if(inventario.isEmpty()) {
            inventario.put("Croquetas Premium 2kg", 20);
            inventario.put("Pate de Atun Gourmet", 50);
            inventario.put("Rascador Torre 1.2m", 8);
            inventario.put("Caña de Plumas", 30);
            inventario.put("Arena Biodegradable 5kg", 15);
        }
    }

    @GetMapping("/")
    public String tienda(Model model) {
        model.addAttribute("producto", new Producto());
        model.addAttribute("categorias", getCategoriasConProductos());
        model.addAttribute("inventario", inventario);
        return "index";
    }

    @GetMapping("/admin/login")
    public String loginPage() { return "login"; }

    @PostMapping("/admin/auth")
    public String auth(@RequestParam String password, HttpSession session) {
        // AQUÍ EDITAS TU CLAVE PERSONAL
        if ("1234".equals(password)) {
            session.setAttribute("adminLogueado", true);
            return "redirect:/admin";
        }
        return "redirect:/admin/login?error=true";
    }

    @GetMapping("/admin")
    public String panelAdmin(Model model, HttpSession session) {
        if (session.getAttribute("adminLogueado") == null) return "redirect:/admin/login";
        double totalGlobal = historialVentas.stream().mapToDouble(Producto::calcularTotal).sum();
        model.addAttribute("historial", historialVentas);
        model.addAttribute("totalGlobal", totalGlobal);
        model.addAttribute("stock", inventario);
        return "admin";
    }

    @GetMapping("/admin/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/";
    }

    @PostMapping("/vender")
    public String procesarVenta(@ModelAttribute Producto producto, @RequestParam String prodInfo) {
        String[] partes = prodInfo.split(":");
        String nombreProd = partes[0];
        Double precio = Double.parseDouble(partes[1]);

        int stockActual = inventario.getOrDefault(nombreProd, 0);
        if (stockActual >= producto.getCantidad()) {
            inventario.put(nombreProd, stockActual - producto.getCantidad());
            producto.setNombre(nombreProd);
            producto.setPrecioBase(precio);
            historialVentas.add(producto);
            return "redirect:/?success=true";
        }
        return "redirect:/?error=no_stock";
    }

    private Map<String, Map<String, Double>> getCategoriasConProductos() {
        Map<String, Map<String, Double>> cat = new LinkedHashMap<>();
        Map<String, Double> alimentacion = new HashMap<>();
        alimentacion.put("Croquetas Premium 2kg", 45.0);
        alimentacion.put("Pate de Atun Gourmet", 8.50);
        cat.put("🍽️ Alimentación", alimentacion);

        Map<String, Double> diversion = new HashMap<>();
        diversion.put("Rascador Torre 1.2m", 120.0);
        diversion.put("Caña de Plumas", 12.0);
        cat.put("🧶 Juguetes", diversion);

        Map<String, Double> higiene = new HashMap<>();
        higiene.put("Arena Biodegradable 5kg", 35.0);
        cat.put("🧼 Higiene", higiene);

        return cat;
    }
}