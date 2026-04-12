package online.rabko.basketball.web.test;

import io.qameta.allure.*;
import online.rabko.basketball.web.assertion.DashboardPageAssertion;
import online.rabko.basketball.web.assertion.OnBoardingPageAssertion;
import online.rabko.basketball.web.page.DashboardPage;
import online.rabko.basketball.web.page.LoginPage;
import online.rabko.basketball.web.page.OnBoardingPage;
import online.rabko.basketball.util.ConfigReader;
import org.junit.jupiter.api.Test;

@Epic("Web test")
@Feature("Dashboard")
public class DashboardTest extends BaseTest {

    @Test
    @AllureId("UI004")
    @Story("Пользователь видит Dashboard после успешного логина и перехода с OnBoarding")
    @Severity(SeverityLevel.NORMAL)
    @Description("Проверка того, что пользователь может открыть Dashboard после авторизации")
    void shouldOpenDashboardAfterLoginAndOnBoarding() {
        LoginPage loginPage = new LoginPage(driver);
        loginPage.load();

        OnBoardingPage onBoardingPage = loginPage
            .get()
            .enterEmail(ConfigReader.getEmail())
            .enterPassword(ConfigReader.getPassword())
            .clickLogin();

        new OnBoardingPageAssertion(onBoardingPage)
            .isOpened();

        DashboardPage dashboardPage = onBoardingPage
            .goToDashboard();

        new DashboardPageAssertion(dashboardPage)
            .isOpened();
    }
}
