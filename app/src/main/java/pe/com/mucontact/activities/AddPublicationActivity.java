package pe.com.mucontact.activities;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import pe.com.mucontact.MuContactApp;
import pe.com.mucontact.R;
import pe.com.mucontact.models.User;
import pe.com.mucontact.network.MuContactApiService;

import org.json.JSONObject;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class AddPublicationActivity extends AppCompatActivity {
    private final String photoRute = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES) + "/misfotos/";
    private File file = new File(photoRute);
    private EditText instrumentEditText;
    private EditText descriptionEditText;
    private EditText locationAtEditText;
    private EditText dateEditText;
    private Button dateButton;
    private int day;
    private int month;
    private int year;
    User user;
    String TAG = "MuContact";
    private static final int REQUEST_PERMISSION_CAMARA = 1;
    Intent cameraIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_publication);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        instrumentEditText = (EditText) findViewById( R.id.instrumentInputEditText);
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

        user = MuContactApp.getInstance().getCurrentUser();
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

    public void floatingActionButtonClick(View view) {
        if(instrumentEditText.getText().toString().isEmpty()
                || descriptionEditText.getText().toString().isEmpty()
                || locationAtEditText.getText().toString().isEmpty())
        {
            Toast.makeText(getApplicationContext(), R.string.empty_fild, Toast.LENGTH_SHORT).show();
        } else {
            if (MuContactApp.getInstance().getCurrentPublication() == null) {
                savePublication();
            } else {
                editPublication();
            }
        }
    }

    public void layoutByOrigin() {
        if(MuContactApp.getInstance().getCurrentPublication() != null) {
            instrumentEditText.setText(MuContactApp.getInstance().getCurrentPublication().getInstrument());
            descriptionEditText.setText(MuContactApp.getInstance().getCurrentPublication().getDescription());
            locationAtEditText.setText(MuContactApp.getInstance().getCurrentPublication().getLocationReference());
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
                .addBodyParameter("instrument", instrumentEditText.getText().toString())
                .addBodyParameter("description", descriptionEditText.getText().toString())
                .addBodyParameter("locationAt", locationAtEditText.getText().toString())
                .addBodyParameter("user", user.get_id())
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
                .addBodyParameter("instrument", instrumentEditText.getText().toString())
                .addBodyParameter("description", descriptionEditText.getText().toString())
                .addBodyParameter("locationAt", locationAtEditText.getText().toString())
                .addBodyParameter("user", user.get_id())
                .addPathParameter("publication_id", MuContactApp.getInstance().getCurrentPublication().get_id())
                .setTag(TAG)
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Toast.makeText(getApplicationContext(), R.string.publication_save, Toast.LENGTH_SHORT).show();
                        MuContactApp.getInstance().setCurrentPublication(null);
                        finish();
                    }
                    @Override
                    public void onError(ANError error) {
                        Toast.makeText(getApplicationContext(), R.string.error_publication_save, Toast.LENGTH_SHORT).show();
                        MuContactApp.getInstance().setCurrentPublication(null);
                    }
                });
    }

    private void deletePublication() {
        AndroidNetworking.delete(MuContactApiService.PUBLICATION_EDIT_URL)
                .addPathParameter("publication_id", MuContactApp.getInstance().getCurrentPublication().get_id())
                .setTag(TAG)
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Toast.makeText(getApplicationContext(), R.string.publication_delete, Toast.LENGTH_SHORT).show();
                        MuContactApp.getInstance().setCurrentPublication(null);
                        finish();
                    }
                    @Override
                    public void onError(ANError error) {
                        Toast.makeText(getApplicationContext(), R.string.error_publication_delete, Toast.LENGTH_SHORT).show();
                        MuContactApp.getInstance().setCurrentPublication(null);
                    }
                });
    }

    @Override
    public void finish() {
        super.finish();
        MuContactApp.getInstance().setCurrentPublication(null);
    }
}
