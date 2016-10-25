package com.fortegrp.at.content.modules.login

import geb.Module
import groovy.util.logging.Slf4j

@Slf4j
class HeaderModule extends Module {

    static content = {
        signInRegisterLink (required:false) { $("a[data-id='register']") }
        userMenuButton (required:false) { $("a.top-account", text: contains("Hi,")) }
        userMenu (required:false) { userMenuButton.siblings('ul.dropdown-menu')}
        userMenuItem (required:false) {title-> userMenu.find('a', text:title)}
        minicartBadge (required:true, wait:true){$("li#minicart-wrapper']")}
        navBar(required:true,wait:true){module NavBarModule, $('nav#nav')}
    }

    def logout(){
        userMenuButton.parent().click()
        waitFor {userMenu.displayed}
        userMenuItem('Log Out').click()
        waitFor {!userMenu.displayed}
    }
}
