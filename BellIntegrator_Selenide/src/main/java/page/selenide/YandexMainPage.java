package page.selenide;

import com.codeborne.selenide.Selenide;
import io.qameta.allure.Step;
import org.openqa.selenium.By;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.switchTo;
import static constants.WebUrls.Yandex.CLICK_TO_SERVICE;
import static constants.WebUrls.Yandex.YANDEX_SERVICES;


/**
 * Класс, отвечающий за работу на странице Яндекса
 */
public class YandexMainPage extends BasePage {

    @Step("Нажать на кнопку 'Сервисы'")
    public YandexMainPage clickToServices() {
        $(By.xpath(YANDEX_SERVICES)).click();
        return this;
    }

    @Step("Перейти в сервис {serviceName}")
    public YandexMainPage clickToCurrentService(String serviceName) {
        $(By.xpath(CLICK_TO_SERVICE(serviceName))).click();
        return this;
    }

    @Step("Переключиться на вкладку {tabNumber}")
    public <T extends BasePage> T switchToAnotherTab(Integer tabNumber, Class<T> typeNextPage) {
        switchTo().window(1);
        return typeNextPage.cast(Selenide.page(typeNextPage));
    }
}
