package online.rabko.basketball.web.assertion;

import online.rabko.basketball.web.page.DashboardPage;

import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Assertion layer для DashboardPage.
 */
public class DashboardPageAssertion {

    private final DashboardPage page;

    public DashboardPageAssertion(DashboardPage page) {
        this.page = page;
    }

    /**
     * Проверка, что Dashboard открыт.
     */
    public DashboardPageAssertion isOpened() {
        assertTrue(
                page.isPageOpened(),
                "DashboardPage должна быть открыта после перехода с OnBoarding"
        );
        return this;
    }
}
