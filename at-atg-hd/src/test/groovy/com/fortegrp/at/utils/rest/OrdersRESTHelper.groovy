package com.fortegrp.at.utils.rest

import com.fortegrp.at.common.utils.LogHelper
import com.fortegrp.at.common.utils.rest.RESTHelper
import groovy.util.logging.Slf4j
import groovyx.net.http.ContentType

@Slf4j
public class OrdersRESTHelper {

    public static getShoppingCartContent() {
        LogHelper.logInfo("REST: Getting shopping cart content")
        RESTHelper.doGet('rest/model/atg/commerce/ShoppingCartActor/summary')
    }

    public static deleteItemFromShoppingCart(items) {
        LogHelper.logInfo("REST: Removing items from shopping cart: "+items)
        RESTHelper.doPost([path              : 'rest/model/atg/commerce/order/purchase/CartModifierActor/removeItemFromOrder',
                           requestContentType: ContentType.JSON,
                           body              : ['removalCommerceIds': items],
                           headers           : ['Content-Type': 'application/json']])
    }

    public static emptyShoppingCart() {
        LogHelper.logInfo("REST: Empty current shopping cart")
        def scContent=getShoppingCartContent().data
        deleteItemFromShoppingCart(scContent.order.commerceItems*.id.toString())
    }


}

