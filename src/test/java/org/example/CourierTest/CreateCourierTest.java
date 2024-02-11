package org.example.CourierTest;

import io.qameta.allure.Description;
import io.qameta.allure.Step;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.example.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class CreateCourierTest {
    protected final RandomCourier RandomCourier = new RandomCourier();
    int courierId;
    private CourierSteps courierSteps;
    private Courier courier;

    @Before
    @Step("Создание тестовых данных курьера")
    public void setUp() {
        courierSteps = new CourierSteps();
        courier = RandomCourier.createRandomCourier();
    }

    @After
    @Step("Удаление тестовых данных")
    public void deleteCourier() {
        if (courierId != 0) {
            courierSteps.deleteCourier(courierId);
        }
    }

    @Test
    @DisplayName("Создание нового курьера")
    @Description("Проверяем, что курьера можно создать")
    public void courierCanBeCreated() {
        ValidatableResponse responseCreateCourier = courierSteps.createCourier(courier);
        courierId = courierSteps.loginCourier(courier).extract().path("id");
        courierSteps.createCourierOk(responseCreateCourier);
    }

    @Test
    @DisplayName("Создание курьера с пустым полем логина")
    @Description("Проверяем, что курьера нельзя создать без логина")
    public void courierCanNotBeCreatedWithoutLogin() {
        courier.setLogin(null);
        ValidatableResponse responseNullLogin = courierSteps.createCourier(courier);
        courierSteps.createCourierError(responseNullLogin);
    }

    @Test
    @DisplayName("Создание курьера с пустым полем пароля")
    @Description("Проверяем, что курьера нельзя создать без пароля")
    public void courierCanNotBeCreatedWithoutPassword() {
        courier.setPassword(null);
        ValidatableResponse responseNullPassword = courierSteps.createCourier(courier);
        courierSteps.createCourierError(responseNullPassword);
    }

    @Test
    @DisplayName("Создание курьера с пустым полем логина и пароля")
    @Description("Проверяем, что курьера нельзя создать без ввода логина и  пароля")
    public void courierCanNotBeCreatedWithoutLoginAndPassword() {
        courier.setLogin(null);
        courier.setPassword(null);
        ValidatableResponse responseNullFields = courierSteps.createCourier(courier);
        courierSteps.createCourierError(responseNullFields);
    }

    @Test
    @DisplayName("Создание курьера с ранее зарегистрированным логином")
    @Description("Проверяем, что курьера нельзя создать с ранее зарегистрированным логином")
    public void courierCanNotBeCreatedWithSameLogin() {
        courierSteps.createCourier(courier);
        ValidatableResponse responseCreateCourier = courierSteps.createCourier(courier);
        courierSteps.createCourierSameLoginError(responseCreateCourier);
    }
}