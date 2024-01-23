package com.example.onlinestore.web.controlers;


import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.onlinestore.business.services.ICategoryService;
import com.example.onlinestore.business.services.IProductService;
import com.example.onlinestore.dao.entities.Category;
import com.example.onlinestore.dao.entities.Product;
import com.example.onlinestore.web.models.requests.ProductForm;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/products")

public class ProductController {
    @Autowired
    IProductService productService;
    @Autowired
    ICategoryService categoryService;

    // Create
    @GetMapping("/create")
    public String showCreateForm(Model model) {
        model.addAttribute("productForm", new ProductForm());
        model.addAttribute("categories", categoryService.getAllCategories());
        return "create";
    }

    @PostMapping("/create")
    public String createProduct(@ModelAttribute("productForm") @Valid ProductForm productForm,
            BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("categories", categoryService.getAllCategories());
            return "create";

        }
        if (productService.getProductByCode(productForm.getCode()).isPresent()) {

            bindingResult.rejectValue("code", "duplicate", "The code must be unique");
            model.addAttribute("categories", categoryService.getAllCategories());
            return "create";
        }
        // Create a new product object from the request body
        Product product = new Product();
        product.setName(productForm.getName());
        product.setCode(productForm.getCode());
        product.setPrice(productForm.getPrice());
        product.setQuantity(productForm.getQuantity());
        product.setImage(productForm.getImage());
        Optional<Category> category = categoryService.getCategory(productForm.getCategory().getId());
        product.setCategory(category.get());
          productService.addProduct(product);

        return "redirect:/products";
    }

    // Read
    @GetMapping
    public String showProductList(Model model) {
        model.addAttribute("products", this.productService.getAllProduct());
        return "list";
    }

    // Update
    @GetMapping("/{id}/edit")
    public String showEditForm(@PathVariable("id") Long id, Model model) {
        Optional<Product> product = productService.getProduct(id);
        if (product == null) {
            // Handle product not found
        }

        ProductForm productForm = new ProductForm(product.get().getId(), product.get().getCode(),
                product.get().getName(),
                product.get().getPrice(), product.get().getQuantity(), product.get().getCategory(),
                product.get().getImage());
        model.addAttribute("productForm", productForm);
        model.addAttribute("categories", categoryService.getAllCategories());
        return "edit";
    }

    @PostMapping("/{id}/edit")
    public String updateProduct(@PathVariable("id") Long id,
            @ModelAttribute("productForm") @Valid ProductForm productForm,
            BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("categories", categoryService.getAllCategories());
            return "edit";

        }
        Optional<Product> product = productService.getProduct(id);
        if (productService.getProductByCode(productForm.getCode()).isPresent()
                && productService.getProductByCode(productForm.getCode()).get().getId() != id) {

            bindingResult.rejectValue("code", "duplicate", "The code must be unique");
            model.addAttribute("categories", categoryService.getAllCategories());
            return "edit";
        }

        if (product.isPresent()) {
            product.get().setCode(productForm.getCode());
            product.get().setName(productForm.getName());
            product.get().setPrice(productForm.getPrice());
            product.get().setQuantity(productForm.getQuantity());
            product.get().setImage(productForm.getImage());
            productService.updatePorduct(product.get());
        } else {
            // Handle product not found
        }

        return "redirect:/products";
    }

    // Delete
    @PostMapping("/{id}/delete")
    public String deleteProduct(@PathVariable("id") Long id) {
        Optional<Product> product = productService.getProduct(id);
        if (!product.isPresent()) {
            // Handle product not found
        }
        this.productService.deleteProduct(id);
        return "redirect:/products";
    }

}
