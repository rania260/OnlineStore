package com.example.onlinestore.web.controlers;



import com.example.onlinestore.business.services.ICategoryService;
import com.example.onlinestore.dao.entities.Category;
import com.example.onlinestore.web.models.requests.CategoryForm;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/categories")
public class CategoryController {
    @Autowired
    private ICategoryService categoryService;

    @GetMapping("/create")
    public String showCreateForm(Model model) {
        model.addAttribute("categoryForm", new CategoryForm());
        return "category/create";
    }

    @PostMapping("/create")
    public String createCategory(@ModelAttribute("categoryForm") @Valid CategoryForm categoryForm,
                                 BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "category/create";
        }

        Category category = new Category();
        category.setName(categoryForm.getName());

        categoryService.addCategory(category);

        return "redirect:/categories";
    }

    @GetMapping
    public String showCategoryList(Model model) {
        model.addAttribute("categories", categoryService.getAllCategories());
        return "category/list";
    }
}
