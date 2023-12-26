package cl.cvega.android.evaluacionu1.modelo

import cl.cvega.android.evaluacionu1.MainActivity
import java.text.NumberFormat
import java.util.*

class CuentaMesa(val mesa: Int) {
    private val items: MutableList<ItemMesa> = mutableListOf()
    var aceptaPropina: Boolean = true

    fun agregarItem(itemMenu: ItemMenu, cantidad: Int) {
        val itemMesa = ItemMesa(itemMenu, cantidad)
        agregarItem(itemMesa)
    }

    fun agregarItem(itemMesa: ItemMesa) {
        items.add(itemMesa)
    }
    fun limpiarItems() {
        items.clear()
    }

    fun calcularTotalSinPropina(): Int {
        return items.sumBy { it.calcularSubtotal() }
    }

    fun calcularPropina(): Int {
        return if (aceptaPropina) (calcularTotalSinPropina() * 0.1).toInt() else 0
    }

    fun calcularTotalConPropina(): Int {
        return calcularTotalSinPropina() + calcularPropina()
    }
}
