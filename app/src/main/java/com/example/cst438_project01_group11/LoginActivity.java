package com.example.cst438_project01_group11;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class LoginActivity extends AppCompatActivity {
    private PokedexDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        db = PokedexDatabase.getInstance(this);

        // creating Buttons
        Button buttonForLoginSystem = findViewById(R.id.login);
        Button buttonForCreateNewAccount = findViewById(R.id.create_account);

        // creating on click listeners
        buttonForLoginSystem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Input Values
                EditText username1 = findViewById(R.id.username);
                EditText password1 = findViewById(R.id.password);
                String username = username1.getText().toString();
                String password = password1.getText().toString();



                /* Validation */
                String res = validateUser(username, password, db);

                switch(res) {
                    case "valid":
                        Bundle bund = new Bundle();
                        bund.putString("username", username);
                        Intent j = new Intent(LoginActivity.this, MainActivity.class);
                        j.putExtras(bund);
                        startActivity(j);
                        break;
                    case "wrong password":
                        Context context = getApplicationContext();
                        CharSequence text = "Wrong password.";
                        int duration = Toast.LENGTH_SHORT;
                        Toast toast = Toast.makeText(context, text, duration);
                        toast.show();
                        return;
                    case "wrong username":
                        Context context2 = getApplicationContext();
                        CharSequence text2 = "Wrong username.";
                        int duration2 = Toast.LENGTH_SHORT;
                        Toast toast2 = Toast.makeText(context2, text2, duration2);
                        toast2.show();
                        return;
                }
            }
        });
        buttonForCreateNewAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(LoginActivity.this, CreateAccountActivity.class);
                startActivity(i);
            }
        });
    }

    /**
     *
     * @param username
     * @param password
     * @param db
     * @return
     */
    public static String validateUser(String username, String password, PokedexDatabase db) {
        List<User> users = db.user().getAll();

        for(int i = 0; i < users.size(); i++) {
            if (users.get(i).getUUsername().equals(username)) {
                if (users.get(i).getUPassword().equals(password)) {
                    return "valid";
                } else {
                    return "wrong password";
                }
            }
        }
        return "wrong username";
    }


}