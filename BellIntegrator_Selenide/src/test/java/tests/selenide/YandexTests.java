package tests.selenide;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import page.selenide.YandexMainPage;
import page.selenide.YandexMarketMainPage;

import java.util.stream.Stream;

import static com.codeborne.selenide.Selenide.*;
import static constants.WebUrls.Urls.YANDEX;

/**
 * Класс, отвечающий за тесты через поисковую систему Yandex
 */
public class YandexTests extends BaseTests {

    @DisplayName("Поиск определённых моделей на сайте Яндекс Маркета")
    @ParameterizedTest
    @MethodSource("setConditions")
    public void yandexMarketSmartphones(String serviceName, String vendorName) throws InterruptedException {
        open(YANDEX, YandexMainPage.class)
                .clickToServices()
                .clickToCurrentService(serviceName)
                .switchToAnotherTab(1, YandexMarketMainPage.class)
                .checkTitleName()
                .antiBotRefreshPage()
                .openCatalogWithSmartphones()
                .clickToSeeAllVendors()
                .setVendor(vendorName)
                .getResults(vendorName);
    }

    /**
     * Метод, содержащий в себе параметры теста
     *
     * @return - возвращает параметры для выполнения теста
     */
    private static Stream<Arguments> setConditions() {
        return Stream.of(Arguments.of("Маркет", "Apple"));
    }
}
