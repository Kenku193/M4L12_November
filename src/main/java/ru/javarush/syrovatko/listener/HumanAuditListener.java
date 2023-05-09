package ru.javarush.syrovatko.listener;

import javax.persistence.*;
import ru.javarush.syrovatko.entities.Human;

public class HumanAuditListener {

    @PrePersist
    @PostPersist
    private void beforeAnyUpdate(Human human){
        System.out.println(human);
    }

}
