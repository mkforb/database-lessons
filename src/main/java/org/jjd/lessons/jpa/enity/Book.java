package org.jjd.lessons.jpa.enity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;

/**
 * Created by User on 07.06.2021.
 */

public class Book extends Parent {
    @Getter
    @Setter
    @Column(nullable = false)
    private String title;
}
