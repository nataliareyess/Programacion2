package cl.cvega.android.evaluacionu1

import java.text.NumberFormat
import java.util.Locale
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import android.widget.Switch
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import cl.cvega.android.evaluacionu1.modelo.CuentaMesa
import cl.cvega.android.evaluacionu1.modelo.ItemMenu


class MainActivity : AppCompatActivity() {
    //Declaramos las Variables
    private lateinit var cuentaMesa: CuentaMesa
    private var etCantidadPastel:EditText? = null
    private var etCantidadCazuela:EditText? = null
    private var textValorComida:TextView? = null
    private var textPropina:TextView? = null
    private var textTotal:TextView? = null
    private var swPropina:Switch? = null
    private var tvValorPastel:TextView? = null
    private var tvValorCazuela:TextView? = null
    //Variables con los valores de los productos
    val pastelChoclo = "Pastel de Choclo"
    val cazuela = "Cazuela"
    val costoPastel = "12000"
    val costoCazuela = "10000"
    //Variable para dar formato a los numeros
    val formatoMonedaChilena = NumberFormat.getCurrencyInstance(Locale("es", "CL"))

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        cuentaMesa = CuentaMesa(1)

        swPropina = findViewById<Switch>(R.id.swPropina)
        etCantidadPastel = findViewById<EditText>(R.id.etCantidadPastel)
        etCantidadCazuela = findViewById<EditText>(R.id.etCantidadCazuela)
        textValorComida = findViewById<TextView>(R.id.tvValorComida)
        textPropina = findViewById<TextView>(R.id.tvValorPropina)
        textTotal = findViewById<TextView>(R.id.tvValorTotal)
        tvValorPastel = findViewById<TextView>(R.id.tvValorPastel)
        tvValorCazuela = findViewById<TextView>(R.id.tvValorCazuela)

        //Inicializamos todos los Valores
        actualizarTotales()

        //Switch
        //swPropina?.isChecked = cuentaMesa.aceptaPropina

        swPropina?.setOnCheckedChangeListener { _, isChecked ->
            cuentaMesa.aceptaPropina  = isChecked
            actualizarTotales()
        }

        //cuentaMesa.aceptaPropina = false

        // Configurar listeners para EditText y Switch
        val textWatcher:TextWatcher = object : TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                //actualizarTotales()
            }
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                //actualizarTotales()
            }
            override fun afterTextChanged(s: Editable?) {
                actualizarTotales()
            }
        }
        //Declaramos que los 2 ET pertenecen o van a trabajar en un solo TextWhacher
        //asi evitamos crear 2 veces esas lineas de codigo
        etCantidadPastel?.addTextChangedListener(textWatcher)
        etCantidadCazuela?.addTextChangedListener(textWatcher)
    }

    fun actualizarTotales() {
        //Limpiamos la lista para poder resetear los valores que vemos en los TextView
        cuentaMesa.limpiarItems()

        //Obtenemos la cantidad de los productos de los EditText
        val cantPastelDeChoclo = etCantidadPastel?.text.toString().toIntOrNull() ?: 0
        val cantCazuela = etCantidadCazuela?.text.toString().toIntOrNull() ?: 0

        cuentaMesa.agregarItem(ItemMenu(pastelChoclo, costoPastel), cantPastelDeChoclo)
        cuentaMesa.agregarItem(ItemMenu(cazuela, costoCazuela), cantCazuela)

        actualizarSubTotal()
        actualizarPropina()
        actualizarPrecioTotal()
        actualizarPrecioPorProducto()
    }
    //Funciones para actualizar cada Valor (Sin propina, Propina, y valor con propina)
    fun actualizarSubTotal(){
        val valorComida = cuentaMesa.calcularTotalSinPropina()
        textValorComida?.setText(valorComida.toString())
        textValorComida?.text = formatoMonedaChilena.format(valorComida)
    }

    fun actualizarPropina(){
        val propina = cuentaMesa.calcularPropina()
        textPropina?.setText(propina.toString())
        textPropina?.text = formatoMonedaChilena.format(propina)
    }

    fun actualizarPrecioTotal(){
        val total = cuentaMesa.calcularTotalConPropina()
        textTotal?.setText(total.toString())
        textTotal?.text = formatoMonedaChilena.format(total)
    }

    //Funcion para actualizar el precio de cada producto
    fun actualizarPrecioPorProducto(){

        val cantPastelDeChoclo = etCantidadPastel?.text.toString().toIntOrNull() ?: 0
        val cantCazuela = etCantidadCazuela?.text.toString().toIntOrNull() ?: 0

        val totalValorPastel = cantPastelDeChoclo * costoPastel.toInt()
        val totalValorCazuela = cantCazuela * costoCazuela.toInt()

        tvValorPastel?.setText(totalValorPastel.toString())
        tvValorCazuela?.setText(totalValorCazuela.toString())

        tvValorPastel?.text = formatoMonedaChilena.format(totalValorPastel)
        tvValorCazuela?.text = formatoMonedaChilena.format(totalValorCazuela)

    }
}

