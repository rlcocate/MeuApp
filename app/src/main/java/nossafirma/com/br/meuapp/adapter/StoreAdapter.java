package nossafirma.com.br.meuapp.adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Interpolator;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.Tasks;

import org.w3c.dom.Text;

import java.util.List;
import java.util.concurrent.ExecutionException;

import nossafirma.com.br.meuapp.NavigationDrawerActivity;
import nossafirma.com.br.meuapp.R;
import nossafirma.com.br.meuapp.fragment.AddStoreFragment;
import nossafirma.com.br.meuapp.model.Beer;
import nossafirma.com.br.meuapp.model.Region;
import nossafirma.com.br.meuapp.model.Store;
import nossafirma.com.br.meuapp.sqlite.AddressDAO;
import nossafirma.com.br.meuapp.sqlite.BeerDAO;
import nossafirma.com.br.meuapp.sqlite.RegionDAO;
import nossafirma.com.br.meuapp.sqlite.StoreDAO;

/**
 * Created by Rodrigo on 30/08/2017.
 */

public class StoreAdapter extends RecyclerView.Adapter<StoreAdapter.StoreViewHolder> {

    private List<Store> _stores;
    private Context _context;

    public class StoreViewHolder extends RecyclerView.ViewHolder {

        public TextView storeId;
        public TextView storeName;
        public TextView regionName;
        public TextView beerName;
        public TextView beerValue;
        public ImageButton ibEdit;
        public ImageButton ibDelete;

        public StoreViewHolder(View view) {
            super(view);
            storeId = (TextView) view.findViewById(R.id.tvIdStore);
            storeName = (TextView) view.findViewById(R.id.tvStoreName);
            regionName = (TextView) view.findViewById(R.id.tvRegionName);
            beerName = (TextView) view.findViewById(R.id.tvBeerName);
            beerValue = (TextView) view.findViewById(R.id.tvValue);
            ibEdit = (ImageButton) view.findViewById(R.id.ibEditStore);
            ibDelete = (ImageButton) view.findViewById(R.id.ibDelStore);

            ibDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    deleteStore(Integer.parseInt(storeId.getText().toString()));
                    notifyItemRemoved(getAdapterPosition());
                    Toast.makeText(_context, R.string.store_deleted, Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    public StoreAdapter(List<Store> stores, Context context) {
        this._stores = stores;
        this._context = context;
    }

    @Override
    public StoreViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(_context).inflate(R.layout.fragment_list_stores_row, parent, false);
        return new StoreViewHolder(view);
    }

    @Override
    public void onBindViewHolder(StoreViewHolder viewHolder, int position) {

        final Store store = this._stores.get(position);

        viewHolder.storeId.setText(store.getId().toString());
        viewHolder.storeName.setText(store.getName());
        viewHolder.regionName.setText(store.getRegion().getName());
        viewHolder.beerName.setText(store.getBeer().getName());
        viewHolder.beerValue.setText(store.getBeerValue().toString());

        viewHolder.ibEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavigationDrawerActivity nav = (NavigationDrawerActivity) _context;
                AddStoreFragment addStoreFragment = new AddStoreFragment();

                nav.getSupportFragmentManager().beginTransaction()
                        .replace(R.id.flContent, addStoreFragment, "findThisFragment")
                        .addToBackStack(null)
                        .commit();
                loadDataForEdit(store);
            }
        });
    }

    @Override
    public int getItemCount() {
        return _stores.size();
    }

    public String deleteStore(Integer storeId) {
        StoreDAO storeDAO = null;
        AddressDAO addressDAO = null;
        Integer rows = 0;
        storeDAO = new StoreDAO(_context);
        addressDAO = new AddressDAO(_context);

        try {
            rows = addressDAO.delete(storeId);
            if (rows > 0) {
                rows = storeDAO.delete(storeId);
            }

        } catch (Exception e) {
            Log.e("DeleteStoreRow", e.getMessage());
        }

        return (rows > 0 ? "Success" : "Failed to delete row");
    }

    private void loadDataForEdit(Store store) {
        LayoutInflater inflater = LayoutInflater.from(_context);
        View view = inflater.inflate(R.layout.fragment_add_store, null);

        final Spinner spRegions = (Spinner) view.findViewById(R.id.spRegions);
        final Spinner spBeers = (Spinner) view.findViewById(R.id.spBeers);

        List<Region> regions = new RegionDAO(_context).getAll();

        ArrayAdapter<Region> regionAdapter = new ArrayAdapter<Region>(_context, android.R.layout.simple_spinner_item, regions);
        regionAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spRegions.setAdapter(regionAdapter);

        List<Beer> beers = new BeerDAO(_context).getAll();

        ArrayAdapter<Beer> beerAdapter = new ArrayAdapter<Beer>(_context, android.R.layout.simple_spinner_item, beers);
        beerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spBeers.setAdapter(beerAdapter);

        final TextView tvIdStore = (TextView) view.findViewById(R.id.tvIdStore);
        final EditText etStoreName = (EditText) view.findViewById(R.id.etStoreName);
        final TextView tvIdAddress = (TextView) view.findViewById(R.id.tvIdAddress);
        final EditText etStreetName = (EditText) view.findViewById(R.id.etStreetName);
        final EditText etComplement = (EditText) view.findViewById(R.id.etComplement);
        final TextView tvLatitude = (TextView) view.findViewById(R.id.tvLatitude);
        final TextView tvLongitude = (TextView) view.findViewById(R.id.tvLongitude);
        final EditText etValue = (EditText) view.findViewById(R.id.etValue);

        try {
            if (store != null) {
                tvIdStore.setText(store.getId().toString());
                etStoreName.setText(store.getName());
                tvIdAddress.setText(store.getLocalAddress().getId().toString());
                etStreetName.setText(store.getLocalAddress().getStreetName());
                etComplement.setText(store.getLocalAddress().getComplement());
                tvLatitude.setText(store.getLocalAddress().getLatitude().toString());
                tvLongitude.setText(store.getLocalAddress().getLongitude().toString());
                etValue.setText(store.getBeerValue().toString());
            }
        } catch (Exception e) {
            Log.e("ViewHolder", e.getMessage());
        }
    }

}
