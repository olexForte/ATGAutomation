package com.fortegrp.at.specs.login

import com.fortegrp.at.common.BaseGTSpec
import com.fortegrp.at.content.pages.HomePage
import com.fortegrp.at.content.pages.LoginPage

import com.fortegrp.at.data.TestData
import spock.lang.Unroll

class LoginSpec extends BaseGTSpec {

    def "Login_Valid_Credentials"() {

        when: "User at Login Page"
        openLoginPage()

        and: "Tries to log into as #testuser.username"
        loginAs(testuser)

        then: "Verify that Home Page has opened for the appropriate user"
        at HomePage
        header.userMenuButton.text().contains(TestData.defaultUser.firstName)

        cleanup: "Logout"
        logout()

        where:
        testuser = TestData.defaultUser
        runtime = ""
    }

    def "Login_Negative_Empty_Password"() {

        given: "User at Login Page"
        openLoginPage()

        when: "Tries to log into with empty password"
        loginAs("user", "")

        then: "Verify that user is still at Login Page"
        at LoginPage

        and: "Verify that error message appears"
        loginPanel.errorMessages.text()=="We're sorry, we do not recognize the email you have entered. Please enter a valid email.\n" +
                "Please enter your password."
    }

    @Unroll
    def "Login_Negative_Empty_Username"() {

        given: "User at Login Page"
        openLoginPage()

        when: "Tries to log into with empty username"
        loginAs("", "password")

        and: "Verify that user is still at Login Page"
        at LoginPage

        then: "Verify that error message #msg appears"
        loginPanel.errorMessages.text()==msg

        where:
        msg="Please enter an email address."
    }

    def "Login_Negative_Wrong_Credentials"() {

        given: "User at Login Page"
        openLoginPage()

        when: "Tries to log into with wrong credentials"
        loginAs("blah", "blah")

        then: "Verify that user is still at Login Page"
        at LoginPage

        then: "Verify that error message appears"
        loginPanel.errorMessages.text()== "We're sorry, we do not recognize the email you have entered. Please enter a valid email.\n" +
                "We're sorry, the password did not match the email provided. Please try again."
    }
}