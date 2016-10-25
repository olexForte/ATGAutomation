package com.fortegrp.at.entities

import groovy.transform.AutoClone

@groovy.transform.InheritConstructors
@AutoClone
class Product {

    def id
    def styleNum
    def displayName
    def listPrice


    def printAsMiniCart(qty = 1) {
        displayName + id + " " + qty + " \$" + listPrice + " \$" + listPrice * qty
    }

    def printAsCart(qty = 1) {
        displayName + " " + qty + " " + String.format(Locale.ROOT, "%.2f", Float.parseFloat(listPrice))
    }


    def String toString() {
        id + " " + displayName + " " + listPrice
    }

}


