package ru.netology.web.test;

import com.codeborne.selenide.Configuration;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.netology.web.data.DataGenerator;
import ru.netology.web.data.PreSet;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;

class AuthTest {
    DataGenerator.UserInfo gen1 = DataGenerator.getUserInfoActive();
    DataGenerator.UserInfo gen2 = DataGenerator.getUserInfoBlocked();

    @BeforeEach
    public void openPage() {
        open("http://localhost:9999");
    }

    public void registration(String login, String password) {
        $("[name=login]").setValue(login);
        $("[name=password]").setValue(password);
        $(".button").click();
    }

    @Test
    public void shouldLoginIfUserExists() {
        Configuration.holdBrowserOpen = true;

        PreSet.setUp(gen1);
        registration(gen1.getLogin(), gen1.getPassword());
        $(byText("Личный кабинет")).shouldBe(visible);
    }

    @Test
    public void shouldNotLoginIfUserIsLockedOut() {
        Configuration.holdBrowserOpen = true;

        PreSet.setUp(gen2);
        registration(gen2.getLogin(), gen2.getPassword());
        $(".notification__title").shouldHave(text("Ошибка")).shouldBe(visible);
        $(".notification__content").shouldHave(text("Ошибка! Пользователь заблокирован")).shouldBe(visible);
    }

    @Test
    public void shouldNotLoginIfWrongUsername() {
        Configuration.holdBrowserOpen = true;

        PreSet.setUp(gen2);
        registration(DataGenerator.getInvalidLogin(), gen2.getPassword());
        $(".notification__title").shouldHave(text("Ошибка")).shouldBe(visible);
        $(".notification__content").shouldHave(text("Ошибка! Неверно указан логин или пароль")).shouldBe(visible);
    }

    @Test
    public void shouldNotLoginIfWrongPassword() {
        Configuration.holdBrowserOpen = true;

        PreSet.setUp(gen2);
        registration(gen2.getLogin(), DataGenerator.getInvalidPassword());
        $(".notification__title").shouldHave(text("Ошибка")).shouldBe(visible);
        $(".notification__content").shouldHave(text("Ошибка! Неверно указан логин или пароль")).shouldBe(visible);
    }

    @Test
    public void shouldNotLoginIfUserNotExist() {
        Configuration.holdBrowserOpen = true;

        registration(gen2.getLogin(), gen2.getPassword());
        $(".notification__title").shouldHave(text("Ошибка")).shouldBe(visible);
        $(".notification__content").shouldHave(text("Ошибка! Неверно указан логин или пароль")).shouldBe(visible);
    }
}
