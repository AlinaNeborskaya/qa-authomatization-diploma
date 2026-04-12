package online.rabko.basketball.web.test;

import io.qameta.allure.*;
import online.rabko.basketball.web.assertion.LoginPageAssertion;
import online.rabko.basketball.web.assertion.OnBoardingPageAssertion;
import online.rabko.basketball.web.page.LoginPage;
import online.rabko.basketball.web.page.OnBoardingPage;
import online.rabko.basketball.util.ConfigReader;
import org.junit.jupiter.api.Test;

@Epic("Web test")
@Feature("Authorization")
public class LoginTest extends BaseTest {

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

        new OnBoardingPageAssertion(onBoardingPage)
            .isOpened();
    }

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

        new LoginPageAssertion(loginPage)
            .errorMessageIsDisplayed();
    }
}
