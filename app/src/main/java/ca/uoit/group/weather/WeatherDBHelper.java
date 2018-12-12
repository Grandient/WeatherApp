package ca.uoit.group.weather;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class WeatherDBHelper extends SQLiteOpenHelper {
    static final String TABLE = "Weather";

    private static final String DB_NAME = "weather.db";
    private static final int DB_VERSION = 1;
    private static final String TABLE_NAME = "Weather";

    static final String CREATE_TABLE_SQL = "CREATE TABLE " + TABLE_NAME + "(" +
            "     timeReceived DECIMAL PRIMARY KEY," +
            "     wID INTEGER NOT NULL," +
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
            "      sunrise DECIMAL NOT NULL, " +
            "      sunset DECIMAL NOT NULL " +
            ")";


    private static final String DELETE_TABLE_SQL = "DROP TABLE IF EXISTS " + TABLE_NAME;

    public WeatherDBHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_SQL);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(DELETE_TABLE_SQL);
        onCreate(db);
    }

    public List<WeatherData> getAllWeather(){
        List<WeatherData> weatherData = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        String[] columns = new String[] {"timeReceived" ,"wID", "type", "descri", "icon", "temper", "humidity", "minTemp, maxTemp, visib, windSpeed, windDeg, cloudy, sunrise, sunset"};
        String[] whereArgs = new String[]{};


        Cursor cursor = db.query(TABLE,columns,"",whereArgs, "","","");

        cursor.moveToFirst();
        while (!cursor.isAfterLast()){
            double timeReceived = cursor.getDouble(0);
            int wID = cursor.getInt(1);
            String type = cursor.getString(2);
            String desc = cursor.getString(3);
            String icon = cursor.getString(4);
            double temp = cursor.getDouble(5);
            double humidity = cursor.getDouble(6);
            double minTemp = cursor.getDouble(7);
            double maxTemp = cursor.getDouble(8);
            double visibility = cursor.getDouble(9);
            double windSpeed = cursor.getDouble(10);
            double windDegree = cursor.getDouble(11);
            double cloudiness = cursor.getDouble(12);
            double sunrise = cursor.getDouble(13);
            double sunset = cursor.getDouble(14);
            weatherData.add(new WeatherData(timeReceived, wID, type, desc, icon, temp, humidity, minTemp, maxTemp, visibility, windSpeed, windDegree, cloudiness, sunrise, sunset));

            cursor.moveToNext();
        }
        return weatherData;
    }

    public void insertWeather(double timeReceived, int wID, String type, String desc, String icon, double temp, double humidity, double minTemp, double maxTemp, double visibility, double windSpeed, double windDegree, double cloudiness, double sunrise, double sunset) {

        String[] cols = {"timeReceived", "wID", "type", "descri", "icon", "temper", "humidity", "minTemp", "maxTemp", "visib", "windSpeed", "windDeg", "cloudy", "sunrise", "sunset"};
        String where = "timeReceived = ? AND wID = ? AND type = ? AND descri = ? AND icon = ? AND temper = ? AND humidity = ? AND minTemp = ? AND maxTemp = ? AND visib = ? AND windSpeed = ? AND windDeg = ? AND cloudy = ? AND sunrise = ? AND sunset = ?";
        String[] whereArgs = { String.valueOf(timeReceived), String.valueOf(wID), type, desc, icon, String.valueOf(temp), String.valueOf(humidity), String.valueOf(minTemp), String.valueOf(maxTemp), String.valueOf(visibility), String.valueOf(windSpeed), String.valueOf(windDegree), String.valueOf(cloudiness), String.valueOf(sunrise), String.valueOf(sunset)};
        Cursor cursor = getReadableDatabase().query(TABLE_NAME, cols, where, whereArgs, "", "", "");

        if (cursor.getCount() == 0) {
            ContentValues data = new ContentValues();
            data.put("timeReceived", timeReceived);
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
            data.put("sunrise", sunrise);
            data.put("sunset", sunset);
            getWritableDatabase().insert(TABLE_NAME, null, data);
        }
    }


    public void deleteWData(int timeReceived){
        String[] whereArgs ={String.valueOf(timeReceived)};
        getWritableDatabase().delete(TABLE_NAME, "timeReceived = ?", whereArgs);
    }
}