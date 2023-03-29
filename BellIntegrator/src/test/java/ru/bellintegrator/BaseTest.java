package ru.bellintegrator;

import constants.WebUrls;
import io.qameta.allure.Step;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.PageFactory;
import pages.GoogleSearch;
import pages.YandexPageFactory;

import java.util.List;
import java.util.concurrent.TimeUnit;

import static constants.WebUrls.Urls.GOOGLE;
import static constants.WebUrls.Urls.YANDEX;

/**
 * Класс, отвечающий за инициализацию веб драйвера, его закрытие и общие тесты
 *
 * @author Vadim Rodionov
 */
public class BaseTest {

    /**
     * Поле веб-драйвера
     */
    protected WebDriver chromeDriver;

    /**
     * Метод, который отвечает за инициализацию веб-драйвера
     */
    @BeforeEach
    public void before() {
        System.setProperty("webdriver.chrome.driver", System.getenv("CHROME_DRIVER"));
        chromeDriver = new ChromeDriver();
        chromeDriver.manage().window().maximize();
        chromeDriver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
        chromeDriver.manage().timeouts().pageLoadTimeout(30, TimeUnit.SECONDS);
        chromeDriver.manage().timeouts().setScriptTimeout(30, TimeUnit.SECONDS);
    }

    /**
     * Метод, который выполяется после отработки теста, в результате чего происходит закрытие браузера и веб-драйвера
     */
    @AfterEach
    public void after() {
        chromeDriver.quit();
    }

    /**
     * Метод, в котором проверяется наличие определённого заголовка на странице поиска
     *
     * @param expectedSearchResult - поле, в котором содержится ожидаемый результат
     * @param actualSearchResults  - поле, в котором содержатся все результаты со страницы
     */
    @Step("Проверка результатов поиска - {expectedSearchResult}")
    public void checkResultSearch(List<WebElement> actualSearchResults, String expectedSearchResult, String resource) {
        Assertions.assertTrue(actualSearchResults.stream().anyMatch(text -> text.getText().contains(expectedSearchResult)),
                "Запрос, который бы находился бы на ресурсе " + resource + " не найдем");
    }

    /**
     * Метод, в котром проверяется разница в покупке и продажи банком валюты
     *
     * @param buyExchange  - поле, в котором содержится цена покупки
     * @param sellExchange - поле, в котором содержится цена продажи
     */
    @Step("Проверка разницы покупки и продажи")
    public void checkExchangeRates(Double buyExchange, Double sellExchange) {
        Assertions.assertTrue(buyExchange < sellExchange, "Цена покупки выше цены продажи");
    }

    /**
     * Метод, в котором проверяется наличие определённой валюты
     *
     * @param expectedCurrency - поле, в котором содержится ожидаемая валюта
     * @param actualCurrency   - поле, в котором содержится полученная со страницы валюта
     */
    @Step("Проверка наличия валюты {expectedCurrency} на странице")
    public void checkExchangeCurrency(String expectedCurrency, String actualCurrency) {
        Assertions.assertEquals(expectedCurrency, actualCurrency, "Валюта " + expectedCurrency + " не была найдена");
    }

    /**
     * Метод, в котором проверяется разница нахождения позиций
     *
     * @param positionFirst  - поле, в котором содержится первая позиция
     * @param positionSecond - поле, в котором содержится следющая позиция
     */
    @Step("Проверка следования позиций в таблице")
    public void checkPosition(Integer positionFirst, Integer positionSecond) {
        Assertions.assertTrue(positionFirst < positionSecond, "Позиции не соответствуют условию следования");
    }

    /**
     * Метод, в котором проверяются наличие наименования в результате поиска
     *
     * @param expectedName - поле, в котором содержится ожидаемое наименование
     * @param actualName   - поле, в котором содержится полученное наименование
     */
    @Step("Проверка наличия '{expectedName}' в результате нового поиска")
    public void checkNamePositionOnPage(String expectedName, String actualName) {
        Assertions.assertEquals(expectedName, actualName, "Названия позиций не совпадают");
    }

    /**
     * Метод, в котором проверяется количество найденных результатов
     *
     * @param expectedSize - поле, в котором содержится минимальное ожидаемое количество резульататов
     * @param actualSize   - поле, в котором содержится реальное количество результатов
     */
    @Step("Проверка полученного размера результатов")
    public void checkSizeResult(Integer expectedSize, Integer actualSize) {
        Assertions.assertTrue(expectedSize < actualSize, "Ожидаемвый результат больше полученного");
    }

    /**
     * Метод, в котором выполняется настройка веб-драйвера с переходом на страницу Google
     *
     * @param searchCondition - поле, в котором содержится условие поиска
     * @return возвращает объект класса GoogleSearch
     */
    @Step("Выполнить поиск в системе Google")
    public GoogleSearch startSearchInGoogle(String searchCondition) {
        chromeDriver.get(GOOGLE);
        GoogleSearch googleSearch = new GoogleSearch(chromeDriver, WebUrls.Google.INPUT_FIELD, WebUrls.Google.BUTTON_SEARCH);
        googleSearch.find(searchCondition);
        return googleSearch;
    }

    /**
     * Метод, в котором выполняется настройка веб-драйвера с переходом на страницу Yandex
     *
     * @param searchCondition - поле, в котором содержится условие поиска
     * @return возвращает объект класса GoogleSearch
     */
    @Step("Выполнить поиск в системе Yandex")
    public YandexPageFactory startSearchInYandex(String searchCondition) {
        chromeDriver.get(YANDEX);
        YandexPageFactory yandexPageFactory = PageFactory.initElements(chromeDriver, YandexPageFactory.class);
        yandexPageFactory.find(searchCondition);
        return yandexPageFactory;
    }
}
