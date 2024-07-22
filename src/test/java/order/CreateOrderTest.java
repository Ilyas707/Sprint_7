package order;

import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import data.OrderData;
import client.OrderClient;

import static org.apache.http.HttpStatus.*;
import static org.hamcrest.Matchers.greaterThan;
import java.util.List;

@RunWith(Parameterized.class)
public class CreateOrderTest {
    private OrderClient orderClient;
    private OrderData orderData;
    private final List<String> color;

    public CreateOrderTest(List<String> color) {
        this.color = color;
    }

    @Parameterized.Parameters(name = "Color Scooter - {0}")
    public static Object[][] dataGen() {
        return new Object[][]{
                {List.of("BLACK", "GREY")},
                {List.of("BLACK")},
                {List.of("GREY")},
                {List.of()}
        };
    }

    @Before
    public void setUp() {
        orderClient = new OrderClient();
    }

    @Test
    @DisplayName("Создание заказа с указанием цвета")
    public void createOrderColorTest() {
        orderData = new OrderData(color);
        ValidatableResponse response = orderClient.createOrders(orderData);
        response.assertThat()
                .statusCode(SC_CREATED)
                .body("track", greaterThan(0))
                .extract()
                .path("track");
    }
}
