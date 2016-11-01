package com.fortegrp.at.content.pages

import com.fortegrp.at.content.modules.login.LoginPanelModule
import com.fortegrp.at.content.modules.login.RegisterPanelModule
import com.fortegrp.at.entities.User

class LoginPage extends BaseHDPage {

    static url = "authentication"
    static at = {
        loginPanel.displayed
    }
    static content = {
        loginPanel { module LoginPanelModule, $("div[data-source=\"v-login-fields\"]") }
        registerPanel { module RegisterPanelModule, $("div[data-source=\"v-account-fields\"]")}
    }

    def loginAs(User user) {
        loginAs(user.username, user.password)
    }

    def registerUser(User user) {
        registerPanel.fillUser(user)
        registerPanel.registerButton.click()
        sleep(500)
    }

    def loginAs(username, password, rememberUser = false) {
        loginPanel.loginInput.value(username)
        loginPanel.passwordInput.value(password)
        loginPanel.rememberMeCheckbox.value(rememberUser)
        loginPanel.loginButton.click()
        sleep(500)
    }

    def loginAs(String username, String password) {
        loginPanel.loginInput.value(username)
        loginPanel.passwordInput.value(password)
        loginPanel.loginButton.click()
        sleep(500)
    }
}
