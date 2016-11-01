package com.fortegrp.at.content.modules.login

import geb.Module
import groovy.util.logging.Slf4j
import org.openqa.selenium.By

@Slf4j
class HeaderModule extends Module {

    static content = {
        signInRegisterLink (required:false) { $("a[data-id='register']") }
        greetingsSpan { $("span.welcome-name") }
        userMenuButton (required:false) { $("a.top-account", text: contains("Hi,")) }
        userMenu (required:false) { userMenuButton.siblings('ul.dropdown-menu')}
        userMenuItem (required:false) {title-> userMenu.find('a', text:title)}
        minicartBadge (required:true, wait:true){$("li#minicart-wrapper']")}
        navBar(required:true,wait:true){module NavBarModule, $('nav#nav')}
        signOutLink (required:false, wait:true) { $(By.xpath("//a[text()='Sign Out']")) }
    }

    def logout(){
        signOutLink.click()
        waitFor {greetingsSpan}
    }
}
