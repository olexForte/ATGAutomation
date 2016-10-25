package com.fortegrp.at.specs.search

import com.fortegrp.at.common.BaseGTSpec
import com.fortegrp.at.content.pages.SearchResultsPage

import static com.fortegrp.at.data.TestData.defaultProduct
class SearchSpec extends BaseGTSpec {

    def "Search product by partial title"() {

        given: "Required user is logged in at Home page"
        login()

        when: "Search for #searchQuery"
        searchFor(searchQuery)

        then: "Verify that user has been redirected to Search Results page"
        at SearchResultsPage

        and: "Verify that Product #sku is present in search results"
        searchResults.itemTitle*.text().contains(sku.displayName)

        and: "Verify that search results title matches #searchResultsTitle"
        def matcher = searchTitle.text() =~ searchResultsTitle
        matcher.matches()

        and: "Verify that search results count corresponds to the search result title"
        matcher[0][1] == "" + searchResults.size()

        where:
        searchQuery = defaultProduct.displayName[0..-2]
        sku = defaultProduct
        searchResultsTitle = String.format("Displaying (\\d) results for %s.*", searchQuery.replace("(","\\(").replace(")","\\)"))

        runtime = ""
    }
}