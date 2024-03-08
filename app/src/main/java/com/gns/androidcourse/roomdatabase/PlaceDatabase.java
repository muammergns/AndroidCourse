package com.gns.androidcourse.roomdatabase;

import androidx.room.Database;
import androidx.room.RoomDatabase;

/** Databasein kendisi
 * version numarası: eğer database de bir değişiklik yapılırsa versiyon atlanmalıdır
 * tek method var. database kurulduğunda dao çağırılır (DAO) Data Access Object
 * /
 * \AndroidCourse\coursemain\roomdatabase\src\main\java\com\gns\roomdatabase\PlaceDatabase.java:12:
 * warning: Schema export directory is not provided to the annotation processor so we cannot export the schema.
 * You can either provide `room.schemaLocation` annotation processor argument OR set exportSchema to false.
 * public abstract class PlaceDatabase extends RoomDatabase {
 *                 ^
 * /
 * böyle bir uyarı var kontrol edilecek
 * Database(entities = {Place.class},version = 1,)
 * Database(entities = {Place.class},version = 1, exportSchema = false)
 * exportSchema = false eklenerek uyarı sorunu çözüldü
 */

@Database(entities = {Place.class},version = 1, exportSchema = false)
public abstract class PlaceDatabase extends RoomDatabase {
    public abstract PlaceDao placeDao();


}
