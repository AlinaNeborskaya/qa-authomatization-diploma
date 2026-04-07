/**
 * Пакет {@code by.agsr.webuitesting.test} содержит базовые и функциональные
 * тесты для веб-интерфейса приложения с использованием Selenium WebDriver,
 * JUnit 5 и Allure для отчетности.
 */
package online.rabko.basketball.web.test;

import io.qameta.allure.AllureId;
import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Story;
import online.rabko.basketball.web.page.DashboardPage;
import online.rabko.basketball.web.page.LoginPage;
import online.rabko.basketball.web.page.OnBoardingPage;
import online.rabko.basketball.util.ConfigReader;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Тесты для страницы {@link DashboardPage Dashboard}.
 * <p>
 * Класс расширяет {@link BaseTest} для получения базовой инициализации WebDriver
 * и закрытия браузера после выполнения тестов. Использует Allure-аннотации
 * для подробной отчетности по тестам.
 * </p>
 *
 * <p>Сценарий теста:</p>
 * <ol>
 *     <li>Открытие страницы логина {@link LoginPage LoginPage}</li>
 *     <li>Ввод email и пароля из {@link ConfigReader ConfigReader}</li>
 *     <li>Проверка открытия страницы OnBoarding {@link by.agsr.webuitesting.page.OnBoardingPage OnBoardingPage}</li>
 *     <li>Переход на Dashboard и проверка его открытия</li>
 * </ol>
 *
 * <p>Пример использования:</p>
 * <pre>{@code
 * DashboardTest dashboardTest = new DashboardTest();
 * dashboardTest.shouldOpenDashboardAfterLoginAndOnBoarding();
 * }</pre>
 *
 * <p>Требования:</p>
 * Для работы теста требуется:
 * <ul>
 *     <li>Настроенный WebDriver (ChromeDriver)</li>
 *     <li>Доступные учетные данные для авторизации (email и пароль)</li>
 * </ul>
 *
 * @author AGSR
 * @version 1.0
 */
@Epic("Web test")
@Feature("Dashboard")
public class DashboardTest extends BaseTest {

    /**
     * Проверяет, что пользователь может открыть страницу Dashboard после
     * успешного входа в систему и прохождения OnBoarding.
     * <p>
     * Тест выполняет следующие шаги:
     * <ol>
     *     <li>Открывает страницу логина</li>
     *     <li>Вводит email и пароль</li>
     *     <li>Проверяет открытие OnBoardingPage</li>
     *     <li>Переходит на DashboardPage и проверяет его открытие</li>
     * </ol>
     * </p>
     */
    @Test
    @AllureId("UI004")
    @Story("Пользователь видит Dashboard после успешного логина и перехода с OnBoarding")
    @Severity(SeverityLevel.NORMAL)
    @Description("Проверка того, что пользователь может открыть страницу Dashboard после авторизации и OnBoarding")
    void shouldOpenDashboardAfterLoginAndOnBoarding() {
        LoginPage loginPage = new LoginPage(driver);
        loginPage.load();

        OnBoardingPage onBoardingPage = loginPage.get()
                .enterEmail(ConfigReader.getEmail())
                .enterPassword(ConfigReader.getPassword())
                .clickLogin();

        assertTrue(onBoardingPage.isPageOpened(), "OnBoardingPage должна быть открыта");

        DashboardPage dashboardPage = onBoardingPage.goToDashboard();
        assertTrue(dashboardPage.isPageOpened(), "DashboardPage должна быть открыта");
    }
}
