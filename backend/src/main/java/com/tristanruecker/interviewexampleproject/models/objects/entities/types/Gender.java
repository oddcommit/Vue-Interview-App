package com.tristanruecker.interviewexampleproject.models.objects.entities.types;

import lombok.Getter;

import java.util.Optional;
import java.util.Arrays;

@Getter
public enum Gender {
    MALE("MALE"),
    FEMALE("FEMALE"),
    OTHER("OTHER");

    private String value;

    Gender(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return this.getValue();
    }

    public static Optional<Gender> getGender(String value) {
        return Arrays.stream(values())
                .filter(gender -> gender.getValue().equals(value)).findFirst();
    }


}
