package com.example.austin.mytask;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.LinkedList;

public class MainActivity extends AppCompatActivity {

    //Creating starting points for referencing TextView's, EditText and the ListView
    TextView title;
    EditText editObject;
    ListView objectList;


    //Creating starting points for LinkedList and ArrayAdapter
    LinkedList<String> toDoObjects;
    ArrayAdapter<String>listViewAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //Finishes instantiating a new instance of the textView, editText, and listView's by referencing them to the XML id's.
        title = (TextView) findViewById(R.id.title);
        editObject = (EditText) findViewById(R.id.editObjectEntry);
        objectList = (ListView) findViewById(R.id.listView);

        //Finishes instantiating the instances of linkedList and ArrayAdapter and then sets the arrayAdapter to the listView and the LinkedList
        toDoObjects = new LinkedList<>();
        listViewAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, toDoObjects);
        objectList.setAdapter(listViewAdapter);


        objectList.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                //Removed a list item at it's position as a parameter
                toDoObjects.remove(position);
                //Notifying the adapter that the list did in fact change via removing from press @ position
                listViewAdapter.notifyDataSetChanged();

                return true;
            }
        });



        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "List object added", Snackbar.LENGTH_SHORT)
                        .setAction("Action", null).show();


                //Creates new string object that takes the text from the edit text field and turns it into a string to be used
                String addingText = editObject.getText().toString();

                //Creates a boolean, if the edit text lenght is less than 0, that sharedPreferences is initiated to send over the name of the Object List to the next activity via key/value pair
                if(editObject.length() > 0){
                    SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("key", MODE_PRIVATE);
                    sharedPreferences.getString("object", "");
                    String previousList = String.valueOf(sharedPreferences.getString("object", ""));
                    title.setText(previousList);
                    //Adds new objects to the array list
                    toDoObjects.add(addingText);
                    //Notifies the array adapter that the data set had changed, updating the listView in the process
                    listViewAdapter.notifyDataSetChanged();
                    //Clears out the edit text so that the users information doesn't persist and become annoying
                    editObject.setText("");
                }

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

//        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_settings) {
//            return true;
//        }

        return super.onOptionsItemSelected(item);
    }
}
