package order;

import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.Test;

import client.OrderClient;

import static org.apache.http.HttpStatus.*;
import static org.hamcrest.Matchers.notNullValue;

public class ListOrderTest {
    private OrderClient orderClient;

    @Test
    @DisplayName("Получение списка заказов")
    @Description("Проверка запроса на получение списка заказов")
    public void orderList() {
        orderClient = new OrderClient();
        ValidatableResponse responce = orderClient.orderList();
        responce.assertThat()
                .statusCode(SC_OK)
                .body("orders", notNullValue());
    }
}
