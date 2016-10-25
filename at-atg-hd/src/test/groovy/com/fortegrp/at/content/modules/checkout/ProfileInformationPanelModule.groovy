package com.fortegrp.at.content.modules.checkout

import com.fortegrp.at.content.modules.common.AddressPanelModule
import groovy.util.logging.Slf4j

@Slf4j
class ProfileInformationPanelModule extends GenericStepPanelModule {

    static content = {
        profileEmail (required:false){ $("span[data-bind*='login_email']") }
    }

}
