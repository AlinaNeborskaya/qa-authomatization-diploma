/**
 * Пакет {@code by.agsr.webuitesting.test} содержит тесты веб-интерфейса приложения
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
 * Тесты для страницы {@link by.agsr.webuitesting.page.OnBoardingPage OnBoarding}.
 * <p>
 * Класс наследует {@link BaseTest} для стандартной инициализации WebDriver
 * и закрытия браузера после каждого теста. Использует Allure-аннотации
 * для генерации подробных отчетов о выполнении тестов.
 * </p>
 *
 * <p>Сценарий теста:</p>
 * <ol>
 *     <li>Открыть страницу логина {@link LoginPage}</li>
 *     <li>Ввести email и пароль из {@link ConfigReader}</li>
 *     <li>Кликнуть Login</li>
 *     <li>Проверить открытие страницы OnBoarding {@link OnBoardingPage}</li>
 * </ol>
 *
 * <p>Требования:</p>
 * <ul>
 *     <li>Настроенный WebDriver (ChromeDriver)</li>
 *     <li>Валидные учетные данные в {@link ConfigReader}</li>
 * </ul>
 *
 * <p>Пример использования:</p>
 * <pre>{@code
 * OnBoardingTest onBoardingTest = new OnBoardingTest();
 * onBoardingTest.shouldOpenOnBoardingPageAfterSuccessfulLogin();
 * }</pre>
 *
 * @author AGSR
 * @version 1.0
 */
@Epic("Web test")
@Feature("Onboarding")
public class OnBoardingTest extends BaseTest {

    /**
     * Проверяет успешное открытие страницы OnBoarding после авторизации.
     * <p>
     * Шаги теста:
     * <ol>
     *     <li>Открыть страницу логина</li>
     *     <li>Ввести валидный email и пароль</li>
     *     <li>Кликнуть Login</li>
     *     <li>Проверить, что OnBoardingPage открыта</li>
     * </ol>
     * </p>
     */
    @Test
    @AllureId("UI003")
    @Story("Пользователь успешно попадает на страницу OnBoarding после логина")
    @Severity(SeverityLevel.NORMAL)
    @Description("Проверка успешного открытия страницы OnBoarding после авторизации")
    void shouldOpenOnBoardingPageAfterSuccessfulLogin() {
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
}
