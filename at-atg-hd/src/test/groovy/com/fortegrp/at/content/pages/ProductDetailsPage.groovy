package com.fortegrp.at.content.pages

import com.fortegrp.at.entities.SKU

class ProductDetailsPage extends BaseHDPage {

    static url = "state/pdp"
    static at = {
        pdpIntro.displayed && reviewContainser.displayed && waitForJStoLoad()
    }
    static content = {
        breadcrumbs { $('.breadcrumb>li>a') }
        pdpIntro(wait: true) { $('div.pdp-intro') }
        reviewContainser(wait: true) { $('#BVRRSummaryContainer') }
        pdpSwatches(required: false) { $('ul.pdp-swatch>li>label') }
        pdpSwatchByTitle(required: false) { swatchTitle ->
            $('ul.pdp-swatch>li>label>.pdp-swatch-title', innerHTML: contains(swatchTitle)).parent()
        }
        pdpSizes(required: false) { $('ul.pdp-select>li>label') }
        pdpSizeByTitle(required: false) { sizeTitle ->
            $('ul.pdp-select>li>label>.pdp-select-option', innerHTML: contains(sizeTitle)).parent()
        }
        quantityInput(wait: true) { $('input.pdp-qty') }
        addToCart(required: false) { $('button', text: "Add to Cart") }

    }

    def addToCart(SKU sku, qty = 1) {
        addToCart(sku.color, sku.size, qty)
    }

    def addToCart(swatch = null, size = null, qty = 1) {
        if (pdpSwatches.size() > 0) {
            if (swatch == null) {
                pdpSwatches[0].click()
            } else {
                if (swatch instanceof Integer) {
                    pdpSwatches[swatch].click()
                } else {
                    pdpSwatchByTitle(swatch).click()
                }
            }
        }
        if (pdpSizes.size() > 0) {
            if (size == null) {
                pdpSizes[0].click()
            } else {
                if (size instanceof Integer) {
                    pdpSizes[size].click()
                } else {
                    pdpSizeByTitle(size).click()
                }

            }
        }
        quantityInput.value(qty)
        addToCart.click()
        waitFor { shoppingCartPopup.displayed }
    }
}
