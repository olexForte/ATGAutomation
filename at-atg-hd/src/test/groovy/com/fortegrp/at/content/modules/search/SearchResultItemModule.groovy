package com.fortegrp.at.content.modules.search


import geb.Module
import groovy.util.logging.Slf4j

@Slf4j
class SearchResultItemModule extends Module {

    static content = {
        thumbnail {$("img.item-thumb")}
        itemTitle { $("div.item-title>a[data-bind*='displayName']") }
        itemPrice { $("div.item-price") }
        compareCheckbox { $("div.item-compare input[type='checkbox']") }
        addToCart{ $('a', text:"Add to Cart")}
    }
}
