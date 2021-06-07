package org.jjd.lessons.jpa.enity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;

/**
 * Created by User on 04.06.2021.
 */
@Entity
public class Contact extends Parent {

    @Getter
    @Setter
    @Column(nullable = false)
    private String phone;

    @Getter
    @Setter
    private String email;

}
