package ipn.morenobeltranlaura.a6im7.vacaciones;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

/*Autora: Laura Moreno Beltrán
La clase pide un número y pide que se seleccione una operación para después llamar al
servicio que realiza la operación y despliega el resultado al usuario
 */
public class Calculadora extends Activity {

    EditText numero;
    Spinner ops;
    String operacion="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calculadora);
        numero=(EditText)findViewById(R.id.numero);
        ops=(Spinner)findViewById(R.id.ops);

        ops.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                switch (i)
                {
                    case 0:
                        operacion="sin";
                        break;
                    case 1:
                        operacion="cos";
                        break;
                    case 2:
                        operacion="tan";
                        break;
                    case 3:
                        operacion="factorial";
                        break;
                }
                Toast.makeText(getApplicationContext(),""+adapterView.getItemAtPosition(i),Toast.LENGTH_LONG).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    public void calcular(View vista){
        if(operacion.equals(""))
            Toast.makeText(getApplicationContext(), "Elije una operación", Toast.LENGTH_SHORT).show();
        else
            new calculador().execute(numero.getText().toString(),operacion);
    }

    private class calculador extends AsyncTask<String,Void,String>
    {

        @Override
        protected String doInBackground(String... strings) {

            String respuesta="";
            try {
                String NAMESPACE = "http://WebServices/";
                String URL = "http://192.168.1.50:8080/WebServices/Tareas";
                String METHOD_NAME = "Calculadora";
                String SOAP_ACTION = "http://WebServices/Tareas/Calculadora";

                SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
                request.addProperty("xNum",strings[0]);
                request.addProperty("operacion",strings[1]);
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
                Log.d("Error",": ",e);
            }
            return respuesta;
        }

        @Override
        protected void onPostExecute(String results) {
            Toast.makeText(getApplicationContext(),"El resultado es: "+results,Toast.LENGTH_LONG).show();
        }
    }
}
