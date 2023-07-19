package org.capacitor.quasar.app;

import com.getcapacitor.BridgeActivity;
import android.app.NotificationManager;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import androidx.annotation.NonNull;
import java.util.concurrent.CompletableFuture;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

public class MainActivity extends BridgeActivity {
  @Override
  public void onCreate(Bundle savedInstanceState) {
    registerPlugin(HelperClassPlugin.class);
    super.onCreate(savedInstanceState);
    NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
    notificationManager.cancelAll();
    FirebaseMessaging.getInstance().getToken()
      .addOnCompleteListener(new OnCompleteListener<String>() {
        @Override
        public void onComplete(@NonNull Task<String> task) {
          if (!task.isSuccessful()) {
            System.out.println("Fetching FCM registration token failed");
            return;
          }
          // Get new FCM registration token
          String token = task.getResult();
          if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            CompletableFuture.runAsync(() -> {
              saveTokenInPreference(token);
              SharedPreferences sh = getSharedPreferences("CapacitorStorage", Context.MODE_PRIVATE);
            });
          }

        }
      });
    FirebaseMessaging.getInstance().setAutoInitEnabled(true);
  }

  public void saveTokenInPreference(String token){
    SharedPreferences sharedPreferences = getSharedPreferences("CapacitorStorage",MODE_PRIVATE);
    SharedPreferences.Editor editor = sharedPreferences.edit();
    editor.putString("token",token);
    editor.apply();
  }
}
