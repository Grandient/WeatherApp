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
* Main Menu
   * Current
   * Daily
   * 5-Day
   * Refresh Button
* Search City
* Graphs
* Weather Analysis
* Preferences
    
*The libraries used in this project are:*
* org.json
* MPAndroidChart
