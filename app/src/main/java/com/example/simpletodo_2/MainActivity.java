package com.example.simpletodo_2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    List<String> items;

    // Add a member variable for each view
    Button btnAdd;
    EditText etItem;
    RecyclerView rvItems;
    ItemsAdapter itemsAdapter;

    //You should not need to surpress Lint!
    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Define member variables

        btnAdd = findViewById(R.id.btnAdd);
        etItem = findViewById(R.id.etItem);
        rvItems = findViewById(R.id.rvItems);





        //etItem.setText("I am doing this from Java");
        //Instantiate the model, add items to our list
//        items = new ArrayList<>();
        loadItems();

        //Using adapter in our main activity

        //We need to do this to access the longclicklistener in the adapter and override. Use ctrl + o to override
        ItemsAdapter.OnLongClickListener onLongClickListener = new ItemsAdapter.OnLongClickListener(){
            @Override
            public void onItemLongClicked(int position) {
                //We know the exact position that the user has long pressed at this point. Now we can
                //Delete the item from the model
                items.remove(position);
                //Notify the adapter of the position we deleted the item.
                itemsAdapter.notifyItemRemoved(position);
                Toast.makeText(getApplicationContext(),"Item was removed", Toast.LENGTH_SHORT).show();
                saveItems();
            }
        };
        // We have to pass in onLongClickListener as a second parameter to our adapter so we can know when an item was long pressed.
       itemsAdapter =  new ItemsAdapter(items, onLongClickListener);
       //Set adapter on the recycler view
        rvItems.setAdapter(itemsAdapter);
        //Set a layout manager on rvItems, we will use Layout manager which puts things on the UI in a vertical way by default.
        rvItems.setLayoutManager(new LinearLayoutManager(this));


        // Use a listener to be notified anytime the user taps on a button. Note that btnAdd refers to the identifier for the add button created

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Get whatever is in the text field, make sure you convert it to a string cause it returns an editable.
                String todoItem = etItem.getText().toString();
                //Add item to the model or list
                items.add(todoItem);
                //Notify the adapter that an item has been inserted
                // The position where the item was inserted will be the size of the model - 1
                itemsAdapter.notifyItemInserted(items.size() - 1);
                //clear the edit text after submission
                etItem.setText("");
                //Use toast to show user the item was added
                Toast.makeText(getApplicationContext(),"Item was added", Toast.LENGTH_SHORT).show();
                saveItems();
            }
        });

    }

    //A recycler view needs a adapter which is a class we create.
    //An adapter defines a view holder (holder for the view).
    //Each item in to-do list will have it's own text view to represent it.
    //The view holder will hold on to the text view and populate data from our model into a view.
    //adapter is a Java  file

    private File getDataFile(){
        return new File(getFilesDir(), "data.txt");

    }

    //  This function will load items by reading all the links of our data.txt.
    //loadItems will only be called once when the app starts up
    private void loadItems(){
        try {
            items = new ArrayList<>(FileUtils.readLines(getDataFile(), Charset.defaultCharset()));
        } catch (IOException e) {
            //Log errors in  logcat
            //Use name of class as  a tag name
            Log.e("Main Activity", "Error reading items", e);
            items = new ArrayList<>();
        }
    }

    // This function saves items by writing them into the data file.
    // Should be called whenever a change is made to the list of items.
    private void saveItems(){
        try {
            FileUtils.writeLines(getDataFile(), items);
        } catch (IOException e) {
            Log.e("Main Activity", "Error writing items", e);
        }

    }
}