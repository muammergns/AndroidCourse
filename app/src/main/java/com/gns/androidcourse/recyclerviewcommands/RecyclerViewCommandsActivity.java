package com.gns.androidcourse.recyclerviewcommands;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import com.gns.androidcourse.R;
import com.gns.androidcourse.databinding.ActivityRecyclerViewCommandsBinding;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class RecyclerViewCommandsActivity extends AppCompatActivity {

    ActivityRecyclerViewCommandsBinding binding;
    ListRecycler recyclerAdapter;
    List<ListModal> list = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //init binding
        binding = ActivityRecyclerViewCommandsBinding.inflate(getLayoutInflater());
        //bir görünüm oluştur
        View mainView = binding.getRoot();
        //bu görünümü aktiviteye bağla
        setContentView(mainView);

        recyclerAdapter = new ListRecycler(list,binding);
        //binding.recyclerView.setLayoutManager(new LinearLayoutManager(this));
        binding.recyclerView.setAdapter(recyclerAdapter);

        //this will use send from add button for information
        //Snackbar.make(view,"My Own Action",Snackbar.LENGTH_LONG).setAction("Action",null).show();

        for (int i=0;i<20;i++){
            list.add(0, new ListModal(getRandomString(12)));
            recyclerAdapter.notifyItemInserted(0);
            recyclerAdapter.notifyItemRangeChanged(0,list.size());
        }
        binding.recyclerView.scrollToPosition(0);
        binding.addButton.setOnClickListener(view -> {
            list.add(0, new ListModal(binding.editTextTextPersonName.getText().toString()));
            recyclerAdapter.notifyItemInserted(0);
            recyclerAdapter.notifyItemRangeChanged(0,list.size());
            binding.recyclerView.scrollToPosition(0);
        });
    }

    private static final String ALLOWED_CHARACTERS ="0123456789qwertyuiopasdfghjklzxcvbnm";

    private static String getRandomString(final int sizeOfRandomString)
    {
        final Random random=new Random();
        final StringBuilder sb=new StringBuilder(sizeOfRandomString);
        for(int i=0;i<sizeOfRandomString;++i)
            sb.append(ALLOWED_CHARACTERS.charAt(random.nextInt(ALLOWED_CHARACTERS.length())));
        return sb.toString();
    }

}