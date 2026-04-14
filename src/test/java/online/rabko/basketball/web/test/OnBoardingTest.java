package online.rabko.basketball.web.test;

import io.qameta.allure.*;
import online.rabko.basketball.web.assertion.OnBoardingPageAssertion;
import online.rabko.basketball.web.page.LoginPage;
import online.rabko.basketball.web.page.OnBoardingPage;
import online.rabko.basketball.util.ConfigReader;
import org.junit.jupiter.api.Test;

@Epic("Web test")
@Feature("Onboarding")
public class OnBoardingTest extends BaseTest {

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

        new OnBoardingPageAssertion(onBoardingPage)
            .isOpened();
    }
}
