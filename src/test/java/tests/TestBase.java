package tests;

import com.codeborne.selenide.logevents.SelenideLogger;
import config.ProjectConfiguration;
import config.web.WebConfig;
import config.web.WebConfigReader;
import helpers.Attach;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;

import java.util.Objects;

import static com.codeborne.selenide.WebDriverRunner.getWebDriver;

public class TestBase {

    private static final WebConfig webConfig = WebConfigReader.Instance.read();

    @BeforeAll
    static void setUp() {
        SelenideLogger.addListener("AllureSelenide", new AllureSelenide());

        ProjectConfiguration projectConfiguration = new ProjectConfiguration(webConfig);
        projectConfiguration.webConfig();
        projectConfiguration.apiConfig();
    }

    @AfterEach
    void addAttachments() {
        if (System.getProperty("browserName") != null) {
            Attach.screenshotAs("Screenshot");
            Attach.pageSource();
            if (Objects.equals(System.getProperty("browserName"), "chrome")) {
                Attach.browserConsoleLogs();
            }
            Attach.addVideo();
        }
    }

    @AfterEach
    void clearCookies() {
        if (System.getProperty("browserName") != null) {
            getWebDriver().manage().deleteAllCookies();
        }
    }
}
