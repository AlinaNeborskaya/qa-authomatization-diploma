package online.rabko.basketball.web.test;

import io.qameta.allure.*;
import java.util.UUID;
import java.util.stream.Stream;
import online.rabko.basketball.web.assertion.ProjectPageAssertion;
import online.rabko.basketball.web.assertion.OnBoardingPageAssertion;
import online.rabko.basketball.web.page.DashboardPage;
import online.rabko.basketball.web.page.LoginPage;
import online.rabko.basketball.web.page.OnBoardingPage;
import online.rabko.basketball.web.page.ProjectPage;
import online.rabko.basketball.util.ConfigReader;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

@Epic("Web test")
@Feature("Project")
public class ProjectTest extends BaseTest {

    private ProjectPage openProjectPage() {
        LoginPage loginPage = new LoginPage(driver);
        loginPage.load();

        OnBoardingPage onBoardingPage =  loginPage
            .get()
            .enterEmail(ConfigReader.getEmail())
            .enterPassword(ConfigReader.getPassword())
            .clickLogin();

        new OnBoardingPageAssertion(onBoardingPage)
            .isOpened();

        DashboardPage dashboardPage = onBoardingPage.goToDashboard();

        ProjectPage projectPage = dashboardPage.goToProject();
        projectPage.get();

        return projectPage;
    }

    @Test
    @AllureId("UI005")
    @Story("Пользователь открывает страницу проекта через Dashboard")
    @Severity(SeverityLevel.CRITICAL)
    @Description("Проверка успешного открытия страницы Project после логина и перехода через Dashboard")
    void shouldOpenProjectPageFromDashboard() {

        ProjectPage projectPage = openProjectPage();

        new ProjectPageAssertion(projectPage)
            .isOpened();
    }

    static Stream<String> validProjectDataProvider() {
        return Stream.of("Project Alpha", "Project Beta", "Project Gamma");
    }

    @ParameterizedTest
    @MethodSource("validProjectDataProvider")
    @AllureId("UI006")
    @Story("Пользователь создаёт проект с валидными данными")
    @Severity(SeverityLevel.CRITICAL)
    @Description("Проверка успешного создания проекта с валидным именем и описанием")
    void shouldCreateProjectWithValidData(String projectData) {

        ProjectPage projectPage = openProjectPage();

        String uuid = UUID.randomUUID().toString();

        projectPage.enterProjectName(projectData + ":" + uuid)
            .enterDescription(projectData + ":" + uuid)
            .clickAddProject();

        new ProjectPageAssertion(projectPage)
            .successMessageIs("Successfully added the new project.");
    }

    @Test
    @AllureId("UI007")
    @Story("Пользователь пытается создать проект с некорректными данными")
    @Severity(SeverityLevel.CRITICAL)
    @Description("Проверка ошибки при попытке создать проект с пустым именем")
    void shouldFailToCreateProjectWithInvalidData() {

        ProjectPage projectPage = openProjectPage();

        projectPage.enterProjectName("")
            .enterDescription("test")
            .clickAddProject();

        new ProjectPageAssertion(projectPage)
            .nameErrorIs("The Name field is required.");
    }
}
