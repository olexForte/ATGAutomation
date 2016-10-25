package com.fortegrp.at.content.modules.checkout

import com.fortegrp.at.content.modules.common.AddressPanelModule
import com.fortegrp.at.content.modules.shoppingcart.ShoppingCartItemModule
import groovy.util.logging.Slf4j

@Slf4j
class SummaryStepPanelModule extends GenericStepPanelModule {

    static content = {
        shoppingCartItems { moduleList ShoppingCartItemModule, $("div.review-products>div.item"), miniCart: true }
        subtotal { $('div.row>div', text: "Subtotal:").siblings('div') }
        shipping { $('div.row>div', text: "Shipping:").siblings('div') }
        tax { $('div.row>div', text: "Tax:").siblings('div') }
        total { $('div.row>div', text: startsWith("Total:")).parent() }
    }
}
