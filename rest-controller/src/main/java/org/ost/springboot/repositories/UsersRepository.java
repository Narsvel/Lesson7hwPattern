package org.ost.springboot.repositories;

import org.ost.springboot.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UsersRepository extends JpaRepository<User, Integer> {

    //здесь можно писать кастомные запросы, для нашего проэкта хватит и default запросов

    List<User> findByName(String name);

    List<User> findByNameOrderByAge(String name);

    List<User> findByEmail(String email);

    List<User> findByNameStartingWith(String startingWith);

    List<User> findByNameOrEmail(String name, String email);

}
