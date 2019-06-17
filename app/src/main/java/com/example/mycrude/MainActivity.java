package com.example.mycrude;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import io.realm.Realm;
import io.realm.RealmResults;

public class MainActivity extends AppCompatActivity {
    Button add, view, update, delete;
    EditText roll_no, firstName,secondName;
    TextView text;
    Realm realm;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        add = (Button)findViewById(R.id.add);
        view = (Button)findViewById(R.id.view);
        update = (Button)findViewById(R.id.update);
        delete = (Button)findViewById(R.id.delete);
        roll_no = (EditText)findViewById(R.id.roll_no);
        firstName = (EditText)findViewById(R.id.first_id);
//        secondName = (EditText)findViewById(R.id.second_id);
        text = (TextView)findViewById(R.id.text);

        Realm.init(this);
        realm = Realm.getDefaultInstance();

    }

    public void afterClick(View view) {

        switch (view.getId()){
            case R.id.add:
                addRecord();
                break;
            case R.id.view:
                viewRecord();
                break;
            case R.id.update:
                updateRecord();
                break;
            case R.id.delete:
                deleteRecord();
        }

    }

    public void addRecord(){
        realm.beginTransaction();

        Students student = realm.createObject(Students.class);
        student.setRoll(Integer.parseInt(roll_no.getText().toString()));

        student.setFirst(firstName.getText().toString());
//        student.setSecond(secondName.getText().toString());


        realm.commitTransaction();
    }

    public void viewRecord(){
        RealmResults<Students> results = realm.where(Students.class).findAll();

        text.setText("");

        for(Students student : results){
            text.append(student.getRoll() + " " + student.getFirst() + "\n");
        }
    }

    public void updateRecord(){
        RealmResults<Students> results = realm.where(Students.class).equalTo("roll_no", Integer.parseInt(roll_no.getText().toString())).findAll();

        realm.beginTransaction();

        for(Students student : results){
            student.setFirst(firstName.getText().toString());
        }

        realm.commitTransaction();
    }

    public void deleteRecord(){
        RealmResults<Students> results = realm.where(Students.class).equalTo("roll_no", Integer.parseInt(roll_no.getText().toString())).findAll();

        realm.beginTransaction();

        results.deleteAllFromRealm();

        realm.commitTransaction();
    }

    @Override
    protected void onDestroy() {
        realm.close();
        super.onDestroy();
    }

    }

