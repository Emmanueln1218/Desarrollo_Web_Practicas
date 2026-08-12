package com.inventario.controller;

import com.inventario.service.CategoriaService;
import com.inventario.service.ProductoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class DashboardController {

    @Autowired
    private ProductoService productoService;

    @Autowired
    private CategoriaService categoriaService;

    @GetMapping({"/", "/dashboard"})
    public String dashboard(Model model) {
        model.addAttribute("totalProductos", productoService.totalProductos());
        model.addAttribute("totalUnidades", productoService.totalUnidades());
        model.addAttribute("totalCategorias", categoriaService.listarTodas().size());
        model.addAttribute("productosStockBajo", productoService.productosStockBajo());
        model.addAttribute("ultimosProductos", productoService.listarTodos());
        return "dashboard";
    }
}