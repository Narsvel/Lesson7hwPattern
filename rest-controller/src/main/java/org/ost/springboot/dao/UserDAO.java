package org.ost.springboot.dao;

import jakarta.persistence.EntityManager;
import org.hibernate.Session;
import org.ost.springboot.models.User;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
public class UserDAO {

    private  final EntityManager entityManager;

    public UserDAO(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    //в UserDAO можно создавать сложные запросы в бд для решения проблем N+1
    //Пример:
//    @Transactional(readOnly = true)
//    public void testNPlus1() {
//        Session session = entityManager.unwrap(Session.class);
//        // SQL: A LEFT JOIN B -> результат объедененная таблица
//        List<User> users = session
//                .createQuery("select u from User u LEFT JOIN FETCH u.items") //указываем FETCH чтобы сразу были загружены items
//                .getResultList();
//        for (User user : users)
//            System.out.println("User " + user.getName() + " has: " + user.getItems());
//    }

}
