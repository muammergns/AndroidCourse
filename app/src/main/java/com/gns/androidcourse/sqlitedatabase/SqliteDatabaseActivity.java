package com.gns.androidcourse.sqlitedatabase;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;


import com.gns.androidcourse.R;

import java.util.List;

public class SqliteDatabaseActivity extends AppCompatActivity {

    Button add;
    ListView listView;
    CheckBox checkBox;
    EditText name,age;
    SQLiteHelper helper;
    List<CustomerModal> list;
    ArrayAdapter<CustomerModal> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sqlite_database);

        add = findViewById(R.id.add_button);
        listView = findViewById(R.id.listView);
        checkBox = findViewById(R.id.checkBox);
        name = findViewById(R.id.editTextTextPersonName);
        age = findViewById(R.id.editTextNumber);
        helper = new SQLiteHelper(SqliteDatabaseActivity.this,"customer.db");

        listCustomer();

        add.setOnClickListener(v -> {

            helper.addOne(getCustomerModal());
            listCustomer();
        });

        listView.setOnItemClickListener((parent, view, position, id) -> {
            CustomerModal selectedModal = (CustomerModal) parent.getItemAtPosition(position);
            helper.updateOne(selectedModal,getCustomerModal());
            //helper.deleteOne(selectedModal);
            listCustomer();
        });

    }

    private CustomerModal getCustomerModal(){

        return new CustomerModal(
                -1,
                name.getText().toString(),
                getEditTextNumber(age),
                checkBox.isChecked(),
                name.getText().toString()+"a",
                getEditTextNumber(age)+1,
                checkBox.isChecked()
        );
    }

    int getEditTextNumber(EditText et){
        try {
            return Integer.parseInt(et.getText().toString());
        }catch (Exception e){
            return 0;
        }
    }

    void listCustomer(){
        list = helper.getEveryone();
        adapter = new ArrayAdapter<>(SqliteDatabaseActivity.this, android.R.layout.simple_list_item_1, list);
        listView.setAdapter(adapter);
    }

}