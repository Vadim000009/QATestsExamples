package page.selenide;

import com.codeborne.selenide.ElementsCollection;
import constants.WebUrls.YandexMarket;
import io.qameta.allure.Step;
import org.junit.jupiter.api.Assertions;

import java.util.Objects;
import java.util.stream.IntStream;

import static com.codeborne.selenide.Selenide.*;

/**
 * Класс, отвечающий за работу на странице Яндекс Маркета
 */
public class YandexMarketMainPage extends BasePage {

    @Step("Переход на Маркет для обхода антибота")
    public YandexMarketMainPage antiBotRefreshPage() {
        $x(YandexMarket.MARKET_LOGO).click();
        return this;
    }

    @Step("Открытие каталога и выбор сматфонов")
    public YandexMarketMainPage openCatalogWithSmartphones() {
        $x(YandexMarket.CATALOG_BUTTON).click();
        $x(YandexMarket.CATALOG_ELECTRONICS).hover();
        $x(YandexMarket.CATALOG_ELECTRONICS_SMARTPHONES).click();
        return this;
    }

    @Step("Показать всех производителей")
    public YandexMarketMainPage clickToSeeAllVendors() {
        $x(YandexMarket.SEE_MORE_VENDORS).click();
        return this;
    }

    @Step("Указать производителем {vendor}")
    public YandexMarketMainPage setVendor(String vendor) {
        $x(YandexMarket.CHOOSE_VENDOR(vendor)).click();
        checkVendorOnPage($x(YandexMarket.CHOOSE_VENDOR(vendor)).getText(), vendor);
        return this;
    }

    @Step("Проверить наличия вендора {expectedResult} на странице")
    private void checkVendorOnPage(String actualResult, String expectedResult) {
        Assertions.assertEquals(expectedResult, actualResult, "Вендор " + expectedResult + " отсутствует на странице");
    }

    @Step("Получить результаты со всех страниц")
    public YandexMarketMainPage getResults(String expectedVendor) {
        ElementsCollection resultsOfSearch = $$x(YandexMarket.RESULTS_OF_SEARCHING);
        Integer pageNumber = 0;
        do {
            pageNumber++;
            $x(YandexMarket.NEXT_PAGE_BUTTON).click();
            resultsOfSearch.addAll(getResultsFromPage(pageNumber, expectedVendor));

        } while ($x(YandexMarket.NEXT_PAGE_BUTTON).isDisplayed());
        return this;
    }

    @Step("Получить результаты по страницы {pageNumber}")
    private ElementsCollection getResultsFromPage(Integer pageNumber, String expectedVendor) {
        ElementsCollection resultsOnPage = $$x("//article[@data-auto='product-snippet']");
        IntStream
                .range(0, resultsOnPage.size())
                .forEachOrdered(i -> Assertions.assertTrue(resultsOnPage.contains(expectedVendor), "На странице содержатся и другие вендоры"));
        return resultsOnPage;
    }

    @Step("Проверяем, что находимся на Яндекс Маркете")
    public YandexMarketMainPage checkTitleName() {
        Assertions.assertTrue(Objects.requireNonNull(title()).contains("Яндекс Маркет"), "Текущий сайт не является Яндекс Маркетом");
        return this;
    }
}
