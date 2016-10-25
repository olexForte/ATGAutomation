package com.fortegrp.at.entities

import groovy.transform.AutoClone

@groovy.transform.InheritConstructors
@AutoClone
class User {

    String id
    String username
    String email
    String password
    String firstName
    String lastName
    String company
    Address address
    String phone
}
