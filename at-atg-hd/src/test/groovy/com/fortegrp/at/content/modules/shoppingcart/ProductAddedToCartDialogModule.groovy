package com.fortegrp.at.content.modules.shoppingcart

import com.fortegrp.at.content.modules.common.ModalDialogModule
import groovy.util.logging.Slf4j

@Slf4j
class ProductAddedToCartDialogModule extends ModalDialogModule {

    static content = {
        shoppingCartItem { module ShoppingCartItemModule, $("div.media"), miniCart: true }
        viewCart { $('a', text: "View Cart") }
    }

}
