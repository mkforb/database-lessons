package org.jjd.lessons.lombok;

import lombok.*;

import java.util.UUID;

// В какой последовательности указаны аннотации, в той же последовательности будет в сгенерированных файлах
@NoArgsConstructor
@RequiredArgsConstructor
@Getter // Если @Getter и @Setter указать над классом, то будут сгенерированы геттеры и сеттеры для всех полей
@Setter
// @Cleanup - указывается перед объектами, у которых необходимо вызывать метод close(). Это вместо автоматического закрытия ресурсов
public class Climber {
    private String fullName;
    @NonNull
    private int age;
    @NonNull
    private String email;
    @NonNull
    private UUID uuid;
}