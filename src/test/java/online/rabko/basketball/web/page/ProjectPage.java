package online.rabko.basketball.web.page;

import io.qameta.allure.Step;
import java.time.Duration;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;


/**
 * Page Object для страницы создания проекта.
 * <p>
 * Содержит методы для заполнения формы создания проекта,
 * проверки загрузки страницы, отображения сообщений и ошибок.
 * </p>
 *
 * <p><b>Особенности:</b></p>
 * <ul>
 *     <li>Страница не может быть открыта напрямую</li>
 *     <li>Проверка загрузки выполняется по заголовку страницы</li>
 *     <li>Используются явные ожидания через {@link WebDriverWait}</li>
 *     <li>Поддерживается fluent API для chain вызовов</li>
 * </ul>
 */
public class ProjectPage extends BasePage<ProjectPage> {
    private final WebDriver driver;
    private final WebDriverWait wait;
    private static final Duration TIMEOUT = Duration.ofSeconds(15);

    private final By projectNameInput = By.cssSelector("[data-testid='addProjectNameInput']");
    private final By descriptionInput = By.cssSelector("div.fr-element.fr-view[contenteditable='true']");
    private final By addProjectButton = By.cssSelector("[data-testid='addEditProjectAddButton']");
    private final By addProjectHeaderLocator = By.cssSelector("[data-testid='testCaseContentHeaderTitle']");
    private final By successMessageLocator = By.cssSelector("[data-testid='messageSuccessDivBox']");
    private final By projectNameErrorLocator = By.id("projectNameError");

    public ProjectPage(WebDriver driver) {
        super(driver);
        this.driver = driver;
        this.wait = new WebDriverWait(driver, TIMEOUT);
    }

    @Override
    protected void load() {
        throw new UnsupportedOperationException("ProjectPage cannot be opened directly");
    }

    /**
     * Проверяет, что страница Project успешно загружена.
     *
     * @throws IllegalStateException если заголовок страницы не содержит "Add Project"
     */
    @Override
    @Step("Проверка загрузки страницы Project")
    protected void isLoaded() {
        try {
            String headerText = getHeaderText();
            if (!headerText.contains("Add Project")) {
                throw new IllegalStateException("Project page not loaded (header mismatch). Found: " + headerText);
            }
        } catch (Exception e) {
            throw new IllegalStateException("ProjectPage не загрузилась корректно", e);
        }
    }

    /**
     * Проверяет, открыта ли страница Project.
     *
     * @return true если заголовок содержит "Add Project", иначе false
     */
    @Step("Проверить, что страница Project открыта")
    public boolean isPageOpened() {
        try {
            return getHeaderText().contains("Add Project");
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Получает текст заголовка страницы Project.
     *
     * @return текст заголовка
     */
    @Step("Получить текст заголовка страницы Project")
    public String getHeaderText() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(addProjectHeaderLocator))
                .getText().trim();
    }

    /**
     * Вводит название проекта.
     *
     * @param name название проекта
     * @return текущий объект для chain вызова
     */
    @Step("Ввести название проекта: {name}")
    public ProjectPage enterProjectName(String name) {
        WebElement input = wait.until(ExpectedConditions.visibilityOfElementLocated(projectNameInput));
        input.clear();
        input.sendKeys(name);
        return this;
    }

    /**
     * Вводит описание проекта.
     *
     * @param text описание проекта
     * @return текущий объект для chain вызова
     */
    @Step("Ввести описание проекта")
    public ProjectPage enterDescription(String text) {
        WebElement editor = wait.until(ExpectedConditions.visibilityOfElementLocated(descriptionInput));
        editor.click();
        editor.sendKeys(Keys.CONTROL + "a");
        editor.sendKeys(Keys.DELETE);
        editor.sendKeys(text);
        return this;
    }

    /**
     * Нажимает кнопку добавления проекта.
     *
     * @return текущий WebDriver для последующих действий
     */
    @Step("Нажать кнопку 'Добавить проект'")
    public ProjectPage clickAddProject() {

        WebElement button = wait.until(ExpectedConditions.elementToBeClickable(addProjectButton));

        ((org.openqa.selenium.JavascriptExecutor) driver)
            .executeScript("arguments[0].scrollIntoView(true);", button);

        button.click();

        return this;
    }

    /**
     * Получает текст сообщения об успешном создании проекта.
     *
     * @return текст сообщения
     */
    @Step("Получить сообщение об успешном создании проекта")
    public String getSuccessMessage() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(successMessageLocator))
                .getText().trim();
    }

    /**
     * Получает текст ошибки названия проекта (если есть).
     *
     * @return текст ошибки или пустую строку, если ошибки нет
     */
    @Step("Получить текст ошибки названия проекта")
    public String getProjectNameErrorMessage() {
        try {
            return wait.until(ExpectedConditions.visibilityOfElementLocated(projectNameErrorLocator))
                    .getText().trim();
        } catch (Exception e) {
            return "";
        }
    }
}
