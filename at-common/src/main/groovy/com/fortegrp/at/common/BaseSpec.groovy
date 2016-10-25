package com.fortegrp.at.common

import com.fortegrp.at.env.CommonEnvironment
import groovy.io.FileType
import groovy.util.logging.Slf4j
import org.apache.commons.lang3.StringUtils

import java.awt.*
import java.awt.datatransfer.Clipboard
import java.awt.datatransfer.DataFlavor
import java.awt.datatransfer.StringSelection

import static com.fortegrp.at.common.utils.LogHelper.logInfo

@Slf4j
trait BaseSpec {
    def random = new Random()
    static int testCounter = 1

    def isFirstRun() {
        (CommonEnvironment.forkNumber == 1) && (testCounter == 1)
    }

    def setup() {
        logInfo("\n")
        logInfo("Iteration #$testCounter")
        setupProject()
        if (!isMethodMarkedWithAnnotation("SkipSetup")) {
            setupTest()
        }
    }

    def setupProject() {
    }

    def setupTest() {
    }

    def cleanup() {
        testCounter=testCounter+1
        cleanupTest()
        cleanupProject()
    }

    def cleanupProject() {
    }

    def cleanupTest() {
    }

    def getCurrentMethodAnnotations() {
        specificationContext.currentIteration.getDescription().getAnnotations()
    }

    def isMethodMarkedWithAnnotation(String annotation) {
        def result = false
        getCurrentMethodAnnotations().each {
            if (it.toString().contains(annotation)) {
                result = true
            }
        }
        result
    }

    def getRandomNum(min, max) {
        random.nextInt((max - min) + 1) + min
    }

    def getRandomItemFromArray(arrayOfItems) {
        def randomGenerator = new Random()
        def getRandomNumber = randomGenerator.nextInt(arrayOfItems.size())
        return arrayOfItems[getRandomNumber]
    }

    def backSpace(numTimes, ourTextObject) {
        for (int i in 0..numTimes) {
            ourTextObject << Keys.chord(Keys.BACK_SPACE)
        }
    }

/**
 * Get level of similarity of 2 strings (in per cents)
 *
 * @param ethalonStr
 * @param actualStr
 *
 * @return similarity level
 */
    def getStringSimilarity(ethalonStr, actualStr) {
        int similarityLevel = getStringSimilarityFromDistance(ethalonStr, actualStr);
        logInfo("String similarity level is " + similarityLevel)
        similarityLevel
    }

/**
 * Get level of similarity of 2 lists
 *
 * @param ethalonList
 * @param actualList
 *
 * @return similarity level
 */
    def getListSimilarity(ethalonList, actualList) {
        def intersectList = ethalonList.intersect(actualList.unique()).size() * 100 / ethalonList.size()
        logInfo("Highlight list similarity level is " + intersectList)
        intersectList.intValue()
    }

/**
 * Transform string distance to the similarity
 *
 * @param ethalonStr
 * @param actualStr
 * @return similarity level
 */
    int getStringSimilarityFromDistance(ethalonStr, actualStr) {
        if (ethalonStr.length() < actualStr.length()) {
            def swap = ethalonStr; ethalonStr = actualStr; actualStr = swap;
        }
        int bigLen = ethalonStr.length();
        if (bigLen == 0) {
            return 100.0;
        }
        ((bigLen - StringUtils.getLevenshteinDistance(ethalonStr, actualStr)) * 100 / bigLen).intValue();
    }

    def sendControlCKeysToElement(element) {
        if (System.getProperty("geb.env").equals("safari")) {
            element << Keys.chord(Keys.COMMAND + "c")
        } else {
            element << Keys.chord(Keys.CONTROL + "c")
        }
    }

    def sendControlVKeysToElement(element) {
        if (System.getProperty("geb.env").equals("safari")) {
            element << Keys.chord(Keys.COMMAND + "v")
        } else {
            element << Keys.chord(Keys.CONTROL + "v")
        }
    }

    def writeValueToClipboard(String value) {
        Toolkit toolkit = Toolkit.getDefaultToolkit()
        Clipboard clipboard = toolkit.getSystemClipboard()
        StringSelection strSel = new StringSelection(value)
        clipboard.setContents(strSel, null)
    }

    def getClipboardContents() {
        Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard()
        clipboard.getContents(null).getTransferData(DataFlavor.stringFlavor)
    }

    def getScreenWidth() {
        def toolkit = Toolkit.getDefaultToolkit()
        def dim = toolkit.getScreenSize()
        dim.width
    }

    def getFilesInDownloadDirectory() {
        def list = []
        CommonEnvironment.getDownloadDir().eachFileRecurse(FileType.FILES) { file ->
            list << file
        }
        list
    }
}
