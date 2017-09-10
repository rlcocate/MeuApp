package nossafirma.com.br.meuapp.sqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.LinkedList;
import java.util.List;

import nossafirma.com.br.meuapp.model.Beer;

/**
 * Created by Rodrigo on 27/08/2017.
 */

public class BeerDAO {

    private DBHelper dbHelper;
    private Beer beer;

    public static final String TABELA = "Beer";

    public static final String COLUNA_ID = "id";
    public static final String COLUNA_NAME = "name";

    public BeerDAO(Context context) {
        dbHelper = new DBHelper(context);
        beer = new Beer();
    }

    public List<Beer> getAll() {

        List<Beer> beers = new LinkedList<>();
        Beer beer = null;

        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String query = "SELECT * FROM " + TABELA + " ORDER BY name ";

        Cursor cursor = db.rawQuery(query, null);

        if (cursor.moveToFirst()) {
            do {
                beer = new Beer();
                beer.setId(cursor.getInt(cursor.getColumnIndex(COLUNA_ID)));
                beer.setName(cursor.getString(cursor.getColumnIndex(COLUNA_NAME)));
                beers.add(beer);
            } while (cursor.moveToNext());
        }
        return beers;
    }

    public Beer getBy(String name) {

        SQLiteDatabase db = dbHelper.getReadableDatabase();

        String colunas[] = {
                COLUNA_ID,
                COLUNA_NAME
        };

        String where = "name = '" + name + "'";
        Cursor cursor = db.query(true, TABELA, colunas, where, null, null, null, null, null);

        beer = null;

        if (cursor != null) {
            if (cursor.getCount() > 0) {
                cursor.moveToFirst();
                beer = new Beer();
                beer.setId(cursor.getInt(cursor.getColumnIndex(COLUNA_ID)));
                beer.setName(cursor.getString(cursor.getColumnIndex(COLUNA_NAME)));
            }
        }
        return beer;
    }

    public void add(Beer beer) {

        SQLiteDatabase db = dbHelper.getWritableDatabase();

        if (getBy(beer.getName()) == null) {

            ContentValues values = new ContentValues();

            values.put(COLUNA_ID, beer.getId());
            values.put(COLUNA_NAME, beer.getName());

            long resultado = db.insert(TABELA, null, values);

            db.close();
        }
    }
}
