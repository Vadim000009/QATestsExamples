package ru.bellintegrator.test;

import io.qameta.allure.Step;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.util.concurrent.TimeUnit;

/**
 * Класс, отвечающий за инициализацию веб драйвера, его закрытие и общие тесты
 *
 * @author Vadim Rodionov
 */
public class Steps {

    /**
     * Поле веб-драйвера
     */
    protected WebDriver chromeDriver;

    /**
     * Метод, который отвечает за инициализацию веб-драйвера
     */
    @Step
    public void открытьХром() {
        System.setProperty("webdriver.chrome.driver", System.getenv("CHROME_DRIVER"));
        ChromeOptions option = new ChromeOptions();
        option.addArguments("--remote-allow-origins=*");

        chromeDriver = new ChromeDriver(option);
        chromeDriver.manage().window().maximize();
        chromeDriver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
        chromeDriver.manage().timeouts().pageLoadTimeout(30, TimeUnit.SECONDS);
        chromeDriver.manage().timeouts().setScriptTimeout(30, TimeUnit.SECONDS);
    }

    /**
     * Метод, который выполяется после отработки теста, в результате чего происходит закрытие браузера и веб-драйвера
     */
    @Step
    public void закрытьХром() {
        chromeDriver.quit();
    }
}
