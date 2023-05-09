package ru.javarush.syrovatko.entities;

import javax.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Embeddable
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserAddress {

    @Column(name = "user_adress_country")
    private String country;

    @Column(name = "user_adress_city")
    private String city;

    @Column(name = "user_adress_street")
    private String street;

    @Column(name = "user_adress_home")
    private String home;

}
