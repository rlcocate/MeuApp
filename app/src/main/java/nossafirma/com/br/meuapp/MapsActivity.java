package nossafirma.com.br.meuapp;

import android.location.Address;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.List;

import nossafirma.com.br.meuapp.model.Store;
import nossafirma.com.br.meuapp.sqlite.StoreDAO;
import nossafirma.com.br.meuapp.utils.CoordinateFinder;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
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
        mMap.setOnMyLocationButtonClickListener(mylocation);

        // Add a marker in Sydney and move the camera
        LatLng sydney = new LatLng(-34, 151);
        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));

        List<Store> stores = new StoreDAO(this).getAll();

        final int size = stores.size();
        for (int i = 0; i < size; i++)
        {
            Store store = stores.get(i);

            CoordinateFinder coordinateFinder = new CoordinateFinder();
            Address address = coordinateFinder.getCoordinates(this, store.getLocalAddress().getStreetName(), store.getLocalAddress().getComplement() == null ? "" : store.getLocalAddress().getComplement());

            LatLng coordinate = null;

            if (address != null) {
                coordinate = new LatLng(address.getLatitude(), address.getLongitude());
                mMap.addMarker(new MarkerOptions().position(coordinate).title(store.getName()));

            }
            //                    newLatLngZoom(LatLng, Zoom --> 2.0 a 21.0)
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(coordinate, 15));

        }
    }

    private GoogleMap.OnMyLocationButtonClickListener mylocation = new GoogleMap.OnMyLocationButtonClickListener() {
        @Override
        public boolean onMyLocationButtonClick() {

            LocationManager manager = (LocationManager) getSystemService(LOCATION_SERVICE);
            Criteria criteria = new Criteria();
            String provider = manager.getBestProvider(criteria, false);

            //BUILD_MAP = false;
            checkPermission(getString(R.string.new_store_question), 0, 0);

            Location location = manager.getLastKnownLocation(provider);
            LatLng latlng = new LatLng(location.getLatitude(), location.getLongitude());

            mMap.addMarker(new MarkerOptions().position(latlng).title(String.valueOf(R.string.store_name)));
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latlng, 18));

            return true;
        }
    };
}
