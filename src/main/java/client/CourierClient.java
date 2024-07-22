package client;

import data.CredentialsForCourier;
import data.CourierData;
import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;
import static io.restassured.RestAssured.given;

public class CourierClient extends RestClient {
    private static final String COURIER_CREATE_PATH = "/api/v1/courier";
    private static final String COURIER_LOGIN_PATH = "/api/v1/courier/login";
    private static final String COURIER_ID_PATH = "/api/v1/courier/";

    @Step("Создание курьера")
    public ValidatableResponse createCourier(CourierData courier) {
        return given()
                .spec(requestSpecification())
                .and()
                .body(courier)
                .when()
                .post(COURIER_CREATE_PATH)
                .then();
    }

    @Step("Логин курьера в системе")
    public ValidatableResponse loginCourier(CredentialsForCourier courierCredentials) {
        return given()
                .spec(requestSpecification())
                .and()
                .body(courierCredentials)
                .when()
                .post(COURIER_LOGIN_PATH)
                .then();
    }

    @Step("Удаления курьера")
    public ValidatableResponse deleteCourier(String id) {
        return given()
                .spec(requestSpecification())
                .delete(COURIER_ID_PATH + id)
                .then();
    }
}
