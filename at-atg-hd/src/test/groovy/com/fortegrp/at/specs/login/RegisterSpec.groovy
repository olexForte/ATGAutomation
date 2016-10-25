package com.fortegrp.at.specs.login

import com.fortegrp.at.common.BaseGTSpec
import com.fortegrp.at.content.pages.HomePage
import com.fortegrp.at.content.pages.LoginPage

import com.fortegrp.at.data.TestData
import com.fortegrp.at.entities.User
import spock.lang.Unroll

class RegisterSpec extends BaseGTSpec {

    @Unroll
    def "Register_User_With_Valid_Attributes"() {

        User newUser = TestData.defaultUser.clone()
        newUser.email =   newUser.username = email
        newUser.firstName = firstName
        newUser.lastName = lastName

        given: "User at Login Page"
        openLoginPage()

        when: "Tries to register user with valid parameters (fname #firstName; lname #lastName; email #email; )"
        registerUser(newUser)

        then: "Verify that user has been created and logged in"
        at HomePage
        header.userMenuButton.text().contains(newUser.firstName)

        when: "Logout"
        logout()

        and: "Re-login using just created user credentials"
        loginAs(newUser)

        then: "Verify that user has been logged in and redirected to the Home Page"
        at HomePage
        header.userMenuButton.text().contains(newUser.firstName)

        where:
        firstName="Automation"
        lastName="Generated"
        email=""+new Date().getTime()+"@mail.com"
        runtime=""

    }

    def "Register_User_With_Already_Registered_Email"() {

        given: "User at Login Page"
        openLoginPage()

        when: "Tries to register user with already registered email"
        registerUser(TestData.defaultUser)

        then: "Verify that user is still at Login Page"
        at LoginPage

        and: "Verify that error message #msg appears"
        registerPanel.errorMessages.text() == "An account has already been registered with that email. Please sign in, get your password, or use a different email address to continue."
    }

}