package org.example;

import com.github.javafaker.Faker;
import io.qameta.allure.Step;

public class RandomCourier {
    static Faker faker = new Faker();

    public static final String NEW_LOGIN_FAKED = faker.name().username();
    @Step("Создание нового курьера с рандомными данными")
    public static Courier createRandomCourier() {
        return new Courier(
                faker.name().username(),
                faker.internet().password(),
                faker.name().firstName());
    }
}
