/**
 * Пакет {@code by.agsr.webuitesting.test} содержит тесты для веб-интерфейса приложения
 * с использованием Selenium WebDriver, JUnit 5 и Allure.
 */
package online.rabko.basketball.web.test;

import io.qameta.allure.AllureId;
import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Story;
import online.rabko.basketball.web.page.LoginPage;
import online.rabko.basketball.web.page.OnBoardingPage;
import online.rabko.basketball.util.ConfigReader;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Тесты для страницы {@link LoginPage Login}.
 * <p>
 * Класс расширяет {@link BaseTest} для обеспечения инициализации WebDriver
 * и закрытия браузера после каждого теста. Использует аннотации Allure для
 * формирования подробной отчетности по тестам.
 * </p>
 *
 * <p>Сценарии тестирования:</p>
 * <ul>
 *     <li>Успешная авторизация с валидными учетными данными</li>
 *     <li>Неудачная авторизация с неверным паролем, проверка отображения ошибки</li>
 * </ul>
 *
 * <p>Требования:</p>
 * <ul>
 *     <li>Настроенный WebDriver (ChromeDriver)</li>
 *     <li>Доступные валидные учетные данные в {@link ConfigReader}</li>
 * </ul>
 *
 * <p>Пример использования:</p>
 * <pre>{@code
 * LoginTest loginTest = new LoginTest();
 * loginTest.shouldLoginSuccessfullyWithValidCredentials();
 * loginTest.shouldNotLoginWithInvalidPassword();
 * }</pre>
 *
 * @author AGSR
 * @version 1.0
 */
@Epic("Web test")
@Feature("Authorization")
public class LoginTest extends BaseTest {

    /**
     * Проверяет успешную авторизацию с валидными учетными данными.
     * <p>
     * Шаги теста:
     * <ol>
     *     <li>Открыть страницу логина</li>
     *     <li>Ввести email и пароль из {@link ConfigReader}</li>
     *     <li>Кликнуть Login</li>
     *     <li>Проверить открытие OnBoardingPage</li>
     * </ol>
     * </p>
     */
    @Test
    @AllureId("UI001")
    @Story("Пользователь успешно логинится с валидными данными")
    @Severity(SeverityLevel.NORMAL)
    @Description("Проверка успешной авторизации с валидным email и паролем")
    void shouldLoginSuccessfullyWithValidCredentials() {
        LoginPage loginPage = new LoginPage(driver);
        loginPage.load();

        OnBoardingPage onBoardingPage = loginPage
                .get()
                .enterEmail(ConfigReader.getEmail())
                .enterPassword(ConfigReader.getPassword())
                .clickLogin();

        onBoardingPage.get();
        assertTrue(onBoardingPage.isPageOpened(), "OnBoardingPage должна быть открыта после успешного логина");
    }

    /**
     * Проверяет, что авторизация не происходит с неверным паролем.
     * <p>
     * Шаги теста:
     * <ol>
     *     <li>Открыть страницу логина</li>
     *     <li>Ввести валидный email и неверный пароль</li>
     *     <li>Кликнуть Login</li>
     *     <li>Проверить, что отображается сообщение об ошибке</li>
     * </ol>
     * </p>
     */
    @Test
    @AllureId("UI002")
    @Story("Пользователь не может залогиниться с неверным паролем")
    @Severity(SeverityLevel.NORMAL)
    @Description("Проверка авторизации с невалидными данными — должно отображаться сообщение об ошибке")
    void shouldNotLoginWithInvalidPassword() {
        LoginPage loginPage = new LoginPage(driver);
        loginPage.load();

        loginPage.get()
                .enterEmail(ConfigReader.getEmail())
                .enterPassword("wrongPassword")
                .clickLogin();

        loginPage.get();
        assertTrue(loginPage.isErrorMessageDisplayed(), "Ошибка авторизации должна отображаться для неверного пароля");
    }
}
