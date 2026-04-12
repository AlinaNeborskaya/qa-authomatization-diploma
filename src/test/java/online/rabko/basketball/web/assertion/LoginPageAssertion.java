package online.rabko.basketball.web.assertion;

import online.rabko.basketball.web.page.LoginPage;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class LoginPageAssertion {

    private final LoginPage loginPage;

    public LoginPageAssertion(LoginPage loginPage) {
        this.loginPage = loginPage;
    }

    public LoginPageAssertion errorMessageIsDisplayed() {
        assertTrue(
                loginPage.isErrorMessageDisplayed(),
                "Ошибка авторизации должна отображаться"
        );
        return this;
    }

    public LoginPageAssertion errorMessageIsNotDisplayed() {
        assertFalse(
                loginPage.isErrorMessageDisplayed(),
                "Ошибка авторизации НЕ должна отображаться"
        );
        return this;
    }
}
