package io.picopalette.sensorstrange.helpers;

import android.content.Context;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class Logger {

    private BufferedWriter buf;
    public final static String LOG_KEY = "log_key";
    public final static String LOG_SENSOR = "log_sensor";

    public Logger(Context context, String session, String type) {
        //TODO: Run Time permission for Writing storage.
        //TODO: store logs inside directory /sdcard/sensor-strange/. mkdir if doesn't exist.
        String filename = "", header = "";
        if(type.matches(LOG_SENSOR)) {
            filename = session + "/sensor.log";
            header = "timestamp,sensor,x,y,z";
        } else if (type.matches(LOG_KEY)){
            filename = session + "/keypress.log";
            header = "timestamp,action,key";
        }
        File logFile = new File(context.getFilesDir(), filename);
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
