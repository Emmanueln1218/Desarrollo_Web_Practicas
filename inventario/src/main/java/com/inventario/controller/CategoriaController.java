package com.inventario.controller;

import com.inventario.model.Categoria;
import com.inventario.service.CategoriaService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/categorias")
public class CategoriaController {

    @Autowired
    private CategoriaService categoriaService;

    @GetMapping
    public String listar(Model model) {
        model.addAttribute("categorias", categoriaService.listarTodas());
        return "categorias/lista";
    }

    @GetMapping("/nuevo")
    public String formularioNuevo(Model model) {
        model.addAttribute("categoria", new Categoria());
        model.addAttribute("titulo", "Nueva Categoría");
        return "categorias/formulario";
    }

    @GetMapping("/editar/{id}")
    public String formularioEditar(@PathVariable Long id, Model model) {
        model.addAttribute("categoria", categoriaService.buscarPorId(id));
        model.addAttribute("titulo", "Editar Categoría");
        return "categorias/formulario";
    }

    @PostMapping("/guardar")
    public String guardar(@Valid @ModelAttribute("categoria") Categoria categoria,
                          BindingResult result,
                          Model model,
                          RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            model.addAttribute("titulo", categoria.getId() == null ? "Nueva Categoría" : "Editar Categoría");
            return "categorias/formulario";
        }

        categoriaService.guardar(categoria);
        redirectAttributes.addFlashAttribute("success", "Categoría guardada correctamente");
        return "redirect:/categorias";
    }

    @GetMapping("/eliminar/{id}")
    public String eliminar(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        categoriaService.eliminar(id);
        redirectAttributes.addFlashAttribute("success", "Categoría eliminada");
        return "redirect:/categorias";
    }
}