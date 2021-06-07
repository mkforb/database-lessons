package org.jjd.lessons.lombok;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@RequiredArgsConstructor // Генерирует конструктор, который принимает на вход все final-поля или поля, которые отмечены NonNull
@ToString(callSuper = true) // callSuper - чтобы вошли поля родителя
@EqualsAndHashCode(callSuper = true) // так же как для ToString
public class Mountain extends Hill{
    @Getter
    private final String name;
}