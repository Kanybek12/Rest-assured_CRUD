package com.java.demo.apiTest;

import com.java.demo.model.UserDTO;
import io.restassured.RestAssured;
import org.apache.http.HttpStatus;
import org.hamcrest.Matchers;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;

import static com.java.demo.specification.Specifications.requestSpecification;

public class PostRequestTest {

    @Test
    @DisplayName("Тестирование тестового запроса Post с проверкой status code = 201")
    public void postRequestCheckStatusCode() {
        RestAssured.given()
                .spec(requestSpecification())                      //---> Указание RequestSpecification для формирования request
                .body(new UserDTO("morpheus", "leader")) //---> body для запроса с методом POST
                .post("/api/users")                              //---> Endpoint для выполнения запроса GET
                .then()
                .statusCode(HttpStatus.SC_CREATED);                 //---> Проверка статус код
    }

    @Test
    @DisplayName("Тестирование тестового запроса Post c проверкой key/value по полям name, job == AK Match")
    public void postRequestCheckResponseJsonBody() {
        RestAssured.given()
                .spec(requestSpecification())                        //---> Указание RequestSpecification для формирования request
                .body(new UserDTO("morpheus", "leader"))   //---> body для запроса с методом POST
                .post("/api/users")                               //---> Endpoint для выполнения запроса GET
                .then()
                .statusCode(HttpStatus.SC_CREATED)                   //---> Проверка статус код
                .assertThat()
                .body("name", Matchers.is("morpheus"))      //---> Проверка Body по key и value в json
                .body("job", Matchers.is("leader"));        //---> Проверка Body по key и value в json
    }
}