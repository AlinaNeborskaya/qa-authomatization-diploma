package online.rabko.basketball.web.page;

import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.LoadableComponent;

/**
 * Абстрактный базовый класс для всех страниц веб-приложения.
 *
 * <p>Этот класс наследуется от {@link LoadableComponent} и обеспечивает базовую
 * функциональность для работы с {@link WebDriver}. Он служит шаблоном для всех
 * страниц, позволяя реализовать паттерн "Loadable Component" для безопасной загрузки страниц.</p>
 *
 * @param <T> тип страницы, наследуемой от {@link LoadableComponent}, что позволяет
 *            использовать fluent API для проверок загрузки страницы.
 */
@Slf4j
public abstract class BasePage<T extends LoadableComponent<T>> extends LoadableComponent<T> {

    /** Экземпляр WebDriver для взаимодействия с веб-страницей. */
    protected WebDriver driver;

    /**
     * Конструктор базовой страницы.
     *
     * @param driver экземпляр {@link WebDriver}, используемый для управления браузером
     */
    protected BasePage(WebDriver driver) {
        this.driver = driver;
        log.info("Инициализирована базовая страница: {}", this.getClass().getSimpleName());
    }
}
