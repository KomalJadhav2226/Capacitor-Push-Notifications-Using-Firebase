package org.capacitor.quasar.app;

import android.content.Context;
import android.content.SharedPreferences;

import com.getcapacitor.JSObject;
import com.getcapacitor.Plugin;
import com.getcapacitor.PluginCall;
import com.getcapacitor.PluginMethod;
import com.getcapacitor.annotation.CapacitorPlugin;

@CapacitorPlugin(name = "HelperClassPlugin")
public class HelperClassPlugin extends Plugin{

  @PluginMethod()
  public void getFirebaseToken(PluginCall call) {
    Context context = getActivity().getApplicationContext();
    SharedPreferences sh = context.getSharedPreferences("CapacitorStorage", Context.MODE_PRIVATE);
    JSObject ret = new JSObject();
    ret.put("token",sh.getString("token","token not found"));
    call.resolve(ret);
  }
}
