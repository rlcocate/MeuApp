package nossafirma.com.br.meuapp.fragment;

import android.content.Context;
import android.location.Address;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.List;

import nossafirma.com.br.meuapp.R;
import nossafirma.com.br.meuapp.model.Beer;
import nossafirma.com.br.meuapp.model.LocalAddress;
import nossafirma.com.br.meuapp.model.Region;
import nossafirma.com.br.meuapp.model.Store;
import nossafirma.com.br.meuapp.sqlite.AddressDAO;
import nossafirma.com.br.meuapp.sqlite.BeerDAO;
import nossafirma.com.br.meuapp.sqlite.RegionDAO;
import nossafirma.com.br.meuapp.sqlite.StoreDAO;
import nossafirma.com.br.meuapp.utils.CoordinateFinder;

/**
 * A simple {@link Fragment} subclass.
 */
public class AddStoreFragment extends Fragment {

    Context context;

    private TextView tvIdStore;
    private EditText etStoreName;
    private Spinner spRegions;
    private Spinner spBeers;
    private TextView tvIdAddress;
    private EditText etStreetName;
    private EditText etComplement;
    private TextView tvLatitude;
    private TextView tvLongitude;
    private EditText etValue;
    private Button btSave;

    private StoreDAO storeDao = null;
    private AddressDAO addressDAO = null;

    public AddStoreFragment() {

    }
//    public AddStoreFragment(Integer id) {
//        storeDao = new StoreDAO(context).getBy(id);
//    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        context = container.getContext();

        storeDao = new StoreDAO(context);
        addressDAO = new AddressDAO(context);

        View view = inflater.inflate(R.layout.fragment_add_store, container, false);

        tvIdStore = (TextView) view.findViewById(R.id.tvIdStore);
        etStoreName = (EditText) view.findViewById(R.id.etStoreName);
        spRegions = (Spinner) view.findViewById(R.id.spRegions);
        spBeers = (Spinner) view.findViewById(R.id.spBeers);
        tvIdAddress = (TextView) view.findViewById(R.id.tvIdAddress);
        etStreetName = (EditText) view.findViewById(R.id.etStreetName);
        etComplement = (EditText) view.findViewById(R.id.etComplement);
        tvLatitude = (TextView) view.findViewById(R.id.tvLatitude);
        tvLongitude = (TextView) view.findViewById(R.id.tvLongitude);
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

            LocalAddress localAddress = new LocalAddress();

            // Código do endereço.
            if (!tvIdAddress.getText().toString().equals(""))
                localAddress.setId(Integer.parseInt(tvIdAddress.getText().toString()));

            String streetName = etStreetName.getText().toString();
            String complement = "";
            localAddress.setStreetName(streetName);
            if (!etComplement.getText().toString().equals("")) {
                complement = etComplement.getText().toString();
                localAddress.setComplement(complement);
            }

            CoordinateFinder coordinateFinder = new CoordinateFinder();
            Address retAddress = coordinateFinder.getCoordinates(context, streetName, complement);

            localAddress.setLatitude(retAddress.getLatitude());
            localAddress.setLongitude(retAddress.getLongitude());

            Integer rowsDeleted = 0;

            String message = storeDao.save(store);
            if (message == "Success") {
                // Alteração: Excluir endereço.
                if (localAddress.getId() != null) {
                    rowsDeleted = addressDAO.delete(localAddress.getId());
                }
                localAddress.setStoreId(storeDao.getBy(store.getName(), store.getBeer().getName()).getId());
                message = addressDAO.save(localAddress, etStoreName.getText().toString());
            }
            Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, R.string.data_must_filled, Toast.LENGTH_SHORT).show();
        }

    }

    private boolean isValid() {

        return (!etStoreName.getText().toString().equals("") &&
                !spRegions.getSelectedItem().equals("") &&
                !spBeers.getSelectedItem().equals("") &&
                !etStreetName.getText().toString().equals("") &&
                !etValue.getText().toString().equals(""));
    }
}
