package nossafirma.com.br.meuapp.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.List;

import nossafirma.com.br.meuapp.R;
import nossafirma.com.br.meuapp.fragment.AddStoreFragment;
import nossafirma.com.br.meuapp.model.Store;

/**
 * Created by Rodrigo on 30/08/2017.
 */

public class StoreAdapter extends RecyclerView.Adapter<StoreAdapter.StoreViewHolder> {

    private List<Store> _stores;
    private Context _context;

    public class StoreViewHolder extends RecyclerView.ViewHolder {

        public TextView storeName;
        public TextView regionName;
        public TextView beerName;
        public TextView beerValue;
        public ImageButton ibEdit;
        public ImageButton ibDelete;

        public StoreViewHolder(View view) {
            super(view);
            storeName = (TextView) view.findViewById(R.id.tvStoreName);
            regionName = (TextView) view.findViewById(R.id.tvRegionName);
            beerName = (TextView) view.findViewById(R.id.tvBeerName);
            beerValue = (TextView) view.findViewById(R.id.tvValue);
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
    public void onBindViewHolder(StoreViewHolder viewHolder, int position) {
        Store store = this._stores.get(position);

        viewHolder.storeName.setText(store.getName());
        viewHolder.regionName.setText(store.getRegion().getName());
        viewHolder.beerName.setText(store.getBeer().getName());
        viewHolder.beerValue.setText(store.getBeerValue().toString());
    }

    @Override
    public int getItemCount() {
        return _stores.size();
    }
}
