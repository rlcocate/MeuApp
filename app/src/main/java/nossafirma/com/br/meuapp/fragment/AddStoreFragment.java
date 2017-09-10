package nossafirma.com.br.meuapp.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.List;

import nossafirma.com.br.meuapp.R;
import nossafirma.com.br.meuapp.model.Beer;
import nossafirma.com.br.meuapp.model.Region;
import nossafirma.com.br.meuapp.model.Store;
import nossafirma.com.br.meuapp.sqlite.BeerDAO;
import nossafirma.com.br.meuapp.sqlite.RegionDAO;
import nossafirma.com.br.meuapp.sqlite.StoreDAO;

/**
 * A simple {@link Fragment} subclass.
 */
public class AddStoreFragment extends Fragment {

    Context context;

    private EditText etStoreName;
    private Spinner spRegions;
    private Spinner spBeers;
    private EditText etValue;
    private Button btSave;

    private StoreDAO storeDao = null;

    public AddStoreFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        context = container.getContext();

        storeDao = new StoreDAO(context);

        View view = inflater.inflate(R.layout.fragment_add_store, container, false);

        etStoreName = (EditText) view.findViewById(R.id.etStoreName);
        spRegions = (Spinner) view.findViewById(R.id.spRegions);
        spBeers = (Spinner) view.findViewById(R.id.spBeers);
        etValue = (EditText) view.findViewById(R.id.etValue);
        btSave = (Button) view.findViewById(R.id.btSave);

        // Preenchimento dos combos.
        getDomains();

        btSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                save();
            }
        });

        // Inflate the layout for this fragment
        return view;
    }

    private void getDomains() {

        List<Region> regions = new RegionDAO(context).getAll();

        ArrayAdapter<Region> regionAdapter = new ArrayAdapter<Region>(context, android.R.layout.simple_spinner_item, regions);
        regionAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spRegions.setAdapter(regionAdapter);

        List<Beer> beers = new BeerDAO(context).getAll();

        ArrayAdapter<Beer> beerAdapter = new ArrayAdapter<Beer>(context, android.R.layout.simple_spinner_item, beers);
        beerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spBeers.setAdapter(beerAdapter);

    }

    private void save() {

        if (isValid()) {

            Store store = new Store();

            store.setName(etStoreName.getText().toString());
            store.setRegion((Region) spRegions.getSelectedItem());
            store.setBeer((Beer) spBeers.getSelectedItem());
            store.setBeerValue(Double.valueOf(etValue.getText().toString()));

            String message = storeDao.save(store);

            Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, R.string.data_must_filled, Toast.LENGTH_SHORT).show();
        }
    }

    private boolean isValid(){

        return (!etStoreName.getText().toString().equals("") &&
                !spRegions.getSelectedItem().equals("") &&
                !spBeers.getSelectedItem().equals("") &&
                !etValue.getText().toString().equals(""));
    }
}
