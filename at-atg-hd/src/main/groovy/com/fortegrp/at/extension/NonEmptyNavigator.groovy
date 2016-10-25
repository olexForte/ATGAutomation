package com.fortegrp.at.extension

import com.fortegrp.at.common.utils.LogHelper
import geb.Browser
import geb.navigator.Navigator
import org.openqa.selenium.WebElement

class NonEmptyNavigator extends geb.navigator.NonEmptyNavigator {
    NonEmptyNavigator(Browser browser, Collection<? extends WebElement> contextElements) {
        super(browser, contextElements)
    }

    //HTML5 stuff: field validation now is browser-side
    def getValidationMessage() {
        def element = this.contextElements[0]
        return browser.js.exec(element, "return arguments[0].validationMessage;")
    }

    def isValid() {
        def element = this.contextElements[0]
        return browser.js.exec(element, "return arguments[0].validity.valid;")
    }

    @Override
    Navigator click() {
        LogHelper.logInfo("Click on element [" + getElementLocator() + "]")
        super.click()
    }

    @Override
    Navigator value(value) {
        LogHelper.logInfo("Set value '" + value + "' to element [" + getElementLocator() + "]")
        if (classes().contains("bootstrap-select")) {
            if (this.value() != value) {
                LogHelper.logInfo(String.format("Current value: %s; target %s", this.value(), value))
                if (!$("div.dropdown-menu").displayed) {
                    $("button.dropdown-toggle").click()
                }
                $("div.dropdown-menu ul li:not(.hide)").has("span", text: value).click()

            } else {
                if ($("div.dropdown-menu").displayed) {
                   this.click()
                }

            }
        } else {
            if (!this.value().equals(value.toString()) && (value != null)) {
                super.value(value)
            } else {
                this
            }
        }
    }

    @Override
    def value() {
        (classes().contains("bootstrap-select")) ? $("span.filter-option").text() : super.value()
    }

    @Override
    Navigator leftShift(value) {
        LogHelper.logInfo("Set value '" + value + "' to element [" + getElementLocator() + "]")
        super.leftShift(value)
    }

    def getElementLocator() {
        def locator = this.contextElements[0].foundBy.toString()
        locator.substring(locator.indexOf('css selector: ') + 14)
    }

    @Override
    String css(String propertyName) {
        def result = super.css(propertyName)
        if (propertyName in ["padding-left", "margin-top"]) {
            result = result.replace("px", "")
        }
        result
    }

    boolean isSelected() {
        firstElement().isSelected()
    }

    @Override
    boolean isDisabled() {
        def value = getAttribute("disabled")
        // Different drivers return different values here
        (value == "disabled" || value == "true" || hasClass('disabled'))
    }

    boolean isMaximized() {
        getWidth() / browser.driver.manage().window().getSize().getWidth() > 0.95
    }

    boolean isRequired() {
        getAttribute("required")
    }
}