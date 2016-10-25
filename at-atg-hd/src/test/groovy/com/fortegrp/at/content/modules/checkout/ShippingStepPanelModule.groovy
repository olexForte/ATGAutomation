package com.fortegrp.at.content.modules.checkout

import com.fortegrp.at.content.modules.common.AddressPanelModule
import groovy.util.logging.Slf4j

@Slf4j
class ShippingStepPanelModule extends GenericStepPanelModule {

    static content = {
        firstName (wait:true){ $("input[data-bind*='shipping_firstname']") }
        lastName { $("input[data-bind*='shipping_lastname']") }
        companyName { $("input[data-bind*='shipping_companyname']") }
        shippingAddress { module AddressPanelModule }
        shippingMethods { $("input[type='radio'][name='shipmethod']") }
        phone { $("input[data-bind*='shipping_phoneNumber']") }
        secondaryShippingAddresses(required:false){$("li.radio[data-bind*='shipping_secondaryAddresses']")}
        shippingAddressPrint{$("div[data-bind*='orderShippingInfo']")}
    }

    def getSelectedShippingMethod(){
        shippingMethods.findAll{it.selected}.siblings('div').text()
    }

    def getSelectedShippingMethodPrice(){
        shippingMethods.findAll{it.selected}.siblings().find('div').last().text()
    }

}
