package online.rabko.basketball.web.assertion;

import online.rabko.basketball.web.page.OnBoardingPage;

import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Assertion layer для OnBoardingPage.
 */
public class OnBoardingPageAssertion {

    private final OnBoardingPage page;

    public OnBoardingPageAssertion(OnBoardingPage page) {
        this.page = page;
    }

    /**
     * Проверка, что OnBoarding страница открыта.
     */
    public OnBoardingPageAssertion isOpened() {
        assertTrue(
                page.isPageOpened(),
                "OnBoardingPage должна быть открыта после успешного логина"
        );
        return this;
    }
}
