package org.example.courier.test;

import io.qameta.allure.Step;

import org.example.Courier;
import org.example.RandomCourier;
import org.junit.After;
import org.junit.Before;
import steps.CourierSteps;


public class BaseTest {

    int courierID;
    CourierSteps courierSteps;
    Courier courier;

    @Before
    @Step("Создание тестовых данных курьера")
    public void setUp() {
        courierSteps = new CourierSteps();
        courier = RandomCourier.createRandomCourier();
    }

    @After
    @Step("Удаление тестовых данных")
    public void deleteCourier() {
        if (courierID != 0) {
            courierSteps.deleteCourier(courierID);
        }
    }
}
