package com.fortegrp.at.env

import com.fortegrp.at.env.CommonEnvironment

class Environment extends CommonEnvironment {


    static String baseUrl
    static String browserType = System.getProperty("browser")
    static {
        envType = System.getProperty("browser")
    }
    static remoteUrl = System.properties['remoteUrl'] ?: "http://xxx.xx.xx.xxx:xxxx/wd/hub"

    static getAppUrl() {
        getTestEnv().baseUrl
    }

    static getTestEnv() {
        getConfig().envs."${System.properties['testEnv']}"
    }

    static File getDownloadDir() {
        new File(getCWD() + "/reports/" + forkNumber + "/");
    }

}
