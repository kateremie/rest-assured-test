package net.dictionary.api;

import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.Matchers.arrayWithSize;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.hasSize;

public class LookupAndgetLangsTest {

    private static final String API_URL = "https://dictionary.yandex.net/api/v1/dicservice.json/";
    private static final String API_KEY = "dict.1.1.20180814T143031Z.539f369b2ee456b7.4dfd7ef95493e7da70c33b54e283e2419f92954d";

    @Test
    public void lookupTest() throws UnirestException {

        given()
                .when()
                .get(API_URL + "lookup" + getPathFormated(API_KEY, "ru-ru","тест"))
                .then()
                .statusCode(200)
                .body("def.tr[0].text", hasItems("задание", "испытание"))
                .body("def.tr[0].syn[0].text", contains("экзамен","проверка", "диагностика", "видеотест", "опробывание"));

        String response = Unirest.get(API_URL + "lookup" + getPathFormated(API_KEY, "ru-ru","тест")).asString().getBody();

        System.out.println(response);

    }

    @Test
    public void langsTest() throws UnirestException {
        given()
                .when()
                .get(API_URL + "getLangs" + "?key=" + API_KEY)
                .then()
                .statusCode(200)
                .body(".", hasSize(101))
                .body(".", hasItems("ru-en","ru-fr","ru-de"));

        String response = Unirest.get(API_URL + "getLangs" + "?key=" + API_KEY).asString().getBody();

        System.out.println(response);
    }

    protected String getPathFormated(String key, String lang, String text) {
        return String.format("?key=%s&lang=%s&text=%s", key, lang, text);
    }
}
