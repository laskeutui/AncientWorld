package com.example.ancientworld;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

/**Coded by: Jeff Murphy */
public class MapsActivity extends FragmentActivity implements OnMapReadyCallback, GoogleMap.OnMarkerClickListener, GoogleMap.OnInfoWindowClickListener {

    //wall of declarations
    private GoogleMap mMap;
    private Marker egyptMarker, romeMarker, greeceMarker, aztecMarker, incaMarker, mayanMarker, japanMarker, chinaMarker, indiaMarker, norseMarker, nativeAmericanMarker;
    private SeekBar seekYear;
    private TextView tvYear;
    private Button btnHelp;

    //location of markers
    private final LatLng egyptLocation = new LatLng(28, 31);
    private final LatLng romeLocation = new LatLng(42, 12);
    private final LatLng greeceLocation = new LatLng(39, 21.8);
    private final LatLng aztecLocation = new LatLng(19.7, -98.9);
    private final LatLng incaLocation = new LatLng(-13.5, -72);
    private final LatLng mayanLocation = new LatLng(17.2, -89.6);
    private final LatLng japanLocation = new LatLng(36.2, 138.3);
    private final LatLng chinaLocation = new LatLng(35.9, 104.2);
    private final LatLng indiaLocation = new LatLng(20.6, 79);
    private final LatLng norseLocation = new LatLng(60.5, 8.5);
    private final LatLng nativeAmericanLocation = new LatLng(37.1, -95.7);

    //range of civilizations in years
    private final Range egyptRange = new Range(-3000, 641);
    private final Range romeRange = new Range(-27, 286);
    private final Range greeceRange = new Range(-1200, 146);
    private final Range aztecRange = new Range(1427, 1524);
    private final Range incaRange = new Range(1438, 1533);
    private final Range mayanRange = new Range(-300, 1500);
    private final Range japanRange = new Range(-400, 1185);
    private final Range chinaRange = new Range(-1600, 221);
    private final Range indiaRange = new Range(-1500, 185);
    private final Range norseRange = new Range(800, 1100);
    private final Range nativeAmericanRange = new Range(-500, 1800);

    /**
     * class that creates a type Range
     * from: https://stackoverflow.com/questions/7721332/how-can-i-represent-a-range-in-java
     * */
    public class Range
    {
        private int low;
        private int high;

        /**
         * sets the range of the culture
         * @param low - start of the culture as year (negative for BCE, positive for CE)
         * @param high - end of the culture as year (negative for BCE, positive for CE)
         */
        public Range(int low, int high){
            this.low = low;
            this.high = high;
        }

        /**
         * returns if a value (in this case year) is within a range
         * @param number - year to be tested
         * @return if year is within range
         */
        public boolean contains(int number){
            return (number >= low && number <= high);
        }
    }
//---------------------UNUSED/UNFINISHED-----------------------------------------------------------
    public class MarkerData {
        LatLng location;
        String title;
        String snippet;
        Drawable icon;

        public LatLng getLocation() {
            return location;
        }

        public void setLocation(LatLng location) {
            this.location = location;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getSnippet() {
            return snippet;
        }

        public void setSnippet(String snippet) {
            this.snippet = snippet;
        }

        public Drawable getIcon(Drawable icon) {
            return icon;
        }

        public void setIcon(Drawable icon) {
            this.icon = icon;
        }
    }
//--------------------------------------------------------------------------------------------------
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.setOnMarkerClickListener(this);
        mMap.setOnInfoWindowClickListener(this);
        seekYear = findViewById(R.id.seekYear);
        tvYear = findViewById(R.id.tvYear);
        btnHelp = findViewById(R.id.btnHelp);
        btnHelp.setOnClickListener(view -> startActivity(new Intent(getApplicationContext(), HelpActivity.class)));

        setMarkers();
        setSeekBarListeners();
        updateYear();
    }

    private void setSeekBarListeners() {
        SeekBar.OnSeekBarChangeListener listener = new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                updateYear();
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        };
        seekYear.setOnSeekBarChangeListener(listener);
    }
    /*
    public void setMarker(String name, LatLng location, String title, String snippet, int icon) {
                mMap.addMarker(new MarkerOptions()
                .position(location)
                .title(title)
                .snippet(snippet)
                .icon(BitmapDescriptorFactory.fromResource(icon)) //doesnt work
        );
    }
    */
    /**
     * sets the map markers with their options
     * icons from: https://www.flaticon.com/
     */
    public void setMarkers() {

        egyptMarker = mMap.addMarker(new MarkerOptions()
                .position(egyptLocation)
                .title("Ancient Egypt")
                .snippet("3150BCE - 641CE")
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.egypt)) //64px
        );

        romeMarker = mMap.addMarker(new MarkerOptions()
                .position(romeLocation)
                .title("Ancient Rome")
                .snippet("27BCE - 286CE")
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.rome))
        );

        greeceMarker = mMap.addMarker(new MarkerOptions()
                .position(greeceLocation)
                .title("Ancient Greece")
                .snippet("1200BCE - 146BCE")
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.greece))
        );

        aztecMarker = mMap.addMarker(new MarkerOptions()
                .position(aztecLocation)
                .title("Aztec Empire")
                .snippet("Teotihuacán, 1427CE - 1524CE")
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.aztec))
        );

        incaMarker = mMap.addMarker(new MarkerOptions()
                .position(incaLocation)
                .title("Inca Empire")
                .snippet("Cusco, 1438CE - 1533CE")
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.inca))
        );

        mayanMarker = mMap.addMarker(new MarkerOptions()
                .position(mayanLocation)
                .title("Mayan Empire")
                .snippet("Tikal, 300BCE - 1500CE")
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.mayan))
        );

        japanMarker = mMap.addMarker(new MarkerOptions()
                .position(japanLocation)
                .title("Ancient Japan")
                .snippet("400BCE - 1185CE")
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.japan))
        );

        chinaMarker = mMap.addMarker(new MarkerOptions()
                .position(chinaLocation)
                .title("Ancient China")
                .snippet("1600BCE - 221BC")
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.china))
        );
        indiaMarker = mMap.addMarker(new MarkerOptions()
                .position(indiaLocation)
                .title("Ancient India")
                .snippet("1500BCE - 185BC")
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.india))
        );

        norseMarker = mMap.addMarker(new MarkerOptions()
                .position(norseLocation)
                .title("The Vikings")
                .snippet("800CE - 1100CE")
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.norse))
        );

        /*
        nativeAmericanMarker = mMap.addMarker(new MarkerOptions()
                .position(nativeAmericanLocation)
                .title("Native American Tribes")
                .snippet("United States")
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.nativeamerican))
        ); */
        mMap.moveCamera(CameraUpdateFactory.newLatLng(egyptLocation));
    }

    /**
     * determines which marker was clicked and calls markerFocus
     * @param marker - marker that was clicked
     * @return if marker was clicked
     */
    @Override
    public boolean onMarkerClick(Marker marker) {
        if (marker.equals(egyptMarker)) {
           markerFocus(egyptMarker, egyptLocation);
           return true;
        } else if (marker.equals(romeMarker)) {
            markerFocus(romeMarker, romeLocation);
            return true;
        } else if (marker.equals(greeceMarker)) {
            markerFocus(greeceMarker, greeceLocation);
            return true;
        } else if (marker.equals(aztecMarker)) {
            markerFocus(aztecMarker, aztecLocation);
            return true;
        } else if (marker.equals(incaMarker)) {
            markerFocus(incaMarker, incaLocation);
            return true;
        } else if (marker.equals(mayanMarker)) {
            markerFocus(mayanMarker, mayanLocation);
            return true;
        } else if (marker.equals(japanMarker)) {
            markerFocus(japanMarker, japanLocation);
            return true;
        } else if (marker.equals(chinaMarker)) {
            markerFocus(chinaMarker, chinaLocation);
            return true;
        } else if (marker.equals(indiaMarker)) {
            markerFocus(indiaMarker, indiaLocation);
            return true;
        } else if (marker.equals(norseMarker)) {
            markerFocus(norseMarker, norseLocation);
            return true;
        } /*else if (marker.equals(nativeAmericanMarker)) {
            markerFocus(nativeAmericanMarker, nativeAmericanLocation);
            return true;
        } */
        return false;
    }

    /**
     * sets the camera position to move to the marker and displays its info window
     * @param marker - marker that was determined to be clicked
     * @param location - location of marker
     */
    public void markerFocus(Marker marker, LatLng location){
        mMap.animateCamera(CameraUpdateFactory.newLatLng(location));
        marker.showInfoWindow();
    }

    /**
     * handles when an infowindow is clicked, starts respective activity
     * @param marker - marker that was clicked
     */
    @Override
    public void onInfoWindowClick(Marker marker) {
        if (marker.equals(egyptMarker)) {
            startActivity(new Intent(getApplicationContext(), EgyptActivity.class));
        } else if (marker.equals(romeMarker)) {
            startActivity(new Intent(getApplicationContext(), RomeActivity.class));
        } else if (marker.equals(greeceMarker)) {
            startActivity(new Intent(getApplicationContext(), GreeceActivity.class));
        } else if (marker.equals(aztecMarker)) {
            startActivity(new Intent(getApplicationContext(), AztecActivity.class));
        } else if (marker.equals(incaMarker)) {
            startActivity(new Intent(getApplicationContext(), IncaActivity.class));
        } else if (marker.equals(mayanMarker)) {
            startActivity(new Intent(getApplicationContext(), MayanActivity.class));
        } else if (marker.equals(japanMarker)) {
            startActivity(new Intent(getApplicationContext(), JapanActivity.class));
        } else if (marker.equals(chinaMarker)) {
            startActivity(new Intent(getApplicationContext(), ChinaActivity.class));
        } else if (marker.equals(indiaMarker)) {
            startActivity(new Intent(getApplicationContext(), IndiaActivity.class));
        }else if (marker.equals(norseMarker)) {
            startActivity(new Intent(getApplicationContext(), NorseActivity.class));
        } /*else if (marker.equals(nativeAmericanMarker)) {
            startActivity(new Intent(getApplicationContext(), NativeAmericanActivity.class));
        } */
    }

    /**
     * updates the year based on the seekbar position
     * seekbar uses its max multiplied to get the years range
     */
    private void updateYear() {
        int year = ((seekYear.getProgress() - 400) * 5); //400 is year minimum/start, 5 is year increment //rand is 400 * 10 = 4000 years (-2000 to 2000)
        int displayYear = year;
        String label;
        if (year >= 0) {
            label = "CE";
        } else {
            label = "BCE";
            displayYear *= -1; //handles negative sign in case of BC
        }
        tvYear.setText(String.format("%d", displayYear) + " " + label);
        updateMarkers(year);
    }

    /**
     * sets the markers visible or invisible based on if the year is within range
     * @param range - range to be used
     * @param marker - marker that is being checked
     * @param year - current year to be checked within range
     */
    private void checkRange(Range range, Marker marker, int year) {
        marker.setVisible(range.contains(year));
    }

/**
 * updates the markers as the seekbar is moved
 * @param year - current year set by seekbar
 */
    private void updateMarkers(int year) {
        checkRange(egyptRange, egyptMarker, year);
        checkRange(romeRange, romeMarker, year);
        checkRange(greeceRange, greeceMarker, year);
        checkRange(aztecRange, aztecMarker, year);
        checkRange(incaRange, incaMarker, year);
        checkRange(mayanRange, mayanMarker, year);
        checkRange(japanRange, japanMarker, year);
        checkRange(chinaRange, chinaMarker, year);
        checkRange(indiaRange, indiaMarker, year);
        checkRange(norseRange, norseMarker, year);
        checkRange(romeRange, romeMarker, year);
        //checkRange(nativeAmericanRange, nativeAmericanMarker, year);
        //for loop rangeArray.length
        //checkRange(rangeArray[0], markerArray[0], year)
    }
}
   /* Seekbar styling help: https://www.tutorialsbuzz.com/2019/09/android-styling-seekbar-thumb-and-progressTrack.html*/