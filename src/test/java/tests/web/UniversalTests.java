package tests.web;

import data.PagesLinks;
import io.qameta.allure.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import tests.TestBase;

import static com.codeborne.selenide.Selenide.open;
import static com.codeborne.selenide.Selenide.title;
import static helpers.Attach.getConsoleLogs;
import static io.qameta.allure.Allure.step;
import static io.qameta.allure.SeverityLevel.NORMAL;
import static io.qameta.allure.SeverityLevel.TRIVIAL;
import static org.assertj.core.api.Assertions.assertThat;

@Owner("werechess")
@Epic(value = "Demo QA Site")
@Feature(value = "All Pages")
@Story(value = "Page titles and errors in console log")
@Tag("ui")
class UniversalTests extends TestBase {

    @Severity(TRIVIAL)
    @DisplayName("Page should have title text")
    @EnumSource(PagesLinks.class)
    @ParameterizedTest(name = "[{index}] {0}")
    void pageShouldHaveTitle(PagesLinks link) {
        step("Open url", () ->
                open(link.getLink()));

        step("Page title should have text 'ToolsQA'", () -> {
            String expectedTitle = "ToolsQA";
            String actualTitle = title();

            assertThat(actualTitle).isEqualTo(expectedTitle);
        });
    }

    @Severity(NORMAL)
    @DisplayName("Page console log should not have errors")
    @EnumSource(PagesLinks.class)
    @ParameterizedTest(name = "[{index}] {0}")
    void consoleLogShouldNotHaveErrors(PagesLinks link) {
        step("Open url", () ->
                open(link.getLink()));

        step("Console logs should not contain text 'SEVERE'", () -> {
            String consoleLogs = getConsoleLogs();
            String errorText = "SEVERE";

            assertThat(consoleLogs).doesNotContain(errorText);
        });
    }
}
