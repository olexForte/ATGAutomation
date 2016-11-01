package com.fortegrp.at.data

import com.fortegrp.at.entities.Address
import com.fortegrp.at.entities.CreditCard
import com.fortegrp.at.entities.Product
import com.fortegrp.at.entities.SKU
import com.fortegrp.at.entities.User

class TestData {
    public static int orderConfirmationTimeoutMS=120

    public static final defaultUser = new User(
            username: "testername@server.com",
            email: "testername@server.com",
            password: "1qazxsW@",
            firstName: "TesterName",
            lastName: "TesterLastName",
    );

//    public static final defaultProduct = new Product(
//            id: "2232",
//            styleNum: "2055H",
//            displayName: "Fashion E Over The Calf Sock Dress Sock (3) Pair",
//            listPrice: "19"
//    )
//
//    public static final defaultSKU = new SKU(
//            productId: defaultProduct.id,
//            size: "6-12.5",
//            color: "Navy",
//    )
//
//    public static final defaultCard = new CreditCard(
//            type: "VISA",
//            name: defaultUser.firstName+" "+defaultUser.lastName,
//            number: "4111111111111111",
//            ccv: "999",
//            expMon: "09",
//            expYear: "2020")
//
//
//    public static final ppAccout = new User(
//            username: "test_b_1352213977_per@go-vsg.com ",
//            password: "abcd1234",
//    )
}