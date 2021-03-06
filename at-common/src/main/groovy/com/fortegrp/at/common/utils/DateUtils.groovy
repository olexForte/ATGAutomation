package com.fortegrp.at.common.utils
import groovy.time.TimeCategory

import java.text.ParseException
import java.text.SimpleDateFormat

class DateUtils {

    static def incrementDate(sourceString, sourceFormat, Map<String, Integer> incrementValues) {
        incrementDate(Date.parse(sourceFormat, sourceString), incrementValues)
    }

    static def incrementDate(Date sourceDate, Map<String, Integer> incrementValues) {
        Date result
        result = sourceDate
        incrementValues.keySet().toArray().each {
            def value = incrementValues.get(it).toInteger()
            def measure = it
            use(TimeCategory) {
                result = result + value."${measure}"
            }
        }
        result
    }

    static def dateToEpochTime(Date sourceDate) {
        (int) (sourceDate.time / 1000)
    }

    static def epochToDate(Long epochTime) {
        new Date(epochTime * 1000)
    }

    static def tryDateStrToTimestampStr(sourceDateStr, format = 'MM/dd/yyyy') {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ");
        try {
            sdf.setLenient(false)
            Date.parse(format, sourceDateStr).format(format).equals(sourceDateStr)?sdf.format(Date.parse(format, sourceDateStr)):sourceDateStr
        } catch (ParseException ex) {
            sourceDateStr
        }
    }

    static def getDateOfPreviousWeekday(Date baseDate, weekDayCode) {
        def cal = Calendar.instance
        cal.setTime(baseDate)
        while (cal.get(Calendar.DAY_OF_WEEK) != weekDayCode) {
            cal.add(Calendar.DAY_OF_WEEK, -1)
        }
        cal.time
    }
}
