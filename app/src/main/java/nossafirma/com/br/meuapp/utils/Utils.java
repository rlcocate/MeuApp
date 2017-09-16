package nossafirma.com.br.meuapp.utils;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.widget.Toast;

import java.util.List;

import nossafirma.com.br.meuapp.R;

/**
 * Created by Rodrigo on 10/09/2017.
 */

public class Utils {

    public Address getCoordinates(Context context, String streetName, String complement) {
        Geocoder geocoder = new Geocoder(context);
        String addressToCheck = streetName + " " + complement;
        Address address = null;

        try {
            List<Address> addresses = geocoder.getFromLocationName(addressToCheck, 4);

            if (addresses == null)
                return null;
            else {
                if (addresses.size() > 0) {
                    address = addresses.get(0);
                    address.getLatitude();
                    address.getLongitude();
                }
            }
        } catch (Exception e) {
            Toast.makeText(context, R.string.error_get_coordinates + " " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
        return address;
    }

    public boolean isNetworkAvaliable(Context ctx) {
        ConnectivityManager connectivityManager = (ConnectivityManager) ctx
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        if ((connectivityManager
                .getNetworkInfo(ConnectivityManager.TYPE_MOBILE) != null && connectivityManager
                .getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED)
                || (connectivityManager
                .getNetworkInfo(ConnectivityManager.TYPE_WIFI) != null && connectivityManager
                .getNetworkInfo(ConnectivityManager.TYPE_WIFI)
                .getState() == NetworkInfo.State.CONNECTED)) {
            return true;
        } else {
            return false;
        }
    }
}
