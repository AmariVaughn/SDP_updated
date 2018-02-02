package com.example.cash.sdp;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;


public class Main2Activity extends AppCompatActivity {
 Button g;
 Intent h;
 TextView k;

    public   AlertDialog.Builder alertDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        g=(Button)findViewById(R.id.button);
        alertDialog = new AlertDialog.Builder(Main2Activity.this);
        LayoutInflater inflater = getLayoutInflater();

        final View convertView = (View) inflater.inflate(R.layout.login, null);



        alertDialog.setView(convertView);
        final EditText event = (EditText) convertView.findViewById(R.id.email2);
        final EditText des = (EditText) convertView.findViewById(R.id.pass);
        // Set the action buttons
        alertDialog.setPositiveButton("Sign in", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                // User clicked OK, so save the mSelectedItems results somewhere
                // or return them to the component that opened the dialog
                if(event.getText().toString().endsWith("@aggies.ncat.edu")&& !des.getText().equals("")) {
                    h = new Intent(Main2Activity.this, MainActivity.class);
                    startActivity(h);
                }else{
                    Context context = getApplicationContext();
                    CharSequence text = "Email or password is incorrect";
                    int duration = Toast.LENGTH_SHORT;

                    Toast toast = Toast.makeText(context, text, duration);
                    toast.show();

                }

            }
        });
        alertDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
             // the window will close
            }
        });



        alertDialog.show();
        g.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                ((ViewGroup) convertView.getParent()).removeView(convertView);
                alertDialog.setView(convertView);
                alertDialog.show();
            }
        });

    }

}
