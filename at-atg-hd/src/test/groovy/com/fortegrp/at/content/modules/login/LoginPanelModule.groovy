package com.fortegrp.at.content.modules.login


import geb.Module
import groovy.util.logging.Slf4j
import org.openqa.selenium.By

@Slf4j
class LoginPanelModule extends Module {


    static content = {
        loginInput { $("input#vLogin-fields-email-1") }
        passwordInput { $("input#vLogin-fields-password-1") }
        forgotPasswordLink { $("a[data-id='forgot-password']") }
        rememberMeCheckbox { $(By.xpath("//strong[text()='Remember me']/../input")) }
        loginButton (required:true, wait:true){ $(By.xpath("//button[text()='Sign In']")) } //$("button[data-bind='click:dologin']") }
        errorMessages (required:false){ $("label.wl-error") }
        wrongCredentials (required:false, wait:true){ $("div.wl-message-error.wl-message p") }
    }

    def checkErrorMessage(String expected){
        sleep(1000)
        for ( message in errorMessages)
            if (message.text().equals(expected))
                return true

        for (message in wrongCredentials )
            if (message.text().contains(expected))
                return true

        return false
    }
}
