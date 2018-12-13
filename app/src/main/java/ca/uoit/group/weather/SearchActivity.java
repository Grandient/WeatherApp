package ca.uoit.group.weather;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import java.util.concurrent.ThreadLocalRandom;

public class SearchActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
    }

    public void cape_town(View view){
        Intent i = new Intent(this, MainMenuActivity.class);
        i.putExtra("id","3369157");
        setResult(Activity.RESULT_OK,i);
        finish();
    }

    public void shanghai(View view){
        Intent i = new Intent(this, MainMenuActivity.class);
        i.putExtra("id","1796236");
        setResult(Activity.RESULT_OK,i);
        finish();
    }

    public void vancouver(View view){
        Intent i = new Intent(this, MainMenuActivity.class);
        i.putExtra("id","5814616");
        setResult(Activity.RESULT_OK,i);
        finish();
    }

    public void london(View view){
        Intent i = new Intent(this, MainMenuActivity.class);
        i.putExtra("id","2643743");
        setResult(Activity.RESULT_OK,i);
        finish();
    }

    public void toronto(View view){
        Intent i = new Intent(this, MainMenuActivity.class);
        i.putExtra("id","6167865");
        setResult(Activity.RESULT_OK,i);
        finish();
    }

    public void miami(View view){
        Intent i = new Intent(this, MainMenuActivity.class);
        i.putExtra("id","4164138");
        setResult(Activity.RESULT_OK,i);
        finish();
    }

    public void los_angeles(View view){
        Intent i = new Intent(this, MainMenuActivity.class);
        i.putExtra("id","5368361");
        setResult(Activity.RESULT_OK,i);
        finish();
    }

    public void moscow(View view){
        Intent i = new Intent(this, MainMenuActivity.class);
        i.putExtra("id","524901");
        setResult(Activity.RESULT_OK,i);
        finish();
    }

    public void search(View view){
        Intent i = new Intent(this, MainMenuActivity.class);
        EditText ed1 = findViewById(R.id.city);
        EditText ed2 = findViewById(R.id.country);
        String result = ed1.getText().toString() + "," +  ed2.getText().toString();
        i.putExtra("id",result);
        setResult(Activity.RESULT_OK,i);
        finish();
    }

}
