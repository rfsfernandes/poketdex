package pt.rfsfernandes;

import android.app.Application;

import com.facebook.drawee.backends.pipeline.Fresco;

/**
 * Class MyApplication created at 1/16/21 19:36 for the project PoketDex
 * By: rodrigofernandes
 */
public class MyApplication extends Application {
  @Override
  public void onCreate() {
    super.onCreate();
    Fresco.initialize(this);
  }
}
