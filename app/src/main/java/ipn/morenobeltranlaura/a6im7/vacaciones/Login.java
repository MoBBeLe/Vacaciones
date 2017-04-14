package ipn.morenobeltranlaura.a6im7.vacaciones;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

/*Autora: Laura Moreno Beltrán
La clase pide un usuario y una clave que se evalúan con el web service
el usuario correcto es LEMB y la clave correcta es 6IM7
 */
public class Login extends Activity {

    EditText nombre;
    EditText clave;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        nombre=(EditText)findViewById(R.id.nombre);
        clave=(EditText)findViewById(R.id.clave);
    }

    public void iniciarSesion(View vista)
    {
        if(nombre.equals("") || clave.equals(""))
            Toast.makeText(getApplicationContext(), "Llena todos los campos", Toast.LENGTH_SHORT).show();
        else
            new consumir().execute(nombre.getText().toString(),clave.getText().toString());
    }

    private class consumir extends AsyncTask<String,Void,String>{

        @Override
        protected String doInBackground(String... strings) {
            String respuesta="";
            try {
                String NAMESPACE = "http://WebServices/";
                String URL = "http://192.168.1.50:8080/WebServices/Tareas";
                String METHOD_NAME = "iniciarSesion";
                String SOAP_ACTION = "http://WebServices/Tareas/iniciarSesion";

                SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
                request.addProperty("nombre",strings[0]);
                request.addProperty("clave",strings[1]);
                SoapSerializationEnvelope envelope =
                        new SoapSerializationEnvelope(SoapEnvelope.VER11);
                envelope.setOutputSoapObject(request);
                HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);
                androidHttpTransport.call(SOAP_ACTION, envelope);
                SoapPrimitive result= (SoapPrimitive) envelope.getResponse();
                respuesta = result.toString();
            }
            catch(Exception e){
                Toast.makeText(getApplicationContext(),"Ha ocurrido un error",Toast.LENGTH_LONG).show();
                Log.d("Error ","1: ",e);
            }
            return respuesta;
        }

        @Override
        protected void onPostExecute(String s) {
            Toast.makeText(getApplicationContext(),s,Toast.LENGTH_LONG).show();
        }
    }
}
