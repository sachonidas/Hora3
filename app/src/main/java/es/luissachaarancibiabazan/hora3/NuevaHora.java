package es.luissachaarancibiabazan.hora3;

import android.app.DatePickerDialog;
import android.database.Cursor;
import android.os.Build;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import java.sql.Date;
import java.util.Calendar;

public class NuevaHora extends AppCompatActivity {

    EditText etHoras, etFechas, etDesc;
    Button btnGuardar;
    AdminSqlite adminSqlite;
    String horaString, fechaString, descString;
    DatePickerDialog datePickerDialog;
    int year, month, dayOfMonth;
    Calendar calendar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nueva_hora);

        etFechas = (EditText)findViewById(R.id.etFechas);
        etHoras = (EditText)findViewById(R.id.etHoras);
        etDesc = (EditText)findViewById(R.id.etDesc);
        btnGuardar = (Button)findViewById(R.id.btnGuardar);

        adminSqlite = new AdminSqlite(this);

        etFechas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                calendar = Calendar.getInstance();
                year = calendar.get(Calendar.YEAR);
                month = calendar.get(Calendar.MONTH);
                dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
                datePickerDialog = new DatePickerDialog(NuevaHora.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                        month += 1;
                        fechaString = day + "/" + month + "/" + year ;
                        etFechas.setText(fechaString);
                    }
                }, year, month, dayOfMonth);
                datePickerDialog.show();
            }
        });

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            etFechas.setShowSoftInputOnFocus(false);
        }

        btnGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Cursor c = adminSqlite.getHoras();
                if (TextUtils.isEmpty(etHoras.getText()) && TextUtils.isEmpty(etFechas.getText())){
                    Toast.makeText(NuevaHora.this, "Rellena todos los campos", Toast.LENGTH_SHORT).show();
                }else{
                    horaString = etHoras.getText().toString();
                    descString = etDesc.getText().toString();
                    Toast.makeText(NuevaHora.this, "Horas: "+horaString+"\n fecha: "+fechaString+"\n descripcion: "+descString, Toast.LENGTH_SHORT).show();
                    adminSqlite.setNewHoras(horaString, fechaString, descString);
                    finish();
                }
            }
        });


    }
}
