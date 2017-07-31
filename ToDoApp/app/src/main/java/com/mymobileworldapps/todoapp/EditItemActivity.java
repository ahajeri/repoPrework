package com.mymobileworldapps.todoapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class EditItemActivity extends AppCompatActivity {

    String text;
    int position;
    EditText etTextToEdit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_item);
        text = getIntent().getStringExtra("textItem");
        position = getIntent().getIntExtra("pos", 0);
        etTextToEdit = (EditText)findViewById(R.id.etEditTextItem);
        etTextToEdit.setText(text, TextView.BufferType.EDITABLE);
    }


    public void saveNewText(View view){
        String newText = etTextToEdit.getText().toString();
        Intent intent = new Intent(EditItemActivity.this, MainActivity.class);
        intent.putExtra("newTextValue", newText);
        intent.putExtra("newPos", position);
        //setResult(RESULT_OK, intent);
        setResult(EditItemActivity.RESULT_OK, intent);
        finish();
    }


}
