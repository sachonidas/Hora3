package es.luissachaarancibiabazan.hora3;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {


    AdminSqlite adminSqlite;
    RecyclerView recyclerView;
    AdapterHoras adapterHoras;
    TextView txtHoras, txtFecha;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        recyclerView = (RecyclerView)findViewById(R.id.reciclerview);
        txtHoras = (TextView) findViewById(R.id.txtHoras);
        txtFecha = (TextView)findViewById(R.id.txtFecha);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), NuevaHora.class );
                startActivity(intent);
            }
        });

        adminSqlite = new AdminSqlite(this);

        setMainRecyclerView();

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
                hora = new Hora();
                hora.setNumeroHoras(horaString);
                hora.setFechaHora(Fecha);
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

    }

    @Override
    protected void onResume() {
        super.onResume();
        setMainRecyclerView();
    }
}
