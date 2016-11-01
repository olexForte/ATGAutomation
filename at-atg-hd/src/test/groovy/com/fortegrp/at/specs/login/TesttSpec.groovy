package com.fortegrp.at.specs.login

import com.fortegrp.at.common.BaseHDSpec

/**
 * Created by odiachuk on 11/1/16.
 */
class TesttSpec extends BaseHDSpec{

    def Feature1(){

//        given:
//        openLoginPage()

        when: "hhjghj"
        at HomePage
        setValueToSearchBox(value)
        $("button.wl-button-primary").click()


        then:
        $('div.well', text:contains("results for")).isDisplayed()

        where:
        value = "BOLT"

    }

}
