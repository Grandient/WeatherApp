package ca.uoit.group.weather;

public class LocationData {

    // Coordinates
    private double lon;
    private double lat;

    public LocationData(double lon, double lat, String countryCode, String cityName, int cityId) {
        this.lon = lon;
        this.lat = lat;
        this.countryCode = countryCode;
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

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
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

    private String countryCode;

    // City ID/Name
    private String cityName;
    private int cityId;


    @Override
    public String toString() {
        return "Location{" +
                "lon=" + lon +
                ", lat=" + lat +
                ", countryCode='" + countryCode + '\'' +
                ", cityName='" + cityName + '\'' +
                ", cityId=" + cityId +
                '}';
    }
}
