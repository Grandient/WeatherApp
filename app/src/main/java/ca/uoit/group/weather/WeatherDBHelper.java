package ca.uoit.group.weather;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import java.util.ArrayList;

import androidx.annotation.Nullable;

public class WeatherDBHelper extends SQLiteOpenHelper {
    static final String TABLE = "Location";
    static final String CREATE_DATA = "CREATE TABLE Location (" +
            "     city_name STRING PRIMARY KEY," +
            "      city_id INTEGER NOT NULL," +
            "      country_code STRING NOT NULL," +
            "      latitude DECIMAL NOT NULL," +
            "      longitude DECIMAL NOT NULL" +
            ")";

    static final String DELETE_DATA = "DROP TABLE Location";


    public WeatherDBHelper(Context context){
        super(context, TABLE, null, 1);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_DATA);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(DELETE_DATA);
        db.execSQL(CREATE_DATA);

    }

    public ArrayList<WeatherData> findWeather(){
        ArrayList<WeatherData> weather = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        String[] columns = new String[] {"city_name", "city_id", "country_code", "latitude", "longitude"};
        String where = "";
        String[] whereArgs = new String[]{};
        String groupBy = "";
        String having = "";
        String orderBy = "";

        Cursor cursor = db.query(TABLE,columns,where,whereArgs, groupBy,having,orderBy);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()){
            String city_name = cursor.getString(0);
            int city_id = cursor.getInt(1);
            String country_code = cursor.getString(2);
            double latitude = cursor.getDouble(3);
            double longitude = cursor.getDouble(4);

            cursor.moveToNext();
        }
        return weather;
    }

    public void insertData(String city_name, int city_id, String country_code, double latitude, double longitude){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues data = new ContentValues();

        data.put("city_name", city_name);
        data.put("city_id", city_id);
        data.put("country_code", country_code);
        data.put("latitude", latitude);
        data.put("longitude", longitude);
        db.insert(TABLE, null, data);
    }

    public void deleteData(String city_name){
        SQLiteDatabase db = this.getWritableDatabase();

        db.delete(TABLE, "city_name = ?", new String[]{"" + city_name});
    }

}
