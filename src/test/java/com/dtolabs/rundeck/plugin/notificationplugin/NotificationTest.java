package com.dtolabs.rundeck.plugin.notificationplugin;

import org.junit.Test;

import java.net.MalformedURLException;
import java.util.HashMap;
import java.util.Map;

import static com.dtolabs.rundeck.plugin.notificationplugin.ImplNotificationPlugin.errorCode;
import static com.dtolabs.rundeck.plugin.notificationplugin.ImplNotificationPlugin.exception;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class NotificationTest {

    private Map<String, String> executionData() {

        Map<String, String> execData = new HashMap<>();
        execData.put("execution.id", "2");
        execData.put("execution.status", "running");
        execData.put("execution.user", "bonfire");
        execData.put("execution.dateStarted", "2018-02-12T19:27:14Z");
        execData.put("execution.dateStartedUnixtime", "1518010034940");
        execData.put("execution.description", "OK");
        execData.put("job.name", "firstjob");
        execData.put("job.group", "coders");
        execData.put("job.project", "webhook");
        execData.put("job.description", "first attempt");
        execData.put("job.id", "de70dbd0-21dc-4d90-ab2a-86f60a98ebc8");
        execData.put("execution.user", "Uncle Bob");

        return execData;
    }

    //This is a functional test of NotificationPlugin Class
    @Test
    public void postXMLNotificationSuccess() {

        String url = "http://httpbin.org/post";
        String contentType = "xml";
        String method = "POST";

        //Create executionData and config
        Map executionData = executionData();
        Map<String, String> config = new HashMap<>();
        config.put("url", url);
        config.put("content-type", contentType);
        config.put("method", method);

        //Test the implementation with the test data (including trigger)
        NotificationPlugin notf = new ImplNotificationPlugin();
        boolean result = notf.postNotification("onstart", executionData, config);

        assertTrue("Notification was not delivered ", result);
    }

    @Test
    public void postJsonNotificationSuccess() {

        String url = "http://httpbin.org/post";
        String contentType = "json";
        String method = "POST";

        //Create executionData and config
        Map executionData = executionData();
        Map<String, String> config = new HashMap<>();
        config.put("url", url);
        config.put("content-type", contentType);
        config.put("method", method);

        //Test the implementation with the test data (including trigger)
        NotificationPlugin notf = new ImplNotificationPlugin();
        boolean result = notf.postNotification("onstart", executionData, config);

        assertTrue("Notification was not delivered ", result);

    }
    //Test exception if empty url string
    @Test
    public void emptyURLInputErrorTest() throws MalformedURLException {

        String url = "";
        String contentType = "json";
        String method = "POST";

        //Create executionData and config
        Map executionData = executionData();
        Map<String, String> config = new HashMap<>();
        config.put("url", url);
        config.put("content-type", contentType);
        config.put("method", method);

        //Test the implementation with the test data (including trigger)
        NotificationPlugin notf = new ImplNotificationPlugin();
        boolean result = notf.postNotification("onstart", executionData, config);

        assertEquals("java.net.MalformedURLException", exception);
    }

    // Test default method POST if not included as a parameter
    @Test
    public void noMethodInputUseDefaultTest() {
        String url = "http://httpbin.org/post";
        String contentType = "json";

        //Create executionData and config
        Map executionData = executionData();
        Map<String, String> config = new HashMap<>();
        config.put("url", url);
        config.put("content-type", contentType);

        //Test the implementation with the test data (including trigger)
        NotificationPlugin notf = new ImplNotificationPlugin();
        boolean result = notf.postNotification("onstart", executionData, config);
        assertTrue("Notification was not delivered ", result);
    }

    // Test default content-type XML if not included as a parameter
    @Test
    public void noContentTypeInputUseDefaultTest() {
        String url = "http://httpbin.org/post";
        String method = "POST";

        //Create executionData and config
        Map executionData = executionData();
        Map<String, String> config = new HashMap<>();
        config.put("url", url);
        config.put("method", method);

        //Test the implementation with the test data (including trigger)
        NotificationPlugin notf = new ImplNotificationPlugin();
        boolean result = notf.postNotification("onstart", executionData, config);
        assertTrue("Notification was not delivered ", result);
    }

    @Test
    public void serverResponseErrorTest() throws Exception {

        String url = "http://httpbin.org";
        String contentType = "json";
        String method = "POST";

        //Create executionData and config
        Map executionData = executionData();
        Map<String, String> config = new HashMap<>();
        config.put("url", url);
        config.put("content-type", contentType);
        config.put("method", method);

        //Test the implementation with the test data (including trigger)
        NotificationPlugin notf = new ImplNotificationPlugin();
        boolean result = notf.postNotification("onstart", executionData, config);
        assertTrue("Check HTTP response code", errorCode >= 300);
    }
}
