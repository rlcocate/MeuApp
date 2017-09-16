package nossafirma.com.br.meuapp.adapter;

import android.content.Context;
import android.os.Bundle;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

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
        public TextView addressId;
        public TextView storeName;
        public TextView regionName;
        public TextView beerName;
        public TextView streetName;
        public TextView complement;
        public TextView beerValue;
        public ImageButton ibEdit;
        public ImageButton ibDelete;

        public StoreViewHolder(View view) {
            super(view);
            storeId = (TextView) view.findViewById(R.id.tvIdStore);
            addressId = (TextView) view.findViewById(R.id.tvIdAddress);
            storeName = (TextView) view.findViewById(R.id.tvStoreName);
            regionName = (TextView) view.findViewById(R.id.tvRegionName);
            beerName = (TextView) view.findViewById(R.id.tvBeerName);
            beerValue = (TextView) view.findViewById(R.id.tvValue);
            streetName = (TextView) view.findViewById(R.id.tvStreetName);
            complement = (TextView) view.findViewById(R.id.tvComplement);
            ibEdit = (ImageButton) view.findViewById(R.id.ibEditStore);
            ibDelete = (ImageButton) view.findViewById(R.id.ibDelStore);
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
    public void onBindViewHolder(final StoreViewHolder viewHolder, final int position) {

        final Store store = this._stores.get(position);

        viewHolder.storeId.setText(store.getId().toString());
//        if (store.getLocalAddress().getId() != null)
//            viewHolder.addressId.setText(store.getLocalAddress().getId().toString());
        viewHolder.storeName.setText(store.getName());
        viewHolder.regionName.setText(store.getRegion().getName());
        viewHolder.beerName.setText(store.getBeer().getName());
        viewHolder.streetName.setText(store.getLocalAddress().getStreetName());
        if (store.getLocalAddress().getComplement() != null)
            viewHolder.complement.setText(store.getLocalAddress().getComplement());
        viewHolder.beerValue.setText(store.getBeerValue().toString());

        viewHolder.ibEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavigationDrawerActivity nav = (NavigationDrawerActivity) _context;
                Bundle bundle = new Bundle();
                bundle.putSerializable("StoreData", store);

                AddStoreFragment addStoreFragment = new AddStoreFragment();
                addStoreFragment.setArguments(bundle);

                nav.getSupportFragmentManager().beginTransaction()
                        .replace(R.id.flContent, addStoreFragment, "findThisFragment")
                        .addToBackStack(null)
                        .commit();
            }
        });

        viewHolder.ibDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteStore(Integer.parseInt(viewHolder.storeId.getText().toString()));
                _stores.remove(position);
                notifyDataSetChanged();
                Toast.makeText(_context, R.string.store_deleted, Toast.LENGTH_SHORT).show();
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
}
