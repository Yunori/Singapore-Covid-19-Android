# Singapore Covid-19 Tracker

## Update 05/05/2020
The app now uses this file for the list activity: https://raw.githubusercontent.com/wentjun/covid-19-sg/master/src/data/covid-sg.json
This file provides details for cases 1 to 6588.

## Update 26/04/2020
Note: The "ListActivity" does not works anymore because the governement closed the API on Saturday 25 April.
You can check the API status at this URL: https://experience.arcgis.com/experience/7e30edc490a5441a874f9efe67bd8b89
Same for the MapActivity, all the infected postions added after the 25 April will be set to the defaut value (around the center of SG). So they don't show the real location of the new infections.

### Presentation

Singapore COVID Tracker is an android application that allows users to follow the situation of the infection in Singapore, thanks to various sources.
I use Volley to gather JSON files:
-	https://github.com/NovelCOVID/API
This API provides the most up to date main stats about the decease.

-	https://services6.arcgis.com/LZwBmoXba0zrRap7/
This is the official (and hidden…) Singapore’s government data. This data is often 1 or 2 days late for some reason (I think it takes them a lot of time to register all the patients’ details on the DB), but it provides detailed data about the infected people.
Please note that this API is often down. I detected that they have a second production server, so I use it in case the first one is down. I have not got any problems since I added this fix, I hope that at least one of the two servers will work when you test it.

-	https://github.com/wentjun/covid-19-sg
This project compiles data from Channel NewsAsia and Gov.sg using the scrapping method. It provides infected locations and clusters zones.
Same as the government API, this data might be 1 or 2 days late.

### Activities
<div style="text-align:center">
<h4>MainActivity</h4>
<img src="https://github.com/Yunori/Singapore-Covid-19-Android/blob/master/screenshots/MainActivity.jpg?raw=true" width="200" height="400" />
<p>This activity shows the main stats. Below the title, you can see the date of the last update of the data.
This activity will check if you have the permissions and an active internet connection before showing the data.</p>
<h4>MapActivity</h4>
<img src="https://github.com/Yunori/Singapore-Covid-19-Android/blob/master/screenshots/MapActivity.jpg?raw=true" width="200" height="400" />
<p>This activity shows the detailed data about the infected. I used a recycler view to build this view.</p>
<h4>ListActivity</h4>
<img src="https://github.com/Yunori/Singapore-Covid-19-Android/blob/master/screenshots/ListActivity.jpg?raw=true" width="200" height="400" />
<p>This activity uses OpenStreetMap and Mapbox. I draw a colored circle for the group of infected and polygons for the infection clusters (e.g., Changi airport).
When you zoom on the map, the group of infected splits into smaller parts. When the group is disbanded, I draw a small red circle to show the original position of the infected person. Some people are infected at the same location, so the group will not be disbanded even if you zoom at the maximum.</p>
</div>
