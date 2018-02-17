package com.dtolabs.rundeck.plugin.notificationplugin;

import java.util.Map;

public interface NotificationPlugin {


        /**
         * Post a notification for the given trigger, dataset, and configuration
         * @param trigger event type causing notification
         * @param executionData execution data
         * @param config notification configuration
         *               @return true if successul
         */
        public boolean postNotification(String trigger, Map executionData, Map config);
    }

