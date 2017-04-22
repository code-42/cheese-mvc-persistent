package org.launchcode.controllers;

import org.launchcode.models.Category;
import org.launchcode.models.Cheese;
import org.launchcode.models.Menu;
import org.launchcode.models.data.CategoryDao;
import org.launchcode.models.data.CheeseDao;
import org.launchcode.models.data.MenuDao;
import org.launchcode.models.forms.AddMenuItemForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.launchcode.controllers.MenuController.*;
import org.launchcode.controllers.CategoryController.*;

import javax.validation.Valid;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


import static java.awt.SystemColor.menu;

/**
 * Created by LaunchCode
 */
@Controller
@RequestMapping("cheese")
public class CheeseController {

    @Autowired
    CheeseDao cheeseDao;

    @Autowired
    CategoryDao categoryDao;

    @Autowired
    MenuDao menuDao;
    private int menuId;

    // Request path: /cheese
    @RequestMapping(value = "")
    public String index(Model model) {

        model.addAttribute("cheeses", cheeseDao.findAll());
        model.addAttribute("title", "My Cheeses");
        return "cheese/index";
    }

    @RequestMapping(value = "add", method = RequestMethod.GET)
    public String displayAddCheeseForm(Model model) {
        model.addAttribute("title", "Add Cheese");
        model.addAttribute(new Cheese());
        model.addAttribute("categories", categoryDao.findAll());
        return "cheese/add";
    }

    @RequestMapping(value = "add", method = RequestMethod.POST)
    public String processAddCheeseForm(@ModelAttribute  @Valid Cheese newCheese,
                                       Errors errors, @RequestParam int categoryId, Model model) {

        if (errors.hasErrors()) {
            model.addAttribute("title", "Add Cheese");
            return "cheese/add";
        }

        Category cat = categoryDao.findOne(categoryId);
        newCheese.setCategory(cat);
        cheeseDao.save(newCheese);
        return "redirect:";
    }

    @RequestMapping(value = "remove", method = RequestMethod.GET)
    public String displayRemoveCheeseForm(Model model) {

        model.addAttribute("cheeses", cheeseDao.findAll());
        model.addAttribute("menus", menuDao.findAll());
        model.addAttribute("title", "Remove Cheese");
        return "cheese/remove";
    }

    @RequestMapping(value = "remove", method = RequestMethod.POST)
    public String processRemoveCheeseForm(Model model, @RequestParam (value="cheeseIds", required=false) int[] cheeseIds) {

        // Remove Cheese functionality disabled per instructions at
        // http://education.launchcode.org/skills-back-end-java/studios/cheese-mvc-persistent/one-to-many/#review-cheese-deletion-code
//        for (int cheeseId : cheeseIds) {
//                cheeseDao.delete(cheeseId);
//        }
//        return "redirect:";

        model.addAttribute("cheeses", cheeseDao.findAll());
        model.addAttribute("menus", menuDao.findAll());
        model.addAttribute("title", "Remove Cheese");
        model.addAttribute("message", true);
        return "cheese/remove";
    }

    @RequestMapping(value = "category/{id}", method = RequestMethod.GET)
    public String category(Model model, @PathVariable int id) {

        Category cat = categoryDao.findOne(id);
        List<Cheese> cheeses = cat.getCheeses();
        model.addAttribute("cheeses", cheeses);
        model.addAttribute("category", cat.getName());
        model.addAttribute("title", "Cheeses in Category: " + cat.getName());
        return "cheese/index";
    }

    @RequestMapping(value = "edit/{cheeseId}", method = RequestMethod.GET)
    public String displayEditForm(Model model, @PathVariable int cheeseId){

        Cheese theCheese = cheeseDao.findOne(cheeseId);
        if (theCheese == null){
            return "redirect:";
        }

        model.addAttribute("cheese", theCheese);
        model.addAttribute("title", "Edit Cheese");
        model.addAttribute("categories", categoryDao.findAll());
        return "cheese/edit";
    }

    @RequestMapping(value = "edit", method = RequestMethod.POST)
    public String processEditForm(Model model, @ModelAttribute @Valid Cheese cheese,
                                  Errors errors, @RequestParam int cheeseId){

        if (errors.hasErrors()) {
            model.addAttribute("cheese", cheese);
            model.addAttribute("title", "Edit Cheese");
            return "cheese/edit";
        }

        Cheese theCheese = cheeseDao.findOne(cheeseId);
        theCheese.setCategory(cheese.getCategory());
        theCheese.setName(cheese.getName());
        theCheese.setDescription(cheese.getDescription());
        cheeseDao.save(theCheese);
        return "redirect:";

    }


}
