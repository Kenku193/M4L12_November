package ru.javarush.syrovatko;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import ru.javarush.syrovatko.entities.Human;
import ru.javarush.syrovatko.entities.UserAddress;
import ru.javarush.syrovatko.enums.GenderType;

import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.time.LocalDate;
import java.time.Month;

public class Main {
    public static void main(String[] args) throws IOException {

        SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();

        try (Session session = sessionFactory.openSession()){
            session.beginTransaction();

            Human human = new Human();
            human.setAddress(new UserAddress("Russia", "Moscow", "Udaltsova", "61"));
            human.setAdult(true);
            human.setGender(GenderType.MALE);
            human.setBirthdayDate(LocalDate.of(1987, Month.JULY, 1));
            human.setIsActive(true);
            human.setRetirementAge(99); // transient
           // human.setPhoto(Human.class.getClassLoader().getResourceAsStream("123.png").readAllBytes());
            human.setPhoto(Files.readAllBytes(new File("src/main/resources/123.png").toPath()));

            session.persist(human);

            session.getTransaction().commit();


        }

    }
}