package com.fortegrp.at.env

import org.apache.commons.io.FileUtils
import org.openqa.selenium.WebDriver
import org.openqa.selenium.chrome.ChromeDriver
import org.openqa.selenium.chrome.ChromeOptions
import org.openqa.selenium.firefox.FirefoxDriver
import org.openqa.selenium.firefox.FirefoxProfile
import org.openqa.selenium.ie.InternetExplorerDriver
import org.openqa.selenium.remote.CapabilityType
import org.openqa.selenium.remote.DesiredCapabilities
import org.openqa.selenium.remote.RemoteWebDriver
import org.openqa.selenium.safari.SafariOptions

import java.util.concurrent.TimeUnit

/**
 * Created by lfedorov on 11/21/2014.
 */
//TODO: Get rid of this class, move to Geb config
class Driver {

    static final SCRIPT_TIMEOUT = 5
    static driverInstance

    static synchronized getInstance() {
        if (driverInstance == null) {
            switch (Environment.browserType) {
                case "chrome":
                    String extension
                    extension = System.properties['os.name'].toLowerCase().contains('windows') ?".exe":""
                    extension = extension == '' ? System.properties['os.name'].toLowerCase().contains('mac') ?"_mac":"" : ""
                    System.setProperty("webdriver.chrome.driver", Environment.getCWD()+"/src/test/resources/chromedriver" + extension)
                    def downloadFilepath = Environment.getDownloadDir()
                    downloadFilepath.mkdirs();
                    FileUtils.cleanDirectory(downloadFilepath)
                    ChromeOptions options = new ChromeOptions();
                    options.setExperimentalOption("prefs", ["profile.default_content_settings.popups": 0,"download.default_directory": downloadFilepath.path]);
                    options.addArguments("--start-maximized")
                    options.addArguments("--test-type")
                    DesiredCapabilities cap = DesiredCapabilities.chrome();
                    cap.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);
                    cap.setCapability(ChromeOptions.CAPABILITY, options);
                    driverInstance=new ChromeDriver(cap);
                    driverInstance.manage().timeouts().setScriptTimeout(SCRIPT_TIMEOUT, TimeUnit.SECONDS)
                    driverInstance
                    break
                case "remote_chrome":
                    DesiredCapabilities caps = new DesiredCapabilities()
                    caps.setBrowserName("chrome")
                    WebDriver driverInstance = new RemoteWebDriver(Environment.remoteUrl.toURL(), caps)
                    driverInstance.manage().timeouts().setScriptTimeout(SCRIPT_TIMEOUT, TimeUnit.SECONDS)
                    driverInstance.manage().window().maximize()
                    driverInstance
                    break
                case "ff":
                    FirefoxProfile profile = new FirefoxProfile()
                    profile.setPreference("app.update.service.enabled", false)
                    profile.setPreference("app.update.auto", false)
                    profile.setPreference("app.update.enabled", false)
                    //automatically download pdf's
                    profile.setPreference("browser.download.folderList", 1)
                    profile.setPreference("browser.helperApps.neverAsk.saveToDisk", "application/pdf")
                    profile.setPreference("pdfjs.disabled", true)

                    profile.setEnableNativeEvents(false)
                    driverInstance = new FirefoxDriver(profile)
                    driverInstance.manage().timeouts().setScriptTimeout(SCRIPT_TIMEOUT, TimeUnit.SECONDS)
                    driverInstance.manage().window().maximize()
                    driverInstance
                    break
                case "ie":
                    def ieDriver = new File('src/test/resources/IEDriverServer.exe')
                    System.setProperty('webdriver.ie.driverInstance', ieDriver.absolutePath)
                    DesiredCapabilities caps = DesiredCapabilities.internetExplorer()
                    caps.setCapability(InternetExplorerDriver.IGNORE_ZOOM_SETTING, true)
                    caps.setCapability(InternetExplorerDriver.ELEMENT_SCROLL_BEHAVIOR, 1)
                    caps.setCapability(InternetExplorerDriver.REQUIRE_WINDOW_FOCUS, false)
                    caps.setCapability(InternetExplorerDriver.NATIVE_EVENTS, false)
                    driverInstance = new InternetExplorerDriver(caps)
                    driverInstance.manage().timeouts().setScriptTimeout(SCRIPT_TIMEOUT, TimeUnit.SECONDS)
                    driverInstance.manage().window().maximize()
                    driverInstance
                    break
                case "safari":
                    def remoteUrl = Environment.remoteUrl.toURL()
                    DesiredCapabilities caps = DesiredCapabilities.safari()
                    def opts = new SafariOptions()
                    opts.useCleanSession = true
                    caps.setCapability(SafariOptions.CAPABILITY, opts)
                    driverInstance = new RemoteWebDriver(remoteUrl, caps)
//                driverInstance = new SafariDriver(caps)
//                driverInstance.manage().timeouts().setScriptTimeout(SCRIPT_TIMEOUT, TimeUnit.SECONDS)
                    driverInstance.manage().window().maximize()
                    driverInstance
                    break
                case "bs":
                    String URL = "http://yauhenihraichona1:xqJzv6PSLxtzmSQpaHVz@hub.browserstack.com/wd/hub";
                    DesiredCapabilities caps = new DesiredCapabilities();
                    caps.setCapability("browser", "chrome");
                    //caps.setCapability("browser_version", "7.0");
                    caps.setCapability("os", "Windows");
                    //caps.setCapability("os_version", "7");
                    caps.setCapability("browserstack.debug", "true");
                    WebDriver driver = new RemoteWebDriver(new URL(URL), caps);
                    driver
                    break
            }
        } else {
            driverInstance
        }
    }

}
