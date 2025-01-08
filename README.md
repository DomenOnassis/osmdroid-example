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
```xml
<org.osmdroid.views.MapView
        android:id="@+id/mapView"
        android:layout_width="match_parent"
        android:layout_height="match_parent" />
```
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

# Uporaba v aplikaciji Safe&Sound (vaja pri PORA)
![Untitled design (1)](https://github.com/user-attachments/assets/8d6c123e-86b0-4795-b89b-398096060fa6)


```kotlin
...
private fun setupMap() {
        val darkTileSource = XYTileSource(
            "DarkMatter",
            0,
            19,
            256,
            ".png",
            arrayOf("https://a.basemaps.cartocdn.com/dark_all/")
        )
        val sharedPreferences = SafeAndSoundApp.getSharedPreferences(requireContext())
        val isDarkMode = sharedPreferences.getBoolean(SafeAndSoundApp.THEME_PREF_KEY, false)

        mapView.setTileSource(if (isDarkMode) {
            darkTileSource
        } else {
            TileSourceFactory.MAPNIK
        })

        mapView.isClickable = true
        mapView.setMultiTouchControls(true)
        mapView.setBuiltInZoomControls(false)

        val eventsOverlay = MapEventsOverlay(object : MapEventsReceiver {
            override fun singleTapConfirmedHelper(p: GeoPoint): Boolean {
                Log.d("MapFragment", "Tapped Location - Lat: ${p.latitude}, Lon: ${p.longitude}")
                if (isDrawingEnabled) {
                    addPointToSafeArea(p)
                }
                return true
            }

            override fun longPressHelper(p: GeoPoint): Boolean {
                handlePolygonLongPress(p)
                return true
            }
        })
        mapView.overlays.add(0, eventsOverlay)
    }

    private fun observeSafeAreas() {
        val currentUserId = auth.currentUser?.uid ?: run {
            Log.e("MapFragment", "User is not authenticated")
            return
        }

        val ownedAreasQuery = firestore.collection("users")
            .document(currentUserId)
            .collection("safeAreas")

        val memberAreasQuery = firestore.collectionGroup("members")
            .whereEqualTo("uid", currentUserId)

        ownedAreasQuery.addSnapshotListener { snapshot, exception ->
            if (exception != null) {
                Log.e("MapFragment", "Failed to listen for owned safe areas: ${exception.message}")
                return@addSnapshotListener
            }

            if (snapshot != null) {
                mapView.overlays.removeAll { overlay -> overlay is Polygon && overlay.title == "Owned" }

                for (document in snapshot.documents) {
                    val pointsData = document.get("points") as? List<Map<String, Double>> ?: continue
                    val geoPoints = pointsData.map { point ->
                        GeoPoint(point["lat"] ?: 0.0, point["lon"] ?: 0.0)
                    }
                    addSafeAreaToMap(geoPoints, document.id, "Owned")
                }

                mapView.invalidate()
                Log.d("MapFragment", "Owned safe areas updated in real-time")
            }
        }

        memberAreasQuery.addSnapshotListener { snapshot, exception ->
            if (exception != null) {
                Log.e("MapFragment", "Failed to listen for member safe areas: ${exception.message}")
                return@addSnapshotListener
            }

            if (snapshot != null) {
                mapView.overlays.removeAll { overlay -> overlay is Polygon && overlay.title == "Member" }

                for (document in snapshot.documents) {
                    val safeAreaDoc = document.reference.parent?.parent ?: continue
                    safeAreaDoc.get().addOnSuccessListener { areaDoc ->
                        val pointsData = areaDoc.get("points") as? List<Map<String, Double>> ?: emptyList()
                        val geoPoints = pointsData.map { point ->
                            GeoPoint(point["lat"] ?: 0.0, point["lon"] ?: 0.0)
                        }
                        addSafeAreaToMap(geoPoints, areaDoc.id, "Member")
                    }
                }

                mapView.invalidate()
                Log.d("MapFragment", "Member safe areas updated in real-time")
            }
        }
    }

    private fun addSafeAreaToMap(points: List<GeoPoint>, documentId: String, type: String) {
        if (points.size < 3) return
        val polygon = Polygon().apply {
            this.points = points
            title = type
            fillPaint.color = if (type == "Owned") 0x44FF0000 else 0x440000FF
            outlinePaint.color = if (type == "Owned") 0xFFFF0000.toInt() else 0xFF0000FF.toInt()
            outlinePaint.strokeWidth = 3f
            if(type == "Owned") {
                setOnClickListener { _, _, _ ->
                    if (isEditing) {
                        if (polygonMetadata[this] != null) {
                            showRenameAreaDialog(this, documentId)
                        }
                    }
                    true
                }
            }
        }
        if(type == "Owned") {
            polygonMetadata[polygon] = documentId
            Log.d("SAFE AREA", "Owned polygon added to metadata: $documentId")
        } else {
            polygonMemberMetadata[polygon] = documentId
            Log.d("SAFE AREA", "Member polygon added to metadata: $documentId")
        }
        mapView.overlays.add(polygon)

        getArea(documentId) { area ->
            val center = getPolygonCenter(points)
            val titleMarker = Marker(mapView).apply {
                position = center
                if (area != null) {
                    title = area.name
                }
                if (area != null) {
                    snippet = getString(R.string.created_by, area.author)
                }
                icon = ResourcesCompat.getDrawable(
                    requireContext().resources,
                    R.drawable.ic_house,
                    null
                )
                setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM)
            }
            titleMarker.infoWindow = CustomInfoWindow(R.layout.marker_popup, mapView)
            titleMarker.setOnMarkerClickListener { clickedMarker, _ ->
                if (clickedMarker.infoWindow?.isOpen == true) {
                    clickedMarker.closeInfoWindow()
                } else {
                    InfoWindow.closeAllInfoWindowsOn(mapView)
                    clickedMarker.showInfoWindow()
                }
                true
            }
            mapView.overlays.add(titleMarker)
            if(type == "Owned") {
                polygonTitleMarkers[polygon] = titleMarker
            } else {
                polygonMemberTitleMarkers[polygon] = titleMarker
            }
            mapView.invalidate()
        }
    }

    private fun addPointToSafeArea(point: GeoPoint) {
        safeAreaPoints.add(point)

        val pointMarker = Marker(mapView).apply {
            position = point
            icon = ResourcesCompat.getDrawable(
                requireContext().resources,
                R.drawable.filled_circle,
                null
            )
            setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_CENTER)
        }
        safeZoneMarkers.add(pointMarker)
        mapView.overlays.add(pointMarker)
        drawSafeArea()
        mapView.invalidate()

        if (safeAreaPoints.size >= 3) {
            saveButton.visibility = View.VISIBLE
            stopDrawingButton.visibility = View.VISIBLE
        }

    }

    private fun drawSafeArea() {
        if (safeAreaPoints.size < 2) return

        if (safeAreaPolygon != null) {
            mapView.overlays.remove(safeAreaPolygon)
        }

        val pointsToDraw = ArrayList(safeAreaPoints)
        pointsToDraw.add(safeAreaPoints[0])

        safeAreaPolygon = Polygon().apply {
            points = pointsToDraw
            fillPaint.color = 0x44FF0000
            outlinePaint.color = 0xFFFF0000.toInt()
            outlinePaint.strokeWidth = 3f
        }

        mapView.overlays.add(safeAreaPolygon)
        mapView.invalidate()
    }

    private fun finalizeSafeArea() {
        saveButton.visibility = View.INVISIBLE
        if (safeAreaPoints.size < 3) {
            Log.d("MapFragment", getString(R.string.not_enough_points))
            return
        }
        Log.d("MapFragment", "Safe area finalized")
        hideMarkers()
        isDrawingEnabled = false
        deleteCurrent()
    }
    private fun handlePolygonLongPress(point: GeoPoint) {
        for (overlay in mapView.overlays) {
            if (overlay is Polygon && overlay.bounds.contains(point)) {
                val documentId = polygonMetadata[overlay]
                if (documentId != null) {
                    showDeleteSafeAreaDialog(overlay, documentId)
                    return
                }
            }
        }
    }

    private fun showFriendLocation(friendId: String) {
        val friend = friendsViewModel.friendsList.value?.find { it.uuid == friendId }

        if (friend != null) {
            val friendLocation = GeoPoint(friend.lat, friend.lon)
            Log.d("MapFragment", "Found friend's location in LiveData: $friendLocation")

            mapView.controller.animateTo(friendLocation)
            if (mapView.zoomLevelDouble < 21.5) {
                mapView.controller.setZoom(21.5)
            }
            mapView.invalidate()
        } else {
            Log.e("MapFragment", "Friend with ID $friendId not found in LiveData.")
        }
    }
    ...
    private fun refreshCurrentAreas() {
        deleteCurrent()

        polygonMetadata.forEach { mapView.overlays.remove(it.key) }
        polygonMetadata.clear()

        polygonTitleMarkers.forEach { mapView.overlays.remove(it.value) }
        polygonTitleMarkers.clear()

        mapView.invalidate()
        Log.d("MapFragment", getString(R.string.safe_area_deleted))
    }

    private fun deleteCurrent() {
        safeAreaPoints.clear()
        safeZoneMarkers.forEach { mapView.overlays.remove(it) }
        safeZoneMarkers.clear()
        if (safeAreaPolygon != null) {
            mapView.overlays.remove(safeAreaPolygon)
            safeAreaPolygon = null
        }
        mapView.invalidate()
    }
```



