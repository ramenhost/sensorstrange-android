package io.picopalette.sensorstrange;

import android.app.Application;

import net.gotev.uploadservice.BuildConfig;
import net.gotev.uploadservice.UploadService;
import net.gotev.uploadservice.okhttp.OkHttpStack;


public class Initializer extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        // setup the broadcast action namespace string which will
        // be used to notify upload status.
        // Gradle automatically generates proper variable as below.
        UploadService.NAMESPACE = BuildConfig.APPLICATION_ID;
        // Or, you can define it manually.
        UploadService.NAMESPACE = "io.picopalette.sensorstrange";
        UploadService.HTTP_STACK = new OkHttpStack();

    }
}