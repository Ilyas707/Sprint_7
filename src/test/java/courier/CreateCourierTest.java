package courier;

import io.qameta.allure.Description;
import io.qameta.allure.Step;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import client.CourierClient;
import data.CredentialsForCourier;
import data.CourierData;
import data.CourierGenerator;

import static org.apache.http.HttpStatus.*;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;

public class CreateCourierTest {
    private CourierClient courierClient;
    private CourierData courier;
    private int courierId;

    @Before
    @Step("Создание тестовых данных")
    public void setUp() {
        courierClient = new CourierClient();
        courier = CourierGenerator.getRandomCourier();
    }

    @After
    @Step("Удаление данных пользователя")
    public void cleanUp() {
        if (courierId != 0) {
            courierClient.deleteCourier(String.valueOf(courierId));
        }
    }

    @Test
    @DisplayName("Создание курьера")
    @Description("Создание курьера с валидными данными")
    public void createCourierTest() {
        ValidatableResponse response = courierClient.createCourier(courier);
        response.assertThat()
                .statusCode(SC_CREATED).body("ok", is(true));
        courierId = courierClient.loginCourier(CredentialsForCourier.from(courier))
                .extract()
                .jsonPath()
                .getInt("id");
    }

    @Test
    @DisplayName("Создание курьера с существующим логином")
    @Description("Проверка на то, что нельзя создать двух одинаковых курьеров")
    public void createDuplicateCourierTest() {
        courierClient.createCourier(courier);
        ValidatableResponse response = courierClient.createCourier(courier);
        response.assertThat()
                .statusCode(SC_CONFLICT)
                .body("message", equalTo("Этот логин уже используется. Попробуйте другой."));
        courierId = courierClient.loginCourier(CredentialsForCourier.from(courier))
                .extract()
                .jsonPath()
                .getInt("id");
    }

    @Test
    @DisplayName("Создание курьера с пустым полем login")
    @Description("Проверка создания курьера с пустым полем login")
    public void createCourierWithEmptyLoginTest() {
        courier.setLogin(null);
        ValidatableResponse response = courierClient.createCourier(courier);
        response.assertThat()
                .statusCode(SC_BAD_REQUEST)
                .body("message", equalTo("Недостаточно данных для создания учетной записи"));
    }

    @Test
    @DisplayName("Создание курьера с пустым полем password")
    @Description("Проверка создания курьера с пустым полем password")
    public void createCourietEmptyPasswordTest() {
        courier.setPassword(null);
        ValidatableResponse response = courierClient.createCourier(courier);
        response.assertThat()
                .statusCode(SC_BAD_REQUEST)
                .body("message", equalTo("Недостаточно данных для создания учетной записи"));
    }
}
