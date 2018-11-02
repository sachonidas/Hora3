package es.luissachaarancibiabazan.hora3;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {


    AdminSqlite adminSqlite;
    RecyclerView recyclerView;
    AdapterHoras adapterHoras;
    TextView txtHoras, txtFecha, txtMedia, txtPrecio;
    LinearLayout layoutMedia, layoutPrecio;
    String spPrecio;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);

        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(this);

        recyclerView = (RecyclerView)findViewById(R.id.reciclerview);
        txtHoras = (TextView) findViewById(R.id.txtHoras);
        txtFecha = (TextView)findViewById(R.id.txtFecha);
        txtMedia = (TextView)findViewById(R.id.txtMedia);
        txtPrecio = (TextView)findViewById(R.id.txtPrecio);
        layoutMedia = (LinearLayout)findViewById(R.id.layoutMedia);
        layoutPrecio = (LinearLayout)findViewById(R.id.layoutPrecio);



        if (sharedPref.getBoolean("media_horas_sw", true)){
            layoutMedia.setVisibility(View.VISIBLE);
        }else{
            layoutMedia.setVisibility(View.GONE);
        }

        if (sharedPref.getBoolean("precio_horas_sw", true)){
            layoutPrecio.setVisibility(View.VISIBLE);
        }else{
            layoutPrecio.setVisibility(View.GONE);
        }


        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), NuevaHora.class );
                startActivity(intent);
            }
        });

        adminSqlite = new AdminSqlite(this);

        spPrecio = sharedPref.getString("precio_horas_et","");
        Log.i("precio", spPrecio);
        setMainRecyclerView();
        setMediaHoras();
        setPrecioHoras();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            /*if (adminSqlite.deleteHoras() != 0){
                Snackbar.make(getWindow().getDecorView().getRootView(), "Tabla Borrada", Snackbar.LENGTH_LONG)
                        .show();
                setMainRecyclerView();
            }*/
            Intent intent = new Intent(this, SettingsActivity.class);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    public List<Hora> getData(){

        List<Hora> horaList = new ArrayList<>();
        Hora hora = null;
        Cursor c = adminSqlite.getHoras();
        if (c != null){
            while (c.moveToNext()) {
                String horaString = c.getString(1);
                String Fecha = c.getString(2);
                String descripcion = c.getString(3);
                hora = new Hora();
                hora.setNumeroHoras(horaString);
                hora.setFechaHora(Fecha);
                hora.setDescripcion(descripcion);
                horaList.add(hora);

            }
        }

        return horaList;
    }

    public  void setMainRecyclerView(){
        adapterHoras = new AdapterHoras(getData());
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapterHoras);
        adapterHoras.notifyDataSetChanged();
    }

    public void setMediaHoras(){
        float mediaHoras;
        mediaHoras = adminSqlite.getMediaHoras();
        Log.i("Media", String.valueOf(mediaHoras));
        if (Float.isNaN(mediaHoras) != true){
            txtMedia.setText(String.valueOf(mediaHoras));
        }else{
            txtMedia.setText("0");
        }

    }
    public void setPrecioHoras(){
        float precioHoras = Float.valueOf(spPrecio);
        float precioFinal = 0;
        //float mediaHoras = adminSqlite.getMediaHoras();
        Cursor c = adminSqlite.getHoras();
        if (c != null){
            while (c.moveToNext()) {
                float horasFloat = c.getFloat(1);
                precioFinal += horasFloat * precioHoras;
            }
        }

        //Log.i("Media", String.valueOf(mediaHoras));
        if (precioFinal != 0){
            txtPrecio.setText(String.valueOf(precioFinal));
        }else{
            txtPrecio.setText("0");
        }

    }

    @Override
    protected void onResume() {
        super.onResume();
        setMainRecyclerView();
    }
}
