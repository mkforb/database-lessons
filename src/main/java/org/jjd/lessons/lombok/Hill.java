package org.jjd.lessons.lombok;


import lombok.*;

import java.util.HashSet;

@ToString(exclude = "climbers") // По умолчанию в метод toString() попадут все поля класса. Чтобы исключить, добавить свойство exclude. Если несколько полей, то {"climbers", "otherField"}
@EqualsAndHashCode(exclude = "climbers") // Для генерации методов equals() и hashCode(). По умолчанию войдут все поля класса. Исключить так же, как с ToString
public class Hill {
    @Getter // Для генерации геттера int getHeight()
    @Setter // Для генерации сеттера void setHeight(int height)
    private int height;

    @Getter
    private final HashSet<Climber> climbers = new HashSet<>();

    // NonNull можно добавить перед аргументами и к полю класса.
    // Ее наличие приведет к генерации кода на проверку на null. Если будет null, то будет выброшен NullPointerException
    // Если NonNull перед полем, то проверка будет добавлена в сеттер
    // Если NonNull перед аргументом, то проверка добавляется в метод
    @SneakyThrows // Означает, что при вызове метода исключене можно не обрабатывать, но если оно произойдет, то будет выброшен RuntimeException (необрабатываемое исключение)
    public void walk(@NonNull Climber climber){
        // if (climber == null) throw new NullPointerException();
        if (climber.getAge() < 18) throw new Exception("Исключение по возрасту");
        climbers.add(climber);
    }
}
