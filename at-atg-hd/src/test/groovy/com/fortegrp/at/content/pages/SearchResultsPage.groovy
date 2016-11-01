package com.fortegrp.at.content.pages

import com.fortegrp.at.content.modules.search.SearchResultItemModule

class SearchResultsPage extends BaseHDPage {

    static url = "search"
    static at = { searchTitle.displayed }
    static content = {
        searchTitle { $('div.well', text:contains("results for")) }
        breadcrumbs { $('.breadcrumb>li>a') }
        searchResults{moduleList SearchResultItemModule, $('div#grid[data-bind*=resultList]>div')}
    }
}
