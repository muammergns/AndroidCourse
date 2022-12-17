package com.gns.sqlitedatabase;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;

import java.util.List;


public class MainActivity extends AppCompatActivity {

    Button add,viewall;
    ListView listView;
    CheckBox checkBox;
    EditText name,age;
    SQLiteHelper helper;
    List<CustomerModal> list;
    ArrayAdapter<CustomerModal> adapter;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        add = findViewById(R.id.button);
        viewall = findViewById(R.id.button2);
        listView = findViewById(R.id.listView);
        checkBox = findViewById(R.id.checkBox);
        name = findViewById(R.id.editTextTextPersonName);
        age = findViewById(R.id.editTextNumber);
        helper = new SQLiteHelper(MainActivity.this,"customer.db");

        listCustomer();

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                helper.addOne(getCustomerModal());
                listCustomer();
            }
        });
        viewall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listCustomer();
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                CustomerModal selectedModal = (CustomerModal) parent.getItemAtPosition(position);
                helper.updateOne(selectedModal,getCustomerModal());
                //helper.deleteOne(selectedModal);
                listCustomer();
            }
        });

    }

    private CustomerModal getCustomerModal(){
        return new CustomerModal(
                -1,
                name.getText().toString(),
                Integer.parseInt(age.getText().toString()),
                checkBox.isChecked(),
                name.getText().toString()+"a",
                Integer.parseInt(age.getText().toString())+1,
                checkBox.isChecked()
        );
    }

    void listCustomer(){
        list = helper.getEveryone();
        adapter = new ArrayAdapter<>(MainActivity.this, android.R.layout.simple_list_item_1, list);
        listView.setAdapter(adapter);
    }


}