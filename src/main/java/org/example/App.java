package org.example;

import org.example.model.Item;
import org.example.model.Person;
import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Hello world!
 *
 */
public class App {
    public static void main(String[] args) {
        Configuration configuration = new Configuration()
                .addAnnotatedClass(Person.class)
                .addAnnotatedClass(Item.class);

        SessionFactory sessionFactory = configuration.buildSessionFactory();

        try {
            Session session = sessionFactory.getCurrentSession();
            session.beginTransaction();

            Person person = session.get(Person.class, 1);
            System.out.println("Получили человека");

            session.getTransaction().commit();
            // session.close()
            System.out.println("Сессия закончилась");


            // Открываем сессию и тразанзакцию еще раз. Можем делать в любом месте в коде
            session = sessionFactory.getCurrentSession();
            session.beginTransaction();

            System.out.println("Внутри второй тразнакции");
            person = (Person) session.merge(person);
//            Hibernate.initialize(person.getItems());

            List<Item> itemList = session.createQuery("SELECT i FROM Item i WHERE i.owner.id=:personId", Item.class)
                    .setParameter("personId", person.getId()).getResultList();

            System.out.println(itemList);

            session.getTransaction().commit();

            System.out.println("Вне сессии");

            // Связанные товары были загружены раннее (2 сессия)
//            System.out.println(person.getItems());

        } finally {
            sessionFactory.close();
        }
    }
}
