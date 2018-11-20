package ca.uoit.group.weather;

public class Location {
    // Coords
    private double lon;
    private double lat;
    // Weather Desc
    private int wID;
    private String type;
    private String desc;
    private String icon;
    // Weather Fields
    private double temp;
    private double humidity;
    private double temp_min;
    private double temp_max;
    // Other
    private double visibility;
    //Wind
    private double windSpeed;
    private double windDegree;
    // Clouds/ Rain/ Snow (Optional)
    private double cloudiness;
    // Time of calculation
    private double timeReceived;
    // System
    private String countryCode;
    private double sunrise;
    private double sunset;

    // City ID/Name
    private String cityName;
    private int cityId;


    @Override
    public String toString() {
        return "Location{" +
                "lon=" + lon +
                ", lat=" + lat +
                ", wID=" + wID +
                ", type='" + type + '\'' +
                ", desc='" + desc + '\'' +
                ", icon='" + icon + '\'' +
                ", temp=" + temp +
                ", humidity=" + humidity +
                ", temp_min=" + temp_min +
                ", temp_max=" + temp_max +
                ", visibility=" + visibility +
                ", windSpeed=" + windSpeed +
                ", windDegree=" + windDegree +
                ", cloudiness=" + cloudiness +
                ", timeReceived=" + timeReceived +
                ", countryCode='" + countryCode + '\'' +
                ", sunrise=" + sunrise +
                ", sunset=" + sunset +
                ", cityName='" + cityName + '\'' +
                ", cityId=" + cityId +
                '}';
    }

    public Location(double lon, double lat, int wID, String type, String desc, String icon, double temp, double humidity, double temp_min, double temp_max, double visibility, double windSpeed, double windDegree, double cloudiness, double timeReceived, String countryCode, double sunrise, double sunset, String cityName, int cityId) {
        this.lon = lon;
        this.lat = lat;
        this.wID = wID;
        this.type = type;
        this.desc = desc;
        this.icon = icon;
        this.temp = temp;
        this.humidity = humidity;
        this.temp_min = temp_min;
        this.temp_max = temp_max;
        this.visibility = visibility;
        this.windSpeed = windSpeed;
        this.windDegree = windDegree;
        this.cloudiness = cloudiness;
        this.timeReceived = timeReceived;
        this.countryCode = countryCode;
        this.sunrise = sunrise;
        this.sunset = sunset;
        this.cityName = cityName;
        this.cityId = cityId;
    }

    public double getLon() {
        return lon;
    }

    public void setLon(double lon) {
        this.lon = lon;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
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

    public double getTemp() {
        return temp;
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

    public double getTemp_min() {
        return temp_min;
    }

    public void setTemp_min(double temp_min) {
        this.temp_min = temp_min;
    }

    public double getTemp_max() {
        return temp_max;
    }

    public void setTemp_max(double temp_max) {
        this.temp_max = temp_max;
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

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
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

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public int getCityId() {
        return cityId;
    }

    public void setCityId(int cityId) {
        this.cityId = cityId;
    }
}



