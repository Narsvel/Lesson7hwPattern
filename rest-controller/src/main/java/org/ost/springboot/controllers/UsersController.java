package org.ost.springboot.controllers;

import jakarta.validation.Valid;
import org.ost.springboot.dto.UserDTO;
import org.ost.springboot.services.UsersService;
import org.ost.springboot.utils.MappingUserUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/users")
public class UsersController {

    private final UsersService usersService;
    private final MappingUserUtils mappingUserUtils;

    public UsersController(UsersService usersService, MappingUserUtils mappingUserUtils) {
        this.usersService = usersService;
        this.mappingUserUtils = mappingUserUtils;
    }

    @GetMapping()
    public String index(Model model) {
        model.addAttribute("usersDTO", mappingUserUtils.mapToDtoList(usersService.findAll()));
        return "users/index";
    }

    @GetMapping("/{id}")
    public String show(@PathVariable("id") int id, Model model) {
        model.addAttribute("userDTO", mappingUserUtils.mapToDto(usersService.findById(id)));
        return "users/show";
    }

    @GetMapping("/new")
    public String newUser(@ModelAttribute("userDTO") UserDTO userDTO) {
        return "users/new";
    }

    @PostMapping()
    public String create(@ModelAttribute("userDTO") @Valid UserDTO userDTO,
                         BindingResult bindingResult) {
        if (bindingResult.hasErrors())
            return "users/new";

        usersService.save(mappingUserUtils.mapToUser(userDTO));
        return "redirect:/users";
    }

    @GetMapping("/{id}/edit")
    public String edit(Model model, @PathVariable("id") int id) {
        model.addAttribute("userDTO", mappingUserUtils.mapToDto(usersService.findById(id)));
        return "users/edit";
    }

    @PatchMapping("/{id}")
    public String update(@ModelAttribute("userDTO") @Valid UserDTO userDTO, BindingResult bindingResult,
                         @PathVariable("id") int id) {
        if (bindingResult.hasErrors())
            return "users/edit";

        usersService.update(id, mappingUserUtils.mapToUser(userDTO));
        return "redirect:/users";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") int id) {
        usersService.delete(id);
        return "redirect:/users";
    }

}
