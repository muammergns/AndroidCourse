package com.gns.androidcourse.roomdatabase;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Flowable;
/** (DAO) Data Access Object
 * Burası database ile ilgili fonksiyonların interfacei
 * Database oluşturulduğunda bu fonksiyonları kullanmak zorunlu olacak
 */
@Dao
public interface PlaceDao {

    @Insert
    Completable insert(Place place);

    @Delete
    Completable delete(Place place);

    @Update
    Completable update(Place place);

    @Query("SELECT * FROM Place")
    Flowable<List<Place>> getAll();



}
