package com.carlos.peopleconnect;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //la app está pensada para pantalla vertical, así que forzamos dicha posicion
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        //listeners
        //listener del boton de registro
        findViewById(R.id.botonRegistro).setOnClickListener(this);
        //listener del boton de buscar
        findViewById(R.id.botonBuscar).setOnClickListener(this);
    }

    /**
     * Método onClick del listener, que ejecuta acciones según el botón pulsado
     *
     * @param v boton pulsado, a continuación se sacará la id para determinar cuál fué.
     */
    @Override
    public void onClick(View v) {

        switch (v.getId()) {

            //boton de registro
            case R.id.botonRegistro:

                //Activity de registro
                Intent i = new Intent(this, Registro.class);
                //salto al activity Registro
                startActivity(i);
                break;

            //boton de buscar
            case R.id.botonBuscar:

                //edittext con el telefono a buscar
                EditText busqueda = findViewById(R.id.editBusqueda);

                //instanciamos la busqueda del usuario
                String busquedaUser = busqueda.getText().toString();

                if (usuarioEncontrado(busquedaUser)) {
                    //Activity donde se cargan los datos del usuario buscado
                    Intent b = new Intent(this, Usuario.class);
                    //ponemos la busqueda como extra para pasarlo al siguiente activity
                    b.putExtra("busqueda", busquedaUser);
                    //saltamos de activity con parametro Intent "b"
                    startActivity(b);
                } else {
                    AlertDialog.Builder builder = new AlertDialog.Builder(this);
                    builder.setTitle("Error");
                    builder.setMessage("No se ha encontrado ningún usuario con ese teléfono");
                    builder.setPositiveButton("Aceptar", null);
                    AlertDialog errorDialog = builder.create();
                    errorDialog.show();
                }
                break;
        }
    }

    /**
     * Método para ver si el usuario buscado encuentra
     *
     * @param busquedaUser busqueda del usuario
     * @return devuelve true si existe y false sino.
     */
    public boolean usuarioEncontrado(String busquedaUser) {

        //booleana de control
        boolean encontrado = false;

        //creamos un objeto admin
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this,
                "administracion", null, 1);

        //abrimos la base de datos
        SQLiteDatabase bd = admin.getWritableDatabase();

        try { //intentamoslanzar el select
            Cursor fila = bd.rawQuery(
                    "select nombre, apellidos, fechaNac, estCiv, pais, provincia, codPos, email, pass, avatar numero from usuario where telefono=" + busquedaUser, null);

            if(fila.moveToFirst()){
                //si se encuentra la booleana de control cambia a true
                encontrado = true;
            }

        } catch (Exception e) {

        } finally { //si algo fallase, igualmente cerramos la base de datos
            bd.close();
        }

        //devolvemos la booleana
        return encontrado;
    }
}