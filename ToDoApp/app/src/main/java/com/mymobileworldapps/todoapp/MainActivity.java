package com.mymobileworldapps.todoapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.view.View;
import android.widget.TextView;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ArrayList<String> todoitmes;
    ArrayAdapter<String> aToDoAdapter;
    ListView lvItems;
    EditText etEditText;
    String strEditText;
    int position;

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            if(resultCode == RESULT_OK) {
                strEditText = data.getStringExtra("newTextValue");
                position = data.getIntExtra("newPos", 0);
                todoitmes.set(position,strEditText);
                aToDoAdapter.notifyDataSetChanged();
                writeItem();
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        populateArrayItems();
        lvItems = (ListView) findViewById(R.id.lvList);
        etEditText = (EditText) findViewById(R.id.etEditText);
        lvItems.setAdapter(aToDoAdapter);

        lvItems.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener(){
            @Override
            public boolean onItemLongClick(AdapterView<?> adapter,
                                           View item, int pos, long id) {
                // Remove the item within array at position
                todoitmes.remove(pos);
                // Refresh the adapter
                aToDoAdapter.notifyDataSetChanged();
                writeItem();
                // Return true consumes the long click event (marks it handled)
                return true;
            }
        });


        lvItems.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> adapter, View item, int pos, long id) {
                Intent i = new Intent(MainActivity.this, EditItemActivity.class);
                i.putExtra("pos", pos);
                i.putExtra("textItem", todoitmes.get(pos));
                startActivityForResult(i, 1);
                //startActivity(i);
            }
        });



    }


    public void populateArrayItems() {
        todoitmes = new ArrayList<String>();
        readItem();
        aToDoAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, todoitmes);
    }

    public void onAddItem(View view){
        todoitmes.add(etEditText.getText().toString());
        etEditText.setText("");
        writeItem();
    }


    private void readItem(){
        File filesDir = getFilesDir();
        File file = new File(filesDir, "todo.txt");
        try {
            todoitmes = new ArrayList<>(FileUtils.readLines(file));
        } catch (IOException e){

        }
    }

    private void writeItem(){
        File filesDir = getFilesDir();
        File file = new File(filesDir, "todo.txt");
        try {
            //todoitmes = new ArrayList<>(FileUtils.readLines(file));
            FileUtils.writeLines(file, todoitmes);
        } catch (IOException e){

        }
    }


}
