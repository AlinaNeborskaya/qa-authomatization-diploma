/**
 * Пакет {@code by.agsr.webuitesting.test} содержит базовые и вспомогательные
 * классы для тестирования веб-интерфейсов с использованием Selenium WebDriver
 * и JUnit 5.
 */
package online.rabko.basketball.web.test;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

/**
 * Базовый класс для веб-тестов.
 * <p>
 * Этот класс инициализирует WebDriver перед каждым тестом и закрывает
 * браузер после выполнения теста. Класс предназначен для расширения
 * другими тестовыми классами, чтобы обеспечить стандартное поведение
 * открытия и закрытия браузера.
 * </p>
 *
 * <p>Пример использования:</p>
 * <pre>{@code
 * public class MyTest extends BaseTest {
 *
 *     @Test
 *     void exampleTest() {
 *         driver.get("https://example.com");
 *         // Дальнейшие проверки
 *     }
 * }
 * }</pre>
 *
 * <p>Настройка:</p>
 * Для работы требуется, чтобы драйвер Chrome был доступен в системе.
 * Можно использовать системное свойство {@code webdriver.chrome.driver} для
 * указания пути к chromedriver.</p>
 *
 * @author AGSR
 * @version 1.0
 */
public class BaseTest {

    /**
     * Экземпляр WebDriver, используемый для взаимодействия с браузером.
     * Доступен наследникам класса.
     */
    protected WebDriver driver;

    /**
     * Метод, выполняющийся перед каждым тестом.
     * <p>
     * Инициализирует ChromeDriver и разворачивает окно браузера на полный экран.
     * </p>
     */
    @BeforeEach
    void setUp() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--window-size=1920,1080");
        options.addArguments("--disable-gpu");
        options.addArguments("--no-sandbox");
        options.addArguments("--disable-dev-shm-usage");
        driver = new ChromeDriver(options);
    }
    /**
     * Метод, выполняющийся после каждого теста.
     * <p>
     * Закрывает браузер и освобождает ресурсы WebDriver, если он был инициализирован.
     * </p>
     */
    @AfterEach
    void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
