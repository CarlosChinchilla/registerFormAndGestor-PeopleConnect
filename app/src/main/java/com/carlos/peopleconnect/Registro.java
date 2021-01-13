package com.carlos.peopleconnect;

import android.app.DatePickerDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.security.SecureRandom;
import java.util.Calendar;

public class Registro extends AppCompatActivity implements View.OnClickListener, DatePickerDialog.OnDateSetListener {

    EditText campoNombre; //edittext del nombre
    EditText campoApellidos; //edittext del nombre
    EditText campoFecha; //edittext de la fecha de nacimiento
    EditText campoCP; //edittext del codigo postal
    EditText campoMail; //edittext de email
    EditText campoTelefono; //edittext del telefono
    EditText campoPass; //edittext de la contraseña
    EditText campoPassRep; //edittext para repetir la contraseña
    CheckBox checkCond; //checkbox de aceptar condiciones
    TextView textCond; //text de las condiciones
    Spinner spinnerPais; //spinner con la selección de paises
    Spinner spinnerProvincia; //spinner con las provincias del pais seleccionado
    RadioGroup groupEstCiv; //radiogroup del estado civil
    RadioGroup groupAvatar; //radiogroup del avatar
    TextView captcha; //captcha
    EditText editCaptcha; //edittext donde se escribe el captcha

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);

        //la app está pensada para pantalla vertical, así que forzamos dicha posicion
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        //edittext que contendra la fecha de nacimiento
        campoNombre = findViewById(R.id.editNombre);
        //edittext que contendra la fecha de nacimiento
        campoApellidos = findViewById(R.id.editApellidos);
        //edittext que contendra la fecha de nacimiento
        campoFecha = findViewById(R.id.editFecNac);
        //edittext del codigo postal
        campoCP = findViewById(R.id.editCP);
        //editetext del email
        campoMail = findViewById(R.id.editMail);
        //editetext del email
        campoTelefono = findViewById(R.id.editTelefono);
        //editetext del email
        campoPass = findViewById(R.id.editPass);
        //editetext del email
        campoPassRep = findViewById(R.id.editRepPass);
        //checkbox de aceptar condiciones
        checkCond = findViewById(R.id.checkBoxCond);
        //textview de las condidicnes
        textCond = findViewById(R.id.textCond);
        //boton de registro
        Button registro = findViewById(R.id.botonRegistro);
        //spinner de paises
        spinnerPais = findViewById(R.id.spinnerPais);
        //spinner de provincias (segun el pais seleccionado)
        spinnerProvincia = findViewById(R.id.spinnerProvincia);
        //radiogroup del estado civil
        groupEstCiv = findViewById(R.id.radioGroup);
        //radiogroup del avatar
        groupAvatar = findViewById(R.id.radioGroupAvatar);
        //edittext donde se escribe el captcha
        editCaptcha = findViewById(R.id.editCaptcha);
        //captcha
        captcha = findViewById(R.id.captcha);

        //adapter del spinner
        ArrayAdapter spinnerAdapter = ArrayAdapter.createFromResource(this, R.array.ArrayPaises, android.R.layout.simple_spinner_item);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //aplicamos el adapter
        spinnerPais.setAdapter(spinnerAdapter);

        //rellenamos el spinner de provincias
        ArrayAdapter spinnerAdapter2 = ArrayAdapter.createFromResource(this, R.array.ArrayEspana, android.R.layout.simple_spinner_item);
        spinnerProvincia.setAdapter(spinnerAdapter2);
        //aplicacion de un color personalizado al spinner
        spinnerProvincia.setSelection(0, true);
        View p = spinnerProvincia.getSelectedView();
        ((TextView) p).setTextColor(getResources().getColor(R.color.colorPrimary));
        //listener para que se aplique a cada opcion
        spinnerProvincia.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            //cuando se selecciona un item se cambiara el color
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //cambiamos su color
                ((TextView) view).setTextColor(getResources().getColor(R.color.colorPrimary));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        //aplicacion de un color personalizado al spinner
        spinnerPais.setSelection(0, true);
        View v = spinnerPais.getSelectedView();
        ((TextView) v).setTextColor(getResources().getColor(R.color.colorPrimary));
        //listener para que se aplique a cada opcion
        spinnerPais.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            //cuando se selecciona un item se cambiara el color y el contenido de "provincias"
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //cambiamos su color
                ((TextView) view).setTextColor(getResources().getColor(R.color.colorPrimary));

                //creamos una variable que contendrá el array de provincias correspondiente
                int provincia = 0;

                //switch segun el pais seleccionado
                switch(spinnerPais.getSelectedItem().toString()){

                    case "España":
                        provincia = R.array.ArrayEspana;
                        break;

                    case "Alemania":
                        provincia = R.array.ArrayAlemania;
                        break;

                    case "Finlandia":
                        provincia = R.array.ArrayFinlandia;
                        break;

                    case "Portugal":
                        provincia = R.array.ArrayPortugal;
                        break;

                    default:
                        break;
                }
                ArrayAdapter spinnerAdapter2 = ArrayAdapter.createFromResource(Registro.this, provincia, android.R.layout.simple_spinner_item);
                spinnerProvincia.setAdapter(spinnerAdapter2);

                //aplicacion de un color personalizado al spinner
                spinnerProvincia.setSelection(0, true);
                View p = spinnerProvincia.getSelectedView();
                ((TextView) p).setTextColor(getResources().getColor(R.color.colorPrimary));
                //listener para que se aplique a cada opcion
                spinnerProvincia.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    //cuando se selecciona un item se cambiara el color
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        //cambiamos su color
                        ((TextView) view).setTextColor(getResources().getColor(R.color.colorPrimary));
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {
                    }
                });
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        //rellenamos el captcha
        captchaGenerator();

        //listeners
        //listener para el edittext con la fecha de nacimiento
        campoFecha.setOnClickListener(this);
        //listener del boton de registro
        registro.setOnClickListener(this);
        //listener boton atras
        findViewById(R.id.imageback).setOnClickListener(this);
        //listener condiciones
        textCond.setOnClickListener(this);

    }

    /**
     * Método onClick del listener, que ejecuta acciones según el botón pulsado
     *
     * @param v boton pulsado, a continuación se sacará la id para determinar cuál fué.
     */
    @Override
    public void onClick(View v) {

        switch (v.getId()) {

            //edittext con la fecha de nacimiento
            case R.id.editFecNac:

                //llamamos al método para introducir una fecha con un picker
                getDatePickerDialog();

                break;

            //botón de registro
            case R.id.botonRegistro:

                if (validar()) { //si se valida los datos de todos los campos

                    //si el telefono no existe en la bd
                    if(!comprobacionTelefono(campoTelefono.getText().toString())){
                        registro(); //registramos al usuario
                    }else{//si no se informa de lo propio al usuario
                        AlertDialog.Builder builder = new AlertDialog.Builder(this);
                        builder.setTitle("Error");
                        builder.setMessage("Ya existe un usuario registrado con ese teléfono");
                        builder.setPositiveButton("Aceptar", null);
                        AlertDialog errorDialog = builder.create();
                        errorDialog.show();
                    }

                } else { //si no, se informa al usuario
                    AlertDialog.Builder builder = new AlertDialog.Builder(this);
                    builder.setTitle("Error");
                    builder.setMessage("Existen campos con datos erroneos");
                    builder.setPositiveButton("Aceptar", null);
                    AlertDialog errorDialog = builder.create();
                    errorDialog.show();
                }

                break;

            //boton atras
            case R.id.imageback:

                //retroceso al activity principal
                Intent a = new Intent(this, MainActivity.class);
                startActivity(a);

                break;

            //condiciones de uso
            case R.id.textCond:

                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("TÉRMINOS Y CONDICIONES DE USO");
                builder.setMessage(R.string.condicionesDeUso);
                builder.setPositiveButton("Aceptar", null);
                AlertDialog condicionesDialog = builder.create();
                condicionesDialog.show();

                break;
        }
    }

    /**
     * Método que creará un dialog donde elegir una fecha
     */
    private void getDatePickerDialog() {

        //creamos el objeto datepickerdialog
        DatePickerDialog datePickerDialog = new DatePickerDialog(
                this, //el contexto es esta activity
                this, //listener
                //llamadas directas al método .get() de la la clase Calendar gracias al método .getinstance()
                Calendar.getInstance().get(Calendar.YEAR), //año
                Calendar.getInstance().get(Calendar.MONTH), //mes
                Calendar.getInstance().get(Calendar.DAY_OF_MONTH) //dia
        );
        //mostramos el dialog
        datePickerDialog.show();
    }

    /**
     * Método que crea un String a través del pick del datePickerDialog y lo introduce en el edittext específico
     *
     * @param view
     * @param year año seleccionado
     * @param month mes seleccionado
     * @param dayOfMonth dia seleccionado
     */
    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

        String date = dayOfMonth + "/" + (month+1) + "/" + year;
        campoFecha.setText(date);
    }

    /**
     * Método que valida los inputs del formulario
     *
     * @return devuelve true si esta correcto y false si no lo está
     */
    public boolean validar() {

        //booleano de control
        boolean todoOk = true;

        //comprobacion de que el nombre tiene al menos 2 caracteres y menos de 15
        if (campoNombre.getText().length() < 2 || campoNombre.getText().length() > 15) {
            todoOk = false;
            campoNombre.setError("Este campo ha de tener al menos 2 caracteres");
        }

        //comprobacion de que el apellido tiene al menos 4 caracteres y menos de 30 e incluye un espacio
        if (campoApellidos.getText().length() < 4 || !campoApellidos.getText().toString().contains(" ") || campoApellidos.getText().length() > 30) {
            todoOk = false;
            campoApellidos.setError("Este campo ha de tener al menos 4 caracteres y un espacio");
        }

        //comprobacion de que se ha elegido una fecha
        if (campoFecha.getText().length() == 0) {
            todoOk = false;
            campoFecha.setError("Seleccione una fecha");
        } else {
            campoFecha.setError(null);
        }

        //comprobacion de que el codigo postal tiene 5 digitos
        if (campoCP.getText().length() != 5) {
            todoOk = false;
            campoCP.setError("El código postal ha de tener 5 dígitos");
        }

        //comprobacion de que el email es correcto
        if (campoMail.getText().length() == 0 || campoMail.getText().length() > 30 || !validacionMail(campoMail.getText().toString())) {
            todoOk = false;
            campoMail.setError("El email debe seguir la estructura tradicional");
        }

        //comprobación de que el email tiene 9 digitos
        if (campoTelefono.getText().length() != 9) {
            todoOk = false;
            campoTelefono.setError("El telefono ha de tener 9 dígitos");
        }

        //comprobación de que la contraseña tiene la estructura correcta ///
        if (campoPass.getText().length() < 8 || campoPass.getText().length() > 20 || !validacionPass(campoPass.getText().toString())) {
            todoOk = false;
            campoPass.setError("La contraseña debe tener una mayúscula, un número y tener más de 8 caracteres");
        }

        //comprobación de que la repeticion de la contraseña coincide con la original
        if (!campoPassRep.getText().toString().equals(campoPass.getText().toString())) {
            todoOk = false;
            campoPassRep.setError("Las contraseñas no coinciden");
        }

        //comprobacion de si se aceptan las condiciones
        if (!checkCond.isChecked()) {
            todoOk = false;
            textCond.setError("Debe aceptar las condiciones de uso");
        } else {
            textCond.setError(null);
        }

        //comprobación de que se ha introducido el captcha correctamente
        if(!editCaptcha.getText().toString().equals(captcha.getText().toString())){
            todoOk = false;
            editCaptcha.setError("El código del captcha no coincide");
        }

        return todoOk;
    }

    /**
     * Método de validacion de email mediante expresionas regulares (palabra + @ + palabra + . + palabra)
     *
     * @param email email a comprobar
     * @return true si es válido y false si no lo es
     */
    static boolean validacionMail(String email) {
        String expReg = "^[\\w-_\\.+]*[\\w-_\\.]\\@([\\w]+\\.)+[\\w]+[\\w]$";
        return email.matches(expReg);
    }

    /**
     * Método de validacion de la contraseña mediante expresionas regulares (8 caracteres / un número / una minúscula / una mayúscula)
     *
     * @param pass contraseña a comprobar
     * @return true si es válido y false si no lo es
     */
    static boolean validacionPass(String pass) {
        String expReg = "^.*(?=.{8,})(?=..*[0-9])(?=.*[a-z])(?=.*[A-Z]).*$";
        return pass.matches(expReg);
    }

    /**
     * Método para registrar nuevos usuario
     *
     */
    public void registro() {

        //creamos un objeto admin
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this,
                "administracion", null, 1);
        //abrimos la base de datos
        SQLiteDatabase bd = admin.getWritableDatabase();

        //instanciamos los datos del formulario
        int telefono = Integer.parseInt(campoTelefono.getText().toString());
        String nombre = campoNombre.getText().toString();
        String apellidos = campoApellidos.getText().toString();
        String fechaNac = campoFecha.getText().toString();
        String pais = spinnerPais.getSelectedItem().toString();
        String provincia = spinnerProvincia.getSelectedItem().toString();
        int codPos = Integer.parseInt(campoCP.getText().toString());
        String email = campoMail.getText().toString();
        String pass = campoPass.getText().toString();

        //obtenemos el id del radio button seleccionado del radiogroup
        int selectedIdEC = groupEstCiv.getCheckedRadioButtonId();
        //instanciamos el radio button en cuestion
        RadioButton radioEstCiv = findViewById(selectedIdEC);
        //dato a insertar
        String estCiv = radioEstCiv.getText().toString();

        //dato a insertar
        String avatar = "";
        //switch del radiogroup
        switch(groupAvatar.getCheckedRadioButtonId()){
            case R.id.radioButAv1:
                avatar = "dovahkiin";
                break;
            case R.id.radioButAv2:
                avatar = "dragon";
                break;
            case R.id.radioButAv3:
                avatar = "assassin";
                break;
        }

        //rellenamos la query
        ContentValues registro = new ContentValues();
        registro.put("telefono", telefono);
        registro.put("nombre", nombre);
        registro.put("apellidos", apellidos);
        registro.put("fechaNac", fechaNac);
        registro.put("estCiv", estCiv);
        registro.put("pais", pais);
        registro.put("provincia", provincia);
        registro.put("codPos", codPos);
        registro.put("email", email);
        registro.put("pass", pass);
        registro.put("avatar", avatar);

        try{
            // los insertamos en la base de datos
            bd.insert("usuario", null, registro);

            //informamos al usuario con un dialog del exito
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Información");
            builder.setMessage("Usuario registrado correctamente.");
            builder.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    // redirigimos a la pantalla principal
                    Intent i = new Intent(Registro.this, MainActivity.class);
                    startActivity(i);
                }
            });
            AlertDialog infoDialog = builder.create();
            infoDialog.show();

        }catch (Exception e){
            //informamos al usuario con un dialog del error
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Error");
            builder.setMessage("Ha ocurrido un error inesperado, contacte con el administrador.");
            builder.setPositiveButton("Aceptar", null);
            AlertDialog errDialog = builder.create();
            errDialog.show();
        }finally { //si algo fallase, igualmente cerramos la base de datos
            bd.close();
        }
    }

    /**
     * Método que comprueba si existe el teléfono ya en la base de datos
     *
     * @param telefono teléfono del usuario que se quiere registrar
     * @return devuelve true si existe ya el telefono en la bd y false si no existe
     */
    public boolean comprobacionTelefono(String telefono) {

        //variable de control (true si existe ya el telefono en la bd y false si no existe)
        boolean noExiste = false;

        //creamos un objeto admin
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this,
                "administracion", null, 1);
        //abrimos la base de datos
        SQLiteDatabase bd = admin.getWritableDatabase();

        try{
            //hacemos una consulta sencilla para ver si existe
            Cursor fila = bd.rawQuery(
                    "select nombre from usuario where telefono=" + telefono, null);
            //si se encuentr coincidencia la variable de control pasa a true
            if (fila.moveToFirst()) {
                noExiste = true;
            }
        }catch (Exception e){

        }finally { //si algo falla se cierra la bd igualmente
            bd.close();
        }

        return noExiste;
    }

    /**
     * Método que genera un String de de seis caracteres aleatorios entre letras minúsculas, mayúsculas y números y lo pone en el campo correspondiente
     */
    public void captchaGenerator(){

        //string de donde saldrán los caracteres que formarán el captcha
        String modelo = "123456789qwertyuiopkjhgfdsazxcvbnmQWERTYUPLKJHGFDSAZXCVBNM";
        //string que contendrá un captcha de 6 caracteres
        StringBuilder textoCaptcha = new StringBuilder();
        //objeto SecureRandom
        SecureRandom random = new SecureRandom();

        //por cada caracter que tendrá el captcha...
        for(int i=0; i < 6; i++){
            //generamos un número al azar dentro del rango del String modelo
            int rand = random.nextInt(modelo.length()-1);
            //guardamos el caracter aleatorio del String modelo
            char caracter = modelo.charAt(rand);
            //se añade el caracter al catcha
            textoCaptcha.append(caracter);
        }

        //ponemos el resultado en el campo correspondiente
        captcha.setText(textoCaptcha);
    }
}