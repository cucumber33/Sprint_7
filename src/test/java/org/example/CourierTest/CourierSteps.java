package org.example.CourierTest;

import io.qameta.allure.Step;
import io.restassured.http.ContentType;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;
import org.example.Courier;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.greaterThan;

public class CourierSteps {

    public static RequestSpecification requestSpec() {
        return given().log().all()
                .contentType(ContentType.JSON)
                .baseUri("http://qa-scooter.praktikum-services.ru/");
    }

    @Step("Регистрация нового курьера")
    public ValidatableResponse createCourier(Courier courier) {
        return requestSpec()
                .body(courier)
                .when()
                .post("/api/v1/courier")
                .then();
    }

    @Step("Авторизация курьера")
    public ValidatableResponse loginCourier(Courier courier) {
        return requestSpec()
                .body(courier)
                .when()
                .post("/api/v1/courier/login")
                .then();
    }

    @Step("Удаление курьера")
    public ValidatableResponse deleteCourier(int courierId) {
        return requestSpec()
                .when()
                .delete("/api/v1/courier/:id" + courierId)
                .then();
    }
    @Step("Регистрация нового курьера с валидными данными")
    public void createCourierOk(ValidatableResponse response) {
        response.assertThat()
                .statusCode(201)
                .body("ok", is(true));
    }

    @Step("Проверка ответа сервера при неполных данных")
    public void createCourierError(ValidatableResponse response) {
        response.assertThat()
                .statusCode(400)
                .body("message", equalTo("Недостаточно данных для создания учетной записи"));
    }

    @Step("Проверка ответа сервера при регистрации под ранее зарегистрированным логином")
    public void createCourierSameLoginError(ValidatableResponse response) {
        response.assertThat()
                .statusCode(409)
                .body("message", equalTo("Этот логин уже используется. Попробуйте другой."));
    }

    @Step("Проверка получения ID при логине с валидными данными")
    public int loginCourierOk(ValidatableResponse response) {
        return response.assertThat()
                .statusCode(200)
                .body("id", greaterThan(0))
                .extract()
                .path("id");
    }

    @Step("Проверка ответа сервера при логине с неполными данными")
    public void loginCourierError(ValidatableResponse response) {
        response.assertThat()
                .statusCode(400)
                .body("message", equalTo("Недостаточно данных для входа"));
    }

    @Step("Проверка ответа сервера при логине с невалидными данными")
    public void loginCourierErrorAccountNotFound(ValidatableResponse response) {
        response.assertThat()
                .statusCode(404)
                .body("message", equalTo("Учетная запись не найдена"));
    }
}
