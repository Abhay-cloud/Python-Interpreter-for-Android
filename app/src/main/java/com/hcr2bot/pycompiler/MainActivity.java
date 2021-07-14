package com.hcr2bot.pycompiler;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.amrdeveloper.codeview.CodeView;
import com.chaquo.python.PyObject;
import com.chaquo.python.Python;
import com.chaquo.python.android.AndroidPlatform;
import com.hcr2bot.pycompiler.syntax.Language;
import com.hcr2bot.pycompiler.syntax.SyntaxManager;

public class MainActivity extends AppCompatActivity {

    ImageButton button;
//    TextView result;
    private CodeView mCodeView;
    //Index of next theme to load it when user click change theme
    private int mNextThemeIndex = 2;

    //To change themes easily
    private final Language mCurrentLanguage = Language.PYTHON;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        button = findViewById(R.id.imageButton);
//        result = findViewById(R.id.resultView);

        mCodeView = findViewById(R.id.codeView);

        SharedPreferences getShared = getSharedPreferences("lastcode", MODE_PRIVATE);
        String lastCode = getShared.getString("lastTimeCode", "# Start to write code from here");
        Log.d("myapp", lastCode);
        mCodeView.setText(lastCode);

        if (! Python.isStarted()) {
            Python.start(new AndroidPlatform(MainActivity.this));
        }


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences shrd = getSharedPreferences("lastcode", MODE_PRIVATE);
                SharedPreferences.Editor editor = shrd.edit();
                editor.putString("lastTimeCode", mCodeView.getText().toString());
                editor.apply();


                Python py = Python.getInstance();
                // here we call script with the name
                PyObject pyObject = py.getModule("myscript"); // give python script name
                // add main method inside script, pass data here
                PyObject object = pyObject.callAttr("main", mCodeView.getText().toString());
                // here we'll set returned value of our python code and set it to textview
//                result.setText(object.toString());
                Toast.makeText(MainActivity.this, object.toString() + "", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(MainActivity.this, Result.class);
                intent.putExtra("result", object.toString());
                startActivity(intent);

            }
        });

        configLanguageAutoComplete();

        //Config the default theme
        SyntaxManager.applyMonokaiTheme(this, mCodeView, mCurrentLanguage);
    }

    private void configLanguageAutoComplete() {
        final String[] languageKeywords;
        switch (mCurrentLanguage) {
            case PYTHON:
                languageKeywords = getResources().getStringArray(R.array.python_keywords);
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + mCurrentLanguage);
        }

        //Custom list item xml layout
        final int layoutId = R.layout.suggestion_list_item;

        //TextView id to put suggestion on it
        final int viewId = R.id.suggestItemTextView;
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, layoutId, viewId, languageKeywords);

        //Add Custom Adapter to the CodeView
        mCodeView.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        final int id = item.getItemId();
        if (id == R.id.changeMenu) {
            changeCodeViewTheme();
        }
        return super.onOptionsItemSelected(item);
    }

    private void changeCodeViewTheme() {
        if (mNextThemeIndex > 4) mNextThemeIndex = 1;
        loadNextTheme();
        mNextThemeIndex = mNextThemeIndex + 1;
    }

    private void loadNextTheme() {
        switch (mNextThemeIndex) {
            case 1:
                SyntaxManager.applyMonokaiTheme(this, mCodeView, mCurrentLanguage);
                Toast.makeText(this, "Monokai", Toast.LENGTH_SHORT).show();
                break;
            case 2:
                SyntaxManager.applyNoctisWhiteTheme(this, mCodeView, mCurrentLanguage);
                Toast.makeText(this, "Noctis White", Toast.LENGTH_SHORT).show();
                break;
            case 3:
                SyntaxManager.applyFiveColorsDarkTheme(this, mCodeView, mCurrentLanguage);
                Toast.makeText(this, "5 Colors Dark", Toast.LENGTH_SHORT).show();
                break;
            default:
                SyntaxManager.applyOrangeBoxTheme(this, mCodeView, mCurrentLanguage);
                Toast.makeText(this, "Orange Box", Toast.LENGTH_SHORT).show();
                break;
        }


    }
}