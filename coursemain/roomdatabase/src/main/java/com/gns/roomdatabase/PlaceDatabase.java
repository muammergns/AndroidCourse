package com.gns.roomdatabase;

import androidx.room.Database;
import androidx.room.RoomDatabase;

/** Databasein kendisi
 * version numarası: eğer database de bir değişiklik yapılırsa versiyon atlanmalıdır
 * tek method var. database kurulduğunda dao çağırılır (DAO) Data Access Object
 */

@Database(entities = {Place.class},version = 1)
public abstract class PlaceDatabase extends RoomDatabase {
    public abstract PlaceDao placeDao();


}
