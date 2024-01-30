package com.example.weather;

import org.json.JSONException;
import org.json.JSONObject;

public class weatherData {

    private String Temperature, weatherState, NameofCity, icon;
    private int Condition;

    public static weatherData fromJson(JSONObject jsonObject) {
     try {
         weatherData weatherD = new weatherData();
         weatherD.NameofCity = jsonObject.getString("name");
         weatherD.Temperature = jsonObject.getJSONObject("main").getString("temp");
         weatherD.Condition = jsonObject.getJSONArray("weather").getJSONObject(0).getInt("id");
         weatherD.weatherState = jsonObject.getJSONArray("weather").getJSONObject(0).getString("main");
         weatherD.icon = jsonObject.getJSONArray("weather").getJSONObject(0).getString("icon");
         double tempResult = jsonObject.getJSONObject("main").getDouble("temp") - 273.15;
         int roundedValue = 0;
         weatherD.Temperature=Integer.toString(roundedValue);
         return weatherD;
     }

     catch (JSONException e) {
         e.printStackTrace();
         return null;
     }

    }

    private static String updateWeatherIcon(int condition) {
        if (condition>=0&&condition<=300) {
            return "thunderstorm";
        }
        else if (condition>=300&&condition<=500) {
            return "light-rain";
        }
        else if (condition>=500&&condition<=600) {
            return "shower3";
        }
        else if (condition>=600&&condition<=700) {
            return "snow4";
        }
        else if (condition>=701&&condition<=771) {
            return "fog";
        }
        else if (condition>=772&&condition<=800) {
            return "overcast";
        }
        else if (condition==800) {
            return "sunny";
    }

        if (condition>=905&&condition<=1000) {
            return "cloudy";
        }

        return "dunno";
}

    public String getTemperature() {
        return Temperature+"°C";
    }

    public String getWeatherState() {
        return weatherState;
    }

    public String getNameofCity() {
        return NameofCity;
    }

    public String getIcon() {
        return icon;
    }
}
