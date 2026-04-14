package online.rabko.basketball.web.page;

import io.qameta.allure.Allure;
import java.time.Duration;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

/**
 * Page Object для страницы авторизации.
 */
public class LoginPage extends BasePage<LoginPage> {

    private static final String URL =
        "https://cabxczc.testrail.io/index.php?/auth/login";

    private static final Duration TIMEOUT = Duration.ofSeconds(15);

    private final WebDriverWait wait;

    private final By emailInputLocator = By.id("name");
    private final By passwordInputLocator = By.id("password");
    private final By loginButtonLocator = By.id("button_primary");
    private final By loginErrorTextLocator =
        By.cssSelector("[data-testid='loginErrorText']");

    public LoginPage(WebDriver driver) {
        super(driver);
        this.wait = new WebDriverWait(driver, TIMEOUT);
    }

    @Override
    public void load() {
        Allure.step("Открыть страницу логина", () -> driver.get(URL));
    }

    @Override
    protected void isLoaded() {
        Allure.step("Проверка загрузки страницы логина", () -> {
            try {
                wait.until(ExpectedConditions.visibilityOfElementLocated(emailInputLocator));
                wait.until(ExpectedConditions.visibilityOfElementLocated(passwordInputLocator));

                WebElement loginButton =
                    wait.until(ExpectedConditions.visibilityOfElementLocated(loginButtonLocator));

                wait.until(ExpectedConditions.elementToBeClickable(loginButton));

            } catch (Exception e) {
                throw new IllegalStateException("LoginPage не загрузилась корректно", e);
            }
        });
    }

    public LoginPage enterEmail(String email) {
        return Allure.step("Ввести email: " + email, () -> {
            WebElement emailInput =
                wait.until(ExpectedConditions.visibilityOfElementLocated(emailInputLocator));
            emailInput.clear();
            emailInput.sendKeys(email);
            return this;
        });
    }

    public LoginPage enterPassword(String password) {
        return Allure.step("Ввести пароль", () -> {
            WebElement passwordInput =
                wait.until(ExpectedConditions.visibilityOfElementLocated(passwordInputLocator));
            passwordInput.clear();
            passwordInput.sendKeys(password);
            return this;
        });
    }

    public OnBoardingPage clickLogin() {
        return Allure.step("Нажать кнопку 'Войти'", () -> {
            WebElement loginButton =
                wait.until(ExpectedConditions.elementToBeClickable(loginButtonLocator));
            loginButton.click();
            return new OnBoardingPage(driver);
        });
    }

    public boolean isErrorMessageDisplayed() {
        return Allure.step("Проверить отображение ошибки авторизации", () -> {
            try {
                return wait.until(
                    ExpectedConditions.visibilityOfElementLocated(loginErrorTextLocator)
                ).isDisplayed();
            } catch (Exception e) {
                return false;
            }
        });
    }
}
