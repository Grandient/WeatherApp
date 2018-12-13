## This repository is the final project for CSCI_4100U, 2018.

## Welcome to Weather Owl 

>The people involved in this project is:
* Gavin Gosling (Grandient): 100626578
* Sinthooran Ravinathan (SinnCosTan): 100620231
* Ting Zheng (Prototype-A): 100625391

>_How to run/install this App:_

*Cloning the Project*
* On the Main Screen of Android Start by clicking `Check out project from Version Control`
   * In there select the `Git` option
   * Once it is selected it will show a prompt where you can enter the cloning url: https://github.com/Grandient/weatherapp.git and select the directory.
   * Once everything is set it will generate the Project with all the necessary build files and the clones files.

*Running the Program*
* To Run the program:
   * Have the SDK downloaded for APIs between 21 at the minimum to 28 at the maximum. 
      * Highly Recommend API 27 as it is out target SDK version
   * Make sure you have the Android x86 Emulator installed or USB debugging and drivers on your phone
   * To run the program allow the gradle build to sync
   * Once it syncs, it will index and then install the apk to run
   * Enjoy the Weather Owl App
  
  
>_Requirements to getting the App to Function_.

*APIs*
This application is to be used on Android Devices on API Level 24 or above.


>_Permissions in the Android Manifest_.

*The permissions required for this app is:* 
* INTERNET PERMISSION
* LOCATION PERMISSION

*API Keys Used*
* This app uses the OpenWeatherMap API in order to get it's data.
* The documentation can be found on this site: "https://openweathermap.org/api".


>_Activities Used in the Weather App_.

*This app's activities include:*
* Splash
* Main Menu:Shows the weather and the tempatures of a specific city based off:
   * Current: Shows the current weather
   * 5-Day: Shows the 5 Day Forecast
   * Refresh Button
* Search City
* Graphs
  * Daily: Shows the Daily Forecast
  * 3-Day: Shows the 3 Day Forecast
  * 5-Day: Shows the 5 Day Forecast
  * Show all Lines: Tap this then tap Daily, 3-Day, or 5-Day forecasts
  * Show Only Temp: Tap this then tap Daily, 3-Day, or 5-Day forecasts
  * Maximum, Minimum anf Average Temperatures
  * Humidity
* Weather Analysis
  * Scatteplot: Shows Average Temperature for each Country
  * RadarChart: Shows how the City rated on a 1-5 scale
* Preferences
  * Automatically Locate City: Uses Location Services to locate the City
  * Manage Cities: Can Delete the Cities stored in your Database
  * Temperature Unit: Changes temperature unit for user preference from Celsius to Fahrenheit
  * Theme: Can change between Light and Dark Themes
  * Weather Notification Enabler: If you want notifications enabled or disabled
    
*The libraries used in this project are:*
* org.json
* MPAndroidChart
