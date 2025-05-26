package org.ost.springboot.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import org.hibernate.annotations.CreationTimestamp;

import java.util.Date;
import java.util.Objects;

@Entity  //сущность
@Table(name = "Users") //указываем соостветствие класса и таблици
public class User {

    @Id //указыаем что это поле особенное, оно соответствует первичному ключу PRIMARY KEY
    @Column(name = "id") //указываем соостветствие поля и колонке в таблице
    @GeneratedValue(strategy = GenerationType.IDENTITY) //указываем что поле генерируется автоматически бд
    private int id;

    @NotEmpty(message = "Name should not be empty.")
    @Size(min = 2, max = 100, message = "Name should be between 2 and 100 characters.")
    @Column(name = "name")
    private String name;

    @NotEmpty(message = "Email should not be empty.")
    @Email
    @Column(name = "email")
    private String email;

    @Min(value = 0, message = "Age should be greater than 0.")
    @Column(name = "age")
    private int age;

    @CreationTimestamp
    //данное поле временная метка, будет сохранена на текущее время JVM один раз при сохнанении объекта
    @Temporal(TemporalType.TIMESTAMP) //указываем формат временной метки в postgreSQl (Map as java.sql.Timestamp)
    @Column(name = "created_at")
    private Date createAt;

    public User() {} //пустой конструктор необходим для работы Hibernate @Entity

    public User(String name, String email, int age) {
        this.name = name;
        this.email = email;
        this.age = age;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public Date getCreateAt() {
        return createAt;
    }

    public void setCreateAt(Date createAt) {
        this.createAt = createAt;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", age=" + age +
                ", createAt=" + createAt +
                "} \n";
    }

    @Override
    public final boolean equals(Object object) {
        if (!(object instanceof User user)) return false;

        return id == user.id && age == user.age && Objects.equals(name, user.name) && Objects.equals(email, user.email) && Objects.equals(createAt, user.createAt);
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + Objects.hashCode(name);
        result = 31 * result + Objects.hashCode(email);
        result = 31 * result + age;
        result = 31 * result + Objects.hashCode(createAt);
        return result;
    }
}
