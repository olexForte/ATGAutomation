package com.fortegrp.at.content.pages

import com.fortegrp.at.content.modules.login.LoginPanelModule
import com.fortegrp.at.content.modules.login.RegisterPanelModule
import com.fortegrp.at.entities.User

class LoginPage extends BaseGTPage {

    static url = "state/register"
    static at = {
        loginPanel.displayed
    }
    static content = {
        loginPanel { module LoginPanelModule, $('form[data-bind*=\'processLogin\']').parent() }
        registerPanel { module RegisterPanelModule, $('form[data-bind*=\'processRegister\']').parent()}
    }

    def loginAs(User user) {
        loginAs(user.username, user.password)
    }

    def registerUser(User user) {
        registerPanel.fillUser(user)
        registerPanel.registerButton.click()
        sleep(500)
    }


    def loginAs(String username, String password) {
        loginPanel.loginInput.value(username)
        loginPanel.passwordInput.value(password)
        loginPanel.loginButton.click()
        sleep(500)
    }
}
