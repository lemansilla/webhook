package com.dtolabs.rundeck.plugin.notificationplugin;

//import com.dtolabs.rundeck.plugins.notification.NotificationPlugin;
//import com.dtolabs.rundeck.core.plugins.Plugin;
//import com.dtolabs.rundeck.plugins.descriptions.PluginDescription;
//import com.dtolabs.rundeck.plugins.descriptions.PluginProperty;

import com.google.gson.Gson;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.MalformedURLException;
import java.util.Map;

/**
 * Main implementation of the plugin interface.
 */
//@Plugin(service="Notification",name="NotificationPlugin")
//@PluginDescription(title="Notification Plugin", description="A Plugin for Rundeck Notifications.")
public class ImplNotificationPlugin implements NotificationPlugin {

    private static final String DEFAULT_METHOD = "POST";
    private static final String DEFAULT_CONTENT = "XML";
    private static final String DEFAULT_TIMEOUT = "3000"; //3 seconds
    /**
     * Interface method implementation
     * @param trigger event type causing notification
     * @param executionData execution data
     * @param config notification configuration
     * @return Boolean indicating success (true) or failure (false)
     */
    public boolean postNotification(String trigger, Map executionData, Map config)  {

        String input = "";
        boolean bool = false;
        String remoteURL = config.containsKey("url") ? (String)config.get("url") : null;
        String method = config.containsKey("method") ? (String)config.get("method") : DEFAULT_METHOD;
        String contentType = config.containsKey("content-type") ?
                String.format("application/%s", config.get("content-type")) : DEFAULT_CONTENT;
        String timeout = config.containsKey("timeout") ? (String)config.get("timeout") : DEFAULT_TIMEOUT;

        try {
            URL url = new URL(remoteURL);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod(method);
            conn.setRequestProperty("Content-Type", contentType);
            conn.setConnectTimeout(Integer.parseInt(timeout));
            conn.setDoOutput(true);  // Triggers POST

            NotificationModel notif = createNotificationObject(executionData);
            notif.setTrigger(trigger);

            if (config.get("content-type").equals("json")) {
                input = createJsonNotification(notif);
            } else {
                input = createXMLNotification(notif);
            }

            OutputStream wr = conn.getOutputStream();
            wr.write(input.getBytes());
            wr.flush();
            wr.close();

            if (conn.getResponseCode() == 200 || conn.getResponseCode() == 201) {
                bool = true;
                System.out.printf("Notification Plugin Log: Notification was delivered,  %s OK\n", conn.getResponseCode());
            } else {
                System.err.printf("Notification Plugin Error: Server reply with error, %s\n", conn.getResponseCode());
                System.err.println(conn.getResponseMessage());
            }
            //Receive Response from server and -if necessary- print it to console
            try (BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())))) {
                String output;
                while ((output = br.readLine()) != null) {
                    System.out.println(output);
                }
            } finally {
                conn.disconnect();
            }

        } catch (MalformedURLException ex) {
            System.err.printf("\nNotification Plugin Error (URL):  %s", ex);
        } catch (IOException ex) {
            System.err.printf("\nNotification Plugin Error (HTTP):  %s", ex);
        } catch (Exception ex) {
            System.err.printf("\nNotification Plugin Error:  %s", ex);
        }
    return bool;
    }

    /**
     *
     * @param execution - this correspond to the information about the Job and Execution for the notification
     * @return This is the class model to be used in the XML and JSON generation
     */
    private NotificationModel createNotificationObject(final Map<Object, String> execution) {

        Job job = new Job();
        job.setName(execution.get("job.name"));
        job.setGroup(execution.get("job.group"));
        job.setProject(execution.get("job.project"));
        job.setDescription(execution.get("job.description"));
        job.setId(execution.get("job.id"));
        job.setHref(execution.get("job.href"));

        DateStarted dateStarted = new DateStarted();
        dateStarted.setDate(execution.get("execution.dateStarted"));
        dateStarted.setUnixtime(execution.get("execution.dateStartedUnixtime"));

        Execution execdata = new Execution();
        execdata.setDescription(execution.get("execution.description"));
        execdata.setJob(job);
        execdata.setDatestarted(dateStarted);
        execdata.setUser(execution.get("execution.user"));
        execdata.setId(execution.get("execution.id"));
        execdata.setHref(execution.get("execution.href"));
        Executions exec = new Executions();
        exec.setExecution(execdata);

        NotificationModel notifModel = new NotificationModel();
        notifModel.setStatus(execution.get("execution.status"));
        notifModel.setExecutions(exec);

        return notifModel;
    }

    /**
     * This method creates an XML String from the class notification model
     * @param notifModel class model
     * @return - XML String
     */
    private String createXMLNotification(NotificationModel notifModel){

        String xmlString = "";

        try {
            JAXBContext context = JAXBContext.newInstance(NotificationModel.class,
                    Executions.class, Execution.class, Job.class);
            Marshaller jaxbMarshaller = context.createMarshaller();

            jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
            //remove xml header
            jaxbMarshaller.setProperty(Marshaller.JAXB_FRAGMENT, Boolean.TRUE);
            jaxbMarshaller.marshal(notifModel, System.out);
            StringWriter sw = new StringWriter();
            jaxbMarshaller.marshal(notifModel, sw);
            xmlString = sw.toString();

        } catch (JAXBException ex) {
            ex.printStackTrace();
            System.err.printf("Notification Plugin Error (JABX):  %s", ex);
        }
    return xmlString;
    }

    /**
     * This method creates a JSON String from the class notification model
     * @param notifModel class model
     * @return - JSON String|
     */
    private String createJsonNotification(NotificationModel notifModel) {

        //String jsonString = "";
        Gson gson = new Gson();
        String jsonString = gson.toJson(notifModel);
        System.out.println(jsonString);

        return jsonString;
    }
}