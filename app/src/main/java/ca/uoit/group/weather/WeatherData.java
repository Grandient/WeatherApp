package ca.uoit.group.weather;

import java.io.Serializable;

public class WeatherData implements Serializable {



    // Weather Desc
    private int wID;
    private String type;
    private String desc;
    private String icon;

    // Weather Fields
    private double temp;
    private double humidity;
    private double minTemp;
    private double maxTemp;

    // Other
    private double visibility;

    //Wind
    private double windSpeed;
    private double windDegree;

    // Clouds/ Rain/ Snow (Optional)
    private double cloudiness;

    // Time of calculation
    private double timeReceived;
    private String timeUpdated;

    // System
    private double sunrise;
    private double sunset;


    public WeatherData(int wID, String type, String desc, String icon,
                       double temp, double humidity, double minTemp, double maxTemp,
                       double visibility, double windSpeed, double windDegree, double cloudiness,
                       double timeReceived, double sunrise, double sunset) {

        this.wID = wID;
        this.type = type;
        this.desc = desc;
        this.icon = icon;
        this.temp = temp;
        this.humidity = humidity;
        this.minTemp = minTemp;
        this.maxTemp = maxTemp;
        this.visibility = visibility;
        this.windSpeed = windSpeed;
        this.windDegree = windDegree;
        this.cloudiness = cloudiness;
        this.timeReceived = timeReceived;
        this.sunrise = sunrise;
        this.sunset = sunset;

    }

    public WeatherData(int wID, String type, String desc, String icon,
                       double temp, double humidity, double minTemp, double maxTemp,
                       double windSpeed, double windDegree, double cloudiness,
                       double timeReceived, String timeUpdated) {
        this.wID = wID;
        this.type = type;
        this.desc = desc;
        this.icon = icon;
        this.temp = temp;
        this.humidity = humidity;
        this.minTemp = minTemp;
        this.maxTemp = maxTemp;
        this.windSpeed = windSpeed;
        this.windDegree = windDegree;
        this.cloudiness = cloudiness;
        this.timeReceived = timeReceived;
        this.timeUpdated = timeUpdated;
    }


    public int getwID() {
        return wID;
    }

    public void setwID(int wID) {
        this.wID = wID;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public double getPreciseTemp() {
        return temp;
    }

    public String getStringTemp() {
        return String.valueOf(Math.round(temp));
    }

    public void setTemp(double temp) {
        this.temp = temp;
    }

    public double getHumidity() {
        return humidity;
    }

    public void setHumidity(double humidity) {
        this.humidity = humidity;
    }

    public double getPreciseMinTemp() {
        return minTemp;
    }

    public String getStringMinTemp() {
        return String.valueOf(Math.round(minTemp));
    }

    public void setMinTemp(double minTemp) {
        this.minTemp = minTemp;
    }

    public double getPreciseMaxTemp() {
        return maxTemp;
    }

    public String getStringMaxTemp() {
        return String.valueOf(Math.round(maxTemp));
    }

    public void setMaxTemp(double maxTemp) {
        this.maxTemp = maxTemp;
    }

    public double getVisibility() {
        return visibility;
    }

    public void setVisibility(double visibility) {
        this.visibility = visibility;
    }

    public double getWindSpeed() {
        return windSpeed;
    }

    public void setWindSpeed(double windSpeed) {
        this.windSpeed = windSpeed;
    }

    public double getWindDegree() {
        return windDegree;
    }

    public void setWindDegree(double windDegree) {
        this.windDegree = windDegree;
    }

    public double getCloudiness() {
        return cloudiness;
    }

    public void setCloudiness(double cloudiness) {
        this.cloudiness = cloudiness;
    }

    public double getTimeReceived() {
        return timeReceived;
    }

    public void setTimeReceived(double timeReceived) {
        this.timeReceived = timeReceived;
    }


    public double getSunrise() {
        return sunrise;
    }

    public void setSunrise(double sunrise) {
        this.sunrise = sunrise;
    }

    public double getSunset() {
        return sunset;
    }

    public void setSunset(double sunset) {
        this.sunset = sunset;
    }



    public String getTimeUpdated() {
        return timeUpdated;
    }

    public void setTimeUpdated(String timeUpdated) {
        this.timeUpdated = timeUpdated;
    }


    @Override
    public String toString() {
        return "Location{" +
                ", wID=" + wID +
                ", type='" + type + '\'' +
                ", desc='" + desc + '\'' +
                ", icon='" + icon + '\'' +
                ", temp=" + temp +
                ", humidity=" + humidity +
                ", temp_min=" + minTemp +
                ", temp_max=" + maxTemp +
                ", visibility=" + visibility +
                ", windSpeed=" + windSpeed +
                ", windDegree=" + windDegree +
                ", cloudiness=" + cloudiness +
                ", timeReceived=" + timeReceived +
                ", sunrise=" + sunrise +
                ", sunset=" + sunset +
                '}';
    }
}



