# EarthQuakeMap
Project was realized as a part of my Objected Oriented Programming in Java course on Coursera. Thus, in result some parts of the code were made by the University of California San Diego. The main goal of the
project was to present the data about the latest earthquakes (obtained up to date from the USGS website) on the map.
For realizing the project, I used the Unfoldingmaps libraries which incorporate with Processing 2 libraries.
Moreover I used Java 8 JDK.
# 1. Basic logic 🗺
The main application window consists of 2 parts- the map and the key which describes marker on the map. Markers indicates the locations of the earthquakes and can be
devided into several categories
1. Based  on **magnitude** of the earthquake
- `high` - indicated by red markers, earthquakes with magnitude >5
- `medium` - indicated by orange markers, earthquakes with magnitude (4,5]
- `low` - indicate by yellow marker, earthquakes with magnitude <4
2. Based on **type** of the earthquake
- `land earthquakes` - happend on the land
- `ocean earthquakes` - happend on the water
3. **Cities** - indicates where are some of the cities (where may be potentially more damage)
4. **Cross** - indicates earthquakes that happend less than an hour ago
As an additional feature, I made all markers semi-transparent so that when they ocerlap, one can easily see that.

# 2. Basic functionalities 📄
- when user hover over the marker, the basic information appears (about either city or earthquake)
- when user clicks on the city, all markers disapear besides the earthquakes that happened in the range that could create potential damage for the city
- when user clicks on earthquake, all markers dispaear besides the nearby cities (for reason as above)
- when user clicks again all markers appear
- method for sorting data (earthquake classes have implemented the Comparable interface) and displaying a certain amount of rows
- method for displaying informations about earthquakes in each country and the number of earthquakes in the ocean
- map is scalable and has default events implemented

# 3. Additional features 🎈
## 3.1 Interactive Key of the map 🙌
As interactivity with the user was my priority during this project, I decided to additionaly implement new class hierarchy in new package which will allow user to interact
with bullets in Key of the map :) 
### 3.1.1 How it works? 
When user clicks on any type of bullet, the coresponding to it markers will disapear from the map. User can click on many bullets so that many kind of markers hide. 
When user clicks on the bullet again, the corresponding markers will appear. 
## 3.2 Setting window parameters 🖥
The visual aspect of my project was as important for me as the interactivity. I put a little setFrame() method in the main class so that one can easily find and add adjustments
to the window frame. 
