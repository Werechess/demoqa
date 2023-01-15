package data.pages;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.CollectionCondition.itemWithText;
import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.*;
import static data.PagesLinks.PROFILE;

public class ProfilePage {

    private final ElementsCollection submitButtons = $$("#submit");

    private final SelenideElement header = $(".main-header"),
            loginSuggestionLabel = $("#notLoggin-label");


    public void checkLoginSuggestionIsVisible() {
        loginSuggestionLabel.shouldHave(text("Currently you are not logged into the Book Store application"));
    }

    public void checkLogoutButtonIsVisible() {
        submitButtons.shouldHave(itemWithText("Log out"));
    }

    public void openPage() {
        open(PROFILE.getLink());
        header.shouldHave(text(PROFILE.getHeader()));
        executeJavaScript("$('footer').remove()");
        executeJavaScript("$('#fixedban').remove()");
    }
}
