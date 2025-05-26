package org.ost.springboot.controllers;

import jakarta.validation.Valid;
import org.ost.springboot.dto.UserDTO;
import org.ost.springboot.services.UsersService;
import org.ost.springboot.utils.MappingUserUtils;
import org.ost.springboot.utils.UserErrorResponse;
import org.ost.springboot.utils.UserNotCreatedException;
import org.ost.springboot.utils.UserNotFoundException;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/users")
public class UsersController {

    private final UsersService usersService;
    private final MappingUserUtils mappingUserUtils;

    public UsersController(UsersService usersService, MappingUserUtils mappingUserUtils) {
        this.usersService = usersService;
        this.mappingUserUtils = mappingUserUtils;
    }

    @GetMapping()
    public ResponseEntity<CollectionModel<UserDTO>> getUsers() {
        List<UserDTO> users = mappingUserUtils.mapToDtoList(usersService.findAll());

        if (users.isEmpty()) {
            return ResponseEntity.noContent().build(); //если список пустой отправляем HTTP ответ с пустым телом и статусом 204
        }

//        for (UserDTO userDTO : users) {
//            userDTO.add(linkTo(methodOn(UsersController.class).getUser(userDTO.getId())).withSelfRel());
//            userDTO.add(linkTo(methodOn(UsersController.class).getUsers()).withRel(IanaLinkRelations.COLLECTION));
//        }

        CollectionModel<UserDTO> collectionModel = CollectionModel.of(users);
//        collectionModel.add(linkTo(methodOn(UsersController.class).getUsers()).withSelfRel());

        return new ResponseEntity<>(collectionModel, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public UserDTO getUser(@PathVariable("id") int id) {
        //если User не будет найден в бд то будет проброшено исключение UserNotFoundException, которое перехватит handleException
        UserDTO userDTO = mappingUserUtils.mapToDto(usersService.findById(id));
//        userDTO.add(linkTo(methodOn(UsersController.class).getUser(userDTO.getId())).withSelfRel());
//        userDTO.add(linkTo(methodOn(UsersController.class).getUsers()).withRel(IanaLinkRelations.COLLECTION));
        return userDTO;  //Jackson конвертирует в JSON
    }

    @PostMapping()
    public ResponseEntity<UserDTO> create(@RequestBody @Valid UserDTO userDTO,
                                          BindingResult bindingResult) {

        isErrorBindingResult(bindingResult);

        UserDTO saveUser = mappingUserUtils.mapToDto(usersService.save(mappingUserUtils.mapToUser(userDTO)));
//        saveUser.add(linkTo(methodOn(UsersController.class).getUser(saveUser.getId())).withSelfRel());
//        saveUser.add(linkTo(methodOn(UsersController.class).getUsers()).withRel(IanaLinkRelations.COLLECTION));

        return ResponseEntity.created(linkTo(methodOn(UsersController.class).getUser(saveUser.getId())).toUri())
                .body(saveUser);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<UserDTO> update(@RequestBody @Valid UserDTO userDTO, BindingResult bindingResult,
                                          @PathVariable("id") int id) {

        isErrorBindingResult(bindingResult);

        UserDTO updateUser = mappingUserUtils.mapToDto(usersService.update(id, mappingUserUtils.mapToUser(userDTO)));
//        updateUser.add(linkTo(methodOn(UsersController.class).getUser(updateUser.getId())).withSelfRel());
//        updateUser.add(linkTo(methodOn(UsersController.class).getUsers()).withRel(IanaLinkRelations.COLLECTION));

        return new ResponseEntity<>(updateUser, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") int id) {
        //если User не будет найден в бд то будет проброшено исключение UserNotFoundException, которое перехватит handleException
        usersService.delete(id);
        return ResponseEntity.noContent().build(); //В HTTP ответе будет пустое тело и статус в заголовке NO_CONTENT - 204
    }

    @ExceptionHandler
    private ResponseEntity<UserErrorResponse> handleException(UserNotFoundException e) {
        UserErrorResponse response = new UserErrorResponse(
                "User with this id wasn't found!",
                System.currentTimeMillis()
        );
        // В HTTP ответе будет тело response и статус в заголовке 404
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND); //NOT_FOUND - 404
    }

    @ExceptionHandler
    private ResponseEntity<UserErrorResponse> handleException(UserNotCreatedException e) {
        UserErrorResponse response = new UserErrorResponse(
                e.getMessage(),
                System.currentTimeMillis()
        );
        // В HTTP ответе будет тело response и статус в заголовке 400
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST); //BAD_REQUEST - 400 статус
    }

    private void isErrorBindingResult(BindingResult bindingResult) {
        if (bindingResult.hasErrors()) { //если была ошибка
            StringBuilder errorMsg = new StringBuilder();

            List<FieldError> errors = bindingResult.getFieldErrors();
            for (FieldError error : errors) {
                errorMsg.append(error.getField())
                        .append(" - ").append(error.getDefaultMessage())
                        .append("; ");
            }

            throw new UserNotCreatedException(errorMsg.toString());
        }
    }

}
