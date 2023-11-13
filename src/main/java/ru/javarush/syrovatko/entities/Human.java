package ru.javarush.syrovatko.entities;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.UpdateTimestamp;
import ru.javarush.syrovatko.enums.GenderType;
import ru.javarush.syrovatko.listener.HumanAuditListener;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "human")
@NoArgsConstructor
@Data
@EntityListeners(value = HumanAuditListener.class)
public class Human {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Type(type="yes_no")
    @Column(name = "is_adult")
    private boolean isAdult;

    @Embedded
    private UserAddress address;

    @Enumerated(EnumType.STRING)
    @Column(name = "gender_type")
    private GenderType gender;

    @Column(name = "is_active")
    private Boolean isActive;

    @Transient
   // @Column(name = "retirement_age")
    private Integer retirementAge;

    @Column(name = "birthday_date")
    private LocalDate birthdayDate;

    @Lob
    @Column(name = "photo", columnDefinition = "BLOB")
    private byte[] photo;

    @CreationTimestamp
    @Column(name = "created_date")
    private LocalDateTime createDate;

    @UpdateTimestamp
    @Column(name = "modified_date")
    private LocalDateTime modifyDate;


}
