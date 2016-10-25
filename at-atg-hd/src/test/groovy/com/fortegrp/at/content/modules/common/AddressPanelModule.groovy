package com.fortegrp.at.content.modules.common

import com.fortegrp.at.entities.Address
import geb.Module
import groovy.util.logging.Slf4j

@Slf4j
class AddressPanelModule extends Module {

    static content = {
        address1Input { $("input[data-bind*='address1']") }
        address2Input { $("input[data-bind*='address2']") }
        cityInput { $("input[data-bind*='city']") }
        stateCombo { $($("select[data-bind*='state']"),$("select[data-bind*='selectedState']"),)}
        zipInput { $($("input[data-bind*='zipcode']"),$("input[data-bind*='postalCode']"))}
        countryCombo { $("select[data-bind*='country'")}
}

    def fillAddress(Address address, fillCountry=false){
        address1Input.value(address.address1)
        address2Input.value(address.address2)
        cityInput.value(address.city)
        stateCombo.value(address.state)
        zipInput.value(address.zip)
        if (fillCountry) {
            countryCombo.value(address.country)
        }
    }
}
