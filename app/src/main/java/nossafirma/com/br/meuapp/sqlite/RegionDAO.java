package nossafirma.com.br.meuapp.sqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.LinkedList;
import java.util.List;

import nossafirma.com.br.meuapp.model.Region;

/**
 * Created by Rodrigo on 27/08/2017.
 */

public class RegionDAO {

    private DBHelper dbHelper;
    private Region region;

    public static final String TABELA = "Region";

    public static final String COLUNA_ID = "id";
    public static final String COLUNA_INITIALS = "initials";
    public static final String COLUNA_NAME = "name";

    public RegionDAO(Context context) {
        dbHelper = new DBHelper(context);
        region = new Region();
    }

    public List<Region> getAll() {

        List<Region> regions = new LinkedList<>();
        Region region = null;

        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String query = "SELECT * FROM " + TABELA + " ORDER BY name ";

        Cursor cursor = db.rawQuery(query, null);

        if (cursor.moveToFirst()) {
            do {
                region = new Region();
                region.setId(cursor.getInt(cursor.getColumnIndex(COLUNA_ID)));
                region.setName(cursor.getString(cursor.getColumnIndex(COLUNA_INITIALS)));
                region.setName(cursor.getString(cursor.getColumnIndex(COLUNA_NAME)));
                regions.add(region);
            } while (cursor.moveToNext());
        }
        return regions;
    }

    public Region getBy(String name) {

        SQLiteDatabase db = dbHelper.getReadableDatabase();

        String colunas[] = {
                COLUNA_ID,
                COLUNA_INITIALS,
                COLUNA_NAME
        };

        String where = "name = '" + name + "'";
        Cursor cursor = db.query(true, TABELA, colunas, where, null, null, null, null, null);

        region = null;

        if (cursor != null) {
            if (cursor.getCount() > 0) {
                cursor.moveToFirst();
                region = new Region();
                region.setId(cursor.getInt(cursor.getColumnIndex(COLUNA_ID)));
                region.setInitials(cursor.getString(cursor.getColumnIndex(COLUNA_INITIALS)));
                region.setName(cursor.getString(cursor.getColumnIndex(COLUNA_NAME)));
            }
        }
        return region;
    }

    public void insert(Region region) {

        SQLiteDatabase db = dbHelper.getWritableDatabase();

        if (getBy(region.getName()) == null) {

            ContentValues values = new ContentValues();

            values.put(COLUNA_ID, region.getId());
            values.put(COLUNA_INITIALS, region.getInitials());
            values.put(COLUNA_NAME, region.getName());

            long resultado = db.insert(TABELA, null, values);

            db.close();
        }
    }
}
