package com.fortegrp.at.content.modules.common

import com.fortegrp.at.entities.Address
import com.fortegrp.at.entities.CreditCard
import geb.Module
import groovy.util.logging.Slf4j

@Slf4j
class CreditCardPanelModule extends Module {

    static content = {
        cardHolderInput { $("input[data-bind*='cc_fullName'") }
        numberInput { $("input[data-bind*='cc_number'") }
        expMonthInput { $("input[data-bind*='cc_exp_month'") }
        expYearInput { $("input[data-bind*='cc_exp_year'") }
        ccvInput { $("input[data-bind*='cc_code'") }
    }

    def fillCreditCard(CreditCard creditCard) {
        cardHolderInput.value(creditCard.name)
        numberInput.value(creditCard.number)
        expMonthInput.value(creditCard.expMon)
        expYearInput.value(creditCard.expYear)
        ccvInput.value(creditCard.ccv)
    }
}
