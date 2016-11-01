package com.fortegrp.at.common

import com.fortegrp.at.common.annotation.RetryOnFailure
import com.fortegrp.at.common.utils.LogHelper
import com.fortegrp.at.common.utils.rest.RESTHelper
import com.fortegrp.at.content.pages.BaseHDPage
import com.fortegrp.at.content.pages.CategoryLandingPage
import com.fortegrp.at.content.pages.CheckoutPage
import com.fortegrp.at.content.pages.HomePage
import com.fortegrp.at.content.pages.LoginPage
import com.fortegrp.at.content.pages.MyProfilePage
import com.fortegrp.at.content.pages.OrderConfirmationPage
import com.fortegrp.at.content.pages.ProductDetailsPage
import com.fortegrp.at.content.pages.SearchResultsPage
import com.fortegrp.at.content.pages.ShoppingCartPage
import com.fortegrp.at.data.TestData
import com.fortegrp.at.entities.User
import com.fortegrp.at.env.Environment
import com.fortegrp.at.utils.rest.CommonRESTHelper
import com.fortegrp.at.utils.rest.OrdersRESTHelper
import geb.Page
import geb.spock.GebReportingSpec
import groovy.util.logging.Slf4j

import static com.fortegrp.at.data.TestData.defaultUser

@Slf4j
@RetryOnFailure
//TODO: Get rid of hardcoded sleeps
class BaseHDSpec extends GebReportingSpec implements BaseSpec {

    static User currentUser

    def setupProject() {
        if (isFirstRun()) {
            startApplication()
            RESTHelper.getClient(Environment.getTestEnv().restUrl)
            CommonRESTHelper.ping()
            waitFor { driver.currentUrl.contains(HomePage.url) }
            at BaseHDPage
        }
    }

    def startApplication() {
        LogHelper.logInfo("Browser window opening...")
        browser.go(baseUrl)
    }


    def openLoginPage() {
        switch (driver.currentUrl) {
            case baseUrl + HomePage.url:
                at BaseHDPage
                if (!page.header.signInRegisterLink.displayed) {
                    logout()
                }
                to LoginPage
                break
            case baseUrl + LoginPage.url:
                at LoginPage
                break
            default:
                logout()
                to LoginPage
                break
        }

    }

    def checkServerSideErrors() {
        def serverSideError = null
        if (driver.pageSource.contains("HTTP Status 403 - Access is denied")) {
            serverSideError = 403
        } else if (driver.pageSource.contains("HTTP Status 500")) {
            serverSideError = 500
        }
        serverSideError
    }

    Class<Page> getCurrentPage() {
        switch (driver.getCurrentUrl()) {
            case ~/.*${LoginPage.url}.*/:
                return LoginPage;
            case ~/.*${CategoryLandingPage.url}.*/:
                return CategoryLandingPage;
            case ~/.*${CheckoutPage.url}.*/:
                return CheckoutPage;
            case ~/.*${HomePage.url}.*/:
                return HomePage;
            case ~/.*${OrderConfirmationPage.url}.*/:
                return OrderConfirmationPage;
            case ~/.*${ProductDetailsPage.url}.*/:
                return ProducsleetDetailsPage;
            case ~/.*${SearchResultsPage.url}.*/:
                return SearchResultsPage;
            case ~/.*${ShoppingCartPage.url}.*/:
                return ShoppingCartPage;
            case ~/.*${MyProfilePage.url}.*/:
                return MyProfilePage;
            case baseUrl:
                return BaseHDPage;
            default:
                throw new RuntimeException("Unknown page has been detected: " + driver.getCurrentUrl())
                break
        }
    }


    def login(user = TestData.defaultUser, reuse = true) {
        //Session re-usage
        def serverSideError = checkServerSideErrors()
        if (serverSideError) {
            go(LoginPage.url)
            logout()
            loginAs(user)
            at HomePage;
        } else {
            def curpage = getCurrentPage()
            if ((currentUser != user) || !reuse) {
                openLoginPage()
                loginAs(user)
                at HomePage
            } else {
                at curpage
                //to curpage
            }
        }
        currentUser = user
    }

    def logout() {
        at BaseHDPage
        if (header.signOutLink.displayed) {
            header.logout()
            sleep(500)
            to LoginPage
        }
        currentUser = null
    }


    def clearShoppingCart(method = 'ui') {
        if ((method == 'api') && CommonRESTHelper.IS_REST_AVAILABLE) {
            CommonRESTHelper.login(defaultUser.username, defaultUser.password)
            OrdersRESTHelper.emptyShoppingCart()
            CommonRESTHelper.logout()
        } else {
            login()
            to ShoppingCartPage
            page.clearShoppingCart()
            waitFor {emptyCartMessage.displayed}
        }
    }
}