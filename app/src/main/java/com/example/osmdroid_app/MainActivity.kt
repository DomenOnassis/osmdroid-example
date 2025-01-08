package com.example.osmdroid_app

import android.graphics.Color
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import org.osmdroid.config.Configuration
import org.osmdroid.tileprovider.tilesource.TileSourceFactory
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.MapView
import org.osmdroid.views.overlay.Marker
import org.osmdroid.views.overlay.Polygon

class MainActivity : AppCompatActivity() {

    private lateinit var mapView: MapView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        Configuration.getInstance().load(applicationContext, applicationContext.getSharedPreferences("osmdroid", MODE_PRIVATE))

        setContentView(R.layout.activity_main)

        mapView = findViewById(R.id.mapView)
        mapView.setMultiTouchControls(true)
        mapView.setTileSource(TileSourceFactory.MAPNIK)
        //mapView.setTileSource(TileSourceFactory.OpenTopo)

        val mapController = mapView.controller
        mapController.setZoom(15.0)
        val ljubljana = GeoPoint(46.056946, 14.505751)
        mapController.setCenter(ljubljana)

        drawPolygon()
        drawCircle()
        drawRectangle()
        addPOI()
    }

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

    override fun onResume() {
        super.onResume()
        mapView.onResume()
    }

    override fun onPause() {
        super.onPause()
        mapView.onPause()
    }
}
