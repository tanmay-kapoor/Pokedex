package edu.northeastern.pokedex.userRV;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.ExportedUserRecord;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.ListUsersPage;
import com.google.firebase.auth.UserRecord;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Iterator;
import java.util.List;

public class UserListActivity extends AppCompatActivity {

    DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();

    protected void onCreate(Bundle savedInstances) {
        super.onCreate(savedInstances);

//        try {
//            // Start listing users from the beginning, 1000 at a time.
//            ListUsersPage page = FirebaseAuth.getInstance().listUsers(null);
//            while (page != null) {
//                for (ExportedUserRecord user : page.getValues()) {
//                    System.out.println("User: " + user.getUid());
//                }
//                page = page.getNextPage();
//            }
//
//            // Iterate through all users. This will still retrieve users in batches,
//            // buffering no more than 1000 users in memory at a time.
//            page = FirebaseAuth.getInstance().listUsers(null);
//            for (ExportedUserRecord user : page.iterateAll()) {
//                System.out.println("User: " + user.getUid());
//            }
//        } catch (FirebaseAuthException e) {
//            throw new RuntimeException(e);
//        }
    }
}
