import com.fortegrp.at.env.Driver
import com.fortegrp.at.extension.GebReporter
import com.fortegrp.at.extension.NonEmptyNavigator
import geb.Browser
import geb.navigator.EmptyNavigator
import org.openqa.selenium.WebElement

envs {
    stage_env {
        baseUrl = "http://dev.healthydirections.com:7003/"
        restUrl = "http://dev.healthydirections.com:7003/"
    }
    dev_env {
        baseUrl = "http://dev.healthydirections.com:7003/"
        restUrl = "http://dev.healthydirections.com:7003/"
    }

}

waiting {
    timeout = 30
    retryInterval = 0.5
    slow { timeout = 10 }
}

// Settings
cacheDriver = true
cacheDriverPerThread = true
quitCachedDriverOnShutdown = true
autoClearCookies = false
atCheckWaiting = true
//unexpectedPages = [ServerUnavailablePage]

// get/set baseUrl from environments closure. Here testEnv is externally-passed parameter (for environment type)
baseUrl = this."envs"."${System.properties['testEnv']}".baseUrl
restUrl = this."envs"."${System.properties['testEnv']}".restUrl

//Environment.baseUrl = baseUrl
driver = { Driver.getInstance() }

innerNavigatorFactory = { Browser browser, List<WebElement> elements ->
    elements ? new NonEmptyNavigator(browser, elements) : new EmptyNavigator(browser)
}

if (!System.properties['geb.build.reportsDir'])
    reportsDir = "reports"

reporter = new GebReporter()