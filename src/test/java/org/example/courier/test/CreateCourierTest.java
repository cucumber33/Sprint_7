package org.example.courier.test;

import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.example.*;
import org.junit.Test;
import steps.CourierSteps;

public class CreateCourierTest extends BaseTest {
    @Test
    @DisplayName("Создание нового курьера")
    @Description("Проверяем, что курьера можно создать")
    public void courierCanBeCreated() {
        ValidatableResponse responseCreateCourier = courierSteps.createCourier(courier);
        courierID = courierSteps.loginCourier(courier).extract().path("id");
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
