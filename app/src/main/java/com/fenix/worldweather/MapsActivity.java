package com.fenix.worldweather;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.app.ActionBar;
import android.support.v4.app.FragmentManager;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.GroundOverlayOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.lang.ref.WeakReference;

public class MapsActivity extends FragmentActivity {

    private GoogleMap mMap; // Might be null if Google Play services APK is not available.
    private WeatherData data=null;
    private WeakReference<Activity> weakActivity = new WeakReference<Activity>(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().
                findFragmentById(R.id.map);
        if (savedInstanceState == null) {
            mapFragment.setRetainInstance(true);
        }else{
            mMap = mapFragment.getMap();
        }


        ActionBar actionBar = getActionBar();
        actionBar.setCustomView(R.layout.actionbar);

        final EditText searchfield = (EditText) actionBar.getCustomView().findViewById(R.id.searchfield);
        ImageButton search = (ImageButton) actionBar.getCustomView().findViewById(R.id.search_button);
        final Context context = this;
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                WeatherTask task =  new WeatherTask(weakActivity.get(),data);
                task.execute(searchfield.getText().toString());

                //Toast.makeText(getBaseContext(), searchfield.getText().toString(), Toast.LENGTH_LONG).show();
            }
        });

        actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM | ActionBar.DISPLAY_SHOW_HOME);
        setUpMapIfNeeded();
    }

    @Override
    protected void onResume() {
        super.onResume();
        setUpMapIfNeeded();
    }

    /**
     * Sets up the map if it is possible to do so (i.e., the Google Play services APK is correctly
     * installed) and the map has not already been instantiated.. This will ensure that we only ever
     * call {@link #setUpMap()} once when {@link #mMap} is not null.
     * <p/>
     * If it isn't installed {@link SupportMapFragment} (and
     * {@link com.google.android.gms.maps.MapView MapView}) will show a prompt for the user to
     * install/update the Google Play services APK on their device.
     * <p/>
     * A user can return to this FragmentActivity after following the prompt and correctly
     * installing/updating/enabling the Google Play services. Since the FragmentActivity may not
     * have been completely destroyed during this process (it is likely that it would only be
     * stopped or paused), {@link #onCreate(Bundle)} may not be called again so we should call this
     * method in {@link #onResume()} to guarantee that it will be called.
     */
    private void setUpMapIfNeeded() {
        // Do a null check to confirm that we have not already instantiated the map.
        if (mMap == null) {
            // Try to obtain the map from the SupportMapFragment.
            mMap = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map))
                    .getMap();
            // Check if we were successful in obtaining the map.
            if (mMap != null) {
                setUpMap();
            }
        }
    }

    /**
     * This is where we can add markers or lines, add listeners or move the camera. In this case, we
     * just add a marker near Africa.
     * <p/>
     * This should only be called once and when we are sure that {@link #mMap} is not null.
     */
    private void setUpMap() {
        //mMap.addMarker(new MarkerOptions().position(new LatLng(0, 0)).title("Marker"));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu_weather,menu);
        menu.findItem(R.id.action_search).getActionView();
        return super.onCreateOptionsMenu(menu);
    }

    public void showWeather(WeatherData data){

        LatLng latLng = new LatLng(data.coord.getLat(),data.coord.getLon());
        Bitmap origin = data.getImage();
        Bitmap output = Bitmap.createScaledBitmap(data.getImage(),100,100,false);



       // GroundOverlayOptions newarkMap = new GroundOverlayOptions().image(BitmapDescriptorFactory.
         //       fromBitmap(b)).position(latLng,50000f,50000f);
        //mMap.addGroundOverlay(newarkMap);

        mMap.addMarker(new MarkerOptions().position(latLng).icon(BitmapDescriptorFactory.
                fromBitmap(output)).title(data.weather.getDescription()).
                snippet("t = "+data.main.getTemp_min()+" - "+data.main.getTemp_max())).showInfoWindow();

    }


}
