package edu.northeastern.pokedex;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.messaging.FirebaseMessaging;

public class cloudMessaging extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        FirebaseMessaging.getInstance().getToken()
                .addOnCompleteListener(new OnCompleteListener<String>() {
                    @Override
                    public void onComplete(@NonNull Task<String> task) {
                        if (!task.isSuccessful()) {
                            Log.w("Fetching FCM registration token failed", task.getException());
                            return;
                        }

                        // Get new FCM registration token
                        String token = task.getResult();

                        // Log and toast
                        Log.d("SOMETAGG", token);
                        Toast.makeText(cloudMessaging.this, "TOKEN= " + token, Toast.LENGTH_SHORT).show();
                    }
                });

    }
}
// ciP-poazSgu9n0d8IDNRv5:APA91bH9_j5EW9u_MESVmG5-VkAtMNmumKf4SGfFJNceyYYhC8bTJ8sNc4rA2oTI8BfJslenm2sabO2wGo9frEUUvjAtQpgYnYt0Kjc7KINXOA-mFVE7BUNd8WzoQKHpzQOa3VTUOVHv
