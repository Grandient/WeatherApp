package ca.uoit.group.weather;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class WeatherDBHelper extends SQLiteOpenHelper {
    static final String TABLE = "Weather";
    static final String CREATE_DATA = "CREATE TABLE Weather (" +
            "     wID INTEGER PRIMARY KEY AUTOINCREMENT," +
            "      type STRING NOT NULL," +
            "      descri STRING NOT NULL," +
            "      icon STRING NOT NULL," +
            "      temper DECIMAL NOT NULL, " +
            "     humidity DECIMAL NOT NULL," +
            "      minTemp DECIMAL NOT NULL," +
            "      maxTemp DECIMAL NOT NULL," +
            "      visib DECIMAL NOT NULL," +
            "      windSpeed DECIMAL NOT NULL, " +
            "      windDeg DECIMAL NOT NULL," +
            "      cloudy DECIMAL NOT NULL," +
            "      timeReceived DECIMAL NOT NULL," +
            "      sunrise DECIMAL NOT NULL, " +
            "      sunset DECIMAL NOT NULL " +
            ")";

    static final String DELETE_DATA = "DROP TABLE Weather";


    public WeatherDBHelper(Context context) {
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
        ArrayList<WeatherData> weatherData = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        String[] columns = new String[] {"wID", "type", "descri", "icon", "temper", "humidity", "minTemp, maxTemp, visib, windSpeed, windDeg, cloudy, timeReceived, sunrise, sunset"};
        String where = "";
        String[] whereArgs = new String[]{};
        String groupBy = "";
        String having = "";
        String orderBy = "";

        Cursor cursor = db.query(TABLE,columns,where,whereArgs, groupBy,having,orderBy);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()){
            int wID = cursor.getInt(0);
            String type = cursor.getString(1);
            String desc = cursor.getString(2);
            String icon = cursor.getString(3);
            double temp = cursor.getDouble(4);
            double humidity = cursor.getDouble(5);
            double minTemp = cursor.getDouble(6);
            double maxTemp = cursor.getDouble(7);
            double visibility = cursor.getDouble(8);
            double windSpeed = cursor.getDouble(9);
            double windDegree = cursor.getDouble(10);
            double cloudiness = cursor.getDouble(11);
            double timeReceived = cursor.getDouble(12);
            double sunrise = cursor.getDouble(13);
            double sunset = cursor.getDouble(14);

            cursor.moveToNext();
        }
        return weatherData;
    }

    public void insertWData(int wID, String type, String desc, String icon,
                           double temp, double humidity, double minTemp, double maxTemp,
                           double visibility, double windSpeed, double windDegree, double cloudiness,
                           double timeReceived, double sunrise, double sunset){

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues data = new ContentValues();

        data.put("wID", wID);
        data.put("type", type);
        data.put("descri", desc);
        data.put("icon", icon);
        data.put("temper", temp);
        data.put("humidity", humidity);
        data.put("minTemp", minTemp);
        data.put("maxTemp", maxTemp);
        data.put("visib", visibility);
        data.put("windSpeed", windSpeed);
        data.put("windDeg", windDegree);
        data.put("cloudy", cloudiness);
        data.put("timeReceived", timeReceived);
        data.put("sunrise", sunrise);
        data.put("sunset", sunset);
        db.insert(TABLE, null, data);
    }

    public void deleteWData(int wID){
        SQLiteDatabase db = this.getWritableDatabase();

        db.delete(TABLE, "wID = ?", new String[]{"" + wID});
    }
}