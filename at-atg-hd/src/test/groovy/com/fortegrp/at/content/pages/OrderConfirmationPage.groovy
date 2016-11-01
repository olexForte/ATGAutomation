package com.fortegrp.at.content.pages

import com.fortegrp.at.content.modules.checkout.PaymentStepPanelModule
import com.fortegrp.at.content.modules.checkout.ShippingStepPanelModule
import com.fortegrp.at.content.modules.checkout.SummaryStepPanelModule

class OrderConfirmationPage extends BaseHDPage {

    static url = "state/order-confirmation"
    static at = { orderConfiramtionTitle.displayed }
    static content = {
        orderConfiramtionTitle(wait:true) { $('h1', text: "Order Confirmation") }
        orderDateLabel{$("dt", text:"Order Date:").next()}
        orderNumberLabel{$("dt", text:"Order Number:").next()}
        orderStatusLabel(required:false){$("dt", text:"Order Status:").next()}

        paymentPanel{module PaymentStepPanelModule, $('div#payment').findAll {it.displayed}}
        shippingPanel{module ShippingStepPanelModule, $('div#shipping').findAll {it.displayed}}
        summaryPanel{module SummaryStepPanelModule, $('div#review').findAll {it.displayed}}
    }
}
