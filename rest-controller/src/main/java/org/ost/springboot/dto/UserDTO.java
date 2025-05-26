package org.ost.springboot.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

import java.util.Objects;

public class UserDTO {

    private int id;

    @NotEmpty(message = "Name should not be empty.")
    @Size(min = 2, max = 100, message = "Name should be between 2 and 100 characters.")
    private String name;

    @NotEmpty(message = "Email should not be empty.")
    @Email
    private String email;

    @Min(value = 0, message = "Age should be greater than 0.")
    private int age;

    public UserDTO() {

    }

    public UserDTO(int id, String name, String email, int age) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "UserDTO{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", age=" + age +
                '}';
    }

    @Override
    public final boolean equals(Object object) {
        if (!(object instanceof UserDTO userDTO)) return false;

        return id == userDTO.id && age == userDTO.age && Objects.equals(name, userDTO.name) && Objects.equals(email, userDTO.email);
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + Objects.hashCode(name);
        result = 31 * result + Objects.hashCode(email);
        result = 31 * result + age;
        return result;
    }
}
