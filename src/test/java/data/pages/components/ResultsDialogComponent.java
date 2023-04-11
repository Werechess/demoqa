package data.pages.components;

import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.$;

public class ResultsDialogComponent {

    private static final String DIALOG_HEADER_TEXT = "Thanks for submitting the form";

    private final SelenideElement dialogWindow = $(".modal-dialog"),
            dialogHeader = $("#example-modal-sizes-title-lg"),
            tableElement = $(".table-responsive");


    public void checkResult(String key, String value) {
        tableElement.$(byText(key)).parent().shouldHave(text(value));
    }

    public void checkResultIsEmpty(String key) {
        tableElement.$(byText(key)).parent().shouldHave(exactText(key));
    }

    public void checkVisible() {
        dialogWindow.shouldBe(visible);
        dialogHeader.shouldHave(text(DIALOG_HEADER_TEXT));
    }
}
