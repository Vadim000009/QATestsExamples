package ru.bellintegrator;

import io.qameta.allure.Feature;
import io.qameta.allure.Step;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import pages.WikipediaPageFactory;
import pages.YandexMarketPageFactory;
import pages.YandexPageFactory;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static constants.WebUrls.Urls.YANDEX;

/**
 * Класс, отвечающий за тесты через поисковую систему Yandex
 */
public class YandexTests extends BaseTest {

    @DisplayName("Задание 1.3 \"Таблица с ссылкой на Википедию и нахождением там преподавателей\"")
    @Feature("Проверка результатов поиска")
    @ParameterizedTest
    @ValueSource(strings = {"таблица"})
    public void findSheetsWithALinkToWikipediaAndFoundTheTeachers(String condition) {
        String wikipedia = "Таблица — Википедия";
        YandexPageFactory yandexPageFactory = startSearchInYandex(condition);

        checkResultSearch(yandexPageFactory.getResultSearch(), wikipedia, "Википедия");

        yandexPageFactory.goPageByLinkName(By.xpath("//h2[contains(., '" + wikipedia + "')]"));
        WikipediaPageFactory wikipediaPageFactory = PageFactory.initElements(chromeDriver, WikipediaPageFactory.class);

        List<String> tableWithRows = wikipediaPageFactory.getTableRows().stream().map(WebElement::getText).collect(Collectors.toList());
        checkPosition(
                IntStream.range(0, tableWithRows.size()).filter(i -> tableWithRows.get(i).contains("Сергей Владимирович")).findFirst().getAsInt(),
                IntStream.range(0, tableWithRows.size()).filter(i -> tableWithRows.get(i).contains("Сергей Адамович")).findFirst().getAsInt()
        );

    }

    @DisplayName("Задание 1.4 \"Поиск ноутбука на сайте Яндекс.Маркет\"")
    @Feature("Проверка результатов поиска")
    @ParameterizedTest
    @MethodSource("setConditions")
    public void findNotebooksOnYandexMarket(List<Integer> prices, List<String> manufactures, Integer expectedSize, Integer position) {
        chromeDriver.get(YANDEX);
        YandexPageFactory yandex = PageFactory.initElements(chromeDriver, YandexPageFactory.class);
        yandex.goPageOnMarket();
        YandexMarketPageFactory yandexMarket = PageFactory.initElements(chromeDriver, YandexMarketPageFactory.class);
        openCatalogAndSelectNotebooks(yandexMarket);
        setNotebookCharacteristics(yandexMarket, prices, manufactures);

        Integer actualSize = getActualResults(yandexMarket).size();
        checkSizeResult(expectedSize, actualSize);

        String expectedResultsSearch = getNotebookName(yandexMarket, position);

        searchInYandexMarket(yandexMarket, expectedResultsSearch);

        String actualResultsSearch = getNotebookName(yandexMarket, 0);
        checkNamePositionOnPage(expectedResultsSearch, actualResultsSearch);
    }

    @Step("Открываем каталог и переходим во категорию Ноутбуки")
    private void openCatalogAndSelectNotebooks(YandexMarketPageFactory yandexMarket) {
        yandexMarket.seeAll();
        yandexMarket.openCatalogWithNotebooks();
    }

    @Step("Устанавливаем характеристики ноутбука")
    private void setNotebookCharacteristics(YandexMarketPageFactory yandexMarket, List<Integer> prices, List<String> manufactures) {
        yandexMarket.setNotebookManufacturing(manufactures);
        yandexMarket.setNotebookPrice(prices);
    }

    @Step("Получить результаты поиска")
    private List<WebElement> getActualResults(YandexMarketPageFactory yandexMarket) {
        return yandexMarket.getListAfterSearching();
    }

    @Step("Выполнить поиск '{expectedResultsSearch}' в Яндекс Маркете")
    private void searchInYandexMarket(YandexMarketPageFactory yandexMarket, String expectedResultsSearch) {
        yandexMarket.setSearch(expectedResultsSearch);
        yandexMarket.pressSearch();
    }

    @Step("Получить наименование ноутбука из позиции {position}")
    private String getNotebookName(YandexMarketPageFactory yandexMarket, Integer position) {
        return yandexMarket.getNotebookFromPosition(position);
    }

    /**
     * Метод, содержащий в себе параметры теста
     *
     * @return - возвращает параметры для выполнения теста
     */
    private static Stream<Arguments> setConditions() {
        return Stream.of(Arguments.of(List.of(10000, 900000), List.of("Huawei", "Lenovo"), 12, 0));
    }
}
