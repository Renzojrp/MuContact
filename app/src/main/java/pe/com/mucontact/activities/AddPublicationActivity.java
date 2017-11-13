package pe.com.mucontact.activities;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Objects;

import pe.com.mucontact.MuContactApp;
import pe.com.mucontact.R;
import pe.com.mucontact.models.Musician;
import pe.com.mucontact.models.User;
import pe.com.mucontact.network.MuContactApiService;

public class AddPublicationActivity extends AppCompatActivity {
    private EditText descriptionEditText;
    private EditText locationAtEditText;
    private EditText dateEditText;
    private Button dateButton;
    private int day;
    private int month;
    private int year;
    private Spinner spinner;
    List<String> nameInstrument = new ArrayList<String>();
    List<String> idInstrument = new ArrayList<String>();
    Musician musician;
    User user;
    String TAG = "MuContact";
    String idInstrumentSelected;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_publication);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        descriptionEditText = (EditText) findViewById( R.id.descriptionInputEditText);
        locationAtEditText = (EditText) findViewById( R.id.locationAtInputEditText);
        dateEditText = (EditText) findViewById(R.id.dateEditText);
        dateButton = (Button) findViewById(R.id.dateButton);
        dateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onDateButton();
            }
        });

        musician = MuContactApp.getInstance().getCurrentMusician();
        spinner = (Spinner) findViewById(R.id.instrumentSpinner);

        for(int i = 0; i<MuContactApp.getInstance().getCurrentInstruments().size(); i++) {
            idInstrument.add(MuContactApp.getInstance().getCurrentInstruments().get(i).getId());
            nameInstrument.add(MuContactApp.getInstance().getCurrentInstruments().get(i).getInstrument());
        }

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, nameInstrument);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(dataAdapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int pos, long id) {
                idInstrumentSelected = idInstrument.get(pos);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                idInstrumentSelected = idInstrument.get(0);
            }
        });

        layoutByOrigin();
    }

    public void floatingActionButtonClick(View view) {
        if(descriptionEditText.getText().toString().isEmpty()
                || locationAtEditText.getText().toString().isEmpty())
        {
            Toast.makeText(getApplicationContext(), R.string.empty_fild, Toast.LENGTH_SHORT).show();
        } else {
            if (MuContactApp.getInstance().getCurrentCraftman() != null) {
                sendOrder();
            } else {
                if (MuContactApp.getInstance().getCurrentPublication() == null) {
                    savePublication();
                } else {
                    editPublication();
                }
            }

        }
    }

    public void layoutByOrigin() {
        if(MuContactApp.getInstance().getCurrentInstruments().size() == 0) {
            Toast.makeText(getApplicationContext(), R.string.error_no_instruments_found, Toast.LENGTH_SHORT).show();
            finish();
        }
        if(MuContactApp.getInstance().getCurrentPublication() != null) {
            descriptionEditText.setText(MuContactApp.getInstance().getCurrentPublication().getDescription());
            locationAtEditText.setText(MuContactApp.getInstance().getCurrentPublication().getLocationAt());
            dateEditText.setText(MuContactApp.getInstance().getCurrentPublication().getDeliveryDay());
            for(int i = 0; i<MuContactApp.getInstance().getCurrentInstruments().size(); i++) {
                if(Objects.equals(MuContactApp.getInstance().getCurrentPublication().getInstrument().getId(), idInstrument.get(i))) {
                    spinner.setSelection(i);
                }
            }

        }
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_publication_sources, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_delete_publication:
                if(MuContactApp.getInstance().getCurrentPublication() == null) {
                    finish();
                } else {
                    deletePublication();
                }
                return true;
            default:
                return super.onContextItemSelected(item);
        }
    }

    public void onDateButton() {
        final Calendar calendar = Calendar.getInstance();
        day = calendar.get(Calendar.DAY_OF_MONTH);
        month = calendar.get(Calendar.MONTH);
        year = calendar.get(Calendar.YEAR);

        DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                dateEditText.setText(dayOfMonth + "/" + (month+1) + "/" + year);
            }
        }, year, month, day);
        datePickerDialog.show();
    }

    private void savePublication() {
        AndroidNetworking.post(MuContactApiService.PUBLICATION_URL)
                .addBodyParameter("musician", musician.getId())
                .addBodyParameter("instrument", idInstrumentSelected)
                .addBodyParameter("description", descriptionEditText.getText().toString())
                .addBodyParameter("locationAt", locationAtEditText.getText().toString())
                .addBodyParameter("deliveryDay", dateEditText.getText().toString())
                .addHeaders("Authorization", MuContactApp.getInstance().getCurrentToken())
                .setTag(TAG)
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Toast.makeText(getApplicationContext(), R.string.publication_save, Toast.LENGTH_SHORT).show();
                        finish();
                    }
                    @Override
                    public void onError(ANError error) {
                        Toast.makeText(getApplicationContext(), R.string.error_publication_save, Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void editPublication() {
        AndroidNetworking.put(MuContactApiService.PUBLICATION_EDIT_URL)
                .addBodyParameter("musician", musician.getId())
                .addBodyParameter("instrument", idInstrumentSelected)
                .addBodyParameter("description", descriptionEditText.getText().toString())
                .addBodyParameter("locationAt", locationAtEditText.getText().toString())
                .addPathParameter("publication_id", MuContactApp.getInstance().getCurrentPublication().getId())
                .addBodyParameter("deliveryDay", dateEditText.getText().toString())
                .addHeaders("Authorization", MuContactApp.getInstance().getCurrentToken())
                .setTag(TAG)
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Toast.makeText(getApplicationContext(), R.string.publication_save, Toast.LENGTH_SHORT).show();
                        finish();
                    }
                    @Override
                    public void onError(ANError error) {
                        Toast.makeText(getApplicationContext(), R.string.error_publication_save, Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void deletePublication() {
        AndroidNetworking.delete(MuContactApiService.PUBLICATION_EDIT_URL)
                .addPathParameter("publication_id", MuContactApp.getInstance().getCurrentPublication().getId())
                .addHeaders("Authorization", MuContactApp.getInstance().getCurrentToken())
                .setTag(TAG)
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Toast.makeText(getApplicationContext(), R.string.publication_delete, Toast.LENGTH_SHORT).show();
                        finish();
                    }
                    @Override
                    public void onError(ANError error) {
                        Toast.makeText(getApplicationContext(), R.string.error_publication_delete, Toast.LENGTH_SHORT).show();
                    }
                });
    }


    private void sendOrder() {
        AndroidNetworking.post(MuContactApiService.ORDER_URL)
                .addBodyParameter("craftman", MuContactApp.getInstance().getCurrentCraftman().getId())
                .addBodyParameter("instrument", idInstrumentSelected)
                .addBodyParameter("musician", musician.getId())
                .addBodyParameter("description", descriptionEditText.getText().toString())
                .addBodyParameter("locationAt", locationAtEditText.getText().toString())
                .addBodyParameter("deliveryDay", dateEditText.getText().toString())
                .addHeaders("Authorization", MuContactApp.getInstance().getCurrentToken())
                .setTag(TAG)
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Toast.makeText(getApplicationContext(), R.string.order_save, Toast.LENGTH_SHORT).show();
                        finish();
                    }
                    @Override
                    public void onError(ANError error) {
                        Toast.makeText(getApplicationContext(), R.string.error_order_save, Toast.LENGTH_SHORT).show();
                    }
                });
    }

}