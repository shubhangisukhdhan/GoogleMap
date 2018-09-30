package shubhangi.mygooglemap;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolygonOptions;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.android.gms.tasks.OnSuccessListener;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;

    private static final int REQUEST_PERMISSION_LOCATION = 1;
    private FusedLocationProviderClient mFusedLocationClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {

        mMap = googleMap;

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED){

            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    REQUEST_PERMISSION_LOCATION);
        }
        else {
            getMyLocation();
            Toast.makeText(this,"Permission is allowed",Toast.LENGTH_LONG).show();
        }






        // Add a marker in Sydney and move the camera
        LatLng VimanNagar = new LatLng(18.5679, 73.9143);
        LatLng Kharadi = new LatLng(18.5515, 73.9348);
        LatLng KalyaniNagar = new LatLng(18.5679, 73.9143);
        LatLng ChandanNagar = new LatLng(18.5636, 73.9326);

        MarkerOptions markerOptionsVimanNagar = new MarkerOptions().position(VimanNagar).title("Marker in VimanNagar");
        MarkerOptions markerOptionsKharadi = new MarkerOptions().position(Kharadi).title("Marker in VimanNagar");
        MarkerOptions markerOptionsKalyaniNagar = new MarkerOptions().position(KalyaniNagar).title("Marker in VimanNagar");
        MarkerOptions markerOptionsChandanNagar = new MarkerOptions().position(ChandanNagar).title("Marker in VimanNagar");


        mMap.addMarker(markerOptionsVimanNagar);
        mMap.addMarker(markerOptionsKharadi);
        mMap.addMarker(markerOptionsChandanNagar);
        mMap.addMarker(markerOptionsKalyaniNagar);

        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(VimanNagar, 12.0f));
        CircleOptions circleOptions = new CircleOptions().center(VimanNagar)
                .radius(1000).strokeColor(Color.BLACK).strokeWidth(3).fillColor(Color.BLUE);

        mMap.addCircle(circleOptions);

        PolylineOptions polylineOptions = new PolylineOptions();
        polylineOptions.add(VimanNagar);
        polylineOptions.add(Kharadi);
        polylineOptions.add(KalyaniNagar);
        polylineOptions.add(ChandanNagar);
        polylineOptions.width(10);
        polylineOptions.color(Color.GREEN);




        mMap.addPolyline(polylineOptions);

        PolygonOptions polygonOptions = new PolygonOptions();
        polygonOptions.add(VimanNagar);
        polygonOptions.add(Kharadi);
        polygonOptions.add(KalyaniNagar);
        polygonOptions.add(ChandanNagar);
        polygonOptions.add(VimanNagar);
        polygonOptions.fillColor(Color.RED);

        mMap.addPolygon(polygonOptions);
    }

    @SuppressLint("MissingPermission")
    private void getMyLocation() {

        mFusedLocationClient.getLastLocation().addOnSuccessListener(new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {

                if (location != null){

                    LatLng myLocation = new LatLng(location.getLatitude(),location.getLongitude());
               MarkerOptions markerOptions=new MarkerOptions().position(myLocation).title("My loacation");
               mMap.addMarker(markerOptions);

                }

            }
        });

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
         if (requestCode == REQUEST_PERMISSION_LOCATION){

             if (grantResults.length>0 && grantResults[0]== PackageManager.PERMISSION_GRANTED) {
             }
             else {

                 Toast.makeText(this,"This permission mandatary",Toast.LENGTH_LONG).show();
                 ActivityCompat.requestPermissions(this,new String[]{
                         Manifest.permission.ACCESS_FINE_LOCATION},REQUEST_PERMISSION_LOCATION);
                 }
             }
         }
    }

