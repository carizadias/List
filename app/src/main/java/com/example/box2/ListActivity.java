package com.example.box2;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class ListActivity extends AppCompatActivity {

    private ArrayList<String> userData;
    private ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        ListView listView = findViewById(R.id.listView);

        userData = new ArrayList<>();

        // Recebe os dados da MainActivity
        Intent intent = getIntent();
        String email = intent.getStringExtra("EMAIL");
        String password = intent.getStringExtra("PASSWORD");

        if (email != null && password != null) {
            String userDetails = "Email: " + email + ", Password: " + password;
            userData.add(userDetails);
        }

        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, userData);
        listView.setAdapter(adapter);

        // Adiciona um ouvinte de clique aos itens da lista para mostrar as opções
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                showOptionsDialog(position);
            }
        });
    }


    private void showOptionsDialog(final int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Choose an action")
                .setItems(new String[]{"Edit", "Delete"}, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case 0:
                                // Edit option clicked, open MainActivity to edit the data
                                String selectedData = userData.get(position);
                                String[] parts = selectedData.split(", Password: ");
                                String email = parts[0].substring(7); // Extracting the email
                                String password = parts[1]; // Extracting the password

                                Intent intent = new Intent(ListActivity.this, MainActivity.class);
                                intent.putExtra("EDIT_EMAIL", email);
                                intent.putExtra("EDIT_PASSWORD", password);
                                startActivity(intent);
                                break;
                            case 1:
                                // Delete option clicked, remove the selected item
                                userData.remove(position);
                                adapter.notifyDataSetChanged();
                                break;
                        }
                    }
                });

        AlertDialog dialog = builder.create();
        dialog.show();
    }
}
