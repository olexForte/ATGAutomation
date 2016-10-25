package com.fortegrp.at.common.utils

import groovy.time.TimeCategory

import java.text.DecimalFormat

class DimensionUtils {

    static def FF_SEMICIRCLES_TO_DEGREES = 11930464.7111111
    static def METERS_IN_MILE = 1609.34
    static def MS_TO_KMH = 3.6
    static def MS_TO_MPH = MS_TO_KMH/METERS_IN_MILE*1000
    static def FOOT_IN_METER=3.28084
    static def inchHgInBar=29.53

    static def farengateToCelsiusStr(farengateTemp, outputFormat = "%.1f") {
        String.format(outputFormat,((farengateTemp - 32) * 5 / 9));
    }


    static def milliBarToInchHG(miliBar, outputFormat = "##.##"){
        new DecimalFormat(outputFormat).format(miliBar*inchHgInBar/1000)
    }


    static def celsiusToFarengateStr(celsiusTemp, outputFormat = "##.#"){
        new DecimalFormat(outputFormat).format(celsiusToFarengate(celsiusTemp))
    }

    static def metersToFeets(meters, outputFormat = "##.#"){
        new DecimalFormat(outputFormat).format(meters*FOOT_IN_METER)
    }

    static def celsiusToFarengate(celsiusTemp) {
        celsiusTemp*9/5 + 32
    }

    static def semicirclesToDegrees(semicircles) {
        semicircles/FF_SEMICIRCLES_TO_DEGREES
    }

    static def degreesToSemicircles(semicircles) {
        (semicircles*FF_SEMICIRCLES_TO_DEGREES).setScale(0, BigDecimal.ROUND_HALF_UP)
    }

    static def metersToMiles(meters,format=null) {
        (format==null)?meters/METERS_IN_MILE:String.format(format,meters/METERS_IN_MILE)
    }


    static def kmhToMph(kmh) {
        kmh*1000/METERS_IN_MILE
    }

    static def msToKmh(meterInSecond) {
        meterInSecond*MS_TO_KMH
    }
    static def msToMph(meterInSecond) {
        meterInSecond*MS_TO_MPH
    }
    static def kmhToPaceMinKm(speedKmH) {
        60/speedKmH
    }
    static def kmhToPaceMinMi(speedKmH) {
        0.06*METERS_IN_MILE/(speedKmH)
    }
}
