package constants;

/**
 * Класс, содеражий в себе статические классы в виде констант или удобных методов для нахождения элементов на странице
 */
public class WebUrls {

    /**
     * Класс, содержащий в себе адреса для обращения
     */
    public static class Urls {
        public static final String YANDEX = "https://ya.ru/";
    }

    /**
     * Класс, содержащий в себе константы для поисковой системы Yandex
     */
    public static class Yandex {
        public static final String YANDEX_SERVICES = "//a[@title='Все сервисы']";

        public static String CLICK_TO_SERVICE(String serviceName) {
            return "//div[text()='" + serviceName + "']";
        }
    }

    /**
     * Класс, содержащий в себе константы для Яндекс Маркета
     */
    public static class YandexMarket {
        public static final String MARKET_LOGO = "//a[@id='logoPartMarket']";
        public static final String CATALOG_BUTTON = "//span[text()='Каталог']";
        public static final String CATALOG_ELECTRONICS = "//span[text()='Электроника']";
        public static final String CATALOG_ELECTRONICS_SMARTPHONES = "//a[text()='Смартфоны']";
        public static final String SEE_MORE_VENDORS = "//span[text()='Показать всё']";
        public static final String RESULTS_OF_SEARCHING = "//article[@data-auto='product-snippet']";
        public static final String NEXT_PAGE_BUTTON = "//div[@data-auto='pagination-next']//span";

        public static String CHOOSE_VENDOR(String serviceName) {
            return "//div[text()='" + serviceName + "']";
        }
    }
}
