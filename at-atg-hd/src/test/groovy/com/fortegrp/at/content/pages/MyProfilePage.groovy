package com.fortegrp.at.content.pages


class MyProfilePage extends BaseGTPage {

    static url = "state/myProfile"
    static at = { myProfileTitle.displayed}
    static content = {
        myProfileTitle { $('h1', text: "My Account") }
    }

}
