package com.fortegrp.at.content.modules.shoppingcart

import com.fortegrp.at.content.modules.common.ModalDialogModule
import geb.Module
import groovy.util.logging.Slf4j

@Slf4j
class ShoppingCartItemModule extends Module {
    boolean miniCart=true;
    static content = {
        thumbnail { $("img.item-thumb") }
        itemTitle { $("div.item-title>a") }
        qtyLabel(required:false) { $("div.item-qty") }
        qtyInput(required:false) { $("input.pdp-qty") }
        itemPack(required:false) {$(".item-pack")}
        itemColor(required:false) {$(".item-color")}
        itemSize(required:false) {$(".item-size")}
        totalPrice { $('*[data-bind*="totalPrice"]')}
        price { $($('*[data-bind*="price"]'),$('*[data-bind*="listPrice"]'))}
        salePrice { $('*[data-bind*="salePrice"]')}
        updateAction(required:false) {$('button',text:contains('Update'))}
        wishListAction(required:false) {$('button',text:contains('Move to Wishlist'))}
        deleteAction(required:false) {$('button',text:contains('Remove'))}
    }

    def asString(){
        miniCart?
                itemTitle.parent().text().replace('\n','')+" "+qtyLabel.text()+" "+price.text():
                itemTitle.parent().text().replace('\n','')+" "+qtyInput.value()+" "+price.text()
    }

    def remove(){
        deleteAction.click()
    }
}
