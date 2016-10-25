package com.fortegrp.at.content.modules.login


import geb.Module
import groovy.util.logging.Slf4j

@Slf4j
class LoginPanelModule extends Module {

    static content = {
        loginInput { $("input[data-bind='value: login_email'") }
        passwordInput { $("input[data-bind='value: login_password'") }
        forgotPasswordLink { $("a", text: "Forgot password?") }
        loginButton { $("button", text: "Sign In") }
        errorMessages (required:false, wait:true){ $("div[data-bind*='login_error'") }
    }
}
