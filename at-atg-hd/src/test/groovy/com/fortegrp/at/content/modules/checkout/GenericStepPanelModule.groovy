package com.fortegrp.at.content.modules.checkout

import com.fortegrp.at.entities.Address
import geb.Module
import groovy.util.logging.Slf4j

@Slf4j
class GenericStepPanelModule extends Module {
    static content = {
        panelHeader { $("div.panel-heading") }
        changeButton(required:false) { $("button", text:"Change") }
        continueButton(required:false) { $("button", text:"Continue") }
    }
}
