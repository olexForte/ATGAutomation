package com.fortegrp.at.content.modules.shoppingcart

import com.fortegrp.at.content.modules.common.ModalDialogModule
import com.fortegrp.at.content.pages.ShoppingCartPage
import geb.Module
import groovy.util.logging.Slf4j

@Slf4j
class ShoppingCartPopupModule extends ModalDialogModule {
    public static EMPTY_SHOPPING_CART_MESSAE = 'You currently have no items in your shopping cart.'
    static content = {
        shoppingCartItems { moduleList ShoppingCartItemModule, $("ul.minicart-list>li"), miniCart: true }
        viewCartButton(to: ShoppingCartPage) { $('button', text: 'View Cart') }
        total(wait: true) { $('p.minicart-total') }
    }

}
