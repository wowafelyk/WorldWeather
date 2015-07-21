package com.fenix.worldweather;

import android.graphics.Bitmap;

/**
 * Saves weather data
 * Created by fenix on 21.07.2015.
 */
public class WeatherData {
    Coord coord = new Coord();
    Weather weather = new Weather();
    Main main = new Main();
    Wind wind = new Wind();
    Sys sys = new Sys();
    private Bitmap image;       //image id
    private String base;        //internal parameter
    private String clouds;      //cloudiness,%
    private String rain=null;        //volume at last 3 hour
    private String snow;        //volume at last 3 hour
    private Long data;          //time of calculation
    private Integer cityID;
    private String cityName;
    private Integer cod;


    class Coord {
        private Float lon;
        private Float lat;

        public Float getLon() {
            return lon;
        }

        public void setLon(Float lon) {
            this.lon = lon;
        }

        public Float getLat() {
            return lat;
        }

        public void setLat(Float lat) {
            this.lat = lat;
        }
    }

    class Weather {
        private Integer id;
        private String main;
        private String description;
        private String icon;

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public String getMain() {
            return main;
        }

        public void setMain(String main) {
            this.main = main;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getIcon() {
            return icon;
        }

        public void setIcon(String icon) {
            this.icon = icon;
        }
    }

    class Main {
        private Float temp;
        private Float pressure;
        private Integer humidity;
        private Float temp_min;
        private Float temp_max;
        private Float sea_level;    //atmospheric pressure on the sea level
        private Float grnd_level;

        public Float getTemp() {
            return temp;
        }

        public void setTemp(Float temp) {
            this.temp = temp;
        }

        public Float getPressure() {
            return pressure;
        }

        public void setPressure(Float pressure) {
            this.pressure = pressure;
        }

        public Integer getHumidity() {
            return humidity;
        }

        public void setHumidity(Integer humidity) {
            this.humidity = humidity;
        }

        public Float getTemp_min() {
            return temp_min;
        }

        public void setTemp_min(Float temp_min) {
            this.temp_min = temp_min;
        }

        public Float getTemp_max() {
            return temp_max;
        }

        public void setTemp_max(Float temp_max) {
            this.temp_max = temp_max;
        }

        public Float getSea_level() {
            return sea_level;
        }

        public void setSea_level(Float sea_level) {
            this.sea_level = sea_level;
        }

        public Float getGrnd_level() {
            return grnd_level;
        }

        public void setGrnd_level(Float grnd_level) {
            this.grnd_level = grnd_level;
        }
    }

    class Wind {
        private Float speed;
        private Float direction;

        public Float getSpeed() {
            return speed;
        }

        public void setSpeed(Float speed) {
            this.speed = speed;
        }

        public Float getDirection() {
            return direction;
        }

        public void setDirection(Float direction) {
            this.direction = direction;
        }
    }

    class Sys {
        private String type;
        private Integer id;
        private String message;
        private String county;
        private Long sunrise;
        private Long sunset;

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        public String getCounty() {
            return county;
        }

        public void setCounty(String county) {
            this.county = county;
        }

        public Long getSunrise() {
            return sunrise;
        }

        public void setSunrise(Long sunrise) {
            this.sunrise = sunrise;
        }

        public Long getSunset() {
            return sunset;
        }

        public void setSunset(Long sunset) {
            this.sunset = sunset;
        }
    }

    public Coord getCoord() {
        return coord;
    }

    public void setCoord(Coord coord) {
        this.coord = coord;
    }

    public Weather getWeather() {
        return weather;
    }

    public void setWeather(Weather weather) {
        this.weather = weather;
    }

    public Main getMain() {
        return main;
    }

    public void setMain(Main main) {
        this.main = main;
    }

    public Bitmap getImage() {
        return image;
    }

    public void setImage(Bitmap image) {
        this.image = image;
    }

    public String getBase() {
        return base;
    }

    public void setBase(String base) {
        this.base = base;
    }

    public String getClouds() {
        return clouds;
    }

    public void setClouds(String clouds) {
        this.clouds = clouds;
    }

    public String getRain() {
        return rain;
    }

    public void setRain(String rain) {
        this.rain = rain;
    }

    public String getSnow() {
        return snow;
    }

    public void setSnow(String snow) {
        this.snow = snow;
    }

    public Long getData() {
        return data;
    }

    public void setData(Long data) {
        this.data = data;
    }

    public Integer getCityID() {
        return cityID;
    }

    public void setCityID(Integer cityID) {
        this.cityID = cityID;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public Integer getCod() {
        return cod;
    }

    public void setCod(Integer cod) {
        this.cod = cod;
    }

    public Wind getWind() {
        return wind;
    }

    public void setWind(Wind wind) {
        this.wind = wind;
    }

    public Sys getSys() {
        return sys;
    }

    public void setSys(Sys sys) {
        this.sys = sys;
    }

}
