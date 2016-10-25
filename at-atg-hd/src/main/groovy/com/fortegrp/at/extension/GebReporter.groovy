package com.fortegrp.at.extension

import com.fortegrp.at.env.Environment
import geb.report.ScreenshotReporter

//Class is necessary for saving unique screenshoots from the tests that are executed multiple times in the same execution.
//For example against multiple browsers.
class GebReporter extends ScreenshotReporter {
    @Override
    protected File saveScreenshotPngBytes(File outputDir, String label, byte ... bytes) {
        return super.saveScreenshotPngBytes(outputDir, label + Environment.reportGlobalSuffix(), bytes)
    }
}