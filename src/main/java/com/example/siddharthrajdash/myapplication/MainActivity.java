package com.example.siddharthrajdash.myapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // this line initializes Slang in your app
        in.slanglabs.myapplication.SlangInterface.init(this);
    }
    /**
     * Show a toast
     * @param view -- the view that is clicked
     */
    public void toastMe(View view){
        // Toast myToast = Toast.makeText(this, message, duration);
        Toast myToast = Toast.makeText(this, "Hi Siddharth!", Toast.LENGTH_SHORT);
        myToast.show();
    }
    public void markPresent(View view) {
        // Create an Intent to start the second activity
        Intent intent = new Intent(this, SeconfActivity.class);
        // Start the new activity.
        startActivity(intent);
    }


}
