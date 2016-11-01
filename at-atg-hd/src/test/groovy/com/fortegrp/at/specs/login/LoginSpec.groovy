package com.fortegrp.at.specs.login

import com.fortegrp.at.common.BaseHDSpec
import com.fortegrp.at.common.utils.CSVHelper
import com.fortegrp.at.content.pages.HomePage
import com.fortegrp.at.content.pages.LoginPage

import com.fortegrp.at.data.TestData
import spock.lang.Unroll

class LoginSpec extends BaseHDSpec {

    def "Login_Valid_Credentials"() {

        when: "User at Login Page"
        openLoginPage()

        and: "Tries to log into as #testuser.username"
        loginAs(TestData.defaultUser)

        then: "Verify that Home Page has opened for the appropriate user"
        at HomePage
        header.greetingsSpan.text().contains(greetings)

        cleanup: "Logout"
        logout()

        where:
        username =  TestData.defaultUser.username
        password =  TestData.defaultUser.password
        greetings=  TestData.defaultUser.firstName

    }

    def "Login_Valid_Credentials_With_Remember_Me_option"() {

        when: "User at Login Page"
        openLoginPage()

        and: "Tries to log into as #testuser.username"
        loginAs(username, password, true)

        then: "Verify that Home Page has opened for the appropriate user"
        at HomePage
        header.greetingsSpan.text().contains(greetings)

        cleanup: "Logout"
        logout()

        where:
        username =  TestData.defaultUser.username
        password =  TestData.defaultUser.password
        greetings=  TestData.defaultUser.firstName
    }

    def "Login_Negative_Empty_Password"() {

        def msg = new CSVHelper( fileNameWithKeys )
                .searchKeyValue( keyFromFile )

        given: "User at Login Page"
        openLoginPage()

        when: "Tries to log into with empty password"
        loginAs(username, password)

        then: "Verify that user is still at Login Page"
        at LoginPage

        and: "Verify that error message appears"
        loginPanel.checkErrorMessage(msg)

        where:
        username = "user@user.com"
        password = ""
        keyFromFile = "LOGIN_EMAIL_PASSWORD_INCORRECT"
        fileNameWithKeys = "HD_UserMessages.csv"

    }

    @Unroll
    def "Login_Negative_Empty_Username"() {

        def msg = new CSVHelper( fileNameWithKeys )
                .searchKeyValue( keyFromFile )

        given: "User at Login Page"
        openLoginPage()

        when: "Tries to log into with empty username"
        loginAs(username, password)

        and: "Verify that user is still at Login Page"
        at LoginPage

        then: "Verify that error message #msg appears"
        loginPanel.checkErrorMessage(msg)

        where:
        username =  TestData.defaultUser.username
        password = "password"
        keyFromFile = "LOGIN_EMAIL_PASSWORD_INCORRECT"
        fileNameWithKeys = "HD_UserMessages.csv"

    }

    def "Login_Negative_Wrong_Credentials"() {

        def msg = new CSVHelper( fileNameWithKeys )
                .searchKeyValue( keyFromFile )

        given: "User at Login Page"
        openLoginPage()

        when: "Tries to log into with wrong credentials"
        loginAs(usr, pswd)

        then: "Verify that user is still at Login Page"
        at LoginPage

        then: "Verify that error message appears"

//        loginPanel.checkErrorMessage(msg)
        for (message in $("div.wl-message-error.wl-message p") )
            message.text().contains(msg)

        where:
        usr = "qwe@qwe.com"
        pswd= "1qazxsW@"
        keyFromFile = "LOGIN_EMAIL_PASSWORD_INCORRECT"
        fileNameWithKeys = "HD_UserMessages.csv"
    }
}