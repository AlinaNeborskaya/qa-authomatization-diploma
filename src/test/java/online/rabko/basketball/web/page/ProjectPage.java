package online.rabko.basketball.web.page;

import io.qameta.allure.Step;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class ProjectPage extends BasePage<ProjectPage> {

    private final WebDriver driver;
    private final WebDriverWait wait;

    private static final Duration TIMEOUT = Duration.ofSeconds(15);

    private final By projectNameInput =
        By.cssSelector("[data-testid='addProjectNameInput']");

    private final By descriptionInput =
        By.cssSelector("div.fr-element.fr-view[contenteditable='true']");

    private final By addProjectButton =
        By.cssSelector("[data-testid='addEditProjectAddButton']");

    private final By addProjectHeaderLocator =
        By.cssSelector("[data-testid='testCaseContentHeaderTitle']");

    public ProjectPage(WebDriver driver) {
        super(driver);
        this.driver = driver;
        this.wait = new WebDriverWait(driver, TIMEOUT);
    }

    @Override
    protected void load() {
        throw new UnsupportedOperationException("ProjectPage cannot be opened directly");
    }

    @Override
    protected void isLoaded() {
        wait.until(ExpectedConditions.textToBePresentInElementLocated(
            addProjectHeaderLocator, "Add Project"
        ));
    }

    // =========================
    // PUBLIC ACTIONS
    // =========================

    @Step("Ввести название проекта: {name}")
    public ProjectPage enterProjectName(String name) {
        WebElement input = stableFindClickable(projectNameInput);
        input.clear();
        input.sendKeys(name);
        return this;
    }

    @Step("Ввести описание проекта")
    public ProjectPage enterDescription(String text) {
        WebElement editor = stableFindClickable(descriptionInput);

        editor.click();
        sleep(200);

        // защита от Froala/overlay состояния
        clearWithJs(editor);
        editor.sendKeys(text);

        return this;
    }

    @Step("Нажать 'Добавить проект'")
    public void clickAddProject() {
        WebElement button = stableFindClickable(addProjectButton);
        button.click();
    }

    // =========================
    // STABILITY CORE (ВАЖНО)
    // =========================

    private WebElement stableFindClickable(By locator) {

        for (int i = 0; i < 5; i++) {
            try {
                removeOverlays();

                WebDriverWait shortWait = new WebDriverWait(driver, Duration.ofSeconds(3));

                WebElement el = shortWait.until(
                    ExpectedConditions.elementToBeClickable(locator)
                );

                return el;

            } catch (Exception ignored) {
                sleep(300);
            }
        }

        throw new RuntimeException("Element not stable/clickable: " + locator);
    }

    private void removeOverlays() {
        try {
            ((JavascriptExecutor) driver).executeScript("""
                document.querySelector('#pendo-guide-container')?.remove();
                document.querySelector('.pendo-backdrop')?.remove();
                document.querySelector('[id*="pendo"]')?.remove();
            """);
        } catch (Exception ignored) {}
    }

    private void clearWithJs(WebElement el) {
        try {
            ((JavascriptExecutor) driver).executeScript(
                "arguments[0].innerText = '';", el
            );
        } catch (Exception ignored) {}
    }

    private void sleep(long ms) {
        try {
            Thread.sleep(ms);
        } catch (InterruptedException ignored) {}
    }
}
