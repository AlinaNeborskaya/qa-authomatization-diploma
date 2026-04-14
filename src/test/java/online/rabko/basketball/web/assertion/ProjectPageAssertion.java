package online.rabko.basketball.web.assertion;

import online.rabko.basketball.web.page.ProjectPage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Assertion layer для ProjectPage.
 */
public class ProjectPageAssertion {

    private final ProjectPage page;

    public ProjectPageAssertion(ProjectPage page) {
        this.page = page;
    }

    public ProjectPageAssertion isOpened() {
        assertTrue(
                page.isPageOpened(),
                "ProjectPage должна быть открыта"
        );
        return this;
    }

    public ProjectPageAssertion successMessageIs(String expected) {
        assertEquals(
                expected,
                page.getSuccessMessage(),
                "Сообщение об успешном создании проекта не соответствует ожидаемому"
        );
        return this;
    }

    public ProjectPageAssertion nameErrorIs(String expected) {
        assertEquals(
                expected,
                page.getProjectNameErrorMessage(),
                "Сообщение об ошибке имени проекта не соответствует ожидаемому"
        );
        return this;
    }
}
