package com.fortegrp.at.content.modules.common

import geb.Module
import groovy.util.logging.Slf4j

@Slf4j
class TableModule extends Module {
    static content = {
        headers { $("thead>tr>th") }
        rows { $("tbody>tr") }
        footer (required:false){ $("tfoot") }
    }

    def asMapList() {
        def result=[]
        def colNames=headers*.text()
        rows.each {
            result.add([colNames, it.find("td")*.text()].transpose().collectEntries())
        }
        result
    }

    def footerAsMap() {
        ([headers*.text(), footer.find("th")*.text()].transpose().collectEntries())
    }
}
