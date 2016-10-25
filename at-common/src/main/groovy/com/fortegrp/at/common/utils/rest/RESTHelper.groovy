package com.fortegrp.at.common.utils.rest

import groovy.util.logging.Slf4j
import groovyx.net.http.HttpResponseException
import groovyx.net.http.Method
import groovyx.net.http.RESTClient
import org.apache.commons.io.IOUtils
import org.apache.http.impl.client.LaxRedirectStrategy

import static com.fortegrp.at.common.utils.LogHelper.logInfo

@Slf4j
public final class RESTHelper {
    private static RESTClient client = null
    private static def authHeaders = null
    public static def DEFAULT_CONNECT_TIMEOUT = 30000
    public static def MIN_CONNECT_TIMEOUT = 2000

    public static synchronized RESTClient getClient(restUrl) {
        if (client == null) {
            client = new RESTClient(restUrl)
            client.ignoreSSLIssues()
            client.getClient().getParams().setParameter("http.connection.timeout", new Integer(DEFAULT_CONNECT_TIMEOUT))
            client.client.setRedirectStrategy(new LaxRedirectStrategy())
            client
        }
        return client
    }

    public static doGetWithCustomTimeout(path, timeout = MIN_CONNECT_TIMEOUT) {
        client.getClient().getParams().setParameter("http.connection.timeout", new Integer(timeout))
        doGetWithAut(path, null)
        client.getClient().getParams().setParameter("http.connection.timeout", new Integer(DEFAULT_CONNECT_TIMEOUT))
    }


    public static doGet(path, expectedResponse = 200) {
        doGetWithAut(path, null, expectedResponse)
    }

    public static doGetWithAut(path, apiKey, expectedResponse = 200, query = null, customHeaders = authHeaders) {
        logInfo(String.format("Sending HTTP GET request to: %s with apiKey: %s " + (query ? query : ""), path, apiKey))
        if (customHeaders) {
            logInfo("Custom headers " + customHeaders)
            customHeaders.keySet().each {
                client.headers[it] = customHeaders.get(it)
            }
        }
        def response
        try {
            response = (path.contains("://") ? client.get(uri: path, query: query) :
                    client.get(path: path, query: query))
        } catch (HttpResponseException e) {
            response = e.response
        }
        logInfo(String.format("HTTP response [%s]: %s", response.status, response.data))
        if (expectedResponse) {
            if (response.status != expectedResponse) {
                throw new RuntimeException("Error occurred during the sending HTTP GET request: " + response.status + ". " + response?.data)
            }
        }
        response
    }


//    public static doPostWithAut(args, apiKey, expectedResponse = 200, customHeaders = authHeaders) {
//        logInfo(String.format("Sending HTTP POST request: %s with apiKey: %s ", args, apiKey))
//        def response
//        if (customHeaders) {
//            logInfo("Custom headers " + customHeaders)
//            customHeaders.keySet().each {
//                client.headers[it] = customHeaders.get(it)
//            }
//        }
//        try {
//            response = client.post(args)
//        } catch (HttpResponseException e) {
//            response = e.response
//        }
//        logInfo(String.format("HTTP response [%s]: %s", response.status, response.data))
//        if (expectedResponse) {
//            if (response.status != expectedResponse) {
//                throw new RuntimeException("Error occurred during the sending HTTP POST request: " + response.status + ". " + response?.data)
//            }
//        }
//        def authCookies = response.responseBase.h.original.headergroup.headers.findAll {
//            it.toString().contains("Cookie")
//        }
//        if (authCookies.size() > 0) {
//            authHeaders = ["Cookie": authCookies[0].toString().split(":")[1].split(';')[0].trim()]
//        }
//        response
//    }

    public static doPostWithAut(args, apiKey, expectedResponse = 200) {
        logInfo(String.format("Sending HTTP POST request: %s with apiKey: %s ", args, apiKey))
        def response
        try {
            response = client.post(args)
        } catch (HttpResponseException e) {
            response = e.response
        }
        logInfo(String.format("HTTP response [%s]: %s", response.status, response.data))
        if (expectedResponse) {
            if (response.status != expectedResponse) {
                throw new RuntimeException("Error occurred during the sending HTTP POST request: " + response.status+". "+response?.data)
            }
        }
        def authCookies=response.responseBase.h.original.headergroup.headers.findAll{it.toString().contains("Cookie")}
        if (authCookies.size()>0) {
            authHeaders = authCookies.collectEntries {
                [it.toString().split(':')[0].replace('Set-', '').replace(':', '').trim(),
                 it.toString().split(':')[1].trim()]
            }
        }
        response
    }

    public
    static doSendMultipartFormWithAut(url, multipartForm, apiKey, expectedResponse = 200, Method sendType = Method.POST) {

        logInfo(String.format("Sending Multipart HTTP POST request with apiKey: %s ", apiKey))
        client.headers['Authorization'] = 'Bearer ' + apiKey
        def response
        try {
            response = client.request(sendType) { req ->
                uri.path = url
                req.entity = multipartForm
            }
        } catch (HttpResponseException e) {
            response = e.response
        }
        logInfo(String.format("HTTP response [%s]: %s", response.status, response?.data))
        if (expectedResponse) {
            if (response.status != expectedResponse) {
                throw new AssertionError("Error occurred during the sending HTTP MULTIPART POST request: " + response.status + ". " + response?.data)
            }

        }

        response
    }

    public static doPostMultipartFormWithAut(url, multipartForm, apiKey, expectedResponse = 200) {
        doSendMultipartFormWithAut(url, multipartForm, apiKey, expectedResponse, Method.POST)
    }

    public static doPutMultipartFormWithAut(url, multipartForm, apiKey, expectedResponse = 200) {
        doSendMultipartFormWithAut(url, multipartForm, apiKey, expectedResponse, Method.PUT)
    }

    public static doPutWithAut(args, apiKey, expectedResponse = 200, customHeaders = authHeaders) {
        logInfo(String.format("Sending HTTP PUT request: %s with apiKey: %s ", args, apiKey))
        client.headers['Authorization'] = 'Bearer ' + apiKey
        def response
        if (customHeaders) {
            logInfo("Custom headers " + customHeaders)
            customHeaders.keySet().each {
                client.headers[it] = customHeaders.get(it)
            }
        }
        try {
            response = client.put(args)
        } catch (HttpResponseException e) {
            response = e.response
        }
        logInfo(String.format("HTTP response [%s]: %s", response.status, response.data))
        if (expectedResponse) {
            if (response.status != expectedResponse) {
                throw new RuntimeException("Error occurred during the sending HTTP PUT request: " + response.status + ". " + response?.data)
            }
        }
        response
    }

    public static doDeleteWithAut(args, apiKey, expectedResponse = 204) {
        logInfo(String.format("Sending HTTP DELETE request: %s with apiKey: %s ", args, apiKey))
        client.headers['Authorization'] = 'Bearer ' + apiKey

        def response
        try {
            response = client.delete(args)
        } catch (HttpResponseException e) {
            response = e.response
        }
        logInfo(String.format("HTTP response [%s]: %s", response.status, response.data))
        if (expectedResponse) {
            if (response.status != expectedResponse) {
                throw new RuntimeException("Error occurred during the sending HTTP DELETE request: " + response.status + ". " + response?.data)
            }
        }
        response
    }


    public static doPut(args, expectedResponse = 200) {
        doPutWithAut(args, null, expectedResponse)
    }

    public static doPost(args, expectedResponse = 200) {
        doPostWithAut(args, null, expectedResponse)
    }

    public static doPostMultipartForm(url, multipartForm, expectedResponse = 200) {
        doPostMultipartFormWithAut(url, multipartForm, null, expectedResponse)
    }

}

