package online.rabko.basketball.web.page;

import io.qameta.allure.Step;
import java.time.Duration;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

/**
 * Page Object для страницы авторизации.
 * <p>
 * Предоставляет методы для взаимодействия с формой входа:
 * ввод email и пароля, отправка формы и проверка отображения ошибки.
 * </p>
 *
 * <p><b>Особенности:</b></p>
 * <ul>
 *     <li>Страница открывается напрямую по URL</li>
 *     <li>Используется явное ожидание элементов</li>
 *     <li>Поддерживается fluent API (chain методов)</li>
 * </ul>
 */
public class LoginPage extends BasePage<LoginPage> {

    /**
     * URL страницы логина.
     */
    private static final String URL = "https://cabxczc.testrail.io/index.php?/auth/login";

    /**
     * Таймаут ожидания элементов.
     */
    private static final Duration TIMEOUT = Duration.ofSeconds(15);

    /**
     * Экземпляр WebDriverWait для синхронизации.
     */
    private final WebDriverWait wait;

    /**
     * Локатор поля email.
     */
    private final By emailInputLocator = By.id("name");

    /**
     * Локатор поля пароля.
     */
    private final By passwordInputLocator = By.id("password");

    /**
     * Локатор кнопки входа.
     */
    private final By loginButtonLocator = By.id("button_primary");

    /**
     * Локатор сообщения об ошибке логина.
     */
    private final By loginErrorTextLocator = By.cssSelector("[data-testid='loginErrorText']");

    /**
     * Конструктор страницы Login.
     *
     * @param driver экземпляр WebDriver
     */
    public LoginPage(WebDriver driver) {
        super(driver);
        this.wait = new WebDriverWait(driver, TIMEOUT);
    }

    /**
     * Открывает страницу логина.
     */
    @Override
    @Step("Открыть страницу логина")
    public void load() {
        driver.get(URL);
    }

    /**
     * Проверяет, что страница логина успешно загружена.
     * <p>
     * Ожидает отображения полей email, пароля и кликабельности кнопки входа.
     * </p>
     *
     * @throws IllegalStateException если страница не загрузилась
     */
    @Override
    @Step("Проверка, что страница логина загружена")
    protected void isLoaded() {
        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(emailInputLocator));
            wait.until(ExpectedConditions.visibilityOfElementLocated(passwordInputLocator));
            WebElement loginButton = wait.until(ExpectedConditions.visibilityOfElementLocated(loginButtonLocator));
            wait.until(ExpectedConditions.elementToBeClickable(loginButton));
        } catch (Exception e) {
            throw new IllegalStateException("LoginPage не загрузилась корректно", e);
        }
    }

    /**
     * Вводит email в поле авторизации.
     *
     * @param email email пользователя
     * @return текущая страница для chaining
     */
    @Step("Ввести email: {email}")
    public LoginPage enterEmail(String email) {
        WebElement emailInput = wait.until(ExpectedConditions.visibilityOfElementLocated(emailInputLocator));
        emailInput.clear();
        emailInput.sendKeys(email);
        return this;
    }

    /**
     * Вводит пароль в поле авторизации.
     *
     * @param password пароль пользователя
     * @return текущая страница для chaining
     */
    @Step("Ввести пароль")
    public LoginPage enterPassword(String password) {
        WebElement passwordInput = wait.until(ExpectedConditions.visibilityOfElementLocated(passwordInputLocator));
        passwordInput.clear();
        passwordInput.sendKeys(password);
        return this;
    }

    /**
     * Нажимает кнопку входа.
     *
     * @return объект страницы {@link OnBoardingPage}
     */
    @Step("Нажать кнопку 'Войти'")
    public OnBoardingPage clickLogin() {
        WebElement loginButton = wait.until(ExpectedConditions.elementToBeClickable(loginButtonLocator));
        loginButton.click();
        return new OnBoardingPage(driver);
    }

    /**
     * Проверяет, отображается ли сообщение об ошибке авторизации.
     *
     * @return true если ошибка отображается, иначе false
     */
    @Step("Проверить отображение ошибки авторизации")
    public boolean isErrorMessageDisplayed() {
        try {
            return wait.until(ExpectedConditions
                            .visibilityOfElementLocated(loginErrorTextLocator))
                    .isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }
}
