package org.example.courier.test;
import io.qameta.allure.Description;
import io.qameta.allure.Step;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;

import org.example.Courier;

import org.example.RandomCourier;
import org.junit.Before;
import org.junit.Test;
import steps.CourierSteps;

public class LoginCourierTest extends BaseTest{
    @Test
    @DisplayName("Логин курьера успешен")
    @Description("Проверяем, что курьер может войти в систему с валидными данными")
    public void courierLoginOkValidData() {
        courierSteps.createCourier(courier);
        ValidatableResponse responseLoginCourier = courierSteps.loginCourier(courier);
        courierSteps.loginCourierOk(responseLoginCourier);
        courierID = responseLoginCourier.extract().path("id");
    }

    @Test
    @DisplayName("Логин курьера с пустым полем логина")
    @Description("Проверяем, что курьер не может войти в систему с пустым полем логина")
    public void courierLoginErrorEmptyLogin() {
        Courier courierWithoutLogin = new Courier("", courier.getPassword()); // c null тесты виснут
        ValidatableResponse responseLoginErrorMessage = courierSteps.loginCourier(courierWithoutLogin);
        courierSteps.loginCourierError(responseLoginErrorMessage);

    }

    @Test
    @DisplayName("Логин курьера с пустым полем пароля")
    @Description("Проверяем, что курьер не может войти в систему с пустым полем пароля")
    public void courierLoginErrorEmptyPassword() {
        Courier courierWithoutPass = new Courier(courier.getLogin(), "");
        ValidatableResponse responseLoginErrorMessage = courierSteps.loginCourier(courierWithoutPass);
        courierSteps.loginCourierError(responseLoginErrorMessage);
    }

    @Test
    @DisplayName("Логин курьера с пустым полями логина и пароля")
    @Description("Проверяем, что курьер не может войти в систему с пустыми полями логина и пароля")
    public void courierLoginErrorEmptyLoginAndPassword() {
        Courier courierWithoutLoginAndPassword = new Courier("", "");
        ValidatableResponse responseLoginErrorMessage = courierSteps.loginCourier(courierWithoutLoginAndPassword);
        courierSteps.loginCourierError(responseLoginErrorMessage);
    }

    @Test
    @DisplayName("Логин курьера c невалидным логином")
    @Description("Проверяем, что курьер не может войти в систему с ранее не зарегистрированным логином")
    public void courierLoginErrorAccountNotFound() {
        Courier courierErrorAccountNotFound = new Courier(RandomCourier.NEW_LOGIN_FAKED, courier.getPassword());
        ValidatableResponse responseLoginErrorMessage = courierSteps.loginCourier(courierErrorAccountNotFound);
        courierSteps.loginCourierErrorAccountNotFound(responseLoginErrorMessage);
    }
}