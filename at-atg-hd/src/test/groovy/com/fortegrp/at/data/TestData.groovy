package com.fortegrp.at.data

import com.fortegrp.at.entities.Address
import com.fortegrp.at.entities.CreditCard
import com.fortegrp.at.entities.Product
import com.fortegrp.at.entities.SKU
import com.fortegrp.at.entities.User

class TestData {
    public static int orderConfirmationTimeoutMS=120
    public static final defaultAddress = new Address(
            address1: "300 BOYLSTON AVE E",
            address2: "SUITE 5A-1204",
            zip: "98102",
            state: "Washington",
            city: "SEATTLE",
            country: "USA"
    )

    public static final alternativeCard = new CreditCard(
            number: "4916054438765291",
            ccv: "353",
            expMon: "03",
            expYear: "2020",
            name: "Yauheni Hraichonak",
            type: "VISA"
    )

    public static final alternativeUser = new User(
            username: "yauhenihraichonak@gmail.com",
            email: "yauhenihraichonak@gmail.com",
            password: "Welcome1",
            firstName: "Yauheni",
            lastName: "Hraichonak",
            address: defaultAddress,
            phone: "222-222-2222"
    );


    public static final defaultUser = new User(
            username: "vsgautomation@gmail.com",
            email: "vsgautomation@gmail.com",
            password: "ATG@utomat1on",
            firstName: "QA",
            lastName: "Automation",
            company: "Home",
            address: defaultAddress,
            phone: "234-567-8901"
    );

    public static final defaultProduct = new Product(
            id: "2232",
            styleNum: "2055H",
            displayName: "Fashion E Over The Calf Sock Dress Sock (3) Pair",
            listPrice: "19"
    )

    public static final defaultSKU = new SKU(
            productId: defaultProduct.id,
            size: "6-12.5",
            color: "Navy",
    )

    public static final defaultCard = new CreditCard(
            type: "VISA",
            name: defaultUser.firstName+" "+defaultUser.lastName,
            number: "4111111111111111",
            ccv: "999",
            expMon: "09",
            expYear: "2020")


    public static final ppAccout = new User(
            username: "test_b_1352213977_per@go-vsg.com ",
            password: "abcd1234",
    )
}