package com.java.demo.apiTest;

import com.java.demo.model.UserDTO;
import io.restassured.RestAssured;
import org.apache.http.HttpStatus;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;

import static com.java.demo.specification.Specifications.requestSpecification;

public class DeleteRequestTest {

    @Test
    @DisplayName("Тестирование запроса Delete c удалением пользователя")
    public void deleteRequestCheckStatusCode() {
        int id; //---> Преобразование с int можно выполнять либо извлекать из Response значение id в виде строки

        id = Integer.parseInt(RestAssured.given()
                .spec(requestSpecification())                      //---> Указание RequestSpecification для формирования request
                .body(new UserDTO("morpheus", "leader")) //---> body для запроса с методом POST
                .post("/api/users")                             //---> Endpoint для выполнения запроса GET
                .then()
                .statusCode(HttpStatus.SC_CREATED)                //---> Проверка статус код
                .extract()
                .response()
                .body()
                .path("id"));                                   //---> указания поля из Response Json для извлечения данных


        RestAssured.given()
                .spec(requestSpecification())                     //---> Указание RequestSpecification для формирования request
                .delete("/api/users/" + id)                    //---> Endpoint для выполнения запроса GET
                .then()
                .statusCode(HttpStatus.SC_NO_CONTENT);            //---> Проверка статус код
    }
}