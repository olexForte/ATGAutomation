package com.fortegrp.at.entities

@groovy.transform.InheritConstructors
class Address {

    String id
    String address1
    String address2
    String city
    String state
    String zip
    String country

    def print(flname) {
        flname + "\n" + address1 + "\n" + address2 + "\n" + city + ", " + state + " " + zip
    }

}
