package ca.uoit.group.weather;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import java.util.ArrayList;
import java.util.List;


public class LocationDBHelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "cities.db";
    private static final int DB_VERSION = 1;
    private static final String TABLE_NAME = "Location";
    private static final String CREATE_TABLE_SQL = "CREATE TABLE " + TABLE_NAME + " (" +
            "     city_name TEXT PRIMARY KEY," +
            "      city_id INTEGER NOT NULL," +
            "      country_code TEXT NOT NULL," +
            "      latitude DECIMAL NOT NULL," +
            "      longitude DECIMAL NOT NULL" +
            ")";

    private static final String DELETE_TABLE_SQL = "DROP TABLE IF EXISTS " + TABLE_NAME;


    public LocationDBHelper(Context context){
        super(context, DB_NAME, null, DB_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        // Create table
        db.execSQL(CREATE_TABLE_SQL);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Recreate table
        db.execSQL(DELETE_TABLE_SQL);
        onCreate(db);
    }

    public List<LocationData> getAllLocations() {
        List<LocationData> locations = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        String[] cols = {"city_name", "city_id", "country_code", "latitude", "longitude"};
        String[] whereArgs = {};

        Cursor cursor = db.query(TABLE_NAME, cols, "", whereArgs, "", "", "");

        // Iterate over all returned location tuples
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            String cityName = cursor.getString(0);
            int cityId = cursor.getInt(1);
            String countryCode = cursor.getString(2);
            double latitude = cursor.getDouble(3);
            double longitude = cursor.getDouble(4);
            locations.add(new LocationData(cityName, cityId, countryCode, latitude, longitude));

            cursor.moveToNext();
        }

        return locations;
    }

    public void insertLocation(String cityName, int cityId, String countryCode,
                              double lat, double lon) {

        String[] cols = {"city_name", "city_id", "country_code", "latitude", "longitude"};
        String where = "city_name = ? AND city_id = ? AND country_code = ? AND latitude = ? AND longitude = ?";
        String[] whereArgs = { cityName, String.valueOf(cityId), countryCode, String.valueOf(lat), String.valueOf(lon)};
        Cursor cursor = getReadableDatabase().query(TABLE_NAME, cols, where, whereArgs, "", "", "");

        if (cursor.getCount() == 0) {
            ContentValues data = new ContentValues();
            data.put("city_name", cityName);
            data.put("city_id", cityId);
            data.put("country_code", countryCode);
            data.put("latitude", lat);
            data.put("longitude", lon);
            getWritableDatabase().insert(TABLE_NAME, null, data);
        }
    }

    public void deleteLocation(String cityName) {
        String[] whereArgs = { cityName };
        getWritableDatabase().delete(TABLE_NAME, "city_name = ?", whereArgs);
    }

}
