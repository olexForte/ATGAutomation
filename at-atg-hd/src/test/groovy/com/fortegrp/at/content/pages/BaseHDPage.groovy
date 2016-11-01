package com.fortegrp.at.content.pages

import com.fortegrp.at.content.BasePage
import com.fortegrp.at.content.modules.login.HeaderModule
import com.fortegrp.at.content.modules.shoppingcart.ShoppingCartPopupModule
import org.openqa.selenium.Keys

class BaseHDPage extends BasePage {
    static at = { header.displayed && footer.displayed}
    static content = {
        header { module HeaderModule }
        footer {$('footer#footer') }
        shoppingCartPopup(required:false) { module ShoppingCartPopupModule,  $('div#minicart') }
    }


    def searchFor(query){
        header.navBar.searchInput.value(query)
        sleep(500)
        interact {
            sendKeys(Keys.ENTER)
        }
    }

    def expandMiniBag() {
        if (!shoppingCartPopup.displayed) {
            header.minicartBadge.click()
            waitFor { shoppingCartPopup.displayed && shoppingCartPopup.total.displayed}
        }
    }
}