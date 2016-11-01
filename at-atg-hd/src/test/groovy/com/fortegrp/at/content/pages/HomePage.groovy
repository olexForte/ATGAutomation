package com.fortegrp.at.content.pages

class HomePage extends BaseHDPage {

    static url = ""
    static at = { header.greetingsSpan.displayed}
    static content = {

        serchBox { $("input.wl-textbox.wl-textbox-search")}

    }

    def setValueToSearchBox ( String value ){
        serchBox.value(value)
    }

}
