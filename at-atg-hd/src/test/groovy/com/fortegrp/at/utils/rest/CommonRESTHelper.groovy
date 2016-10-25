package com.fortegrp.at.utils.rest

import com.fortegrp.at.common.utils.LogHelper
import com.fortegrp.at.common.utils.rest.RESTHelper
import groovy.util.logging.Slf4j
import groovyx.net.http.ContentType

@Slf4j
public class CommonRESTHelper {

    public static boolean IS_REST_AVAILABLE=false

    public static login(user, password) {
        LogHelper.logInfo("REST: Logging in as " + user)
        def response=RESTHelper.doPost([path : 'rest/model/gldngt/userprofiling/ProfileActor/login',
                           body              : [login: user, password: password],
                           requestContentType: ContentType.JSON,
                           headers           : ['Content-Type': 'application/json']])

        return response
    }

    public static ping() {
        LogHelper.logInfo("REST: Ping service")
        try {
            RESTHelper.doGetWithCustomTimeout('rest/model/atg/userprofiling/ProfileActor/summary')
            IS_REST_AVAILABLE = true
        } catch (SocketTimeoutException ex) {
            IS_REST_AVAILABLE = false
        }
    }

    public static logout() {
        LogHelper.logInfo("REST: Logging out")
        RESTHelper.doPost([path: 'rest/model/gldngt/userprofiling/ProfileActor/logout'])
    }


}

