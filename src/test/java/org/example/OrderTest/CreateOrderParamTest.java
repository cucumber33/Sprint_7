package org.example.OrderTest;

import io.qameta.allure.Description;
import io.qameta.allure.Step;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.example.Order;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;

@RunWith(Parameterized.class)
public class CreateOrderParamTest {
    private final List<String> colour;
    private int track;
    private OrderSteps orderSteps;

    public CreateOrderParamTest(List<String> colour) {
        this.colour = colour;
    }

    @Parameterized.Parameters(name = "Цвет самоката: {0}")
    public static Object[][] getScooterColour() {
        return new Object[][]{
                {List.of("BLACK")},
                {List.of("GRAY")},
                {List.of("BLACK, GRAY")},
                {List.of()}
        };
    }

    @Before
    public void setUp() {
        orderSteps = new OrderSteps();
    }

    @After
    @Step("Cancel test order")
    public void CancelTestOrder() {
        orderSteps.cancelOrder(track);
    }

    @Test
    @DisplayName("Размещение заказа с самокатами разных цветов")
    @Description("Проверяем корректность размещения заказа с самокатами разных цветов")
    public void OrderingWithScootersInDifferentColors() {
        Order order = new Order(colour);
        ValidatableResponse responseCreateOrder = orderSteps.createNewOrder(order);
        track = responseCreateOrder.extract().path("track");
        responseCreateOrder.assertThat()
                .statusCode(201)
                .body("track", is(notNullValue()));
    }
}