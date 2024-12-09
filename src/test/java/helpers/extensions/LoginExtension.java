package helpers.extensions;

import api.AuthorizationWithApi;
import org.openqa.selenium.Cookie;
import models.AuthResponseModel;
import org.junit.jupiter.api.extension.BeforeEachCallback;
import org.junit.jupiter.api.extension.ExtensionContext;

import static com.codeborne.selenide.Selenide.open;
import static com.codeborne.selenide.WebDriverRunner.getWebDriver;

import static data.AuthData.USER_NAME;
import static data.AuthData.USER_PASSWORD;
import static data.AuthData.USER_ID;
import static data.AuthData.USER_TOKEN;
import static data.AuthData.EXPIRES;
import static data.AuthData.CREATE_DATE;
import static data.AuthData.IS_ACTIVE;

public class LoginExtension implements BeforeEachCallback {

    @Override
    public void beforeEach(ExtensionContext context) {

        USER_NAME = System.getProperty("profileUserName", "login");
        USER_PASSWORD = System.getProperty("profileUserPassword", "password");

        AuthResponseModel authResponse = AuthorizationWithApi.getAuthData(USER_NAME, USER_PASSWORD);

        open("/favicon.ico");

        getWebDriver().manage().addCookie(new Cookie("token", authResponse.getToken()));
        getWebDriver().manage().addCookie(new Cookie("expires", authResponse.getExpires()));
        getWebDriver().manage().addCookie(new Cookie("userID", authResponse.getUserId()));

        USER_ID = authResponse.getUserId();
        USER_TOKEN = authResponse.getToken();
        EXPIRES = authResponse.getExpires();
        CREATE_DATE = authResponse.getCreatedDate();
        IS_ACTIVE = authResponse.getIsActive();
    }
}