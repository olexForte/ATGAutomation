package com.fortegrp.at.content.pages

class CategoryLandingPage extends BaseGTPage {

    static url = "#/browse"
    static at = { categoryTitle.displayed }
    static content = {
        categoryTitle {$("h1[data-bid='text: categoryName']")}
    }
}
