package nossafirma.com.br.meuapp.sqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.net.IDN;
import java.util.LinkedList;
import java.util.List;

import nossafirma.com.br.meuapp.model.LocalAddress;

public class AddressDAO {

    private DBHelper dbHelper;
    private LocalAddress address;

    public static final String T_ADDRESS = "LocalAddress";
    public static final String T_STORE = "Store";

    public static final String C_ID = "id"; // Identity
    public static final String C_STORE_ID = "storeId";
    public static final String C_STREET_NAME = "streetName";
    public static final String C_COMPLEMENT = "complement";
    public static final String C_LATITUDE = "latitude";
    public static final String C_LONGITUDE = "longitude";

    private String query = "SELECT A.*, S.id, S.name FROM " + T_ADDRESS + " as A " +
            " LEFT JOIN " + T_STORE + " as S on (A." + C_STORE_ID + " = " + "S.id)";

    public AddressDAO(Context context) {
        dbHelper = new DBHelper(context);
        address = new LocalAddress();
    }

    public List<LocalAddress> getAll() {

        List<LocalAddress> addresses = new LinkedList<>();
        LocalAddress address;

        SQLiteDatabase db = dbHelper.getReadableDatabase();

        Cursor cursor = null;
        try {
            cursor = db.rawQuery(query, null);
        } catch (Exception e) {
            e.getMessage();
        }

        if (cursor.moveToFirst()) {
            do {
                address = new LocalAddress();
                address.setId(cursor.getInt(cursor.getColumnIndex(C_ID)));
                address.setStreetName(cursor.getString(cursor.getColumnIndex(C_STREET_NAME)));
                address.setStoreId(cursor.getInt(cursor.getColumnIndex(C_STORE_ID)));
                address.setComplement(cursor.getString(cursor.getColumnIndex(C_COMPLEMENT)));
                address.setLatitude(cursor.getDouble(cursor.getColumnIndex(C_LATITUDE)));
                address.setLongitude(cursor.getDouble(cursor.getColumnIndex(C_LONGITUDE)));
                addresses.add(address);
            } while (cursor.moveToNext());
        }
        return addresses;
    }

    public LocalAddress getBy(String name, String storeName) {

        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = null;
        String q = query + " where lower(A.streetName) = '" + name.toLowerCase() + "' and lower(S.name) = '" + storeName.toLowerCase() + "'";

        try {
            cursor = db.rawQuery(q, null);
        } catch (Exception e) {
            e.getMessage();
        }

        address = null;
        if (cursor != null) {
            if (cursor.getCount() > 0) {
                cursor.moveToFirst();
                address = new LocalAddress();
                address.setId(cursor.getInt(cursor.getColumnIndex(C_ID)));
                address.setStreetName(cursor.getString(cursor.getColumnIndex(C_STREET_NAME)));
                address.setStoreId(cursor.getInt(cursor.getColumnIndex(C_STORE_ID)));
                address.setComplement(cursor.getString(cursor.getColumnIndex(C_COMPLEMENT)));
                address.setLatitude(cursor.getDouble(cursor.getColumnIndex(C_LATITUDE)));
                address.setLongitude(cursor.getDouble(cursor.getColumnIndex(C_LONGITUDE)));
            }
        }
        return address;
    }

    public String save(LocalAddress address, String storeName) {

        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(C_ID, address.getId());
        values.put(C_STORE_ID, address.getStoreId());
        values.put(C_STREET_NAME, address.getStreetName());
        if (address.getComplement() != null) values.put(C_COMPLEMENT, address.getComplement());
        if (address.getLatitude() != null) values.put(C_LATITUDE, address.getLatitude());
        if (address.getLongitude() != null) values.put(C_LONGITUDE, address.getLongitude());

        long retRows = 0;

        address = getBy(address.getStreetName(), storeName);
        try {
            if (address == null) {
                retRows = db.insert(T_ADDRESS, null, values);
            } else {
                retRows = db.update(T_ADDRESS, values, C_ID + " = ?", new String[]{Integer.toString(address.getId())});
            }
        } catch (Exception e) {
            Log.e("SaveLocalAddress", e.getMessage());
        }

        db.close();

        return (retRows > 0) ? "Success" : "Not saved";
    }

    public Integer delete(Integer id) {

        SQLiteDatabase db = null;
        Integer rows = 0;
        try {
            db = dbHelper.getWritableDatabase();
            rows = db.delete(T_ADDRESS, C_ID + " = ?", new String[]{Integer.toString(id)});
        } catch (Exception e) {
            Log.e("DeleteLocalAddress", e.getMessage());
        }
        return rows;
    }
}