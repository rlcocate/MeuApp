package nossafirma.com.br.meuapp.sqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import java.util.LinkedList;
import java.util.List;

import nossafirma.com.br.meuapp.model.Beer;
import nossafirma.com.br.meuapp.model.LocalAddress;
import nossafirma.com.br.meuapp.model.Login;
import nossafirma.com.br.meuapp.model.Region;
import nossafirma.com.br.meuapp.model.Store;

public class StoreDAO {

    private DBHelper dbHelper;
    private Beer beer;
    private Region region;
    private Store store;
    private LocalAddress localAddress;

    public static final String T_STORE = "Store";
    public static final String T_BEER = "Beer";
    public static final String T_REGION = "Region";
    public static final String T_ADDRESS = "LocalAddress";

    public static final String C_ID = "id"; // Identity
    public static final String C_NAME = "name";
    public static final String C_REGION_ID = "regionId";
    public static final String C_INIT_REGION_NAME = "InitRegionName";
    public static final String C_REGION_NAME = "RegionName";
    public static final String C_BEER_NAME = "BeerName";
    public static final String C_BEER_ID = "beerId";
    public static final String C_BEER_VALUE = "beerValue";
    public static final String C_ADDRESS_ID = "AddressId";
    public static final String C_STREET_NAME = "streetName";
    public static final String C_COMPLEMENT = "complement";
    public static final String C_LATITUDE = "latitude";
    public static final String C_LONGITUDE = "longitude";

    private String query =
            "SELECT S.*, R.initials as InitRegionName, R.name as RegionName, B.name as BeerName, " +
                    " A.id as AddressId, A.streetName, A.complement, A.latitude, A.longitude " +
                    "  FROM " + T_STORE + " as S " +
                    " INNER JOIN " + T_ADDRESS + " as A ON (S.id " + " = A.storeId)" +
                    " INNER JOIN " + T_REGION + " as R ON (S." + C_REGION_ID + " = R.id)" +
                    " INNER JOIN " + T_BEER + " as B ON (S." + C_BEER_ID + "   = B.id)";

    public StoreDAO(Context context) {
        dbHelper = new DBHelper(context);

        region = new Region();
        beer = new Beer();
        store = new Store();
        localAddress = new LocalAddress();
    }

    public List<Store> getAll() {

        List<Store> stores = new LinkedList<>();
        Store store;

        SQLiteDatabase db = dbHelper.getReadableDatabase();

        Cursor cursor = null;
        try {
            cursor = db.rawQuery(query, null);
        } catch (Exception e) {
            e.getMessage();
        }

        if (cursor.moveToFirst()) {
            do {
                store = new Store();
                store.setId(cursor.getInt(cursor.getColumnIndex(C_ID)));
                store.setName(cursor.getString(cursor.getColumnIndex(C_NAME)));
                store.setRegion(new Region(cursor.getInt(cursor.getColumnIndex(C_REGION_ID)), cursor.getString(cursor.getColumnIndex(C_INIT_REGION_NAME)), cursor.getString(cursor.getColumnIndex(C_REGION_NAME))));
                store.setBeer(new Beer(cursor.getInt(cursor.getColumnIndex(C_BEER_ID)), cursor.getString(cursor.getColumnIndex(C_BEER_NAME))));
                store.setLocalAddress(new LocalAddress(cursor.getInt(cursor.getColumnIndex(C_ADDRESS_ID)), cursor.getString(cursor.getColumnIndex(C_STREET_NAME)), cursor.getString(cursor.getColumnIndex(C_COMPLEMENT)), cursor.getDouble(cursor.getColumnIndex(C_LATITUDE)), cursor.getDouble(cursor.getColumnIndex(C_LONGITUDE)), cursor.getLong(cursor.getColumnIndex(C_ID))));
                store.setBeerValue(cursor.getDouble(cursor.getColumnIndex(C_BEER_VALUE)));
                stores.add(store);
            } while (cursor.moveToNext());
        }
        return stores;
    }

    public Store getBy(String name, String beerName) {

        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = null;
        String q = query + " where lower(S.name) = '" + name.toLowerCase() + "' and lower(B.name) = '" + beerName.toLowerCase() + "'";
//        String q = query + " where id = " + id; // lower(S.name) = '" + name.toLowerCase() + "' and lower(B.name) = '" + beerName.toLowerCase() + "'";

        try {
            cursor = db.rawQuery(q, null);
        } catch (Exception e) {
            e.getMessage();
        }

        store = null;
        if (cursor != null) {
            if (cursor.getCount() > 0) {
                cursor.moveToFirst();
                store = new Store();
                store.setId(cursor.getInt(cursor.getColumnIndex(C_ID)));
                store.setName(cursor.getString(cursor.getColumnIndex(C_NAME)));
                store.setRegion(
                        new Region(
                                cursor.getInt(cursor.getColumnIndex(C_REGION_ID)),
                                cursor.getString(cursor.getColumnIndex(C_INIT_REGION_NAME)),
                                cursor.getString(cursor.getColumnIndex(C_REGION_NAME))
                        )
                );
                store.setBeer(
                        new Beer(
                                cursor.getInt(cursor.getColumnIndex(C_BEER_ID)),
                                cursor.getString(cursor.getColumnIndex(C_BEER_NAME)))
                );
                store.setBeerValue(cursor.getDouble(cursor.getColumnIndex(C_BEER_VALUE)));
            }
        }
        return store;
    }

    public Long save(Store store) {

        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(C_NAME, store.getName());
        values.put(C_REGION_ID, store.getRegion().getId());
        values.put(C_BEER_ID, store.getBeer().getId());
        values.put(C_BEER_VALUE, store.getBeerValue());

        Long retCode = Long.valueOf(0);

        if (store.getId() == null) {
            retCode = db.insert(T_STORE, null, values);
        } else {
            retCode = Long.valueOf(db.update(T_STORE, values, C_ID + " = ?", new String[]{Integer.toString(store.getId())}));
        }
        db.close();

        return (retCode > 0) ? retCode : 0;
    }

    public Integer delete(Integer id) {

        SQLiteDatabase db = null;
        Integer rows = 0;
        try {
            db = dbHelper.getWritableDatabase();
            rows = db.delete(T_STORE, C_ID + " = ?", new String[]{Integer.toString(id)});
        } catch (Exception e) {
            Log.e("DeleteStore", e.getMessage());
        }
        return rows;
    }
}