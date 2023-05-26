package com.java.demo.apiTest;

import io.restassured.RestAssured;
import org.apache.http.HttpStatus;
import org.hamcrest.Matchers;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import java.text.SimpleDateFormat;
import java.util.Date;
import static com.java.demo.specification.Specifications.requestSpecification;
import static com.java.demo.specification.Specifications.responseSpecificationScOk;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;


public class GetRequestTest {

    Date date = new Date();
    String pattern = "HH:mm:ss";
    SimpleDateFormat dateFormat = new SimpleDateFormat(pattern);


    @Test
    @DisplayName("Тестирование запроса Get c проверкой status code = 200")


    public void getRequestCheckStatusCode() {
        RestAssured.given()
                .spec(requestSpecification())           //---> Указание RequestSpecification для формирования request
                .get("/api/users/2")                 //---> Endpoint для выполнения запроса GET
                .then()
                .statusCode(HttpStatus.SC_OK);         //---> Проверка статус код


        System.out.println("1. Тестирование запроса GET c проверкой status code = OK (200) "+ dateFormat.format(date));

    }

    @Test
    @DisplayName("Тестирование запроса Get c проверкой key/value по полям id, email, first_name, last_name")
    public void getRequestCheckResponseJsonBody() {
        RestAssured.given()
                .spec(requestSpecification())               //---> Указание RequestSpecification для формирования request
                .get("/api/users/2")                     //---> Endpoint для выполнения запроса GET
                .then()
                .statusCode(HttpStatus.SC_OK)              //---> Проверка статус код
                .assertThat()
                .body("data.id", Matchers.is(2))   //---> Проверка Body по key и value в json
                .body("data.email", Matchers.is("janet.weaver@reqres.in"))   //---> Проверка Body по key и value в json
                .body("data.first_name", Matchers.is("Janet"))    //---> Проверка Body по key и value в json
                .body("data.last_name", Matchers.is("Weaver"));   //---> Проверка Body по key и value в json
        System.out.println("2. Тестирование запроса Get c проверкой key/value по полям id, email, first_name, last_name "+ dateFormat.format(date));
    }

    @Test
    @DisplayName("Тестирование запроса Get c валидацией ответа по json схеме")
    public void getRequestCheckResponseWithJsonSchema() {
        RestAssured.given()
                .spec(requestSpecification())//---> Указание RequestSpecification для формирования request
                .get("/api/users/2")//---> Endpoint для выполнения запроса GET
                .then()
                .spec(responseSpecificationScOk())//---> Указание ResponseSpecification
                .assertThat()
                .body(matchesJsonSchemaInClasspath("SchemaUsersById.json"));//---> Валидация Response json по Json Schema.
               // Сгенерировать Json Schema можно https://www.liquid-technologies.com/online-json-to-schema-converter и далее создать файл SchemaUsersById.
              // json в каталоге src/test/resources. !!!Внимание по умолчанию вычитывается из папки resources помеченной как ресурсы тестов в проекте
        System.out.println("3. Тестирование запроса Get c валидацией ответа по json схеме "+ dateFormat.format(date));
    }
}