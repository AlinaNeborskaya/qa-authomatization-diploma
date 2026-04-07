package online.rabko.basketball.web.test;

import io.qameta.allure.AllureId;
import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Story;
import java.util.UUID;
import java.util.stream.Stream;
import online.rabko.basketball.web.page.DashboardPage;
import online.rabko.basketball.web.page.LoginPage;
import online.rabko.basketball.web.page.OnBoardingPage;
import online.rabko.basketball.web.page.ProjectPage;
import online.rabko.basketball.util.ConfigReader;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Тесты для страницы {@link ProjectPage Project}.
 * <p>
 * Класс расширяет {@link BaseTest} для стандартной инициализации WebDriver
 * и закрытия браузера после каждого теста. Использует Allure-аннотации
 * для формирования подробных отчетов о выполнении тестов.
 * </p>
 *
 * <p>Сценарии тестирования:</p>
 * <ul>
 *     <li>Открытие страницы проекта через Dashboard</li>
 *     <li>Создание проекта с валидными данными</li>
 *     <li>Попытка создания проекта с некорректными данными (пустое имя)</li>
 * </ul>
 *
 * <p>Требования:</p>
 * <ul>
 *     <li>Настроенный WebDriver (ChromeDriver)</li>
 *     <li>Валидные учетные данные в {@link ConfigReader}</li>
 * </ul>
 *
 * <p>Пример использования:</p>
 * <pre>{@code
 * ProjectTest projectTest = new ProjectTest();
 * projectTest.shouldOpenProjectPageFromDashboard();
 * projectTest.shouldCreateProjectWithValidData();
 * projectTest.shouldFailToCreateProjectWithInvalidData();
 * }</pre>
 *
 * @author AGSR
 * @version 1.0
 */
@Epic("Web test")
@Feature("Project")
public class ProjectTest extends BaseTest {

    /**
     * Проверяет, что пользователь может открыть страницу проекта через Dashboard.
     * <p>
     * Шаги теста:
     * <ol>
     *     <li>Открыть страницу логина</li>
     *     <li>Ввести валидный email и пароль</li>
     *     <li>Проверить открытие OnBoardingPage</li>
     *     <li>Перейти на DashboardPage и проверить открытие</li>
     *     <li>Перейти на ProjectPage и проверить открытие</li>
     * </ol>
     * </p>
     */
    @Test
    @AllureId("UI005")
    @Story("Пользователь открывает страницу проекта через Dashboard")
    @Severity(SeverityLevel.CRITICAL)
    @Description("Проверка успешного открытия страницы проекта Project")
    void shouldOpenProjectPageFromDashboard() {
        LoginPage loginPage = new LoginPage(driver);
        loginPage.load();

        OnBoardingPage onBoardingPage = loginPage
                .get()
                .enterEmail(ConfigReader.getEmail())
                .enterPassword(ConfigReader.getPassword())
                .clickLogin();
        onBoardingPage.get();
        assertTrue(onBoardingPage.isPageOpened(), "OnBoardingPage должна быть открыта");

        DashboardPage dashboardPage = onBoardingPage.goToDashboard();
        dashboardPage.get();
        assertTrue(dashboardPage.isPageOpened(), "DashboardPage должна быть открыта");

        ProjectPage projectPage = dashboardPage.goToProject();
        projectPage.get();
        assertTrue(projectPage.isPageOpened(), "ProjectPage должна быть открыта");
    }

    /**
     * Проверяет успешное создание проекта с валидными данными.
     * <p>
     * Шаги теста:
     * <ol>
     *     <li>Авторизация и переход на DashboardPage</li>
     *     <li>Переход на ProjectPage</li>
     *     <li>Создание проекта с уникальным именем и описанием</li>
     *     <li>Проверка успешного сообщения</li>
     * </ol>
     * </p>
     */
    /**
     * Поставщик данных для параметризованного теста.
     * Каждый массив содержит имя проекта и описание проекта.
     */
    static Stream<String> validProjectDataProvider() {
        return Stream.of(
                "Project Alpha",
                "Project Beta",
                "Project Gamma"
        );
    }

    @ParameterizedTest
    @MethodSource("validProjectDataProvider")
    @AllureId("UI006")
    @Story("Пользователь создаёт проект с валидными данными")
    @Severity(SeverityLevel.CRITICAL)
    @Description("Проверка успешного создания проекта с корректным именем и описанием")
    void shouldCreateProjectWithValidData(String projectData) {
        LoginPage loginPage = new LoginPage(driver);
        loginPage.load();

        OnBoardingPage onBoardingPage = loginPage
                .get()
                .enterEmail(ConfigReader.getEmail())
                .enterPassword(ConfigReader.getPassword())
                .clickLogin();
        onBoardingPage.get();
        assertTrue(onBoardingPage.isPageOpened(), "OnBoardingPage должна быть открыта");

        DashboardPage dashboardPage = onBoardingPage.goToDashboard();
        dashboardPage.get();
        assertTrue(dashboardPage.isPageOpened(), "DashboardPage должна быть открыта");

        ProjectPage projectPage = dashboardPage.goToProject();
        projectPage.get();
        String uuid = UUID.randomUUID().toString();
        projectPage.enterProjectName(projectData + ":" + uuid)
                .enterDescription(projectData + ":" + uuid)
                .clickAddProject();

        assertEquals("Successfully added the new project.", projectPage.getSuccessMessage(),
                "Сообщение об успешном создании проекта не соответствует ожидаемому");
    }

    /**
     * Проверяет, что создание проекта с некорректными данными (пустое имя) возвращает ошибку.
     * <p>
     * Шаги теста:
     * <ol>
     *     <li>Авторизация и переход на DashboardPage</li>
     *     <li>Переход на ProjectPage</li>
     *     <li>Попытка создать проект с пустым именем</li>
     *     <li>Проверка сообщения об ошибке имени проекта</li>
     * </ol>
     * </p>
     */
    @Test
    @AllureId("UI007")
    @Story("Пользователь пытается создать проект с некорректными данными")
    @Severity(SeverityLevel.CRITICAL)
    @Description("Проверка, что создание проекта с пустым именем возвращает сообщение об ошибке")
    void shouldFailToCreateProjectWithInvalidData() {
        LoginPage loginPage = new LoginPage(driver);
        loginPage.load();

        OnBoardingPage onBoardingPage = loginPage
                .get()
                .enterEmail(ConfigReader.getEmail())
                .enterPassword(ConfigReader.getPassword())
                .clickLogin();
        onBoardingPage.get();
        assertTrue(onBoardingPage.isPageOpened(), "OnBoardingPage должна быть открыта");

        DashboardPage dashboardPage = onBoardingPage.goToDashboard();
        dashboardPage.get();
        assertTrue(dashboardPage.isPageOpened(), "DashboardPage должна быть открыта");

        ProjectPage projectPage = dashboardPage.goToProject();
        projectPage.get();
        projectPage.enterProjectName("")
                .enterDescription("test")
                .clickAddProject();

        assertEquals("The Name field is required.", projectPage.getProjectNameErrorMessage(),
                "Сообщение об ошибке имени проекта не соответствует ожидаемому");
    }
}
