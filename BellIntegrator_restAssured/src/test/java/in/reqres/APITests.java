package in.reqres;

import data.*;
import io.qameta.allure.Step;
import io.restassured.http.ContentType;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import static io.restassured.RestAssured.given;

public class APITests {

    /**
     * Задание 2.1
     */
    @Test
    public void checkAvatarsTest() {
        Resourse resourse = given()
                .when()
                .get("https://reqres.in/api/users?page=2")
                .then()
                .extract()
                .body()
                .as(Resourse.class);
        List<String> responseAvatars = new ArrayList<>();
        resourse.getData().stream().map(DataUser::getAvatar).forEach(responseAvatars::add);

        Set<String> uniqueAvatars = new HashSet<String>(responseAvatars);

        checkSize(responseAvatars.size(), uniqueAvatars.size());
    }

    /**
     * Задание 2.2 - Успешный логин
     */
    @Test
    public void checkLoginInSystemTest() {
        Map<String, String> data = new HashMap<>();
        data.put("email", "eve.holt@reqres.in");
        data.put("password", "cityslicka");
        LoginToken loginToken = given()
                .contentType(ContentType.JSON)
                .body(data)
                .when()
                .post("https://reqres.in/api/login")
                .then()
                .statusCode(200)
                .extract()
                .body().as(LoginToken.class);
        Assert.assertEquals(loginToken.getToken(), "QpwL5tke4Pnpja7X4", "Токен невалиден");
    }

    /**
     * Задание 2.2 - Неуспешный логин
     */
    @Test
    public void checkFailLoginInSystemTest() {
        Map<String, String> data = new HashMap<>();
        data.put("email", "eve.holt@reqres.in");
        LoginError loginError = given()
                .contentType(ContentType.JSON)
                .body(data)
                .when()
                .post("https://reqres.in/api/login")
                .then()
                .statusCode(400)
                .extract()
                .body().as(LoginError.class);
        Assert.assertEquals(loginError.getError(), "Missing password", "Ошибки авторизации не произошло");

    }

    /**
     * Задание 2.3
     */
    @Test
    public void checkIsListSortedTest() {
        ResourcesUnknown response = given()
                .contentType(ContentType.JSON)
                .when()
                .get("https://reqres.in/api/unknown")
                .then()
                .extract()
                .body()
                .as(ResourcesUnknown.class);
        List<Integer> unsortedYears = response.getData().stream().map(DataUserFromUnknown::getYear).collect(Collectors.toList());
        List<Integer> sortedYears = response.getData().stream().map(DataUserFromUnknown::getYear).sorted().collect(Collectors.toList());
        checkLists(unsortedYears, sortedYears);
    }

    /**
     * Задание 2.4
     */
    @Test
    public void checkTagsSizeTest() {
        String response = given()
                .contentType(ContentType.XML)
                .when()
                .get("https://gateway.autodns.com/")
                .then()
                .assertThat()
                .extract()
                .asString();
        Pattern pattern = Pattern.compile("</.*>", Pattern.MULTILINE);
        Matcher matcher = pattern.matcher(response);
        int from = 0, tagCount = 0;
        while (matcher.find(from)) {
            tagCount++;
            from = matcher.start() + 1;
        }
        checkSize(tagCount, 14);
    }

    @Step("Проверка размеров")
    private void checkSize(Integer actualSize, Integer expectedSize) {
        Assert.assertEquals(actualSize, expectedSize, "Размеры не совпадают");
    }

    @Step("Проверка одинаковости")
    private void checkLists(List<Integer> actualList, List<Integer> expectedList) {
        Assert.assertEquals(actualList, expectedList);
    }
}