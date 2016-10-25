package com.fortegrp.at.env

class CommonEnvironment {
    static conf
    static String envType
    protected static int retryTimes = 0
    static String testCategory = System.getProperty("testCategory") ?: "all"

    static getForkNumber() {
        if (System.properties['fork.number'] != null) {
            System.properties['fork.number'].toInteger()
        } else {
            System.properties['org.gradle.test.worker'] ? System.properties['org.gradle.test.worker'].toInteger() - 1 : 1
        }
    }


    static getTestEnv() {
        getConfig().environments."${System.properties['testEnv']}"
    }

    static ignoreKnownFailures() {
        if ((System.properties['ignoreFailures'] != null) && (System.properties['ignoreFailures'] != "")) {
            Boolean.parseBoolean(System.properties['ignoreFailures'])
        } else {
            true
        }
    }

    static retries() {
        def val = System.properties['retry.times']
        if ((val != null) && (val != "")) {
            retryTimes = val.toInteger()
        }
        retryTimes
    }

    static File getDownloadDir() {
        new File(getCWD()+"/reports/"+forkNumber+"/");
    }

    static getCWD(){
        System.getProperty("user.dir")
    }

    static reportGlobalSuffix() {
        "_${envType}"
    }

    static getConfig(configFile='src/test/resources/GebConfig.groovy') {
        if (conf == null) {
            conf = new ConfigSlurper().parse(new File(configFile).toURL())
        }
        conf
    }
}
