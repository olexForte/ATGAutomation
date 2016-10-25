package com.fortegrp.at.content.modules.login

import geb.Module
import groovy.util.logging.Slf4j

@Slf4j
class NavBarModule extends Module {

    static content = {
        searchInput {$('input#search-term')}
        searchButton {$("button[data-bind='click: doSearch']")}
    }
}
