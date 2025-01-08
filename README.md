# osmdroid <img src="https://github.com/user-attachments/assets/3b13c111-32a1-4e27-b55c-84a27d7910cc" alt="osmdroid logo" style="height: 1em; vertical-align: middle;">

Povezava do GitHub repozitorija: [osmdroid/osmdroid](https://github.com/osmdroid/osmdroid)

## Zakaj? 🧐
[osmdroid](https://github.com/osmdroid/osmdroid) je odprtokodna knjižnica za Android, ki omogoča uporabo zemljevidov OpenStreetMap v aplikacijah.
Ponuja funkcije, kot so prikaz zemljevidov, dodajanje markerjev, delo z offline zemljevidi in prilagajanje slojev, brez stroškov licenciranja ali omejitev uporabe.


Originalno kot alternativa Google Maps, prišlo z Android 1.0, deluje na Api Level 8+
## Prednosti ✅
- brezplačno
- enostavno
- neomejena uporaba (brez API token-a) 🙏
- prilagodljivo
  
## Slabosti ❌
- predpomniljenje ploščic lahko povzroča težave
- osmdroid ne podpira naprednih vizualnih učinkov (npr. gladko povečanje/odmik, vizualni prehodi), kot jih ponuja Mapbox ali Google Maps
- Podatki OpenStreetMap, na katerih temelji osmdroid, so odvisni od prispevkov skupnosti. Na nekaterih območjih (zlasti v manj razvitih regijah) so lahko zemljevidi manj natančni ali nepopolni v primerjavi z ostalimi rešitvami
- GitHub je arhiviran
- še odprti issue-i

## Licenca 📄
osmdroid je licenciran pod [Apache License 2.0](https://www.apache.org/licenses/LICENSE-2.0) - odprtokodna licenca, ki omogoča:
- Uporabo, spreminjanje in distribucijo programske opreme brez omejitev.
- Uporabo za osebne, izobraževalne ali komercialne namene.
- Vključitev programske opreme v zaprte (proprietarne) projekte.

## Število uporabnikov 👥
![GitHub stars](https://img.shields.io/github/stars/osmdroid/osmdroid?style=social)

![GitHub followers](https://img.shields.io/github/followers/osmdroid?style=social)

![GitHub forks](https://img.shields.io/github/forks/osmdroid/osmdroid?style=social)

## Vzdrževanje projekta 🏗️
![GitHub last commit](https://img.shields.io/github/last-commit/osmdroid/osmdroid)

![GitHub commit activity](https://img.shields.io/github/commit-activity/m/osmdroid/osmdroid)

![GitHub contributors](https://img.shields.io/github/contributors/osmdroid/osmdroid)

Repozitorij je arhiviran od Nov 20, 2024
Zadnja verzija: 6.1.20 - Aug 18, 2024

# Primer uporabe
Uporablja se MapView komponenta
```kotlin
private lateinit var mapView: MapView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        Configuration.getInstance().load(applicationContext, applicationContext.getSharedPreferences("osmdroid", MODE_PRIVATE))

        setContentView(R.layout.activity_main)

        mapView = findViewById(R.id.mapView)
        mapView.setMultiTouchControls(true)

        val mapController = mapView.controller
        mapController.setZoom(15.0)
        val ljubljana = GeoPoint(46.056946, 14.505751)
        mapController.setCenter(ljubljana)

        drawPolygon()
        addPOI()
    }
```
Točka in poligon
```kotlin
private fun drawPolygon() {
        val polygonPoints = arrayListOf(
            GeoPoint(46.056946, 14.505751),
            GeoPoint(46.057946, 14.505751),
            GeoPoint(46.057946, 14.507751),
            GeoPoint(46.056946, 14.507751),
            GeoPoint(46.056946, 14.505751)
        )

        val polygon = Polygon()
        polygon.points = polygonPoints
        polygon.fillColor = Color.argb(50, 0, 255, 0) // Semi-transparent green
        polygon.strokeColor = Color.GREEN
        polygon.strokeWidth = 5.0f

        mapView.overlayManager.add(polygon)
    }

private fun addPOI() {
        val poiLocation = GeoPoint(46.058946, 14.506751)

        val marker = Marker(mapView)
        marker.position = poiLocation
        marker.title = "Point of Interest"
        marker.snippet = "This is a POI near Ljubljana."
        marker.icon = resources.getDrawable(R.drawable.ic_user_marker, null)

        marker.setOnMarkerClickListener { _, _ ->
            Toast.makeText(this, "Marker clicked!", Toast.LENGTH_SHORT).show()
            true
        }
        marker.isDraggable = true

        mapView.overlayManager.add(marker)
    }
```
<img src="https://github.com/user-attachments/assets/f86a3659-5127-4055-8495-595a8f1e7764" alt="Screenshot of MainActivity" width="300"/>

Še pravokotnik in krog

```kotlin
private fun drawCircle() {
        val center = GeoPoint(46.058946, 14.506751) // Center of the circle
        val radius = 0.001 // Approx. 100 meters (degree-to-meter conversion)

        val circlePoints = ArrayList<GeoPoint>()
        for (i in 0..360 step 10) {
            val angle = Math.toRadians(i.toDouble())
            val lat = center.latitude + radius * Math.cos(angle)
            val lon = center.longitude + radius * Math.sin(angle)
            circlePoints.add(GeoPoint(lat, lon))
        }

        val circle = Polygon()
        circle.points = circlePoints
        circle.fillColor = Color.argb(50, 0, 0, 255) // Semi-transparent blue
        circle.strokeColor = Color.BLUE
        circle.strokeWidth = 5.0f

        mapView.overlayManager.add(circle)
    }
...
private fun drawRectangle() {
        val rectanglePoints = arrayListOf(
            GeoPoint(46.056, 14.504),
            GeoPoint(46.056, 14.506),
            GeoPoint(46.058, 14.506),
            GeoPoint(46.058, 14.504),
            GeoPoint(46.056, 14.504) // Close the rectangle
        )

        val rectangle = Polygon()
        rectangle.points = rectanglePoints
        rectangle.fillColor = Color.argb(50, 255, 255, 0) // Semi-transparent yellow
        rectangle.strokeColor = Color.YELLOW
        rectangle.strokeWidth = 5.0f

        mapView.overlayManager.add(rectangle)
    }
```

<img src="https://github.com/user-attachments/assets/ae23625a-69a2-4f88-b7cf-0f7fac0e701f" alt="Screenshot of MainActivity" width="300"/>

Druga tema
```kotlin
mapView.setTileSource(TileSourceFactory.OpenTopo)
```

<img src="https://github.com/user-attachments/assets/40ba3586-a39a-41fe-8da8-cd560ccaa528" alt="Screenshot of MainActivity" width="300"/>

Dinamičnost layer-jev

![Untitled design](https://github.com/user-attachments/assets/768fbb21-beeb-4817-8276-7d3ac02c4aad)

```kotlin
marker.setOnMarkerClickListener { _, _ ->
            Toast.makeText(this, "Marker clicked!", Toast.LENGTH_SHORT).show()
            true
        }
marker.isDraggable = true
```





