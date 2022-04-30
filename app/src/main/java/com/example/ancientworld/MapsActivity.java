package com.example.ancientworld;

import androidx.fragment.app.FragmentActivity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polygon;
import com.google.android.gms.maps.model.PolygonOptions;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**Coded by: Jeff Murphy */
public class MapsActivity extends FragmentActivity implements OnMapReadyCallback, GoogleMap.OnMarkerClickListener, GoogleMap.OnInfoWindowClickListener {

    //wall of declarations
    private GoogleMap mMap;
    private Marker egyptMarker, romeMarker, greeceMarker, aztecMarker, incaMarker, mayanMarker, japanMarker, chinaMarker, indiaMarker, norseMarker;
    private Polygon egyptPolygon, romePolygon, greecePolygon, aztecPolygon, incaPolygon, mayanPolygon, japanPolygon, chinaPolygon, indiaPolygon, norsePolygon;
    private SeekBar seekYear;
    private TextView tvYear;
    private Button btnHelp;
    private ToggleButton tglFullscreen,tglPolygons;

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

    //lists of markers, polygons, ranges
    List<Marker> markerList = new ArrayList<>();
    List<Polygon> polygonList = new ArrayList<>();
    List<Range> rangeList = new ArrayList<>();

    /*
    private Civ Egypt = new Civ(egyptLocation, egyptRange, egyptMarker, egyptPolygon);
    private Civ Rome = new Civ(romeLocation, romeRange, romeMarker, romePolygon);
    private Civ Greece = new Civ(greeceLocation, greeceRange, greeceMarker, greecePolygon);
    private Civ Aztec = new Civ(aztecLocation, aztecRange, aztecMarker, aztecPolygon);
    private Civ Inca = new Civ(incaLocation, incaRange, incaMarker, incaPolygon);
    private Civ Mayan = new Civ(mayanLocation, mayanRange, mayanMarker, mayanPolygon);
    private Civ Japan = new Civ(japanLocation, japanRange, japanMarker, japanPolygon);
    private Civ China = new Civ(chinaLocation, chinaRange, chinaMarker, chinaPolygon);
    private Civ India  = new Civ(indiaLocation, indiaRange, indiaMarker, indiaPolygon);
    private Civ Norse = new Civ(norseLocation, norseRange, norseMarker, norsePolygon);



    //unused Civ class, tested and uneeded/not utilized
    public class Civ {
        private LatLng location;
        private Range range;
        private Polygon Polygon;
        private Marker marker;
        List<Civ> civList = new ArrayList<Civ>();

        public Civ(LatLng loc, Range range, Marker marker, com.google.android.gms.maps.model.Polygon polygon) {
            this.location = loc;
            this.range = range;
            this.Polygon = polygon;
            this.marker = marker;
            civList.add(this);
        }

        public void showMarker () { this.marker.setVisible(true); }
        public void hideMarker() { this.marker.setVisible(false); }
        public void showPolygon() {this.Polygon.setVisible(true); }
        public void hidePolygon() {this.Polygon.setVisible(false); }
        public void civMarkerFocus(Marker marker, LatLng location, Polygon polygon){
            mMap.animateCamera(CameraUpdateFactory.newLatLng(location));
            marker.showInfoWindow();
            polygon.setVisible(true);
        }
    }*/

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
        tglFullscreen = findViewById(R.id.tglFullscreen);
        tglPolygons = findViewById(R.id.tglPolygons);

        //listener for help button, launches help activity window
        btnHelp.setOnClickListener(view -> startActivity(new Intent(getApplicationContext(), HelpActivity.class)));
        //listener for fullscreen toggle button
        tglFullscreen.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    fullscreen();
                } else {
                    minimize();
                }
            }
        });
        //listener for civ polygons toggle button
        tglPolygons.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    allPolygons();
                } else {
                    clearPolygons();
                }
            }
        });
        //set map features
        setMarkers();
        setPolygons();
        setSeekBarListeners();
        updateYear();
    }
    //listener for seekbar
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

        mMap.moveCamera(CameraUpdateFactory.newLatLng(egyptLocation)); //starting camera position

        //add markers to list
        Collections.addAll(markerList, egyptMarker, romeMarker, greeceMarker, aztecMarker, incaMarker, mayanMarker, japanMarker, chinaMarker, indiaMarker, norseMarker);
    }

    /**
     * sets the approximate maximum borders of each civilization at their height of power
     * not accurate, only estimated for visual purpose
     * number of points required for each make animation impractical
     * used http://www.birdtheme.org/useful/v3largemap.html for easier coordinates
     */
    private void setPolygons() {
        egyptPolygon = mMap.addPolygon(new PolygonOptions()
                .add (new LatLng(31.271386,27.404030), new LatLng(29.087251,28.041237), new LatLng(28.490293,29.183815), new LatLng(27.598242,29.491432),
                        new LatLng(26.796978,29.381569), new LatLng(25.888074,30.062056), new LatLng(25.005162,31.149702), new LatLng(24.995205,31.907759),
                        new LatLng(25.224011,32.599898), new LatLng(24.736041,33.566695), new LatLng(25.005162,34.039107), new LatLng(25.680331,34.412642),
                        new LatLng(26.263060,34.258833), new LatLng(28.737979,32.841597), new LatLng(29.026572,32.643843), new LatLng(29.304781,32.621870),
                        new LatLng(29.658639,32.336226), new LatLng(30.020769,32.533980), new LatLng(29.390968,32.764693), new LatLng(28.815016,33.193159),
                        new LatLng(28.574088,33.270064), new LatLng(27.993611,33.786421), new LatLng(27.770260,34.225874), new LatLng(29.496209,34.874068),
                        new LatLng(31.969099,35.891409), new LatLng(33.813996,36.242971), new LatLng(34.730932,36.781302), new LatLng(35.593151,36.473684),
                        new LatLng(36.241690,36.631950), new LatLng(36.999995,36.950554), new LatLng(37.219030,36.664909), new LatLng(37.078920,35.994743),
                        new LatLng(36.894633,36.005730), new LatLng(36.568851,36.082634), new LatLng(36.330248,35.731072), new LatLng(35.993194,35.961784),
                        new LatLng(35.592181,35.753044), new LatLng(34.531074,35.906853), new LatLng(34.195524,35.643181), new LatLng(33.180856,35.181755),
                        new LatLng(31.921474,34.676384), new LatLng(31.283324,34.091911), new LatLng(31.048307,33.344841), new LatLng(31.104764,32.411003),
                        new LatLng(31.527123,31.938591), new LatLng(31.433429,31.510124), new LatLng(31.620722,31.059684), new LatLng(31.527123,30.235710),
                        new LatLng(30.831577,29.027214), new LatLng(31.271386,27.404030))
                .strokeColor(Color.argb(150, 252, 144, 3))
                .fillColor(Color.argb(100, 252, 144, 3))
                .visible(false)
        );

        romePolygon = mMap.addPolygon(new PolygonOptions()
                .add(new LatLng(34.011245,-6.879941), new LatLng(33.810657,-6.264707), new LatLng(33.664479,-5.847226), new LatLng(33.883653,-5.319883),
                        new LatLng(34.156829,-4.682676), new LatLng(34.392870,-3.649961), new LatLng(34.429125,-2.683164), new LatLng(34.573988,-1.870176),
                        new LatLng(34.736658,-1.057187), new LatLng(34.953054,-0.068418), new LatLng(35.384135,0.568789), new LatLng(35.723783,1.491641),
                        new LatLng(35.919762,2.458438), new LatLng(35.973127,3.425235), new LatLng(35.937555,4.348086), new LatLng(35.884166,5.161074),
                        new LatLng(35.545200,6.215762), new LatLng(34.989065,7.116641), new LatLng(34.193187,7.995547), new LatLng(33.481407,9.028262),
                        new LatLng(32.421940,11.163102), new LatLng(32.068848,12.239762), new LatLng(31.095518,14.480973), new LatLng(30.737355,15.535660),
                        new LatLng(30.283030,17.139664), new LatLng(29.902810,18.150406), new LatLng(29.807528,19.446793), new LatLng(30.169118,20.303727),
                        new LatLng(30.661782,20.853043), new LatLng(30.699576,22.127457), new LatLng(30.567232,23.292008), new LatLng(30.283030,24.544449),
                        new LatLng(29.864708,25.687027), new LatLng(28.734193,27.752457), new LatLng(27.863608,28.565445), new LatLng(26.790001,28.785172),
                        new LatLng(25.963235,29.729996), new LatLng(25.607109,30.828629), new LatLng(25.428646,31.927262), new LatLng(25.448488,32.586441),
                        new LatLng(25.706140,33.157731), new LatLng(26.200057,33.838883), new LatLng(26.357671,34.190445), new LatLng(27.844181,33.531266),
                        new LatLng(28.173965,33.223648), new LatLng(28.753458,32.696305), new LatLng(29.233928,32.630387), new LatLng(29.616689,32.388688),
                        new LatLng(29.921856,32.520523), new LatLng(29.348908,32.894059), new LatLng(28.753458,33.267594), new LatLng(28.579944,33.289566),
                        new LatLng(28.038294,33.904801), new LatLng(27.591315,34.124527), new LatLng(27.902451,34.366227), new LatLng(28.425468,34.607926),
                        new LatLng(29.080421,34.673844), new LatLng(29.368058,34.827652), new LatLng(29.502012,35.003434), new LatLng(28.907451,34.871598),
                        new LatLng(28.367482,34.673844), new LatLng(28.135219,34.607926), new LatLng(28.077074,34.959488), new LatLng(27.746994,35.354996),
                        new LatLng(26.907625,35.992203), new LatLng(27.746994,38.013688), new LatLng(28.096459,38.453141), new LatLng(28.965140,39.419938),
                        new LatLng(29.902810,39.507828), new LatLng(30.775120,39.244156), new LatLng(31.471078,39.485856), new LatLng(31.583454,40.342789),
                        new LatLng(31.489817,41.331559), new LatLng(30.264054,43.748551), new LatLng(29.807528,44.671402), new LatLng(29.616689,45.638199),
                        new LatLng(29.444625,46.868668), new LatLng(29.463758,47.593766), new LatLng(29.463758,47.923356), new LatLng(29.635789,48.121109),
                        new LatLng(29.788460,48.406754), new LatLng(29.959936,48.296891), new LatLng(29.978971,48.758316), new LatLng(30.112112,49.021988),
                        new LatLng(30.491526,49.043961), new LatLng(31.189548,48.121109), new LatLng(34.446607,45.869327), new LatLng(35.473023,45.693545),
                        new LatLng(36.926890,45.341983), new LatLng(37.434571,45.341983), new LatLng(38.106177,45.254092), new LatLng(38.347830,45.495791),
                        new LatLng(38.965529,48.022647), new LatLng(38.948443,48.484073), new LatLng(38.999690,48.835635), new LatLng(39.050899,48.989444),
                        new LatLng(39.255366,49.055362), new LatLng(39.187277,49.231143), new LatLng(39.391348,49.253116), new LatLng(39.763931,49.450870),
                        new LatLng(40.151306,49.450870), new LatLng(40.402759,49.912295), new LatLng(40.352543,50.153995), new LatLng(40.302290,50.395694),
                        new LatLng(40.519783,50.219913), new LatLng(40.736573,49.912295), new LatLng(40.819767,49.472842), new LatLng(41.356404,48.013761),
                        new LatLng(41.339909,47.288663), new LatLng(41.632908,46.106519), new LatLng(42.270210,44.963941), new LatLng(42.675382,44.063062),
                        new LatLng(42.933316,43.008374), new LatLng(43.254219,41.909741), new LatLng(43.811752,40.591382), new LatLng(44.134793,39.740691),
                        new LatLng(44.292281,39.279265), new LatLng(44.746614,38.444304), new LatLng(45.119934,37.894988), new LatLng(45.243835,37.213835),
                        new LatLng(45.367466,36.774382), new LatLng(45.352027,35.785613), new LatLng(45.166429,35.126433), new LatLng(44.964679,34.445280),
                        new LatLng(44.933577,33.895964), new LatLng(44.793412,33.500456), new LatLng(44.512056,33.324675), new LatLng(44.496385,33.610320),
                        new LatLng(44.355158,33.873991), new LatLng(44.621634,34.445280), new LatLng(44.793412,34.752898), new LatLng(45.073402,35.456023),
                        new LatLng(45.042359,35.785613), new LatLng(45.088917,36.093230), new LatLng(45.135437,36.554655), new LatLng(45.088917,36.950163),
                        new LatLng(44.809002,37.191863), new LatLng(44.762217,37.697234), new LatLng(44.559044,38.048796), new LatLng(44.370867,38.686003),
                        new LatLng(44.071680,39.037566), new LatLng(43.786837,39.455046), new LatLng(43.420887,39.938445), new LatLng(43.251712,40.286440),
                        new LatLng(43.155614,40.385317), new LatLng(43.067390,40.648989), new LatLng(42.987077,40.967592), new LatLng(42.834192,41.143374),
                        new LatLng(42.705153,41.483950), new LatLng(42.543477,41.516909), new LatLng(41.957966,41.692690), new LatLng(41.695995,41.626772),
                        new LatLng(41.416478,41.450991), new LatLng(41.218444,40.934633), new LatLng(41.036385,40.407290), new LatLng(40.936867,40.165590),
                        new LatLng(41.003229,39.890932), new LatLng(41.094367,39.473452), new LatLng(41.110925,39.242739), new LatLng(41.028097,38.836245),
                        new LatLng(40.903661,38.429751), new LatLng(40.978351,37.924379), new LatLng(41.086087,37.792544), new LatLng(41.086087,37.605776),
                        new LatLng(41.069524,37.419008), new LatLng(41.152299,37.133364), new LatLng(41.276266,36.902651), new LatLng(41.391757,36.660952),
                        new LatLng(41.375271,36.375307), new LatLng(41.451842,36.123356), new LatLng(41.558801,36.079411), new LatLng(41.706606,36.024479),
                        new LatLng(41.673790,35.606998), new LatLng(41.731207,35.365299), new LatLng(41.845888,35.101627), new LatLng(42.131693,35.112614),
                        new LatLng(41.952194,34.739079), new LatLng(42.001199,34.024967), new LatLng(42.022508,33.367754), new LatLng(41.977604,33.208453),
                        new LatLng(41.850885,32.681109), new LatLng(41.744411,32.318560), new LatLng(41.645970,32.153765), new LatLng(41.543267,31.934039),
                        new LatLng(41.456870,31.736285), new LatLng(41.370358,31.544024), new LatLng(41.357990,31.373736), new LatLng(41.308493,31.412188),
                        new LatLng(41.202500,31.384722), new LatLng(41.115650,31.241900), new LatLng(41.090814,30.978228), new LatLng(41.090814,30.763995),
                        new LatLng(41.185966,30.324542), new LatLng(41.196292,30.255337), new LatLng(41.154945,30.096035), new LatLng(41.130125,29.953213),
                        new LatLng(41.163217,29.678555), new LatLng(41.225219,29.288540), new LatLng(41.216956,29.112759), new LatLng(41.407130,28.551875),
                        new LatLng(41.559392,28.189326), new LatLng(41.662069,28.101436), new LatLng(41.866933,27.986079), new LatLng(41.916003,28.019038),
                        new LatLng(41.911916,28.079463), new LatLng(42.148571,27.909175), new LatLng(42.258438,27.782832), new LatLng(42.376230,27.722407),
                        new LatLng(42.437070,27.678462), new LatLng(42.461390,27.546626), new LatLng(42.469494,27.431270), new LatLng(42.518099,27.546626),
                        new LatLng(42.582846,27.640010), new LatLng(42.679842,27.705928), new LatLng(42.659647,27.837764), new LatLng(42.740387,27.914668),
                        new LatLng(43.022150,27.887202), new LatLng(43.150527,27.986079), new LatLng(43.202604,27.931148), new LatLng(43.238631,28.057490),
                        new LatLng(43.386518,28.145381), new LatLng(43.378533,28.293696), new LatLng(43.414456,28.463985), new LatLng(43.524230,28.604059),
                        new LatLng(43.631676,28.571100), new LatLng(44.027950,28.653498), new LatLng(44.256584,28.664484), new LatLng(44.343075,28.658991),
                        new LatLng(44.401973,28.741388), new LatLng(44.597874,28.933649), new LatLng(44.773621,29.136896), new LatLng(44.839875,29.559870),
                        new LatLng(44.839875,29.603815), new LatLng(44.995469,29.625788), new LatLng(45.166134,29.669733), new LatLng(45.255143,29.741144),
                        new LatLng(45.401896,29.741144), new LatLng(45.517485,29.680719), new LatLng(45.548720,29.368929), new LatLng(45.510238,29.039339),
                        new LatLng(45.772299,26.327657), new LatLng(46.351653,25.141133), new LatLng(47.699582,23.866719), new LatLng(48.550238,23.031758),
                        new LatLng(48.782424,21.186055), new LatLng(49.214893,18.505391), new LatLng(49.501121,17.099141), new LatLng(49.983890,16.044453),
                        new LatLng(50.040371,12.830432), new LatLng(49.983890,11.687854), new LatLng(49.899045,10.017932), new LatLng(50.712970,7.952503),
                        new LatLng(51.100922,7.073597), new LatLng(51.840008,6.150745), new LatLng(52.272347,5.271839), new LatLng(52.567157,4.788440),
                        new LatLng(52.608904,4.569886), new LatLng(52.334518,4.492982), new LatLng(52.139407,4.273255), new LatLng(51.970518,4.086488),
                        new LatLng(51.767009,3.822816), new LatLng(51.569407,3.559144), new LatLng(51.370942,3.394349), new LatLng(51.247322,2.845032),
                        new LatLng(51.005997,1.878236), new LatLng(50.978337,1.779359), new LatLng(50.929891,1.691468), new LatLng(51.206042,1.416810),
                        new LatLng(51.350362,1.405823), new LatLng(51.391267,1.416220), new LatLng(51.384411,1.278891), new LatLng(51.360407,0.993246),
                        new LatLng(51.384411,0.921835), new LatLng(51.446078,0.800986), new LatLng(51.490564,0.593504), new LatLng(51.531588,0.697875),
                        new LatLng(51.586231,0.912108), new LatLng(51.766765,0.961546), new LatLng(51.770165,1.104369), new LatLng(51.821125,1.269164),
                        new LatLng(51.868636,1.296629), new LatLng(51.895762,1.269164), new LatLng(51.960122,1.296629), new LatLng(51.983811,1.401000),
                        new LatLng(52.075063,1.532835), new LatLng(52.129050,1.620726), new LatLng(52.330921,1.675658), new LatLng(52.421464,1.736083),
                        new LatLng(52.588646,1.725096), new LatLng(52.721934,1.697630), new LatLng(52.824953,1.554808), new LatLng(52.940971,1.274657),
                        new LatLng(52.991514,0.973651), new LatLng(52.994821,0.556170), new LatLng(52.872316,0.429828), new LatLng(52.812593,0.391375),
                        new LatLng(52.832510,0.215594), new LatLng(52.882261,0.166156), new LatLng(52.895519,0.083758), new LatLng(52.961745,0.083758),
                        new LatLng(53.021263,0.188128), new LatLng(53.097193,0.352923), new LatLng(53.228928,0.352923), new LatLng(53.393028,0.221087),
                        new LatLng(53.497722,0.133197), new LatLng(53.517323,0.045306), new LatLng(53.553235,-0.020612), new LatLng(53.611935,-0.135968),
                        new LatLng(53.634740,0.045306), new LatLng(53.615193,0.138690), new LatLng(53.673807,0.122210), new LatLng(53.755080,0.012347),
                        new LatLng(54.007642,-0.218366), new LatLng(54.097129,-0.163434), new LatLng(54.138984,-0.113996), new LatLng(54.203294,-0.262311),
                        new LatLng(54.257879,-0.333722), new LatLng(54.293161,-0.427106), new LatLng(54.382831,-0.438092), new LatLng(54.427594,-0.476545),
                        new LatLng(54.628419,-1.075299), new LatLng(54.599790,-1.229108), new LatLng(54.695141,-1.207135), new LatLng(54.929386,-1.399396),
                        new LatLng(54.989310,-1.382917), new LatLng(54.882013,-3.349469), new LatLng(54.863049,-3.426374), new LatLng(54.726875,-3.508771),
                        new LatLng(54.618878,-3.563703), new LatLng(54.555216,-3.635114), new LatLng(54.437179,-3.536237), new LatLng(54.273920,-3.387921),
                        new LatLng(54.187226,-3.261579), new LatLng(54.100350,-3.283551), new LatLng(54.042331,-3.195661), new LatLng(54.171152,-3.025373),
                        new LatLng(54.161504,-2.959455), new LatLng(54.129329,-2.827619), new LatLng(54.032653,-2.931989), new LatLng(53.958383,-2.888044),
                        new LatLng(53.926050,-3.047345), new LatLng(53.734776,-3.052838), new LatLng(53.549156,-3.124250), new LatLng(53.445069,-3.042384),
                        new LatLng(53.386138,-3.174220), new LatLng(53.271315,-3.124782), new LatLng(53.353363,-3.344508), new LatLng(53.300870,-3.723537),
                        new LatLng(53.369753,-3.866359), new LatLng(53.363198,-3.915797), new LatLng(53.317281,-3.871852), new LatLng(53.264745,-4.080592),
                        new LatLng(53.323844,-4.069606), new LatLng(53.314000,-4.146510), new LatLng(53.435253,-4.388209), new LatLng(53.402516,-4.569484),
                        new LatLng(53.307436,-4.580470), new LatLng(53.340246,-4.695827), new LatLng(53.258173,-4.613429), new LatLng(53.106747,-4.360744),
                        new LatLng(52.997785,-4.415675), new LatLng(52.862025,-4.728786), new LatLng(52.775712,-4.767238), new LatLng(52.825529,-4.591457),
                        new LatLng(52.792324,-4.509059), new LatLng(52.868657,-4.481593), new LatLng(52.918367,-4.256374), new LatLng(52.891862,-4.168483),
                        new LatLng(52.835485,-4.152003), new LatLng(52.805609,-4.113551), new LatLng(52.755769,-4.102565), new LatLng(52.702544,-4.080592),
                        new LatLng(52.639255,-4.124538), new LatLng(52.428745,-4.069606), new LatLng(52.328147,-4.141017), new LatLng(52.209802,-4.331355),
                        new LatLng(52.213168,-4.386287), new LatLng(52.159282,-4.452205), new LatLng(52.112079,-4.562068), new LatLng(52.115452,-4.721370),
                        new LatLng(52.054693,-4.809261), new LatLng(52.031042,-4.875179), new LatLng(52.020902,-5.001521), new LatLng(52.044559,-5.083919),
                        new LatLng(51.949859,-5.133357), new LatLng(51.929540,-5.270687), new LatLng(51.902434,-5.397029), new LatLng(51.858351,-5.166316),
                        new LatLng(51.807433,-5.105892), new LatLng(51.736051,-5.144344), new LatLng(51.725844,-5.215755), new LatLng(51.705424,-5.171810),
                        new LatLng(51.705424,-5.061946), new LatLng(51.640699,-5.050960), new LatLng(51.606596,-4.957576), new LatLng(51.623651,-4.842220),
                        new LatLng(51.739453,-4.627986), new LatLng(51.746255,-4.507137), new LatLng(51.729247,-4.424739), new LatLng(51.759858,-4.369808),
                        new LatLng(51.715635,-4.369808), new LatLng(51.688400,-4.336849), new LatLng(51.695211,-4.144588), new LatLng(51.671370,-4.133602),
                        new LatLng(51.640699,-4.259944), new LatLng(51.596361,-4.303890), new LatLng(51.548563,-4.259944), new LatLng(51.538314,-4.155574),
                        new LatLng(51.579296,-4.023738), new LatLng(51.603185,-3.908382), new LatLng(51.538314,-3.771053), new LatLng(51.404867,-3.562312),
                        new LatLng(51.384302,-3.304134), new LatLng(51.425423,-3.139339), new LatLng(51.514391,-3.034969), new LatLng(51.534897,-2.870174),
                        new LatLng(51.582709,-2.694393), new LatLng(51.708828,-2.513118), new LatLng(51.473350,-2.776790), new LatLng(51.432273,-2.897640),
                        new LatLng(51.350007,-2.985530), new LatLng(51.295081,-3.018489), new LatLng(51.233211,-3.051448), new LatLng(51.212569,-3.133846),
                        new LatLng(51.185032,-3.353572), new LatLng(51.229771,-3.518367), new LatLng(51.222891,-3.814998), new LatLng(51.209128,-4.183040),
                        new LatLng(51.062241,-4.311073), new LatLng(51.006971,-4.382484), new LatLng(51.031160,-4.508827), new LatLng(50.937791,-4.558265),
                        new LatLng(50.802593,-4.591224), new LatLng(50.673965,-4.772499), new LatLng(50.538002,-4.959266), new LatLng(50.503076,-5.080116),
                        new LatLng(50.342085,-5.135047), new LatLng(50.257873,-5.288856), new LatLng(50.226255,-5.514076), new LatLng(50.141837,-5.711830),
                        new LatLng(50.036105,-5.695350), new LatLng(50.064323,-5.508583), new LatLng(50.124231,-5.508583), new LatLng(50.103096,-5.420692),
                        new LatLng(50.011401,-5.261390), new LatLng(49.976087,-5.206459), new LatLng(50.032577,-5.047157), new LatLng(50.145357,-5.058143),
                        new LatLng(50.229769,-4.783485), new LatLng(50.335073,-4.723060), new LatLng(50.349096,-4.398963), new LatLng(50.303506,-3.975990),
                        new LatLng(50.187582,-3.783729), new LatLng(50.254361,-3.580482), new LatLng(50.401646,-3.437660), new LatLng(50.408648,-3.520057),
                        new LatLng(50.614747,-3.415687), new LatLng(50.733108,-2.893836), new LatLng(50.642624,-2.575233), new LatLng(50.572902,-2.454383),
                        new LatLng(50.583367,-2.020423), new LatLng(50.639140,-1.910560), new LatLng(50.722676,-1.899574), new LatLng(50.729631,-1.740272),
                        new LatLng(50.729631,-1.575477), new LatLng(50.583367,-1.317298), new LatLng(50.611262,-1.152503), new LatLng(50.673965,-1.081092),
                        new LatLng(50.726154,-1.136024), new LatLng(50.743537,-1.405189), new LatLng(50.719199,-1.482093), new LatLng(50.747013,-1.520545),
                        new LatLng(50.819948,-1.333778), new LatLng(50.799121,-1.207435), new LatLng(50.747013,-1.075599), new LatLng(50.757439,-0.855873),
                        new LatLng(50.719199,-0.784461), new LatLng(50.832096,-0.213092), new LatLng(50.724419,0.237348), new LatLng(50.842503,0.413129),
                        new LatLng(50.887574,0.693281), new LatLng(50.936063,0.841596), new LatLng(50.915289,0.951459), new LatLng(51.032891,1.000898),
                        new LatLng(51.112276,1.303022), new LatLng(51.146749,1.407392), new LatLng(50.901433,1.649091), new LatLng(50.842503,1.566694),
                        new LatLng(50.696591,1.561200), new LatLng(50.550223,1.539228), new LatLng(50.350853,1.539228), new LatLng(50.210441,1.522748),
                        new LatLng(50.076667,1.434858), new LatLng(49.967258,1.198652), new LatLng(49.871763,0.792157), new LatLng(49.776079,0.341718),
                        new LatLng(49.673097,0.149457), new LatLng(49.491459,0.078046), new LatLng(49.374242,0.039594), new LatLng(49.309819,-0.158160),
                        new LatLng(49.352777,-0.449298), new LatLng(49.370665,-0.680010), new LatLng(49.363510,-0.932696), new LatLng(49.424290,-1.113970),
                        new LatLng(49.520667,-1.273272), new LatLng(49.606176,-1.256793), new LatLng(49.680874,-1.234820), new LatLng(49.723508,-1.394122),
                        new LatLng(49.727059,-1.954425), new LatLng(49.570566,-1.904986), new LatLng(49.438580,-1.883013), new LatLng(49.324142,-1.756671),
                        new LatLng(49.191492,-1.630328), new LatLng(48.856498,-1.531451), new LatLng(48.639176,-1.531451), new LatLng(48.621023,-1.855548),
                        new LatLng(48.682715,-1.894000), new LatLng(48.628285,-2.058795), new LatLng(48.700846,-2.316973), new LatLng(48.570161,-2.646563),
                        new LatLng(48.631915,-2.855303), new LatLng(48.863726,-3.069537), new LatLng(48.679088,-3.923724), new LatLng(48.606496,-4.593890),
                        new LatLng(48.468283,-4.813616), new LatLng(48.315082,-4.747698), new LatLng(48.212691,-4.538958), new LatLng(48.154090,-4.319232),
                        new LatLng(48.044033,-4.714739), new LatLng(47.815834,-4.308245), new LatLng(47.778934,-4.044573), new LatLng(47.727229,-3.451312),
                        new LatLng(47.497632,-2.891009), new LatLng(47.371297,-2.495501), new LatLng(47.087802,-2.001116), new LatLng(46.892958,-2.176898),
                        new LatLng(46.689869,-2.023089), new LatLng(46.372427,-1.715472), new LatLng(46.304163,-1.330950), new LatLng(46.174986,-1.089251),
                        new LatLng(45.908076,-1.056292), new LatLng(45.716637,-1.155169), new LatLng(45.386024,-1.181479), new LatLng(43.797977,-1.445151),
                        new LatLng(43.456025,-1.587973), new LatLng(43.280320,-1.928550), new LatLng(43.304310,-2.532798), new LatLng(43.448049,-2.840415),
                        new LatLng(43.413573,-4.138489), new LatLng(43.588892,-5.456848), new LatLng(43.534762,-7.227814), new LatLng(43.693844,-7.623322),
                        new LatLng(43.693844,-8.194611), new LatLng(43.407194,-8.546173), new LatLng(43.215336,-9.117462), new LatLng(43.151249,-9.359161),
                        new LatLng(40.799414,-8.765900), new LatLng(40.197916,-8.787872), new LatLng(39.472406,-9.293243), new LatLng(39.047080,-9.534943),
                        new LatLng(38.516100,-9.205353), new LatLng(38.567658,-8.765900), new LatLng(37.092596,-8.875763), new LatLng(37.110121,-8.304474),
                        new LatLng(37.162671,-7.249786), new LatLng(37.127642,-6.788361), new LatLng(36.370601,-6.195099), new LatLng(36.015950,-5.887482),
                        new LatLng(36.158003,-5.448029), new LatLng(36.529669,-4.722931), new LatLng(36.723642,-3.668243), new LatLng(36.776459,-2.459747),
                        new LatLng(36.794056,-1.976349), new LatLng(37.529496,-1.514923), new LatLng(37.738305,-0.723908), new LatLng(38.447298,-0.350372),
                        new LatLng(38.739250,0.111053), new LatLng(39.302585,-0.284454), new LatLng(39.591034,-0.306427), new LatLng(40.147547,0.154999),
                        new LatLng(40.948945,1.033905), new LatLng(41.164338,1.517303), new LatLng(41.527241,2.637909), new LatLng(41.822657,3.143280),
                        new LatLng(42.208910,3.136546), new LatLng(42.888766,3.004710), new LatLng(43.241923,3.158519), new LatLng(43.513424,3.993480),
                        new LatLng(43.465600,4.476878), new LatLng(43.355530,4.772598), new LatLng(43.419403,5.124160), new LatLng(43.283594,5.245010),
                        new LatLng(43.099368,5.563613), new LatLng(43.043189,6.068984), new LatLng(43.179535,6.607315), new LatLng(43.483208,6.881973),
                        new LatLng(43.658324,7.299453), new LatLng(43.840856,8.046524), new LatLng(44.062318,8.211318), new LatLng(44.377259,8.683731),
                        new LatLng(44.361552,9.068252), new LatLng(44.164859,9.716445), new LatLng(44.022832,10.133926), new LatLng(43.515085,10.419570),
                        new LatLng(43.307582,10.452529), new LatLng(42.978922,10.419570), new LatLng(42.962844,9.353897), new LatLng(42.729250,9.255020),
                        new LatLng(42.713108,8.991348), new LatLng(42.454256,8.606826), new LatLng(41.957846,8.595840), new LatLng(41.580926,8.826553),
                        new LatLng(41.350412,9.090225), new LatLng(41.193526,9.222061), new LatLng(40.870316,8.551895), new LatLng(40.927138,8.269222),
                        new LatLng(40.735947,8.093441), new LatLng(40.535856,8.181331), new LatLng(40.368656,8.379085), new LatLng(40.033009,8.521908),
                        new LatLng(39.373726,8.379085), new LatLng(39.178120,8.346126), new LatLng(39.041725,8.477962), new LatLng(38.913615,8.697689),
                        new LatLng(38.905066,8.928402), new LatLng(39.007585,9.082210), new LatLng(39.186636,9.082210), new LatLng(39.178120,9.455746),
                        new LatLng(39.075849,9.543636), new LatLng(39.830824,9.708431), new LatLng(40.192649,9.686458), new LatLng(40.326791,9.697445),
                        new LatLng(40.494095,9.873226), new LatLng(40.819141,9.642513), new LatLng(41.241809,9.367855), new LatLng(41.250069,9.247005),
                        new LatLng(41.448007,9.236019), new LatLng(41.850261,9.400814), new LatLng(42.144192,9.554622), new LatLng(42.412436,9.587581),
                        new LatLng(42.744118,9.477718), new LatLng(42.905273,9.477718), new LatLng(42.945497,10.499447), new LatLng(42.921366,10.807064),
                        new LatLng(42.736049,10.894955), new LatLng(42.493497,11.103695), new LatLng(42.388098,11.257503), new LatLng(42.331271,11.488216),
                        new LatLng(42.225599,11.718929), new LatLng(42.095298,11.784847), new LatLng(41.997396,11.861751), new LatLng(41.948389,12.070492),
                        new LatLng(41.579632,12.444027), new LatLng(41.390338,12.674740), new LatLng(41.208757,13.191097), new LatLng(41.241809,13.674496),
                        new LatLng(40.852390,13.993099), new LatLng(40.819141,14.300716), new LatLng(40.585935,14.344662), new LatLng(40.619300,14.784115),
                        new LatLng(40.209431,14.948910), new LatLng(40.075056,15.179622), new LatLng(39.974100,15.399349), new LatLng(40.058240,15.586117),
                        new LatLng(38.828080,16.179378), new LatLng(38.699584,15.926693), new LatLng(38.450502,15.827816), new LatLng(38.209191,15.630062),
                        new LatLng(38.235085,15.333431), new LatLng(38.140098,14.806087), new LatLng(38.070939,14.465511), new LatLng(37.993057,14.135921),
                        new LatLng(38.010371,13.817318), new LatLng(38.286843,13.224056), new LatLng(38.105527,12.795589), new LatLng(38.105527,12.487972),
                        new LatLng(37.932425,12.444027), new LatLng(37.672006,12.476986), new LatLng(37.541452,12.762630), new LatLng(37.497884,12.982357),
                        new LatLng(37.070461,13.897159), new LatLng(37.096754,14.226749), new LatLng(36.798230,14.490421), new LatLng(36.719014,14.863956),
                        new LatLng(36.657344,15.072696), new LatLng(36.894939,15.127628), new LatLng(37.149313,15.380313), new LatLng(37.350451,15.160587),
                        new LatLng(37.768486,15.314395), new LatLng(38.149620,15.556095), new LatLng(37.924643,15.753848), new LatLng(37.959300,16.094425),
                        new LatLng(38.227335,16.237247), new LatLng(38.408349,16.621768), new LatLng(38.700459,16.577823), new LatLng(38.957216,16.951358),
                        new LatLng(38.905939,17.138126), new LatLng(39.400065,17.127139), new LatLng(39.620443,16.841495), new LatLng(39.679656,16.544864),
                        new LatLng(39.882286,16.588809), new LatLng(40.168324,16.698673), new LatLng(40.478237,17.061222), new LatLng(40.344395,17.302921),
                        new LatLng(40.319270,17.841251), new LatLng(39.966540,18.039005), new LatLng(39.755712,18.258731), new LatLng(39.924426,18.445499),
                        new LatLng(40.185113,18.533389), new LatLng(40.536709,18.170841), new LatLng(40.703490,17.841251), new LatLng(40.961179,17.247989),
                        new LatLng(41.193068,16.676700), new LatLng(41.391179,16.193302), new LatLng(41.629056,15.896030), new LatLng(41.743919,16.126743),
                        new LatLng(41.899478,16.170688), new LatLng(41.940351,16.027866), new LatLng(41.907654,15.665317), new LatLng(41.899478,15.335727),
                        new LatLng(41.973031,15.116000), new LatLng(42.176901,14.654575), new LatLng(42.566494,14.094272), new LatLng(42.832937,13.918491),
                        new LatLng(43.354403,13.786655), new LatLng(43.696932,13.325229), new LatLng(44.053309,12.567172), new LatLng(44.500821,12.305178),
                        new LatLng(44.821207,12.228274), new LatLng(44.891299,12.469973), new LatLng(45.070035,12.338137), new LatLng(45.178558,12.294192),
                        new LatLng(45.310058,12.184328), new LatLng(45.472079,12.305178), new LatLng(45.495187,12.755617), new LatLng(45.672033,13.118166),
                        new LatLng(45.656678,13.469729), new LatLng(45.771744,13.590578), new LatLng(45.618270,13.788332), new LatLng(45.441254,13.491701),
                        new LatLng(44.984622,13.667483), new LatLng(44.782231,13.953127), new LatLng(45.108817,14.205813), new LatLng(45.085551,14.315676),
                        new LatLng(44.899082,14.315676), new LatLng(44.665143,14.359621), new LatLng(44.516491,14.458498), new LatLng(43.823040,15.216555),
                        new LatLng(43.911069,15.544220), new LatLng(43.601594,15.939728), new LatLng(43.490110,15.950714), new LatLng(43.482139,16.104523),
                        new LatLng(43.322497,16.313263), new LatLng(43.210498,16.543976), new LatLng(43.082246,16.478058), new LatLng(42.897413,16.752716),
                        new LatLng(42.857159,17.016388), new LatLng(42.865212,17.258087), new LatLng(42.832993,17.554718), new LatLng(42.736238,17.818390),
                        new LatLng(42.461275,18.444611), new LatLng(41.834083,19.235626), new LatLng(41.825896,19.499298), new LatLng(41.743977,19.587189),
                        new LatLng(41.637325,19.620148), new LatLng(41.514045,19.499298), new LatLng(41.068291,19.477326), new LatLng(40.719490,19.334503),
                        new LatLng(40.201236,19.532257), new LatLng(40.075252,19.784943), new LatLng(39.890053,19.949738), new LatLng(39.763493,19.894806),
                        new LatLng(39.746601,19.631134), new LatLng(39.407886,19.905792), new LatLng(39.373924,20.004669), new LatLng(39.484241,20.070587),
                        new LatLng(39.628239,19.982697), new LatLng(39.678990,20.125519), new LatLng(39.484241,20.180451), new LatLng(39.280442,20.455109),
                        new LatLng(38.999247,20.707794), new LatLng(38.702635,20.523595), new LatLng(38.410533,20.435705), new LatLng(38.220895,20.281896),
                        new LatLng(38.143173,20.578527), new LatLng(37.944173,20.600500), new LatLng(37.779374,20.644445), new LatLng(37.605503,20.831213),
                        new LatLng(37.779374,21.050939), new LatLng(38.212263,20.798254), new LatLng(38.700670,20.754308), new LatLng(38.726387,20.897131),
                        new LatLng(38.477399,21.050939), new LatLng(38.313801,21.105871), new LatLng(38.356889,21.402502), new LatLng(38.305180,21.677160),
                        new LatLng(38.201652,21.600256), new LatLng(38.184383,21.457433), new LatLng(38.080683,21.292638), new LatLng(37.924856,21.193761),
                        new LatLng(37.768699,21.193761), new LatLng(37.481553,21.534338), new LatLng(37.341932,21.710119), new LatLng(37.184547,21.589269),
                        new LatLng(36.895155,21.666174), new LatLng(36.719230,21.798010), new LatLng(36.903941,22.017736), new LatLng(36.824834,22.182531),
                        new LatLng(36.542901,22.303381), new LatLng(36.454585,22.468176), new LatLng(36.710423,22.621984), new LatLng(36.604662,22.918615),
                        new LatLng(36.454585,22.907629), new LatLng(36.180167,22.929601), new LatLng(36.136500,23.035991), new LatLng(36.242900,23.046977),
                        new LatLng(36.366851,23.112895), new LatLng(36.534755,23.145854), new LatLng(36.895833,23.090922), new LatLng(37.534516,22.684428),
                        new LatLng(37.360072,23.365581), new LatLng(37.456067,23.497416), new LatLng(37.734623,23.222758), new LatLng(37.968842,23.343608),
                        new LatLng(37.986162,23.552348), new LatLng(37.795418,23.761088), new LatLng(37.682473,23.892924), new LatLng(37.656384,24.068706),
                        new LatLng(38.176412,23.991801), new LatLng(38.297223,24.035747), new LatLng(38.133217,24.222514), new LatLng(37.986162,24.321391),
                        new LatLng(37.951518,24.486186), new LatLng(37.708552,24.892680), new LatLng(37.525803,25.079448), new LatLng(37.464787,25.233256),
                        new LatLng(37.464787,25.387065), new LatLng(37.673778,25.189311), new LatLng(37.873509,24.859721), new LatLng(38.107288,24.650981),
                        new LatLng(38.185048,24.497172), new LatLng(38.254099,24.277446), new LatLng(38.435046,24.178569), new LatLng(38.589783,24.101665),
                        new LatLng(38.709904,23.837993), new LatLng(38.795581,23.541362), new LatLng(38.983709,23.288676), new LatLng(39.205399,23.321635),
                        new LatLng(39.426392,23.169356), new LatLng(39.553570,23.037520), new LatLng(39.663603,22.894698), new LatLng(39.891574,22.828780),
                        new LatLng(40.076769,22.696944), new LatLng(40.085175,22.609053), new LatLng(40.361991,22.576094), new LatLng(40.420564,22.828780),
                        new LatLng(40.286608,23.004561), new LatLng(40.043134,23.334151), new LatLng(39.942131,23.455001), new LatLng(39.942131,23.718673),
                        new LatLng(40.085175,23.487960), new LatLng(40.143988,23.397322), new LatLng(40.257269,23.386336), new LatLng(40.219530,23.682967),
                        new LatLng(40.080972,23.748885), new LatLng(39.942131,23.902694), new LatLng(39.963186,23.990584), new LatLng(40.038929,24.034529),
                        new LatLng(40.248885,23.754378), new LatLng(40.328498,23.831282), new LatLng(40.307556,23.979598), new LatLng(40.148187,24.243270),
                        new LatLng(40.118788,24.353133), new LatLng(40.190162,24.408065), new LatLng(40.324310,24.232283), new LatLng(40.403834,24.029036),
                        new LatLng(40.537558,23.820296), new LatLng(40.658514,23.759871), new LatLng(40.712665,23.682967), new LatLng(40.770932,23.886214),
                        new LatLng(40.666848,24.083968), new LatLng(40.775092,24.287215), new LatLng(40.916377,24.430037), new LatLng(40.916377,24.528914),
                        new LatLng(40.808363,24.589339), new LatLng(40.637675,24.484969), new LatLng(40.562601,24.644271), new LatLng(40.646012,24.798079),
                        new LatLng(40.808363,24.765120), new LatLng(40.887314,24.951888), new LatLng(40.986906,25.028792), new LatLng(40.957874,25.155135),
                        new LatLng(40.899771,25.468245), new LatLng(40.824992,25.946151), new LatLng(40.595978,26.110945), new LatLng(40.600149,26.495467),
                        new LatLng(40.633507,26.786605), new LatLng(40.466550,26.583358), new LatLng(40.332685,26.281234), new LatLng(40.223724,25.737410),
                        new LatLng(40.097782,25.567122), new LatLng(39.967396,24.979354), new LatLng(39.845196,25.094710), new LatLng(39.798788,25.188094),
                        new LatLng(39.773461,25.297957), new LatLng(39.807228,25.380355), new LatLng(39.929495,25.391341), new LatLng(40.009483,25.462752),
                        new LatLng(40.148187,25.995589), new LatLng(40.240499,26.182357), new LatLng(40.290798,26.303206), new LatLng(40.206945,26.248275),
                        new LatLng(40.068362,26.160384), new LatLng(39.946342,26.171370), new LatLng(39.735453,26.132918), new LatLng(39.515441,26.034041),
                        new LatLng(39.447605,26.077986), new LatLng(39.572718,26.892289), new LatLng(39.496460,26.864823), new LatLng(39.394653,26.749467),
                        new LatLng(39.330947,26.639603), new LatLng(39.309699,26.546220), new LatLng(39.398898,26.348466), new LatLng(39.339445,26.101273),
                        new LatLng(39.271437,25.854081), new LatLng(39.118177,25.881547), new LatLng(39.075546,26.040849), new LatLng(38.985936,26.222123),
                        new LatLng(38.985936,26.375932), new LatLng(39.045689,26.557206), new LatLng(39.041422,26.650590), new LatLng(39.190591,26.557206),
                        new LatLng(39.203363,26.732987), new LatLng(39.113915,26.809892), new LatLng(38.990206,26.793412), new LatLng(38.930406,26.842850),
                        new LatLng(38.930406,26.980180), new LatLng(38.823496,26.963700), new LatLng(38.789251,26.749467), new LatLng(38.729282,26.700028),
                        new LatLng(38.609194,26.765946), new LatLng(38.518996,26.853837), new LatLng(38.454499,26.952714), new LatLng(38.454499,27.150468),
                        new LatLng(38.398555,27.057084), new LatLng(38.428684,26.722001), new LatLng(38.549074,26.601151), new LatLng(38.686417,26.452836),
                        new LatLng(38.682129,26.342973), new LatLng(38.579140,26.315507), new LatLng(38.514698,26.342973), new LatLng(38.557666,26.222123),
                        new LatLng(38.622070,25.859574), new LatLng(38.544778,25.821122), new LatLng(38.471704,25.898026), new LatLng(38.407164,25.958451),
                        new LatLng(38.333950,25.985917), new LatLng(38.269288,25.914506), new LatLng(38.256349,25.887040), new LatLng(38.195935,25.919999),
                        new LatLng(38.157071,26.018876), new LatLng(38.204568,26.101273), new LatLng(38.295160,26.128739), new LatLng(38.454499,26.167191),
                        new LatLng(38.488904,26.326493), new LatLng(38.445895,26.463822), new LatLng(38.368413,26.458329), new LatLng(38.321023,26.310014),
                        new LatLng(38.273601,26.260575), new LatLng(38.208885,26.419877), new LatLng(38.170028,26.496781), new LatLng(38.113864,26.540726),
                        new LatLng(38.157071,26.650590), new LatLng(38.204568,26.661576), new LatLng(38.200252,26.754960), new LatLng(38.139791,26.798905),
                        new LatLng(38.061982,26.853837), new LatLng(38.066307,27.018632), new LatLng(38.010064,27.073563), new LatLng(37.953777,27.315263),
                        new LatLng(37.802022,27.243851), new LatLng(37.680393,27.073563), new LatLng(37.773650,26.919755), new LatLng(37.817057,26.722001),
                        new LatLng(37.777992,26.579179), new LatLng(37.730217,26.513261), new LatLng(37.708491,26.551713), new LatLng(37.691106,26.705521),
                        new LatLng(37.673717,26.760453), new LatLng(37.669369,27.040604), new LatLng(37.564941,27.172440), new LatLng(37.355645,27.238358),
                        new LatLng(37.277008,27.447099), new LatLng(37.202664,27.556962), new LatLng(37.119487,27.386674), new LatLng(37.123867,27.265824),
                        new LatLng(36.908949,27.232865), new LatLng(36.816655,26.936234), new LatLng(36.715894,26.899800), new LatLng(36.658629,26.938252),
                        new LatLng(36.685064,27.015157), new LatLng(36.768716,27.190938), new LatLng(36.900613,27.405171), new LatLng(37.001579,27.399678),
                        new LatLng(36.957697,27.624898), new LatLng(37.010353,27.899556), new LatLng(37.023511,28.223653), new LatLng(37.032282,28.333516),
                        new LatLng(36.905005,28.119283), new LatLng(36.931356,28.047871), new LatLng(36.865462,28.025899), new LatLng(36.781916,27.998433),
                        new LatLng(36.768716,27.531514), new LatLng(36.707086,27.405171), new LatLng(36.641000,27.487569), new LatLng(36.645407,27.701802),
                        new LatLng(36.596911,27.822652), new LatLng(36.543970,27.844624), new LatLng(36.579268,28.053365), new LatLng(36.437981,28.042378),
                        new LatLng(36.327421,27.844624), new LatLng(36.181241,27.784200), new LatLng(36.105830,27.707295), new LatLng(36.034788,27.723775),
                        new LatLng(35.901413,27.223897), new LatLng(35.611660,27.064595), new LatLng(35.419398,26.894307), new LatLng(35.397012,26.828389),
                        new LatLng(35.312319,26.101936), new LatLng(35.227107,25.942635), new LatLng(35.137313,25.711922), new LatLng(35.307837,25.689949),
                        new LatLng(35.330247,25.475716), new LatLng(35.325766,25.338387), new LatLng(35.375050,25.223030), new LatLng(35.455632,25.003304),
                        new LatLng(35.433256,24.712166), new LatLng(35.401919,24.470467), new LatLng(35.357132,24.311165), new LatLng(35.446682,24.289192),
                        new LatLng(35.531663,24.267220), new LatLng(35.656734,24.055411), new LatLng(35.585289,23.956534), new LatLng(35.558480,23.791739),
                        new LatLng(35.661197,23.769766), new LatLng(35.701355,23.736807), new LatLng(35.643343,23.676383), new LatLng(35.527192,23.626944),
                        new LatLng(35.460106,23.511588), new LatLng(35.339210,23.500601), new LatLng(35.285420,23.511588), new LatLng(35.218132,23.588492),
                        new LatLng(35.236081,23.764273), new LatLng(35.222620,23.929068), new LatLng(35.186712,24.093863), new LatLng(35.164262,24.275137),
                        new LatLng(35.069903,24.588248), new LatLng(34.948423,24.786002), new LatLng(34.921403,25.005728), new LatLng(34.957428,25.258414),
                        new LatLng(35.006936,25.588004), new LatLng(35.015934,25.835196), new LatLng(35.011435,26.082388), new LatLng(35.020433,26.197745),
                        new LatLng(35.159771,26.285635), new LatLng(35.231594,26.318594), new LatLng(35.334729,26.884390), new LatLng(35.424304,27.027213),
                        new LatLng(35.455632,27.208487), new LatLng(35.571886,27.268912), new LatLng(35.603156,27.202994), new LatLng(35.701355,27.219473),
                        new LatLng(35.879588,27.252432), new LatLng(35.910738,27.856680), new LatLng(36.017444,28.010489), new LatLng(36.150624,28.131339),
                        new LatLng(36.363241,28.230216), new LatLng(36.531153,28.219229), new LatLng(36.619381,28.136832), new LatLng(36.694296,28.274161),
                        new LatLng(36.725122,28.340079), new LatLng(36.782338,28.274161), new LatLng(36.782338,28.433463), new LatLng(36.742731,28.609244),
                        new LatLng(36.645830,28.779532), new LatLng(36.584102,28.856436), new LatLng(36.720719,28.949820), new LatLng(36.610563,29.098135),
                        new LatLng(36.380933,29.109122), new LatLng(36.253023,29.386042), new LatLng(36.182115,29.638727), new LatLng(36.226440,30.089167),
                        new LatLng(36.306162,30.440729), new LatLng(36.447689,30.594538), new LatLng(36.791581,30.649469), new LatLng(36.791581,31.121881),
                        new LatLng(36.588959,31.737116), new LatLng(36.403490,32.077692), new LatLng(36.146636,32.407282), new LatLng(36.040105,32.681940),
                        new LatLng(36.031221,32.945612), new LatLng(36.173247,33.461969), new LatLng(36.350417,34.088190), new LatLng(36.571314,34.395807),
                        new LatLng(36.809175,34.681452), new LatLng(36.641868,35.120905), new LatLng(36.544840,35.351618), new LatLng(36.650683,35.593317),
                        new LatLng(36.747578,35.692194), new LatLng(36.861933,35.977838), new LatLng(36.888299,36.120661), new LatLng(36.765182,36.186579),
                        new LatLng(36.606599,36.054743), new LatLng(36.350417,35.802057), new LatLng(35.897839,35.867975), new LatLng(35.585740,35.736139),
                        new LatLng(35.666111,34.472711), new LatLng(35.549994,34.220026), new LatLng(35.362065,33.824518), new LatLng(35.344144,33.582819),
                        new LatLng(35.371024,32.868708), new LatLng(35.209610,32.813776), new LatLng(35.128783,32.506159), new LatLng(34.894830,32.165583),
                        new LatLng(34.741500,32.308405), new LatLng(34.687316,32.868708), new LatLng(34.696349,33.330133), new LatLng(34.894830,33.703668),
                        new LatLng(35.047875,34.044245), new LatLng(35.182676,33.923395), new LatLng(35.290357,34.088190), new LatLng(35.603608,34.527643),
                        new LatLng(35.532115,35.725153), new LatLng(35.353105,35.900934), new LatLng(34.867793,35.867975), new LatLng(34.574317,35.967242),
                        new LatLng(34.479279,35.846392), new LatLng(34.365998,35.659625), new LatLng(34.234399,35.637652), new LatLng(34.066199,35.582721),
                        new LatLng(33.943247,35.560748), new LatLng(33.852056,35.472857), new LatLng(33.655667,35.445391), new LatLng(33.500065,35.313556),
                        new LatLng(33.316644,35.209185), new LatLng(33.165031,35.181720), new LatLng(33.100747,35.073629), new LatLng(32.833445,35.057150),
                        new LatLng(32.819597,34.941793), new LatLng(32.077922,34.749533), new LatLng(31.625323,34.518820), new LatLng(31.240987,34.084860),
                        new LatLng(31.128201,33.672872), new LatLng(31.114094,33.436666), new LatLng(31.071758,33.178488), new LatLng(31.175212,33.112570),
                        new LatLng(31.104687,32.964254), new LatLng(31.048230,32.700582), new LatLng(31.090576,32.502828), new LatLng(31.250380,32.381979),
                        new LatLng(31.273858,32.228170), new LatLng(31.353639,32.074362), new LatLng(31.442726,31.975485), new LatLng(31.545773,31.948019),
                        new LatLng(31.447831,31.535638), new LatLng(31.630419,31.118157), new LatLng(31.574276,30.799554), new LatLng(31.494683,30.574334),
                        new LatLng(31.480630,30.305169), new LatLng(31.321216,30.222772), new LatLng(31.297750,30.008538), new LatLng(31.175632,29.854730),
                        new LatLng(31.039238,29.635003), new LatLng(30.831922,29.146111), new LatLng(30.841355,28.915398), new LatLng(31.058063,28.525384),
                        new LatLng(31.086726,27.882716), new LatLng(31.227749,27.794826), new LatLng(31.218354,27.553127), new LatLng(31.462321,27.256496),
                        new LatLng(31.602782,26.289699), new LatLng(31.630849,25.927150), new LatLng(31.565347,25.663478), new LatLng(31.584066,25.180080),
                        new LatLng(31.929703,24.927394), new LatLng(32.069458,24.619777), new LatLng(32.004265,24.443996), new LatLng(32.088075,23.993556),
                        new LatLng(32.264756,23.652980), new LatLng(32.218294,23.312404), new LatLng(32.431820,23.059718), new LatLng(32.617085,23.103664),
                        new LatLng(32.912713,22.202785), new LatLng(32.885040,21.774318), new LatLng(32.949597,21.697414), new LatLng(32.829667,21.400783),
                        new LatLng(32.737302,21.148097), new LatLng(32.681838,20.785549), new LatLng(32.496706,20.499904), new LatLng(32.274045,20.280177),
                        new LatLng(31.994948,19.939601), new LatLng(31.724345,19.928615), new LatLng(31.415453,20.005519), new LatLng(31.170445,20.077217),
                        new LatLng(31.019922,20.099189), new LatLng(30.774814,19.912422), new LatLng(30.529080,19.637763), new LatLng(30.311180,19.352119),
                        new LatLng(30.301695,19.088447), new LatLng(30.443873,18.736885), new LatLng(30.727606,18.341377), new LatLng(30.982254,17.506416),
                        new LatLng(31.226830,16.693428), new LatLng(31.283181,16.078193), new LatLng(31.442659,15.671699), new LatLng(31.798160,15.440986),
                        new LatLng(32.328854,15.331123), new LatLng(32.477265,15.078437), new LatLng(32.505066,14.595039), new LatLng(32.745639,14.177558),
                        new LatLng(32.810298,13.716133), new LatLng(32.921033,13.199775), new LatLng(32.814869,12.337951), new LatLng(32.962477,12.063293),
                        new LatLng(33.137442,11.689758), new LatLng(33.201815,11.206359), new LatLng(33.513811,11.162414), new LatLng(33.833812,11.063537),
                        new LatLng(33.897669,10.733947), new LatLng(33.715093,10.657043), new LatLng(33.678532,10.349426), new LatLng(34.143527,10.030822),
                        new LatLng(34.388672,10.217590), new LatLng(34.542655,10.569152), new LatLng(35.200638,11.118469), new LatLng(35.612545,11.074524),
                        new LatLng(35.853334,10.744934), new LatLng(36.102271,10.514221), new LatLng(36.368117,10.569152), new LatLng(36.500701,10.832824),
                        new LatLng(36.879516,11.129455), new LatLng(37.081372,11.030578), new LatLng(37.055073,10.876770), new LatLng(36.879516,10.744934),
                        new LatLng(36.826771,10.547180), new LatLng(36.668314,10.371399), new LatLng(36.835564,10.217590), new LatLng(37.046305,10.360412),
                        new LatLng(37.142700,10.261535), new LatLng(37.221477,10.118713), new LatLng(37.317648,9.811096), new LatLng(37.317648,9.602356),
                        new LatLng(37.211804,9.047649), new LatLng(36.983972,8.663127), new LatLng(36.904948,8.179729), new LatLng(36.861010,7.850139),
                        new LatLng(37.071681,7.520549), new LatLng(37.045379,7.158000), new LatLng(36.931299,7.059123), new LatLng(36.896163,6.872356),
                        new LatLng(37.124257,6.399943), new LatLng(36.948861,6.191203), new LatLng(36.781859,5.971477), new LatLng(36.693816,5.444133),
                        new LatLng(36.737850,4.971721), new LatLng(36.896163,4.817912), new LatLng(36.878588,4.466350), new LatLng(36.931299,3.686320),
                        new LatLng(36.817047,3.367717), new LatLng(36.693816,2.554729), new LatLng(36.596853,1.961467), new LatLng(36.455765,1.106979),
                        new LatLng(36.243399,0.524703), new LatLng(36.048223,0.173141), new LatLng(35.790204,-0.090531), new LatLng(35.888172,-0.321244),
                        new LatLng(35.799115,-0.617875), new LatLng(35.736716,-0.936478), new LatLng(35.575240,-1.223201), new LatLng(35.338093,-1.322078),
                        new LatLng(35.311202,-1.525325), new LatLng(35.243935,-1.613216), new LatLng(35.100247,-1.915340), new LatLng(35.077773,-2.107601),
                        new LatLng(35.104741,-2.305355), new LatLng(35.145177,-2.475643), new LatLng(35.077773,-2.667903), new LatLng(35.163142,-2.832698),
                        new LatLng(35.458991,-2.975521), new LatLng(35.423188,-3.013973), new LatLng(35.373933,-3.052425), new LatLng(35.266364,-3.156795),
                        new LatLng(35.212525,-3.288631), new LatLng(35.212525,-3.414974), new LatLng(35.248422,-3.596248), new LatLng(35.288786,-3.717098),
                        new LatLng(35.257393,-3.804988), new LatLng(35.239449,-3.870906), new LatLng(35.261879,-4.030208), new LatLng(35.212525,-4.162044),
                        new LatLng(35.185593,-4.266414), new LatLng(35.149669,-4.519100), new LatLng(35.252907,-4.777278), new LatLng(35.382891,-5.007991),
                        new LatLng(35.637766,-5.299129), new LatLng(35.758213,-5.326595), new LatLng(35.918527,-5.321102), new LatLng(35.931871,-5.430965),
                        new LatLng(35.878479,-5.496883), new LatLng(35.793866,-5.661678), new LatLng(35.798322,-5.831966), new LatLng(35.798322,-5.941829),
                        new LatLng(34.978813,-6.271419), new LatLng(34.503037,-6.510119), new LatLng(34.299078,-6.680407), new LatLng(34.058223,-6.839709))
                .strokeColor(Color.argb(125, 224, 31, 31))
                .strokeWidth(7)
                .fillColor(Color.argb(75, 224, 31, 31))
                .visible(false)
        );
        greecePolygon = mMap.addPolygon(new PolygonOptions()
                .add (new LatLng(36.817966,21.653708), new LatLng(37.648921,21.346091), new LatLng(37.666316,20.796774), new LatLng(38.160364,20.335348),
                        new LatLng(38.925118,20.686911), new LatLng(39.453051,20.192526), new LatLng(39.419110,19.851950), new LatLng(39.783103,19.643210),
                        new LatLng(39.850611,20.005758), new LatLng(40.379919,19.324606), new LatLng(40.605503,20.005758), new LatLng(40.563786,20.840719),
                        new LatLng(41.087524,20.906637), new LatLng(41.228141,23.191794), new LatLng(41.054394,23.949850), new LatLng(41.062678,25.498923),
                        new LatLng(40.830329,26.147116), new LatLng(40.822015,26.806296), new LatLng(41.170277,27.817038), new LatLng(41.112361,28.586081),
                        new LatLng(40.921706,29.443014), new LatLng(40.772113,30.047262), new LatLng(40.647195,29.805563), new LatLng(40.563786,29.124411),
                        new LatLng(40.321311,29.267233), new LatLng(40.178764,28.267477), new LatLng(40.111579,26.960104), new LatLng(39.918053,26.894186),
                        new LatLng(39.664804,27.498434), new LatLng(39.232142,27.586325), new LatLng(38.651086,27.849997), new LatLng(38.186274,28.025778),
                        new LatLng(37.735856,28.102682), new LatLng(37.335110,28.146628), new LatLng(37.002438,28.662985), new LatLng(36.659491,28.564108),
                        new LatLng(36.217572,28.113669), new LatLng(35.388933,27.091940), new LatLng(34.903836,26.059225), new LatLng(34.876801,24.916647),
                        new LatLng(35.200628,23.686178), new LatLng(36.226435,22.741354), new LatLng(36.536008,22.345846), new LatLng(36.817966,21.653708))
                .strokeColor(Color.argb(150, 27, 250, 213))
                .fillColor(Color.argb(100, 27, 250, 213))
                .visible(false)
        );

        aztecPolygon = mMap.addPolygon(new PolygonOptions()
                .add (new LatLng(23.574788,-97.773770), new LatLng(24.797432,-98.762540), new LatLng(25.671955,-99.509610), new LatLng(26.264577,-100.300626),
                        new LatLng(26.008141,-101.157559), new LatLng(25.414229,-101.750821), new LatLng(24.657727,-102.014493), new LatLng(24.057225,-102.190274),
                        new LatLng(23.615059,-102.871426), new LatLng(22.900108,-103.822352), new LatLng(21.863942,-104.283778), new LatLng(20.881802,-104.217860),
                        new LatLng(20.655809,-104.745203), new LatLng(20.635248,-105.184656), new LatLng(20.429479,-105.624110), new LatLng(19.727820,-105.316492),
                        new LatLng(19.168410,-104.393641), new LatLng(19.147655,-103.976160), new LatLng(19.437996,-103.690516), new LatLng(19.147655,-103.470789),
                        new LatLng(18.523779,-103.448817), new LatLng(18.190107,-103.229090), new LatLng(17.834880,-102.152430), new LatLng(17.478943,-101.273524),
                        new LatLng(17.395091,-100.438563), new LatLng(17.730266,-99.603602), new LatLng(18.482105,-98.900477), new LatLng(19.645067,-98.307215),
                        new LatLng(20.037763,-97.779871), new LatLng(20.491239,-97.208582), new LatLng(20.676368,-97.142664), new LatLng(21.455518,-97.406336),
                        new LatLng(21.884332,-97.779871), new LatLng(22.454081,-97.823817), new LatLng(22.920347,-97.713953), new LatLng(23.574788,-97.773770))
                .strokeColor(Color.argb(125, 224, 31, 31))
                .fillColor(Color.argb(75, 224, 31, 31))
                .visible(false)
        );

        incaPolygon = mMap.addPolygon(new PolygonOptions()
                .add (new LatLng(2.432335,-78.752650), new LatLng(1.378245,-79.104213), new LatLng(-0.467320,-80.510463), new LatLng(-2.751423,-80.774134),
                        new LatLng(-3.190285,-79.895228), new LatLng(-4.505643,-81.389369), new LatLng(-6.168266,-81.213588), new LatLng(-6.866842,-79.719447),
                        new LatLng(-11.808609,-77.258509), new LatLng(-13.694419,-76.379603), new LatLng(-14.291394,-76.203822), new LatLng(-18.339269,-70.490931),
                        new LatLng(-21.887364,-70.139369), new LatLng(-28.019009,-71.018275), new LatLng(-29.406459,-71.457728), new LatLng(-29.941007,-71.106166),
                        new LatLng(-30.624081,-71.721400), new LatLng(-32.718319,-71.633509), new LatLng(-37.601498,-73.567103), new LatLng(-39.049368,-73.391322),
                        new LatLng(-40.668448,-73.654994), new LatLng(-43.281500,-74.182338), new LatLng(-43.217483,-73.479213), new LatLng(-41.660905,-73.039759),
                        new LatLng(-38.844302,-71.457728), new LatLng(-32.496206,-69.436244), new LatLng(-28.406254,-68.293666), new LatLng(-24.712274,-67.151088),
                        new LatLng(-21.397198,-65.569056), new LatLng(-19.171497,-65.393275), new LatLng(-17.251378,-66.799525), new LatLng(-14.035738,-69.699916),
                        new LatLng(-13.694419,-71.457728), new LatLng(-12.763985,-73.193568), new LatLng(-11.205744,-73.918666), new LatLng(-8.608580,-75.588588),
                        new LatLng(-5.381271,-76.906947), new LatLng(-1.170369,-77.434291), new LatLng(2.344520,-78.664759))
                .strokeColor(Color.argb(150, 25, 212, 19))
                .fillColor(Color.argb(100, 25, 212, 19))
                .visible(false)
        );

        mayanPolygon = mMap.addPolygon(new PolygonOptions()
                .add (new LatLng(21.597908,-87.068594), new LatLng(21.465052,-87.288320), new LatLng(21.608122,-88.310049), new LatLng(21.383235,-89.057119),
                        new LatLng(21.117012,-90.221670), new LatLng(20.840044,-90.474355), new LatLng(20.212430,-90.551260), new LatLng(19.892500,-90.463369),
                        new LatLng(19.592624,-90.738027), new LatLng(19.354394,-90.792959), new LatLng(19.063904,-90.990713), new LatLng(18.918467,-91.397207),
                        new LatLng(18.668853,-92.089346), new LatLng(18.595980,-92.748525), new LatLng(18.429295,-92.990225), new LatLng(18.408448,-93.374746),
                        new LatLng(18.293744,-94.000967), new LatLng(18.147647,-94.418447), new LatLng(18.387598,-94.737051), new LatLng(18.543908,-94.846914),
                        new LatLng(18.540222,-95.025670), new LatLng(18.685986,-95.289342), new LatLng(18.696393,-95.618932), new LatLng(18.039522,-95.256383),
                        new LatLng(17.516439,-95.223424), new LatLng(16.960322,-95.256383), new LatLng(16.444706,-95.399205), new LatLng(16.107234,-95.509068),
                        new LatLng(15.959408,-95.564000), new LatLng(16.276042,-94.816930), new LatLng(16.181105,-94.366490), new LatLng(16.033335,-93.938024),
                        new LatLng(14.209202,-91.828649), new LatLng(13.932128,-91.268891), new LatLng(13.900136,-90.609712), new LatLng(13.654721,-90.126313),
                        new LatLng(13.878806,-90.477876), new LatLng(13.515895,-89.796723), new LatLng(13.312850,-89.049653), new LatLng(13.152431,-88.368501),
                        new LatLng(13.227306,-87.830171), new LatLng(13.355610,-87.610444), new LatLng(13.152431,-87.412690), new LatLng(13.387676,-86.742524),
                        new LatLng(13.782796,-86.522798), new LatLng(13.942791,-85.951509), new LatLng(14.358256,-85.830659), new LatLng(14.719828,-85.588960),
                        new LatLng(15.038367,-85.490083), new LatLng(15.367023,-85.885591), new LatLng(15.663430,-86.445893), new LatLng(15.790331,-87.039155),
                        new LatLng(15.885454,-87.577485), new LatLng(15.822044,-87.972993), new LatLng(15.705739,-88.280610), new LatLng(15.938281,-88.533296),
                        new LatLng(15.737465,-88.588227), new LatLng(15.843183,-88.862885), new LatLng(16.022775,-88.884858), new LatLng(16.349851,-88.489350),
                        new LatLng(16.991845,-88.247651), new LatLng(17.809554,-88.203706), new LatLng(18.029075,-87.852143), new LatLng(19.187324,-87.489594),
                        new LatLng(19.539730,-87.423676), new LatLng(20.077213,-87.478608), new LatLng(20.509996,-87.225923), new LatLng(21.033876,-86.775483),
                        new LatLng(21.361651,-86.819428), new LatLng(21.597908,-87.068594))
                .strokeColor(Color.argb(150, 20, 10, 255))
                .fillColor(Color.argb(100, 20, 10, 255))
                .visible(false)
        );

        japanPolygon = mMap.addPolygon(new PolygonOptions()
                .add (new LatLng(45.644645,141.737883), new LatLng(45.197398,141.584074), new LatLng(44.699772,141.781828), new LatLng(44.213583,141.628020),
                        new LatLng(43.913596,141.628020), new LatLng(43.770966,141.320402), new LatLng(43.516560,141.364348), new LatLng(43.245074,141.034758),
                        new LatLng(43.293072,140.661223), new LatLng(43.341031,140.485441), new LatLng(43.341031,140.375578), new LatLng(43.213054,140.331633),
                        new LatLng(42.988447,140.507414), new LatLng(42.827509,140.331633), new LatLng(42.746882,139.936125), new LatLng(42.617661,139.738371),
                        new LatLng(42.195838,139.760344), new LatLng(41.978452,140.067961), new LatLng(41.568783,139.936125), new LatLng(41.371214,140.111906),
                        new LatLng(41.732965,140.617277), new LatLng(41.470074,140.837004), new LatLng(41.222641,140.551359), new LatLng(41.189579,140.287688),
                        new LatLng(40.791533,140.111906), new LatLng(40.524835,139.870207), new LatLng(39.920870,139.826262), new LatLng(39.616872,140.002043),
                        new LatLng(38.782533,139.782316), new LatLng(38.266835,139.386809), new LatLng(37.695308,138.815520), new LatLng(37.189391,137.980559),
                        new LatLng(36.856077,137.211516), new LatLng(37.136860,137.013762), new LatLng(37.521240,137.365324), new LatLng(37.468941,136.925871),
                        new LatLng(37.224392,136.618254), new LatLng(36.768120,136.684172), new LatLng(36.344522,136.178801), new LatLng(35.954188,135.959074),
                        new LatLng(35.669090,136.046965), new LatLng(35.508274,135.409758), new LatLng(35.561915,135.255949), new LatLng(35.722624,135.299895),
                        new LatLng(35.758293,135.058195), new LatLng(35.615521,134.728606), new LatLng(35.633381,134.311125), new LatLng(35.526158,133.344328),
                        new LatLng(35.526158,133.036711), new LatLng(35.382971,132.575285), new LatLng(34.879815,132.025969), new LatLng(34.554719,131.586516),
                        new LatLng(34.446070,131.256926), new LatLng(34.355421,130.883391), new LatLng(34.064686,130.773527), new LatLng(33.827726,130.487883),
                        new LatLng(33.626708,130.158293), new LatLng(33.333478,129.718840), new LatLng(33.351834,129.521086), new LatLng(31.777879,130.180266),
                        new LatLng(31.309731,130.180266), new LatLng(31.027719,130.707609), new LatLng(31.197027,131.191008), new LatLng(31.572183,131.520598),
                        new LatLng(31.927189,131.564543), new LatLng(32.799478,131.850188), new LatLng(33.168101,132.003996), new LatLng(33.718138,131.696379),
                        new LatLng(33.736413,131.212981), new LatLng(33.991846,131.169035), new LatLng(33.973626,131.850188), new LatLng(33.791213,132.069914),
                        new LatLng(34.192006,132.729094), new LatLng(33.991846,132.838957), new LatLng(33.590108,132.509367), new LatLng(33.186491,132.355559),
                        new LatLng(32.873325,132.685148), new LatLng(32.836409,132.970793), new LatLng(33.443555,133.454191), new LatLng(33.406878,134.003508),
                        new LatLng(33.149706,134.113371), new LatLng(33.809471,134.640715), new LatLng(34.192006,134.728606), new LatLng(34.264674,135.080168),
                        new LatLng(33.882468,135.102141), new LatLng(33.535180,135.431731), new LatLng(33.388534,135.761320), new LatLng(34.137464,136.310637),
                        new LatLng(34.319134,136.925871), new LatLng(34.590904,137.013762), new LatLng(34.590904,137.321379), new LatLng(34.663226,137.980559),
                        new LatLng(34.717427,138.310148), new LatLng(35.077854,138.837492), new LatLng(34.608990,138.771574), new LatLng(34.699364,138.947356),
                        new LatLng(34.843756,139.123137), new LatLng(35.077854,140.089934), new LatLng(35.544039,140.617277), new LatLng(35.811767,140.815031),
                        new LatLng(36.167338,140.639250), new LatLng(37.014145,140.968840), new LatLng(37.816913,141.056731), new LatLng(38.301330,141.144621),
                        new LatLng(38.335808,141.474211), new LatLng(38.662534,141.496184), new LatLng(39.498288,142.067473), new LatLng(40.021905,141.935637),
                        new LatLng(40.474710,141.671965), new LatLng(40.691646,141.474211), new LatLng(41.206112,141.474211), new LatLng(41.420662,141.452238),
                        new LatLng(41.519447,140.946867), new LatLng(41.749360,141.144621), new LatLng(42.011112,141.122648), new LatLng(42.174163,140.683195),
                        new LatLng(42.271792,140.353606), new LatLng(42.515204,140.529387), new LatLng(42.385502,140.837004), new LatLng(42.450386,141.298430),
                        new LatLng(42.660798,141.759856), new LatLng(42.206723,142.462981), new LatLng(41.945775,143.210051), new LatLng(42.222996,143.385832),
                        new LatLng(42.838284,143.825285), new LatLng(42.967046,144.352629), new LatLng(42.886601,144.836027), new LatLng(43.335737,145.714934),
                        new LatLng(43.383663,145.605070), new LatLng(43.287773,145.341398), new LatLng(43.781576,145.099699), new LatLng(44.365657,145.341398),
                        new LatLng(43.940004,144.682219), new LatLng(44.129562,143.891203), new LatLng(44.506859,143.100188), new LatLng(44.990610,142.572844),
                        new LatLng(45.547304,141.869719), new LatLng(45.644645,141.737883))
                .strokeColor(Color.argb(125, 224, 31, 31))
                .fillColor(Color.argb(75, 224, 31, 31))
                .visible(false)
        );

        indiaPolygon = mMap.addPolygon(new PolygonOptions()
                .add (new LatLng(25.503078,59.008361), new LatLng(26.647749,59.975158), new LatLng(27.508560,62.567931), new LatLng(27.781059,64.062072),
                        new LatLng(29.171633,64.853088), new LatLng(31.109667,65.688048), new LatLng(32.898311,67.138244), new LatLng(34.216614,69.071838),
                        new LatLng(36.013824,71.313048), new LatLng(35.978269,73.422423), new LatLng(35.120175,74.828673), new LatLng(32.472510,77.894131),
                        new LatLng(30.372686,80.926358), new LatLng(28.882968,84.441983), new LatLng(28.149310,87.913663), new LatLng(27.019789,89.627530),
                        new LatLng(25.005774,90.066983), new LatLng(22.634090,90.945889), new LatLng(21.861295,90.682217), new LatLng(21.371040,87.166592),
                        new LatLng(20.426807,86.639248), new LatLng(19.010597,84.664096), new LatLng(17.214675,82.906283), new LatLng(15.739803,80.313510),
                        new LatLng(13.614374,80.093783), new LatLng(11.297356,79.742221), new LatLng(10.347767,79.830112), new LatLng(8.918059,78.335971),
                        new LatLng(8.266281,78.028354), new LatLng(8.135793,77.325229), new LatLng(9.004877,76.490268), new LatLng(10.520642,76.050815),
                        new LatLng(12.200863,75.084018), new LatLng(16.331096,73.414096), new LatLng(19.508426,72.579135), new LatLng(21.729286,72.710971),
                        new LatLng(21.770102,72.315463), new LatLng(20.828412,71.524447), new LatLng(20.787333,70.645541), new LatLng(20.746243,70.381869),
                        new LatLng(22.218318,68.975619), new LatLng(22.665107,70.206088), new LatLng(22.827217,70.337924), new LatLng(22.827217,69.459018),
                        new LatLng(23.191258,68.580112), new LatLng(23.996683,67.525424), new LatLng(25.513088,66.558627), new LatLng(25.076038,61.988315),
                        new LatLng(25.394049,59.175815))
                        .strokeColor(Color.argb(150, 25, 212, 19))
                        .fillColor(Color.argb(100, 25, 212, 19))
                .visible(false)
        );

        chinaPolygon = mMap.addPolygon(new PolygonOptions()
                .add (new LatLng(40.436655,128.868742), new LatLng(41.695632,130.450774), new LatLng(42.672493,131.329680), new LatLng(44.455499,131.593352),
                        new LatLng(46.489082,130.099211), new LatLng(48.449383,125.089446), new LatLng(49.201585,119.816008), new LatLng(49.715736,116.036711),
                        new LatLng(49.144124,111.290617), new LatLng(47.863072,107.247649), new LatLng(46.002831,103.380461), new LatLng(44.329892,101.534758),
                        new LatLng(42.801600,100.040617), new LatLng(42.218503,98.194914), new LatLng(40.836830,93.976164), new LatLng(39.493606,91.427336),
                        new LatLng(37.498998,90.812102), new LatLng(36.233366,91.515227), new LatLng(34.802700,95.119101), new LatLng(35.090869,98.283163),
                        new LatLng(34.946911,100.480429), new LatLng(33.859186,101.798788), new LatLng(32.387097,101.974570), new LatLng(30.437015,101.447226),
                        new LatLng(28.060047,100.216757), new LatLng(27.359757,98.371054), new LatLng(26.655010,94.240195), new LatLng(26.890416,92.746054),
                        new LatLng(25.629334,92.746054), new LatLng(23.793076,92.658163), new LatLng(22.824482,92.570273), new LatLng(23.390353,95.294882),
                        new LatLng(23.632136,98.195273), new LatLng(22.418839,99.689413), new LatLng(21.112883,99.513632), new LatLng(19.795330,100.392538),
                        new LatLng(20.125773,101.271445), new LatLng(20.620128,103.380820), new LatLng(21.848947,105.314413), new LatLng(21.848947,107.160117),
                        new LatLng(21.848947,108.302695), new LatLng(21.276773,109.796835), new LatLng(20.620128,109.621054), new LatLng(20.125773,110.236288),
                        new LatLng(20.537846,110.499960), new LatLng(21.112883,110.587851), new LatLng(21.848947,112.433554), new LatLng(23.067290,116.388632),
                        new LatLng(24.274700,118.366171), new LatLng(25.343102,119.530722), new LatLng(28.832827,121.925742), new LatLng(30.209416,122.101523),
                        new LatLng(30.815167,121.222617), new LatLng(31.716664,122.277304), new LatLng(32.905132,121.398398), new LatLng(34.441066,120.255820),
                        new LatLng(34.946911,119.289023), new LatLng(36.233366,120.783163), new LatLng(36.939084,122.189413), new LatLng(37.359409,122.540976),
                        new LatLng(37.916200,120.519492), new LatLng(37.289517,119.728476), new LatLng(37.149538,119.025351), new LatLng(38.054745,118.849570),
                        new LatLng(38.399961,117.794882), new LatLng(39.289834,117.706992), new LatLng(38.812054,118.410117), new LatLng(39.221777,119.025351),
                        new LatLng(39.899362,119.640585), new LatLng(40.101345,120.343710), new LatLng(40.836830,121.222617), new LatLng(40.637042,121.925742),
                        new LatLng(39.966756,121.925742), new LatLng(39.493606,121.310507), new LatLng(39.051348,121.560049), new LatLng(38.846288,120.988759),
                        new LatLng(38.674953,121.164541), new LatLng(39.865640,123.977041), new LatLng(39.730587,125.646963), new LatLng(39.933068,127.228994),
                        new LatLng(40.269208,128.503408))
                        .strokeColor(Color.argb(150, 20, 10, 255))
                        .fillColor(Color.argb(100, 20, 10, 255))
                .visible(false)
        );
        norsePolygon = mMap.addPolygon(new PolygonOptions()
                .add (new LatLng(52.789419,1.584981), new LatLng(52.975052,1.057637), new LatLng(52.948581,0.530294), new LatLng(52.842538,0.222677),
                        new LatLng(52.975052,0.134786), new LatLng(53.159890,0.354512), new LatLng(53.553307,0.178731), new LatLng(53.709658,-0.216777),
                        new LatLng(53.709658,0.090841), new LatLng(54.200955,-0.304667), new LatLng(54.533778,-0.700175), new LatLng(54.787963,-1.227519),
                        new LatLng(55.291575,-1.666972), new LatLng(54.939712,-2.018534), new LatLng(54.508272,-2.545878), new LatLng(54.093263,-2.754172),
                        new LatLng(53.938340,-2.842063), new LatLng(53.873618,-3.061789), new LatLng(53.704872,-2.864035), new LatLng(53.600690,-3.039817),
                        new LatLng(53.470101,-2.929953), new LatLng(53.378449,-3.171653), new LatLng(53.273462,-2.951926), new LatLng(53.234025,-2.380637),
                        new LatLng(53.062711,-2.798117), new LatLng(53.009862,-3.149680), new LatLng(52.744642,-3.413352), new LatLng(52.598079,-3.611106),
                        new LatLng(52.504556,-3.874778), new LatLng(52.477799,-3.984641), new LatLng(52.370605,-4.050559), new LatLng(52.263151,-4.006614),
                        new LatLng(52.222789,-3.369406), new LatLng(52.182390,-2.929953), new LatLng(51.979842,-2.512473), new LatLng(51.803557,-2.270774),
                        new LatLng(51.435214,-2.248801), new LatLng(51.160437,-2.490500), new LatLng(50.856281,-2.776145), new LatLng(50.703454,-2.798117),
                        new LatLng(50.647757,-2.578391), new LatLng(50.605941,-2.248801), new LatLng(50.591994,-1.897239), new LatLng(50.703454,-1.875266),
                        new LatLng(50.745184,-1.545676), new LatLng(50.675614,-1.545676), new LatLng(50.578043,-1.325949), new LatLng(50.619884,-1.128196),
                        new LatLng(50.703454,-1.106223), new LatLng(50.814650,-1.303977), new LatLng(50.786876,-1.062278), new LatLng(50.745184,-0.842551),
                        new LatLng(50.814650,-0.183371), new LatLng(50.717368,0.212136), new LatLng(50.856281,0.673562), new LatLng(50.911730,0.827371),
                        new LatLng(50.884014,0.959207), new LatLng(51.036251,1.113015), new LatLng(51.229285,1.464578), new LatLng(51.380390,1.398660),
                        new LatLng(51.407810,0.937234), new LatLng(51.435214,0.541726), new LatLng(51.585641,0.893289), new LatLng(51.694730,0.937234),
                        new LatLng(51.708348,0.805398), new LatLng(51.844300,1.332742), new LatLng(52.047460,1.508523), new LatLng(52.384019,1.772195),
                        new LatLng(52.731339,1.684304), new LatLng(52.744642,1.618386), new LatLng(54.577988,10.019866), new LatLng(54.730525,10.041839),
                        new LatLng(54.857201,9.690276), new LatLng(54.945639,10.063812), new LatLng(55.033882,9.844085), new LatLng(55.059059,9.580413),
                        new LatLng(55.222323,9.690276), new LatLng(55.447279,9.690276), new LatLng(55.008690,10.283538), new LatLng(55.046473,10.679046),
                        new LatLng(54.743210,10.657073), new LatLng(54.616176,11.382171), new LatLng(54.603451,11.909515), new LatLng(54.945639,12.480804),
                        new LatLng(55.059059,12.217132), new LatLng(55.234854,12.414886), new LatLng(55.446408,12.303180), new LatLng(55.620496,12.588825),
                        new LatLng(55.545981,12.874469), new LatLng(55.359075,13.072223), new LatLng(55.384047,14.170856), new LatLng(55.545981,14.368610),
                        new LatLng(55.769102,14.236774), new LatLng(56.003240,14.566364), new LatLng(56.027804,14.786090), new LatLng(56.138149,15.928669),
                        new LatLng(56.309169,16.543903), new LatLng(57.236206,17.181110), new LatLng(56.913746,18.213825), new LatLng(57.200515,18.521442),
                        new LatLng(57.271862,18.785114), new LatLng(57.437801,18.916950), new LatLng(57.732259,18.829059), new LatLng(57.919463,19.202594),
                        new LatLng(57.989414,19.180622), new LatLng(57.896116,18.894977), new LatLng(57.896116,18.587360), new LatLng(57.579438,18.081989),
                        new LatLng(56.985646,18.235797), new LatLng(57.366776,17.027301), new LatLng(56.949714,16.741657), new LatLng(56.805636,16.543903),
                        new LatLng(56.503687,16.412067), new LatLng(56.345717,16.412067), new LatLng(56.248178,15.950641), new LatLng(57.021544,16.543903),
                        new LatLng(57.496883,16.697712), new LatLng(58.012701,16.785602), new LatLng(58.612860,16.873493), new LatLng(59.090203,18.323688),
                        new LatLng(59.315203,18.763141), new LatLng(59.404788,18.587360), new LatLng(59.738633,19.026813), new LatLng(59.992256,19.099651),
                        new LatLng(60.211305,18.748088), new LatLng(60.363775,18.352580), new LatLng(60.483074,18.088909), new LatLng(60.634277,17.957073),
                        new LatLng(60.515534,17.627483), new LatLng(60.666586,17.605510), new LatLng(60.688107,17.253948), new LatLng(61.652206,17.100139),
                        new LatLng(61.673065,17.319866), new LatLng(61.599996,17.583537), new LatLng(61.745961,17.407756), new LatLng(62.056427,17.473674),
                        new LatLng(62.230963,17.627483), new LatLng(62.333162,17.429729), new LatLng(62.414672,17.583537), new LatLng(62.581320,17.978962),
                        new LatLng(63.202061,18.879841), new LatLng(63.458480,19.473103), new LatLng(63.595608,20.066365), new LatLng(63.935555,20.901325),
                        new LatLng(64.663948,20.137652), new LatLng(64.494171,18.929156), new LatLng(64.389896,18.072222), new LatLng(64.418374,16.094683),
                        new LatLng(64.541437,14.512652), new LatLng(64.981761,13.677691), new LatLng(65.551717,14.139117), new LatLng(65.984581,14.908160),
                        new LatLng(67.189202,16.907672), new LatLng(67.993237,18.072222), new LatLng(68.714527,18.511676), new LatLng(69.031312,18.643512),
                        new LatLng(69.289266,18.511676), new LatLng(69.420964,18.357867), new LatLng(69.536503,17.962359), new LatLng(69.597870,17.632769),
                        new LatLng(69.574878,17.500933), new LatLng(69.459546,17.171344), new LatLng(69.382313,16.907672), new LatLng(69.250377,16.907672),
                        new LatLng(69.125466,16.885699), new LatLng(69.062742,16.753863), new LatLng(68.991961,17.193316), new LatLng(69.148942,17.566851),
                        new LatLng(69.070592,17.544879), new LatLng(68.991961,17.325152), new LatLng(68.881404,17.061480), new LatLng(68.865565,16.644000),
                        new LatLng(69.015580,16.424273), new LatLng(68.920952,16.094683), new LatLng(68.984083,15.809039), new LatLng(69.289266,16.226519),
                        new LatLng(69.335839,16.138629), new LatLng(69.188011,15.699176), new LatLng(69.023447,15.479449), new LatLng(68.865565,14.732379),
                        new LatLng(68.738443,14.424762), new LatLng(68.602577,14.358844), new LatLng(68.610592,14.710406), new LatLng(68.457818,14.512652),
                        new LatLng(68.352689,14.314898), new LatLng(68.287753,13.765582), new LatLng(68.295880,13.523883), new LatLng(68.214477,13.392047),
                        new LatLng(68.100025,13.260211), new LatLng(67.952032,12.820758), new LatLng(66.351348,-14.593512), new LatLng(66.333715,-15.032965),
                        new LatLng(66.245359,-15.384527), new LatLng(66.404176,-15.736090), new LatLng(66.561990,-16.131598), new LatLng(66.527007,-16.658941),
                        new LatLng(66.263055,-16.571051), new LatLng(66.138923,-16.878668), new LatLng(66.209930,-17.274176), new LatLng(66.049883,-17.493902),
                        new LatLng(66.174451,-18.109137), new LatLng(66.138923,-18.372808), new LatLng(65.978426,-18.197027), new LatLng(65.978426,-18.372808),
                        new LatLng(66.227651,-18.724371), new LatLng(66.049883,-19.515387), new LatLng(65.726745,-19.471441), new LatLng(66.138923,-20.042730),
                        new LatLng(66.067716,-20.350348), new LatLng(65.695751,-20.563847), new LatLng(65.659552,-21.047245), new LatLng(65.496027,-21.179081),
                        new LatLng(65.659552,-21.398808), new LatLng(66.001411,-21.223027), new LatLng(66.285784,-21.970097), new LatLng(66.496977,-22.673222),
                        new LatLng(66.426777,-23.332402), new LatLng(66.285784,-23.200566), new LatLng(66.161763,-23.640019), new LatLng(65.858024,-23.903691),
                        new LatLng(65.605158,-24.387089), new LatLng(65.441290,-24.518925), new LatLng(65.496027,-24.035527), new LatLng(65.496027,-23.068730),
                        new LatLng(65.477794,-22.277714), new LatLng(65.404735,-21.882206), new LatLng(65.147417,-22.453495), new LatLng(65.128942,-21.970097),
                        new LatLng(65.054909,-21.838261), new LatLng(64.906225,-24.123417), new LatLng(64.775446,-23.991581), new LatLng(64.737964,-23.332402),
                        new LatLng(64.756712,-22.365605), new LatLng(64.131136,-22.014042), new LatLng(64.054334,-22.277714), new LatLng(64.092762,-22.585331),
                        new LatLng(63.900094,-22.761113), new LatLng(63.647635,-40.822636), new LatLng(63.803262,-41.657597), new LatLng(63.667135,-42.360722),
                        new LatLng(63.274576,-43.547245), new LatLng(63.135904,-44.601933), new LatLng(63.254806,-46.183964), new LatLng(63.491150,-47.765995),
                        new LatLng(64.303166,-48.293339), new LatLng(65.441290,-48.820683), new LatLng(66.619352,-49.172245), new LatLng(67.876709,-49.919316),
                        new LatLng(68.319291,-50.446660), new LatLng(68.497176,-51.369511), new LatLng(68.577573,-52.204472), new LatLng(68.657683,-52.556035),
                        new LatLng(68.705612,-53.171269), new LatLng(68.432652,-53.478886), new LatLng(68.156362,-53.347050), new LatLng(67.975796,-53.478886),
                        new LatLng(67.793812,-53.698613), new LatLng(67.610401,-53.742558), new LatLng(67.000055,-53.874394), new LatLng(66.741126,-53.259160),
                        new LatLng(66.444345,-53.610722), new LatLng(66.019278,-53.610722), new LatLng(65.767999,-52.951542), new LatLng(65.066129,-52.292363),
                        new LatLng(64.542322,-52.204472), new LatLng(63.950422,-51.721074), new LatLng(63.286541,-51.237675), new LatLng(62.505747,-50.270878),
                        new LatLng(61.410939,-48.864628), new LatLng(60.709234,-47.941777), new LatLng(60.837975,-47.150761), new LatLng(60.623119,-46.271855),
                        new LatLng(60.406823,-45.480839), new LatLng(59.925867,-45.041386), new LatLng(59.793473,-43.810917), new LatLng(60.101575,-43.151738),
                        new LatLng(61.932253,-42.140995), new LatLng(62.728079,-42.448613), new LatLng(62.888731,-41.701542), new LatLng(63.108199,-41.306035),
                        new LatLng(63.619262,-40.653544), new LatLng(63.633903,-40.620585), new LatLng(63.894898,-22.760346), new LatLng(63.815020,-22.730134),
                        new LatLng(63.801685,-22.716401), new LatLng(63.841671,-21.376069), new LatLng(63.836677,-21.080483), new LatLng(63.528905,-20.181036),
                        new LatLng(63.522783,-19.741583), new LatLng(63.398836,-19.129095), new LatLng(63.394111,-18.679342), new LatLng(63.466593,-18.149252),
                        new LatLng(63.563358,-17.869100), new LatLng(63.710933,-17.676840), new LatLng(63.789893,-16.817160), new LatLng(63.795436,-16.644125),
                        new LatLng(64.142574,-15.910787), new LatLng(64.223579,-15.594624), new LatLng(64.241489,-14.954670), new LatLng(64.405717,-14.528950),
                        new LatLng(64.618101,-14.349367), new LatLng(64.833867,-13.690187), new LatLng(65.149685,-13.426515), new LatLng(65.232661,-13.646242),
                        new LatLng(65.507376,-13.536379), new LatLng(65.688928,-14.283449), new LatLng(65.788242,-14.349367), new LatLng(65.725086,-14.788820),
                        new LatLng(65.923056,-14.613039), new LatLng(66.030399,-14.613039), new LatLng(66.066080,-14.744875), new LatLng(66.030399,-15.030519),
                        new LatLng(66.119507,-15.118410), new LatLng(66.190569,-14.898683), new LatLng(66.269170,-15.015413), new LatLng(66.344221,-14.683077),
                        new LatLng(67.953869,12.900211), new LatLng(67.886761,12.856266), new LatLng(67.845367,12.823307), new LatLng(67.836044,12.812320),
                        new LatLng(68.144833,14.060642), new LatLng(68.372723,16.301853), new LatLng(67.581845,14.631931), new LatLng(67.193227,14.368259),
                        new LatLng(66.484619,13.181736), new LatLng(66.078061,12.786228), new LatLng(65.519620,12.346775), new LatLng(64.967565,11.379978),
                        new LatLng(65.023290,10.588962), new LatLng(64.837085,10.632907), new LatLng(64.818393,11.028415), new LatLng(64.818393,11.379978),
                        new LatLng(64.384887,10.413181), new LatLng(64.060006,9.929782), new LatLng(63.789582,9.490329), new LatLng(63.575270,9.226657),
                        new LatLng(63.828374,8.875095), new LatLng(63.672886,8.303806), new LatLng(63.379031,7.864353), new LatLng(63.082139,7.512790),
                        new LatLng(62.620951,6.370212), new LatLng(62.111357,5.095712), new LatLng(61.697449,4.832040), new LatLng(60.702515,4.963876),
                        new LatLng(59.206620,5.271493), new LatLng(58.616656,5.667001), new LatLng(58.132756,6.633798), new LatLng(58.132756,8.303720),
                        new LatLng(58.708076,9.314462), new LatLng(59.003541,10.281259), new LatLng(59.206620,10.632822), new LatLng(59.586941,10.676767),
                        new LatLng(59.274046,10.940439), new LatLng(58.730894,11.204111), new LatLng(58.086324,11.292001), new LatLng(56.689150,12.654306),
                        new LatLng(56.325397,12.610361), new LatLng(55.908913,11.555673), new LatLng(55.562532,10.413095), new LatLng(55.908913,10.413095),
                        new LatLng(56.203370,10.588876), new LatLng(56.447036,11.072275), new LatLng(56.665008,10.588876), new LatLng(57.049422,10.457040),
                        new LatLng(57.642193,10.544931), new LatLng(57.736150,10.544931), new LatLng(57.618665,9.929697), new LatLng(57.287679,9.446298),
                        new LatLng(57.121060,8.655282), new LatLng(56.977645,8.259775), new LatLng(56.471317,8.040048), new LatLng(55.859618,8.127939),
                        new LatLng(55.487908,8.215829), new LatLng(55.338233,8.743173), new LatLng(54.860513,8.523447), new LatLng(54.453782,8.918954),
                        new LatLng(54.375090,8.607576), new LatLng(54.291815,8.635042), new LatLng(52.762853,1.649778), new LatLng(52.789419,1.584981))
                .strokeColor(Color.argb(150, 25, 212, 19))
                .fillColor(Color.argb(100, 25, 212, 19))
                .visible(false)
        );
        //add all polygons to list
        Collections.addAll(polygonList, egyptPolygon, romePolygon, greecePolygon, aztecPolygon, incaPolygon, mayanPolygon, japanPolygon, chinaPolygon, indiaPolygon, norsePolygon);
    }

    /**
     * determines which marker was clicked and calls markerFocus
     * @param marker - marker that was clicked
     * @return if marker was clicked
     */
    @Override
    public boolean onMarkerClick(Marker marker) {
        clearPolygons();
        if (marker.equals(egyptMarker)) {
           markerFocus(egyptMarker, egyptLocation, egyptPolygon);
           return true;
        } else if (marker.equals(romeMarker)) {
            markerFocus(romeMarker, romeLocation, romePolygon);
            return true;
        } else if (marker.equals(greeceMarker)) {
            markerFocus(greeceMarker, greeceLocation, greecePolygon);
            return true;
        } else if (marker.equals(aztecMarker)) {
            markerFocus(aztecMarker, aztecLocation, aztecPolygon);
            return true;
        } else if (marker.equals(incaMarker)) {
            markerFocus(incaMarker, incaLocation, incaPolygon);
            return true;
        } else if (marker.equals(mayanMarker)) {
            markerFocus(mayanMarker, mayanLocation, mayanPolygon);
            return true;
        } else if (marker.equals(japanMarker)) {
            markerFocus(japanMarker, japanLocation, japanPolygon);
            return true;
        } else if (marker.equals(chinaMarker)) {
            markerFocus(chinaMarker, chinaLocation, chinaPolygon);
            return true;
        } else if (marker.equals(indiaMarker)) {
            markerFocus(indiaMarker, indiaLocation, indiaPolygon);
            return true;
        } else if (marker.equals(norseMarker)) {
            markerFocus(norseMarker, norseLocation, norsePolygon);
            return true;
        }
        return false;
    }

    /**
     * sets the camera position to move to the marker and displays its info window
     * @param marker - marker that was determined to be clicked
     * @param location - location of marker
     */
    public void markerFocus(Marker marker, LatLng location, Polygon polygon){
        mMap.animateCamera(CameraUpdateFactory.newLatLng(location));
        marker.showInfoWindow();
        polygon.setVisible(true);
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
        }
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
        clearPolygons();
    }

/**
 * updates the markers as the seekbar is moved
 * @param year - current year set by seekbar
 */
    private void updateMarkers(int year) {
        //add all ranges to list
        Collections.addAll(rangeList, egyptRange, romeRange, greeceRange, aztecRange, incaRange, mayanRange, japanRange, chinaRange, indiaRange, norseRange);

        //dangerous if lists are out of order from one another
        for (int i = 0; i < markerList.size(); i++) {
            checkRange(rangeList.get(i), markerList.get(i), year);
        }
    }

    /**
     * clears all polygons (civ areas) from map
     */
    private void clearPolygons() {
        for (Polygon polygon : polygonList) {
            polygon.setVisible(false);
        }
    }

    /**
     * clears all polygons (civ areas) from map
     */
    private void allPolygons() {
        seekYear.setVisibility(View.INVISIBLE);
        tvYear.setVisibility(View.INVISIBLE);
        for (Polygon polygon : polygonList) {
            polygon.setVisible(true);
        }
    }

    /**
     * changes mode to fullscreen - time features hidden, all civilizations shown
     */
    private void fullscreen() {
        seekYear.setVisibility(View.INVISIBLE);
        tvYear.setVisibility(View.INVISIBLE);
        mMap.animateCamera(CameraUpdateFactory.zoomBy(-10));
        clearPolygons();
        for (Marker marker : markerList) {
            marker.setVisible(true);
        }
    }

    /**
     * changes mode to minimized - time features visible, civs only shown during correct time period
     */
    private void minimize() {
        seekYear.setVisibility(View.VISIBLE);
        tvYear.setVisibility(View.VISIBLE);
        clearPolygons();
        updateYear();
    }
}
   /* Seekbar styling help: https://www.tutorialsbuzz.com/2019/09/android-styling-seekbar-thumb-and-progressTrack.html*/


