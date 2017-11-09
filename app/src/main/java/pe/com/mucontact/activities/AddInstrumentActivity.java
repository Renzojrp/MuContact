package pe.com.mucontact.activities;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import pe.com.mucontact.MuContactApp;
import pe.com.mucontact.R;
import pe.com.mucontact.models.Instrument;
import pe.com.mucontact.models.Musician;
import pe.com.mucontact.network.MuContactApiService;

public class AddInstrumentActivity extends AppCompatActivity {
    private final String photoRute = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES) + "/misfotos/";
    private File file = new File(photoRute);
    private EditText instrumentEditText;
    private EditText brandEditText;
    private EditText modelEditText;
    private EditText serialNumberEditText;
    Musician musician;
    String TAG = "MuContact";
    private static final int REQUEST_PERMISSION_CAMARA = 1;
    Intent cameraIntent;
    List<Instrument> instruments;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_instrument);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        instrumentEditText = (EditText) findViewById( R.id.instrumentInputEditText);
        brandEditText = (EditText) findViewById( R.id.brandInputEditText);
        modelEditText = (EditText) findViewById( R.id.modelInputEditText);
        serialNumberEditText = (EditText) findViewById( R.id.serialNumberInputEditText);

        musician = MuContactApp.getInstance().getCurrentMusician();
        layoutByOrigin();
        file.mkdirs();
    }

    @SuppressLint("SimpleDateFormat")
    private String getCode() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyymmddhhmmss");
        String date = dateFormat.format(new Date() );
        String photoCode = "pic_" + date;
        return photoCode;
    }

    public void camaraClick(View v) {
        cameraIntent = new Intent(
                android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
        File imagesFolder = new File(
                Environment.getExternalStorageDirectory(), "misfots");
        imagesFolder.mkdirs();
        File image = new File(imagesFolder, "foto.jpg");
        Uri uriSavedImage = Uri.fromFile(image);
        //cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, uriSavedImage);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
            startActivityForResult(cameraIntent, 1);
        } else {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, REQUEST_PERMISSION_CAMARA);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_PERMISSION_CAMARA) {
            if (grantResults.length == 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                startActivityForResult(cameraIntent, 1);
            }
        }
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_instrument_sources, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_delete_instrument:
                if(MuContactApp.getInstance().getCurrentInstrument() == null) {
                    finish();
                } else {
                    deleteInstrument();
                }
                return true;
            default:
                return super.onContextItemSelected(item);
        }
    }

    public void floatingActionButtonClick(View view) {
        if(instrumentEditText.getText().toString().isEmpty()
                || brandEditText.getText().toString().isEmpty()
                || modelEditText.getText().toString().isEmpty())
        {
            Toast.makeText(getApplicationContext(), R.string.empty_fild, Toast.LENGTH_SHORT).show();
        } else {
            if (MuContactApp.getInstance().getCurrentInstrument() == null) {
                saveInstrument();
            } else {
                editInstrument();
            }
        }
    }

    public void layoutByOrigin() {
        if(MuContactApp.getInstance().getCurrentInstrument() != null) {
            instrumentEditText.setText(MuContactApp.getInstance().getCurrentInstrument().getInstrument());
            brandEditText.setText(MuContactApp.getInstance().getCurrentInstrument().getBrand());
            modelEditText.setText(MuContactApp.getInstance().getCurrentInstrument().getModel());
            serialNumberEditText.setText(MuContactApp.getInstance().getCurrentInstrument().getSerialNumber());
        }
    }

    private void saveInstrument() {
        AndroidNetworking.post(MuContactApiService.INSTRUMET_URL)
                .addBodyParameter("instrument", instrumentEditText.getText().toString())
                .addBodyParameter("musician", musician.getId())
                .addBodyParameter("brand", brandEditText.getText().toString())
                .addBodyParameter("model", modelEditText.getText().toString())
                .addBodyParameter("serialNumber", serialNumberEditText.getText().toString())
                .addBodyParameter("picture", "")
                .setTag(TAG)
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Toast.makeText(getApplicationContext(), R.string.instrument_save, Toast.LENGTH_SHORT).show();
                        updateInstruments();
                        finish();
                    }
                    @Override
                    public void onError(ANError error) {
                        Toast.makeText(getApplicationContext(), R.string.error_instrument_save, Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void editInstrument() {
        AndroidNetworking.put(MuContactApiService.INSTRUMET_EDIT_URL)
                .addPathParameter("instrument_id", MuContactApp.getInstance().getCurrentInstrument().getId())
                .addBodyParameter("instrument", instrumentEditText.getText().toString())
                .addBodyParameter("musician", musician.getId())
                .addBodyParameter("brand", brandEditText.getText().toString())
                .addBodyParameter("model", modelEditText.getText().toString())
                .addBodyParameter("serialNumber", serialNumberEditText.getText().toString())
                .addBodyParameter("picture", "")
                .setTag(TAG)
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Toast.makeText(getApplicationContext(), R.string.instrument_save, Toast.LENGTH_SHORT).show();
                        updateInstruments();
                        finish();
                    }
                    @Override
                    public void onError(ANError error) {
                        Toast.makeText(getApplicationContext(), R.string.error_publication_save, Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void deleteInstrument() {
        AndroidNetworking.delete(MuContactApiService.INSTRUMET_EDIT_URL)
                .addPathParameter("instrument_id", MuContactApp.getInstance().getCurrentInstrument().getId())
                .setTag(TAG)
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Toast.makeText(getApplicationContext(), R.string.instrument_delete, Toast.LENGTH_SHORT).show();
                        updateInstruments();
                        finish();
                    }
                    @Override
                    public void onError(ANError error) {
                        Toast.makeText(getApplicationContext(), R.string.error_instrument_delete, Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void updateInstruments() {
        AndroidNetworking
                .get(MuContactApiService.INSTRUMET_MUSICIAN_URL)
                .addPathParameter("musician_id", musician.getId())
                .setTag(TAG)
                .setPriority(Priority.LOW)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        if(response == null) return;
                        try {
                            instruments = Instrument.build(response.getJSONArray("instruments"), musician);
                            Log.d(TAG, "Found Instruments: " + String.valueOf(instruments.size()));
                            MuContactApp.getInstance().setCurrentInstruments(instruments);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                    @Override
                    public void onError(ANError anError) {
                        Log.d(TAG, anError.getMessage());
                    }
                });
    }
}
