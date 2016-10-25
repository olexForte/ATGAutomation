package com.fortegrp.at.entities

import groovy.transform.AutoClone

@groovy.transform.InheritConstructors
@AutoClone
class CreditCard {
    String id
    String type
    String number
    String ccv
    String name
    String expMon
    String expYear

    def print() {
        type.toLowerCase().capitalize() + "\n ending in " + number[-4..-1] + "\nExpires " + expMon + "/" + expYear[-2..-1]
    }
}
