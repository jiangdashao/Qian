package me.rerere.qian.data;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@AllArgsConstructor
@Getter
@EqualsAndHashCode
public class PlayerEcoData {
    private final String uuid;
    private final String name;
    private final double balance;
}
