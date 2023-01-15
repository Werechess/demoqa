package data.pages;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;

import java.util.List;

import static com.codeborne.selenide.CollectionCondition.exactTexts;
import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.*;
import static data.PagesLinks.BOOK_STORE;

public class BookStorePage {

    private final ElementsCollection booksCollection = $$(".action-buttons");

    private final SelenideElement searchInput = $("#searchBox"),
            header = $(".main-header"),
            noDataDiv = $(".rt-noData");


    public void search(String value) {
        searchInput.setValue(value);
    }

    public void checkFoundBook(String value) {
        booksCollection.shouldHave(exactTexts(value));
    }

    public void checkFoundBooks(List<String> values) {
        booksCollection.shouldHave(exactTexts(values));
    }

    public void checkNoDataMessage(String value) {
        noDataDiv.shouldHave(exactText(value));
    }

    public void openPage() {
        open(BOOK_STORE.getLink());
        header.shouldHave(text(BOOK_STORE.getHeader()));
        executeJavaScript("$('footer').remove()");
        executeJavaScript("$('#fixedban').remove()");
    }
}
