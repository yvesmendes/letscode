package br.com.letscode.rebels.person.domain.entity;

import lombok.Getter;

public enum GenderEnum {

    M("Masculino"),
    F("Feminino");

    @Getter
    private final String gender;

    GenderEnum(String gender) {
        this.gender = gender;
    }
}
