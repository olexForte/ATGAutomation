package com.fortegrp.at.content.modules.checkout

import com.fortegrp.at.content.modules.shoppingcart.ShoppingCartItemModule
import groovy.util.logging.Slf4j

@Slf4j
class FinalizeStepPanelModule extends GenericStepPanelModule {

    static content = {
        submitOrder { $('button', text: 'Submit Order') }
    }
}
