package ru.netology;

import com.codeborne.selenide.Condition;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;

import java.time.Duration;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selectors.withText;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static ru.netology.DataTest.serviceDate;

public class OrderCardTest {

    @BeforeEach
    void shouldOpenWebApp() {
        open("http://localhost:9999");
    }

    @Test
    void shouldTestValidation() {
        $("[data-test-id=city] input").setValue(DataTest.serviceCity());
        $("[data-test-id=date] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.BACK_SPACE);
        $("[data-test-id=date] input").setValue(serviceDate(3));
        $("[data-test-id=name] input").setValue(DataTest.serviceFIO());
        $("[data-test-id=phone] input").setValue(DataTest.servicePhone());
        $("[data-test-id=agreement]").click();
        $(withText("Запланировать")).click();
        $("[data-test-id='success-notification']").shouldBe(visible, Duration.ofSeconds(15)).shouldHave(text("Успешно!"));
        assertEquals(serviceDate(3), $("[data-test-id=date] input").getValue());
        $("[data-test-id='success-notification']").shouldBe(visible, Duration.ofSeconds(15)).shouldHave(text("Встреча успешно запланирована на " + serviceDate(3)));
        $("[data-test-id=date] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.BACK_SPACE);
        $("[data-test-id=date] input").setValue(serviceDate(5));
        $(withText("Запланировать")).click();
        $(withText("Перепланировать")).click();
        $("[data-test-id='success-notification']").shouldBe(visible, Duration.ofSeconds(15)).shouldHave(text("Успешно!"));
        assertEquals(serviceDate(5), $("[data-test-id=date] input").getValue());
        $("[data-test-id='success-notification']").shouldBe(visible, Duration.ofSeconds(15)).shouldHave(text("Встреча успешно запланирована на " + serviceDate(5)));

    }
}

