package com.fortegrp.at.content.pages


class MyProfilePage extends BaseHDPage {

    static url = "state/myProfile"
    static at = { myProfileTitle.displayed}
    static content = {
        myProfileTitle { $('h1', text: "My Account") }
    }

}
