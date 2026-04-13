package online.rabko.basketball.web.page;

import io.qameta.allure.Allure;
import java.time.Duration;
import org.openqa.selenium.*;

import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

/**
 * Page Object для страницы создания проекта.
 */
public class ProjectPage extends BasePage<ProjectPage> {

    private final WebDriver driver;
    private final WebDriverWait wait;

    private static final Duration TIMEOUT = Duration.ofSeconds(30);

    private final By projectNameInput =
        By.cssSelector("[data-testid='addProjectNameInput']");
    private final By descriptionInput =
        By.cssSelector("div.fr-element.fr-view[contenteditable='true']");
    private final By addProjectButton =
        By.cssSelector("[data-testid='addEditProjectAddButton']");
    private final By addProjectHeaderLocator =
        By.cssSelector("[data-testid='testCaseContentHeaderTitle']");
    private final By successMessageLocator =
        By.cssSelector("[data-testid='messageSuccessDivBox']");
    private final By projectNameErrorLocator =
        By.id("projectNameError");

    public ProjectPage(WebDriver driver) {
        super(driver);
        this.driver = driver;
        this.wait = new WebDriverWait(driver, TIMEOUT);
    }

    @Override
    protected void load() {
        throw new UnsupportedOperationException(
            "ProjectPage cannot be opened directly"
        );
    }

    @Override
    protected void isLoaded() {
        Allure.step("Проверка загрузки страницы Project", () -> {
            try {
                String headerText = getHeaderText();

                if (!headerText.contains("Add Project")) {
                    throw new IllegalStateException(
                        "Project page not loaded (header mismatch). Found: " + headerText
                    );
                }

            } catch (Exception e) {
                throw new IllegalStateException(
                    "ProjectPage не загрузилась корректно", e
                );
            }
        });
    }

    public boolean isPageOpened() {
        return Allure.step("Проверить, что страница Project открыта", () -> {
            try {
                return getHeaderText().contains("Add Project");
            } catch (Exception e) {
                return false;
            }
        });
    }

    public String getHeaderText() {
        return Allure.step("Получить текст заголовка страницы Project", () ->
            wait.until(ExpectedConditions
                    .visibilityOfElementLocated(addProjectHeaderLocator))
                .getText()
                .trim()
        );
    }

    public ProjectPage enterProjectName(String name) {
        return Allure.step("Ввести название проекта: " + name, () -> {
            prepareUI();

            WebElement input =
                wait.until(ExpectedConditions.visibilityOfElementLocated(projectNameInput));

            input.clear();
            input.sendKeys(name);

            return this;
        });
    }

    public ProjectPage enterDescription(String text) {
        return Allure.step("Ввести описание проекта", () -> {
            prepareUI();

            WebElement editor =
                wait.until(ExpectedConditions.visibilityOfElementLocated(descriptionInput));

            editor.click();
            editor.sendKeys(Keys.CONTROL + "a");
            editor.sendKeys(Keys.DELETE);
            editor.sendKeys(text);

            return this;
        });
    }

    public WebDriver clickAddProject() {
        return Allure.step("Нажать кнопку 'Добавить проект'", () -> {
            prepareUI();

            wait.until(d -> d.findElements(By.id("pendo-guide-container")).isEmpty());

            WebElement button =
                wait.until(ExpectedConditions.elementToBeClickable(addProjectButton));

            button.click();

            return driver;
        });
    }

    public String getSuccessMessage() {
        return Allure.step("Получить сообщение об успешном создании проекта", () ->
            wait.until(ExpectedConditions
                    .visibilityOfElementLocated(successMessageLocator))
                .getText()
                .trim()
        );
    }

    public String getProjectNameErrorMessage() {
        return Allure.step("Получить текст ошибки названия проекта", () -> {
            try {
                return wait.until(ExpectedConditions
                        .visibilityOfElementLocated(projectNameErrorLocator))
                    .getText()
                    .trim();
            } catch (Exception e) {
                return "";
            }
        });
    }

    private void prepareUI() {
        ((JavascriptExecutor) driver).executeScript("""
            const killPendo = () => {
                document.querySelectorAll('[id*="pendo"]').forEach(e => e.remove());
                document.querySelectorAll('[class*="pendo"]').forEach(e => e.remove());
            };

            killPendo();

            new MutationObserver(killPendo)
                .observe(document.body, { childList: true, subtree: true });
        """);
    }
}
