package com.fortegrp.at.content.modules.checkout

import com.fortegrp.at.content.modules.common.AddressPanelModule
import com.fortegrp.at.content.modules.common.CreditCardPanelModule
import groovy.util.logging.Slf4j

@Slf4j
class PaymentStepPanelModule extends GenericStepPanelModule {

    static content = {
        creditCard (required:false){ module CreditCardPanelModule }
        useShippingAddressAsBilling { $("input[data-bind='checked: checkout.sameAsShipping']") }
        shippingAddressPrint(required:false) {$('div.payment-address-old')}
        firstName (required:false){ $("input[data-bind='textInput: checkout.shippingAddress.firstName']") }
        lastName (required:false){ $("input[data-bind='textInput: checkout.shippingAddress.lastName']") }
        billingAddress(required:false){ module AddressPanelModule}
        phone { $("input[data-bind='textInput: checkout.shippingAddress.phoneNumber']") }

        couponCodeInput {$("input[data-bind='value: checkout.cart.promotion.couponName']")}
        couponCodeApply {$("button", text:"Apply")}

        paymnentInfoPrint (required:false) {$("div[data-bind*='orderPaymentInfo']")}
    }
}
