package com.fortegrp.at.specs.order

import com.fortegrp.at.common.BaseHDSpec
import com.fortegrp.at.content.pages.CheckoutPage
import com.fortegrp.at.content.pages.OrderConfirmationPage
import com.fortegrp.at.content.pages.ProductDetailsPage
import com.fortegrp.at.content.pages.ShoppingCartPage

import static com.fortegrp.at.data.TestData.*

class CheckoutSpec extends BaseHDSpec {
    def setupTest() {
        login()
         clearShoppingCart()
    }

    def "Checkout Single SKU"() {

        when: "Open pdp for #sku.id"
        to ProductDetailsPage, pid:defaultProduct.id

        and: "Add 1 #sku.displayName to cart"
        addToCart(defaultSKU,1)

        and: "Expand mini bag"
        expandMiniBag()

        then: "Verify just added Product is displayed in mini-bag"
        waitFor{shoppingCartPopup.shoppingCartItems[0].itemTitle.text() == sku.displayName}

        when: "Click View/Edit Cart button at mini cart"
        shoppingCartPopup.viewCartButton.click()

        then: "Shopping cart page has been opened"
        at ShoppingCartPage

        and: "Verify just added Product is displayed in shopping cart list"
        shoppingCartItems[0].asString() == sku.printAsCart()

        when: "Click Checkout button"
        checkOutButton.click()

        then: "Checkout page has been opened"
        at CheckoutPage

        and: "Verify that profile information is displayed correctly"
        waitFor{profileInformationPanel.profileEmail.text() == defaultUser.email}

        when: "Fill Shipping info with valid address"
        shippingPanel.firstName.value(defaultUser.firstName)
        shippingPanel.lastName.value(defaultUser.lastName)
        shippingPanel.shippingAddress.fillAddress(defaultAddress, false)
        shippingPanel.phone.value(defaultUser.phone)

        and: "Leave the default shipping method"
        def shipPrice = shippingPanel.getSelectedShippingMethodPrice().replace(".0", "").replace("\$", "")

        and: "Click Continue button"
        shippingPanel.continueButton.click()

        then: "Payment Info panel became active"
        waitFor {(!shippingPanel.continueButton.displayed)&&paymentPanel.continueButton.displayed}

        and: "Order Summary recalculated respectively"
        summaryPanel.subtotal.text() == String.format(Locale.ROOT, '$%.2f',Float.parseFloat(sku.listPrice))
        summaryPanel.shipping.text() == '$' + shipPrice
        summaryPanel.tax.text() == '$0.00'
        summaryPanel.total.text().contains(String.format(Locale.ROOT, '$%.2f',(Float.parseFloat(shipPrice) + Float.parseFloat(sku.listPrice))))


        when: "Fill payment info with valid credit card"
        paymentPanel.creditCard.fillCreditCard(defaultCard)

        and: "Click Continue button"
        paymentPanel.continueButton.click()

        then: "Finalize Order Panel became active"
        waitFor {(!paymentPanel.continueButton.displayed)&&finalizeOrderPanel.submitOrder.displayed}

        when: "Click Submit Order"
        finalizeOrderPanel.submitOrder.click()

        then: "User has been redirected to Order Confirmation page"
        waitFor orderConfirmationTimeoutMS,{at OrderConfirmationPage}

        and: "Order date is #orderDate"
        orderDateLabel.text().startsWith(orderDate)

        and: "Order number is displayed"
        orderNumberLabel.text()

        and: "Shipping information panel contains valid data"
        shippingPanel.shippingAddressPrint.text().contains(defaultAddress.print(defaultUser.firstName + " " + defaultUser.lastName))

        and: "Payment information panel contains valid data"
        paymentPanel.paymnentInfoPrint.text().contains(defaultAddress.print(defaultUser.firstName + " " + defaultUser.lastName))
        paymentPanel.paymnentInfoPrint.text().contains(defaultCard.print())

        and: "Order summary panel contains valid data"
        summaryPanel.subtotal.text() == String.format(Locale.ROOT, '$%.2f',Float.parseFloat(sku.listPrice))
        summaryPanel.shipping.text() == '$' + shipPrice
        summaryPanel.tax.text() == '$0.00'
        summaryPanel.total.text().contains(String.format(Locale.ROOT,'$%.2f',(Float.parseFloat(shipPrice) + Float.parseFloat(sku.listPrice))))

        where:
        orderDate = new Date().format('MM/dd/yyyy')
        sku = defaultProduct
        runtime = ""
    }
}