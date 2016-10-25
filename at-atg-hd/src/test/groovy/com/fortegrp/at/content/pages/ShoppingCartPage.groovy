package com.fortegrp.at.content.pages

import com.fortegrp.at.content.modules.shoppingcart.ShoppingCartItemModule

class ShoppingCartPage extends BaseGTPage {

    static url = "state/shoppingCart"
    static at = { cartTitle.displayed  && (shoppingCartTotals.displayed||emptyCartMessage.displayed)}
    static content = {
        cartTitle { $('h1', text: "Shopping Cart") }
        continueShopping { $('a', text: "Continue Shopping") }
        checkOutButton (required:false, wait:true) { $('a', text:contains("Check Out")) }

        shoppingCartItems { moduleList ShoppingCartItemModule, $("ol.item-cart>li"), miniCart: false }
        shoppingCartTotals { $('div.cart-totals')}

        estShippingZipInput { $('input[data-bind*="zipCode"]') }
        estShippingZipButton { estShippingZipInput.siblings()[0].find('i')[0]}

        moveToWishList { $('button', text: "Add All to Wish List") }
        emptyCartMessage (required: false) {$("div[data-bind*='emptyCartMessage'")}
    }

    def clearShoppingCart(){
        def itemsCount=shoppingCartItems.size()
        (1..itemsCount).each{
            this.find("ol.item-cart>li").module(ShoppingCartItemModule).remove()
        }
    }
}
