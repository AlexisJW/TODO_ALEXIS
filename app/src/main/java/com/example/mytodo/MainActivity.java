package com.example.mytodo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ArrayList<String> items;
    ArrayAdapter<String> itemsAdapter;
    ListView lvItems;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        readItems();
        itemsAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, items);
        lvItems = (ListView) findViewById(R.id.idListView);
        lvItems.setAdapter(itemsAdapter);

        //Enregistrement de donnees
//        items.add("Premiere");
//        items.add("Deuxieme");
//        items.add("Troisieme");
        deleteItem();
    }

    public void AddItem(View v){
        EditText newItem = (EditText) findViewById(R.id.IdeditText);
        String itemText = newItem.getText().toString();
        if(!(itemText.isEmpty())){
            itemsAdapter.add(itemText);
            Toast.makeText(getApplicationContext(), "Item added to list", Toast.LENGTH_SHORT).show();
        }else {
            Toast.makeText(getApplicationContext(), "The Item cannot empty! please insert a item!!!", Toast.LENGTH_SHORT).show();
        }
        newItem.setText("");
        writeItems();
    }

    private void deleteItem(){
        Log.i("MainActivity", "******* Setting up Listener");
        lvItems.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                Log.i("MainActivity", "************ Item removed "+position);
                items.remove(position);
                itemsAdapter.notifyDataSetChanged();
                writeItems();
                Toast.makeText(getApplicationContext(), "Item deleted to list", Toast.LENGTH_SHORT).show();
                return true;
            }
        });
    }


    private File getDataFile(){
        return new File(getFilesDir(), "fichierTode.txt");
    }

    private void readItems(){
        try {
            items = new ArrayList<>(FileUtils.readLines(getDataFile(), Charset.defaultCharset()));
        } catch (IOException e) {
            Log.e("MainActivity", "**************  Erreur pour lire le fichier!!!  ***************", e);
            items = new ArrayList<>();
        }
    }

    private void writeItems(){
        try {
            FileUtils.writeLines(getDataFile(), items);
        } catch (IOException e) {
            Log.e("MainActivity", "**************  Erreur pour ecrire dans le fichier!!!  ***************", e);
        }
    }

}
