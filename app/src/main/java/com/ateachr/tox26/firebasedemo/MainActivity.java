package com.ateachr.tox26.firebasedemo;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.ateachr.tox26.databasedemo.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends Activity implements View.OnClickListener{

    private Button buttonUpdate;
    private Button buttonDelete;
    private Button buttonRead;
    private Button buttonGetAll;

    private EditText editTextEMail;
    private EditText editTextName;
    private EditText editTextGrade;
    private EditText editNameReadGrade;
    private EditText editEmailDeleteGrade;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        buttonUpdate = (Button) findViewById(R.id.buttonUpdate);
        buttonDelete = (Button) findViewById(R.id.buttonDelete);
        buttonRead = (Button) findViewById(R.id.buttonRead);
        buttonGetAll = (Button) findViewById(R.id.buttonGetAll);

        buttonUpdate.setOnClickListener(this);
        buttonDelete.setOnClickListener(this);
        buttonRead.setOnClickListener(this);
        buttonGetAll.setOnClickListener(this);

        editTextEMail = (EditText) findViewById(R.id.editTextEMail);
        editTextName = (EditText) findViewById(R.id.editTextName);
        editTextGrade = (EditText) findViewById(R.id.editTextGrade);
        editEmailDeleteGrade = (EditText) findViewById(R.id.editTextNameDeleteGrade);
        editNameReadGrade = (EditText) findViewById(R.id.editTextNameReadGrade);
    }

    @Override
    public void onClick(View view) {

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("Users");

        if (view == buttonUpdate) {
            String email = editTextEMail.getText().toString();
            String name = editTextName.getText().toString();
            Double grade = Double.parseDouble(editTextGrade.getText().toString());

            Student student = new Student(email, grade);

            myRef.child(name).setValue(student);


        } else if (view == buttonDelete) {

            String namedelete = editEmailDeleteGrade.getText().toString();
            //myRef.child(namedelete).child("grade").setValue(0);
            myRef.child(namedelete).setValue(null);

        } else if (view == buttonRead) {

            final String nameread = editNameReadGrade.getText().toString();

            myRef.child(nameread).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    // This method is called once with the initial value and again
                    // whenever data at this location is updated.
                    Student readstudent = dataSnapshot.getValue(Student.class);
                    //Log.d(TAG, "Value is: " + value);
                    Toast.makeText(MainActivity.this, "Grade for " + nameread + " is: " + readstudent.grade, Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onCancelled(DatabaseError error) {
                    // Failed to read value
                    //Log.w(TAG, "Failed to read value.", error.toException());
                }
            });

        } else if(view == buttonGetAll) {

            myRef.orderByValue().limitToFirst(300).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    Toast.makeText(MainActivity.this, dataSnapshot.getValue().toString(), Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });




        }
    }
}
