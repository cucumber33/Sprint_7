package org.example.OrderTest;

import io.qameta.allure.Step;
import io.restassured.http.ContentType;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;
import org.example.Order;

import static io.restassured.RestAssured.given;

public class OrderSteps {

    public static RequestSpecification requestSpec() {
        return given().log().all()
                .contentType(ContentType.JSON)
                .baseUri("http://qa-scooter.praktikum-services.ru/");
    }

    @Step("Создание заказа")
    public ValidatableResponse createNewOrder(Order order) {
        return requestSpec()
                .body(order)
                .when()
                .post("/api/v1/orders")
                .then();
    }

    @Step("Получение списка заказов")
    public ValidatableResponse getOrderList() {
        return requestSpec()
                .when()
                .get("/api/v1/orders")
                .then();
    }

    @Step("Отмена заказа")
    public ValidatableResponse cancelOrder(int track) {
        return requestSpec()
                .body(track)
                .when()
                .put("/api/v1/orders/cancel")
                .then();
    }
}
