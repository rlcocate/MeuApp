package nossafirma.com.br.meuapp.utils;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.widget.Toast;

import java.util.List;

import nossafirma.com.br.meuapp.R;
import nossafirma.com.br.meuapp.model.LocalAddress;

/**
 * Created by Rodrigo on 10/09/2017.
 */

public class CoordinateFinder {

    public Address getCoordinates(Context context, String streetName, String complement) {
        Geocoder geocoder = new Geocoder(context);
        String addressToCheck = streetName + " " + complement;
        Address address = null;

        try {
            List<Address> addresses = geocoder.getFromLocationName(addressToCheck, 10);

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
}
