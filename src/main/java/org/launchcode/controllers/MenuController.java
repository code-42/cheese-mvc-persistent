package org.launchcode.controllers;

import org.launchcode.models.Category;
import org.launchcode.models.Cheese;
import org.launchcode.models.CheeseType;
import org.launchcode.models.Menu;
import org.launchcode.models.data.CheeseDao;
import org.launchcode.models.data.MenuDao;
import org.launchcode.models.forms.AddMenuItemForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * Created by melocal on 4/11/17.
 */
@Controller
@RequestMapping(value = "menu")
public class MenuController {

    @Autowired
    private MenuDao menuDao;

    @Autowired
    private CheeseDao cheeseDao;

    // Request path: /menu
    @RequestMapping(value = "")
    public String index(Model model) {

        model.addAttribute("menu", menuDao.findAll());
        model.addAttribute("title", "My Menus");

        return "menu/index";
    }

    @RequestMapping(value = "add", method = RequestMethod.GET)
    public String displayAddMenuForm(Model model) {
        model.addAttribute("title", "Add Menu");
        model.addAttribute(new Menu());
//        model.addAttribute("menus", Menu.values());
        return "menu/add";
    }

    @RequestMapping(value = "add", method = RequestMethod.POST)
    public String processAddMenuForm(@ModelAttribute @Valid Menu newMenu,
                                       Errors errors, Model model) {

        if (errors.hasErrors()) {
            model.addAttribute("title", "Add Menu");
            return "menu/add";
        }

//        menuDao.save(newMenu);
        return "redirect:view/"; // + menu.getId();
    }

    @RequestMapping(value = "view", method = RequestMethod.GET)
    public String viewMenu(Model model, @PathVariable(value = "0") int id) {
        model.addAttribute("cheeses", menuDao.findAll());
        model.addAttribute("title", "View Menu");
        return "cheese/menu";
    }

    @RequestMapping(value = "add-item", method = RequestMethod.GET)
    public String addItem(Model model, @RequestParam int id) {
        model.addAttribute(new Menu());

        model.addAttribute("title", "Add Menu Item");
//        model.addAttribute("cheeses", menuDao.getId());
        return "redirect:/view?id="+id;
    }
}
