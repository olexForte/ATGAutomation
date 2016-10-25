package com.fortegrp.at.content

import com.fortegrp.at.common.utils.LogHelper
import geb.Page

class BasePage extends Page {

    @Override
    void to(Map params, Object... args) {
        LogHelper.logInfo("Navigate to page " + getClass().simpleName)
        super.to(params, args)
    }

    @Override
    void onLoad(Page previousPage) {
        LogHelper.logInfo("Loading page " + browser.getCurrentUrl())
        super.onLoad(previousPage)
    }

    @Override
    boolean verifyAt() {
        LogHelper.logInfo("Verify at page " + getClass().simpleName)
        return super.verifyAt()
    }

    def getCurrentDateFormatted() {
        def date = new Date()
        date.format('MMMM d, yyyy')
    }

    def back() {
        driver.navigate().back()
    }

    def isVerticallyScrollable() {
        js.exec("return document.documentElement.scrollHeight>document.documentElement.clientHeight;")
    }

    protected void switchToLastWindow(timeout = 5000) {
        Thread.sleep(timeout)
        driver.switchTo().window(driver.getWindowHandles()[driver.getWindowHandles().size() - 1])
    }


    def boolean waitForJStoLoad() {
        return waitFor {
                    (browser.js.exec("return document.readyState;").toString() == 'complete')
        }
    }
}
