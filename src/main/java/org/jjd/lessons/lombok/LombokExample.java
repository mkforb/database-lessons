package org.jjd.lessons.lombok;

import java.util.UUID;

public class LombokExample {
    public static void main(String[] args) {
        Climber climber = new Climber(23, "qwe@yandex.ru", UUID.randomUUID());
        Mountain mountain = new Mountain("Гора");
        mountain.setHeight(1500);
        System.out.println(climber);
        System.out.println(mountain);
    }
}
