package ipn.morenobeltranlaura.a6im7.vacaciones;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;
import android.widget.Toast;

/*Autora: Laura Moreno Beltrán
Muestra una lista con las actividades disponmibles e inicia la actividad seleccionada
al hacer click en el botón
 */
public class menu extends Activity {

    Spinner actividad;
    String eleccion="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        actividad=(Spinner)findViewById(R.id.actividades);

        actividad.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                eleccion=adapterView.getItemAtPosition(i).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                eleccion="";
            }
        });
    }

    public void crearActividad(View vista)
    {
        switch (eleccion)
        {
            case "Conversor":
                Intent intento=new Intent(this,Conversor.class);
                finish();
                startActivity(intento);
                break;
            case "Login":
                Intent intentodos=new Intent(this,Login.class);
                finish();
                startActivity(intentodos);
                break;
            case "Calculadora":
                Intent intentotres=new Intent(this,Calculadora.class);
                finish();
                startActivity(intentotres);
                break;
            default:
                Toast.makeText(getApplicationContext(),"Elije alguna actividad",Toast.LENGTH_LONG).show();
                break;
        }
    }


}
