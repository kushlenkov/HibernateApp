package org.example;

import org.example.model.Passport;
import org.example.model.Person;
import org.example.model.Principal;
import org.example.model.School;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

/**
 * Hello world!
 *
 */
public class App {
    public static void main(String[] args) {
        Configuration configuration = new Configuration()
                .addAnnotatedClass(Principal.class)
                .addAnnotatedClass(School.class)
                .addAnnotatedClass(Person.class)
                .addAnnotatedClass(Passport.class);

        SessionFactory sessionFactory = configuration.buildSessionFactory();
        Session session = sessionFactory.getCurrentSession();

        try {
            session.beginTransaction();

            Principal principal = session.get(Principal.class, 5);
            School school = session.get(School.class, 3);
            principal.setSchool(school);

            session.getTransaction().commit();

        } finally {
            sessionFactory.close();
        }
    }
}
