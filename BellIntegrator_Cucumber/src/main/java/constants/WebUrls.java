package constants;

/**
 * Класс, содеражий в себе статические классы в виде констант или удобных методов для нахождения элементов на странице
 */
public class WebUrls {

    /**
     * Класс, содержащий в себе константы для поисковой системы Yandex
     */
    public static class Yandex {
        public static final String INPUT_FIELD = "//input[@placeholder='найдётся всё']";
        public static final String BUTTON_SEARCH = "//div/button[text()='Найти']";
        public static final String RESULTS_OF_SEARCH = "//*[@id='search-result']/li";
        public static final String YANDEX_SERVICES = "//div[@class='services-pinned__content']";
        public static final String YANDEX_MARKET = "//*[@aria-label='Маркет']";
    }

    public static class YandexMarket {
        public static final String CATALOG_BUTTON = "//button[@id='catalogPopupButton']";
        public static final String CATALOG_NOTEBOOKS_AND_PCS = "//div[@data-apiary-widget-name='@MarketNode/HeaderCatalog']//li//span[text()='Ноутбуки и компьютеры']";
        public static final String CATALOG_NOTEBOOKS = "//div[@data-apiary-widget-name='@MarketNode/HeaderCatalog']//div[@role='heading']//a[text()='Ноутбуки']";
        public static final String SEE_ALL = "//div[@data-baobab-name='main']//span[@color='cobaltBlue']";
        public static final String NOTEBOOK_PRICE = "//div[@class='cia-cs']//div[@data-auto='filter-range-glprice']//input[@type='text']";
        public static final String NOTEBOOK_MANUFACTURING = "//div[@class='cia-cs' and @data-zone-data[contains(.,'Производитель')]]//div[@data-zone-name='FilterValue']//input[@type='checkbox']";
        public static final String SEE_MORE_MANUFACTURING = "//div[@class='cia-cs' and @data-zone-data[contains(.,'Производитель')]]//button";
        public static final String RESULTS_OF_SEARCHING = "//article[@data-auto='product-snippet']";
        public static final String SHOW_MORE_BUTTON = "//div[@data-baobab-name='more']//button//span[text()='Показать ещё']";
        public static final String NEXT_PAGE_BUTTON = "//div[@data-auto='pagination-next']//span";
        public static final String SEARCH_FIELD_ON_MARKET = "//input[@placeholder='Искать товары']";
        public static final String SEARCH_BUTTON_ON_MARKET = "//button//span[text()='Найти']";
    }
}
