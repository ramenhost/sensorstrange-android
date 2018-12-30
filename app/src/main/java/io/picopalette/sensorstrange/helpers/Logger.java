package io.picopalette.sensorstrange.helpers;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class Logger {

    private BufferedWriter buf;
    public final static String LOG_KEY = "log_key";
    public final static String LOG_SENSOR = "log_sensor";

    public Logger(String session, String type) {
        //TODO: Run Time permission for Writing storage.
        //TODO: store logs inside directory /sdcard/sensor-strange/. mkdir if doesn't exist.
        String path = "", header = "";
        if(type.matches(LOG_SENSOR)) {
            path = "sdcard/sensor_" + session + ".log";
            header = "timestamp,sensor,x,y,z";
        } else if (type.matches(LOG_KEY)){
            path = "sdcard/keypress_" + session + ".log";
            header = "timestamp,action,key";
        }
        File logFile = new File(path);
        try {
            if (!logFile.exists()) {
                if (logFile.createNewFile()) {
                    buf = new BufferedWriter(new FileWriter(logFile, true));
                    buf.append(header);
                    buf.newLine();
                }
            } else {
                buf = new BufferedWriter(new FileWriter(logFile, true));
            }
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    public void appendLog(String text)
    {
        try
        {
            buf.append(text);
            buf.newLine();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    public void close() {
        try {
            buf.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
