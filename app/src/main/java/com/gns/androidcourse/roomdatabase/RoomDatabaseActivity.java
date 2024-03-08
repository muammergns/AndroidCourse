package com.gns.androidcourse.roomdatabase;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.gns.androidcourse.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class RoomDatabaseActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    //Database
    PlaceDatabase db;

    //Data erişim nesnesi (Data access object)
    PlaceDao placeDao;

    /** Composite Disposible
     * Veritabanı işlemlerini asenkron olarak yapmaya yarar
     * daha güvenilir ve büyük verilerde UI thread engelleneceği için kullanılması tavsiye edilir
     * ayrıca ram kullanımını kontrol eder
     * son olarak sqlitetan daha basit
     */
    CompositeDisposable compositeDisposable = new CompositeDisposable();

    //listView için listenin tutulduğu obje
    List<String> list = new ArrayList<>();

    //veritabanı dönüş listesinin tutulduğu obje
    List<Place> places;

    //liste görünüm obje
    ListView listView;

    //liste görünüm kontrolcü
    ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_room_database);

        //init listview
        listView = findViewById(R.id.listView);

        //create listview adapter
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,list);

        //adapter setting to listview
        listView.setAdapter(adapter);

        //create room database
        db = Room.databaseBuilder(getApplicationContext(),PlaceDatabase.class,"Places")
                //.allowMainThreadQueries()//veritabanını ana thread da çalışmasına izin verir. Tavsiye edilmez!!
                .build();

        //init data access object
        placeDao = db.placeDao();

        //init insert button event
        findViewById(R.id.insert).setOnClickListener(view -> {
            /** threading -> Main (UI)
             * Default (CPU Intensive)
             * IO (network, database)*/

            //create database object
            Place place = new Place(generateString(),new Random().nextDouble()*100,new Random().nextDouble()*100);

            //burası .allowMainThreadQueries() aktifken kullanılabilir. yukarıda açıklama mevcut
            //placeDao.insert(place).subscribeOn(Schedulers.io()).subscribe();

            //Composite Disposible
            compositeDisposable.add(placeDao.insert(place)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(RoomDatabaseActivity.this::getList)
            );
        });

        //init listview item long click event for update item
        listView.setOnItemLongClickListener((adapterView, view, i, l) -> {
            //create database object
            Place place = new Place(generateString(),new Random().nextDouble()*100,new Random().nextDouble()*100);

            //yeni oluşturulan place in idsini güncellenecek objenin idsine eşitle
            place.id = places.get(i).id;

            //Composite Disposible
            compositeDisposable.add(placeDao.update(place)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(RoomDatabaseActivity.this::getList)
            );
            return true;
        });

        //init listview item click event for delete item
        listView.setOnItemClickListener((adapterView, view, i, l) -> {

            //get selected place object
            Place place = places.get(i);

            //Composite Disposible
            compositeDisposable.delete(placeDao.delete(place)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(RoomDatabaseActivity.this::getList)
            );
        });

        //get all list on create
        getList();

    }

    void getList(){
        Log.d(TAG, "getList: ok");

        //Flowable Composite Disposible nasıl çalıştığını anlamak güç
        compositeDisposable.add(placeDao.getAll()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(RoomDatabaseActivity.this::handleResponse)
        );
    }

    void handleResponse(List<Place> places){
        //yeni veriler için listeyi temizle
        list.clear();
        //veritabanından gelen verileri liste için düzenle
        for(Place place : places){
            list.add(
                    place.id+": "+place.name+"\n"
                            +String.valueOf(place.latitude)+" : "
                            +String.valueOf(place.longitude)
            );
        }
        //veritabanından gelen verileri
        this.places = places;
        adapter.notifyDataSetChanged();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        compositeDisposable.clear();
    }

    String generateString(){
        int leftLimit = 97; // letter 'a'
        int rightLimit = 122; // letter 'z'
        int targetStringLength = 10;
        Random random = new Random();
        StringBuilder buffer = new StringBuilder(targetStringLength);
        for (int i = 0; i < targetStringLength; i++) {
            int randomLimitedInt = leftLimit + (int)
                    (random.nextFloat() * (rightLimit - leftLimit + 1));
            buffer.append((char) randomLimitedInt);
        }
        return buffer.toString();
    }
}