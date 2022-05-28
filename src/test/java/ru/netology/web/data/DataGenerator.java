package ru.netology.web.data;

import com.github.javafaker.Faker;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Value;

import java.util.Locale;

public class DataGenerator {

    private DataGenerator() {
    }

    @Value
    @Data
    @AllArgsConstructor
    public static class UserInfo {
        private String login;
        private String password;
        private String status;
    }

    private static Faker faker = new Faker(new Locale("en"));

    public static UserInfo getUserInfoActive() {
        return new UserInfo(faker.name().username(),
                faker.internet().password(),
                "active");
    }

    public static UserInfo getUserInfoBlocked() {
        return new UserInfo(faker.name().username(),
                faker.internet().password(),
                "blocked");
    }

    public static String getInvalidLogin() {
        return faker.name().username();
    }

    public static String getInvalidPassword() {
        return faker.internet().password();
    }
}
