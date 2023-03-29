package ru.bellintegrator;

import constants.WebUrls.Google;
import io.qameta.allure.Feature;
import models.Exchange;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import pages.GoogleSearch;
import pages.OtkritieBank;

import java.util.List;
import java.util.stream.IntStream;

/**
 * Класс, отвечающий за тесты через поисковую систему Google
 */
public class GoogleTests extends BaseTest {

    @DisplayName("Задание 1.1 \"Гладиолус с ссылкой на Википедию\"")
    @Feature("Проверка результатов поиска")
    @ParameterizedTest
    @ValueSource(strings = {"Википедия"})
    public void findGladiolusWithALinkToWikipedia(String condition) {
        GoogleSearch googleSearch = startSearchInGoogle("Гладиолус");

        List<WebElement> resultSearch = chromeDriver.findElements(By.xpath(Google.RESULTS_OF_SEARCH(condition)));

        checkResultSearch(resultSearch, condition, condition);
    }

    @DisplayName("Задание 1.2 \"Банк открытие и сверка курса\"")
    @Feature("Проверка результатов поиска")
    @ParameterizedTest
    @ValueSource(strings = {"Открытие"})
    public void findTheOtkritieBankAndCheckTheDifferenceInExchange(String condition) {
        String expectedResultSearch = "Банк Открытие: кредит наличными — под 8,9% каждому";
        String[] currencies = {"USD", "EUR"};
        GoogleSearch googleSearch = startSearchInGoogle(condition);

        List<WebElement> actualResultSearch = chromeDriver.findElements(By.xpath(Google.RESULTS_OF_SEARCH(condition)));

        checkResultSearch(actualResultSearch, expectedResultSearch, condition);

        googleSearch.goPageByLinkName(By.xpath(Google.RESULTS_OF_SEARCH(condition)));
        OtkritieBank otkritieBank = new OtkritieBank(chromeDriver);
        List<Exchange> exchangeRates = otkritieBank.getExchangeList();
        IntStream.range(0, exchangeRates.size()).forEach(i -> {
            checkExchangeCurrency(currencies[i], exchangeRates.get(i).getCurrency());
            checkExchangeRates(exchangeRates.get(i).getBuyPrice(), exchangeRates.get(i).getSellPrice());
        });
    }
}
