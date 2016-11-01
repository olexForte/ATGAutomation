package com.fortegrp.at.content.pages

import com.fortegrp.at.content.modules.checkout.FinalizeStepPanelModule
import com.fortegrp.at.content.modules.checkout.PaymentStepPanelModule
import com.fortegrp.at.content.modules.checkout.ProfileInformationPanelModule
import com.fortegrp.at.content.modules.checkout.ShippingStepPanelModule
import com.fortegrp.at.content.modules.checkout.SummaryStepPanelModule

class CheckoutPage extends BaseHDPage {

    static url = "state/checkout"
    static at = {
        profileInformationPanel.displayed
    }
    static content = {
        checkoutTitle { $('h1', text: "Checkout") }
        profileInformationPanel { module ProfileInformationPanelModule, $('div#auth').findAll {it.displayed}  }
        summaryPanel { module SummaryStepPanelModule, $('div#review').findAll {it.displayed}  }
        shippingPanel { module ShippingStepPanelModule, $('div#shipping').findAll {it.displayed} }
        paymentPanel { module PaymentStepPanelModule, $('div#payment').findAll {it.displayed}  }
        finalizeOrderPanel { module FinalizeStepPanelModule, paymentPanel.siblings() }
    }
}
