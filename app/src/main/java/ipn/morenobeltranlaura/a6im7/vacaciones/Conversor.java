package ipn.morenobeltranlaura.a6im7.vacaciones;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.io.IOException;

/*Autora: Laura Moreno Beltrán
La clase pide una cantidad y después de seleccionar alguna moneda manda los datos
al servicio web para que realice la conversión y despliegue el resultado
 */
public class Conversor extends Activity {

    EditText dinero;
    String divisa="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conversor);
        dinero=(EditText)findViewById(R.id.dinero);
    }

    // Aquí se establece qué divisa se va a usar.
    public void setDivisa(View vista)
    {
        switch (vista.getId())
        {
            case R.id.dolar:
                divisa="dolar";
                break;
            case R.id.euro:
                divisa="euros";
                break;
            case R.id.franco:
                divisa="franco suizo";
                break;
            case R.id.marco:
                divisa="marco";
                break;
            case R.id.rublo:
                divisa="rublo";
                break;

        }
        Toast.makeText(getApplicationContext(),divisa,Toast.LENGTH_LONG).show();
    }

    public void convertir(View vista)
    {
        if(divisa.equals(""))
        {
            Toast.makeText(getApplicationContext(),"Pulsa algún botón de divisa",Toast.LENGTH_LONG).show();
        }else
            new conversor().execute(dinero.getText().toString(),divisa);
    }

    private class conversor extends AsyncTask<String,Void,String>{

        @Override
        protected String doInBackground(String... strings) {

            String result="";
            HttpClient cliente=new DefaultHttpClient();
            HttpGet peticion=new HttpGet("http://192.168.1.50:8080/WebServices/webresources/Conversor/" +
                    "Monedas/dinero="+strings[0]+"&moneda="+strings[1]+"");
            peticion.setHeader("content-type","text/plain");
            try {
                HttpResponse res=cliente.execute(peticion);
                result= EntityUtils.toString(res.getEntity());
            } catch (Exception e) {
                Toast.makeText(getApplicationContext(),"Error: "+e.getMessage(),Toast.LENGTH_LONG).show();
                Log.d("Error",":",e);
            }
            return result;
        }

        @Override
        protected void onPostExecute(String s) {
            Toast.makeText(getApplicationContext(),s,Toast.LENGTH_LONG).show();
        }
    }
}
