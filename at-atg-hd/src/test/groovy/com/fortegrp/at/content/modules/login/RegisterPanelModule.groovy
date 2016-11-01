package com.fortegrp.at.content.modules.login

import com.fortegrp.at.content.modules.common.AddressPanelModule
import com.fortegrp.at.entities.User
import geb.Module
import groovy.util.logging.Slf4j

@Slf4j
class RegisterPanelModule extends Module {

    static content = {
        firstNameInput { $("input[data-bind*='firstName']") }
        lastNameInput { $("input[data-bind*='lastName']") }
//        companyInput { $("input[data-bind*='company']") }
        emailInput { $("input[data-bind*='email']") }
//        confirmEmailInput { $("input[data-bind*='confirmEmail']") }
        passwordInput { $("input[data-bind*='password']") }
        confirmPasswordInput { $("input[data-bind*='confirmPassword']") }
//        addressPanel{module AddressPanelModule}
//        phoneInput { $("input[data-bind*='phoneNumber']") }
        errorMessages (required: false, wait:true){$('div.alert-danger[data-bind*=\'errors\']').findAll {it.displayed}[0]}
        registerButton { $("button", text: "Create Account") }
    }

    def fillUser(User user){
        firstNameInput.value(user.firstName)
        lastNameInput.value(user.lastName)
//        companyInput.value(user.company)
        emailInput.value(user.email)
//        confirmEmailInput.value(user.email)
        passwordInput.value(user.password)
        confirmPasswordInput.value(user.password)
//        addressPanel.fillAddress(user.address)
//        phoneInput.value(user.phone)
    }
}
