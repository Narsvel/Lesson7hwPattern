package org.ost.springboot.controllers;

import org.junit.jupiter.api.Test;
import org.ost.springboot.dto.UserDTO;
import org.ost.springboot.models.User;
import org.ost.springboot.services.UsersService;
import org.ost.springboot.utils.MappingUserUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(UsersController.class)
class UsersControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private UsersService usersService;
    @MockitoBean
    private MappingUserUtils mappingUserUtils;


    @Test
    void index() throws Exception {
        when(usersService.findAll()).thenReturn(List.of());
        when(mappingUserUtils.mapToDtoList(List.of()))
                .thenReturn(List.of(new UserDTO()));

        this.mockMvc.perform(get("/users")).andDo(print()).andExpect(status().isOk())
                .andExpect(view().name("users/index"));
    }

    @Test
    void show() throws Exception {
        int id = 1;
        User user = new User();
        when(usersService.findById(id)).thenReturn(user);
        when(mappingUserUtils.mapToDto(user)).thenReturn(new UserDTO());

        this.mockMvc.perform(get("/users/" + id)).andDo(print()).andExpect(status().isOk())
                .andExpect(view().name("users/show"));
    }

    @Test
    void newUser() throws Exception {
        this.mockMvc.perform(get("/users/new")).andDo(print()).andExpect(status().isOk())
                .andExpect(view().name("users/new"));
    }

    @Test
    void create() throws Exception {
//        UserDTO userDTO = new UserDTO(0,"Bob", "bob@mail.ru", 20);
//        User user = new User("Bob", "bob@mail.ru", 20);
//        when(mappingUserUtils.mapToUser(userDTO)).thenReturn(user);
//        when(usersService.save(user)).thenReturn(user);

        this.mockMvc.perform(post("/users")
                .param("name", "Bob")
                .param("age", "20")
                .param("email", "bob@mail.ru"))
                .andDo(print())
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/users"));
    }

    @Test
    void edit() throws Exception {
        int id = 1;
        User user = new User();
        when(usersService.findById(id)).thenReturn(user);
        when(mappingUserUtils.mapToDto(user)).thenReturn(new UserDTO());

        this.mockMvc.perform(get("/users/" + id + "/edit")).andDo(print()).andExpect(status().isOk())
                .andExpect(view().name("users/edit"));
    }

    @Test
    void update() throws Exception {
        int id = 1;
//        UserDTO userDTO = new UserDTO(0,"Bob", "bob@mail.ru", 20);
//        User user = new User("Bob", "bob@mail.ru", 20);
//        when(mappingUserUtils.mapToUser(userDTO)).thenReturn(user);
//        when(usersService.update(id, user)).thenReturn(user);

        this.mockMvc.perform(patch("/users/" + id)
                        .param("id", String.valueOf(id))
                        .param("name", "Bob")
                        .param("age", "20")
                        .param("email", "bob@mail.ru"))
                .andDo(print())
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/users"));
    }

    @Test
    void delete() throws Exception {
        int id = 1;

        this.mockMvc.perform(MockMvcRequestBuilders.delete("/users/" + id)
                        .param("id", String.valueOf(id)))
                .andDo(print())
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/users"));
    }
}