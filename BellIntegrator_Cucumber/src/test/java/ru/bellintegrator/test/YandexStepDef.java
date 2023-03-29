package ru.bellintegrator.test;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.junit.Assert;
import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.support.PageFactory;
import pages.YandexMarketPageFactory;
import pages.YandexPageFactory;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Класс, отвечающий за тесты через поисковую систему Yandex
 */
public class YandexStepDef extends Steps {

    private YandexMarketPageFactory yandexMarket;
    private YandexPageFactory yandex;

    @Given("перейти на сайт Яндекса")
    public void перейтиНаСайтЯндекса() {
        открытьХром();
        chromeDriver.get("https:\\\\ya.ru/");
    }

    @Then("открыть меню сервисов")
    public void открытьМенюСервисов() {
        yandex = PageFactory.initElements(chromeDriver, YandexPageFactory.class);
    }

    @When("открыть меню сервисов и перейти в Яндекс Маркет")
    public void открытьМенюСервисовИПерейтиВЯндексМаркет() {
        yandex.goPageOnMarket();
        yandexMarket = PageFactory.initElements(chromeDriver, YandexMarketPageFactory.class);
    }

    @Then("проверить, что мы перешли в {string}")
    public void проверитьЧтоМыПерешлиВЯндексМаркет(String checkTitle) {
        String title = chromeDriver.getTitle();
        Assert.assertTrue(title.contains(checkTitle));
    }

    @When("открыть каталог")
    public void открытьКаталог() {
        yandexMarket.seeAll();
    }

    @When("выбрать ноутбуки")
    public void выбратьНоутбуки() {
        yandexMarket.openCatalogWithNotebooks();
    }

    @When("указать производителя {string}")
    public void указатьПроизводителя(String condition) {
        List<String> conditions = Arrays.asList(condition.split(","));
        yandexMarket.setNotebookManufacturing(conditions);

    }

    @When("указать цену от {string} до {string}")
    public void указатьЦенуОтДо(int minPrice, int maxPrice) {
        List <Integer> condition = new ArrayList<>();
        condition.add(minPrice);
        condition.add(maxPrice);
        yandexMarket.setNotebookPrice(condition);
    }

    @Then("проверить, что результатов на странице больше {string}")
    public void проверитьЧтоРезультатовНаСтраницеБольше(int expectedSize) {
        int actualSize = yandexMarket.getListAfterSearching().size();
        Assertions.assertTrue(expectedSize < actualSize, "Ожидаемвый результат больше полученного");
    }

    @Then("проверить, что результат в позиции {string} есть в поисковой выдаче")
    public void проверитьЧтоРезультатВПозицииЕстьВПоисковойВыдаче(int arg0) {
        String expectedResultsSearch = yandexMarket.getNotebookFromPosition(arg0);
        yandexMarket.setSearch(expectedResultsSearch);
        yandexMarket.pressSearch();
        String actualResultsSearch = yandexMarket.getNotebookFromPosition(0);
        Assertions.assertEquals(expectedResultsSearch, actualResultsSearch, "Названия позиций не совпадают");
    }

    @Then("закончить работу")
    public void закончитьРаботу() {
        закрытьХром();
    }

}
