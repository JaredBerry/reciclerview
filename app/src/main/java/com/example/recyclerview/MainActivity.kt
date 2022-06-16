package com.example.recyclerview

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
//import android.view.ActionMode
import androidx.appcompat.view.ActionMode
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout

class MainActivity : AppCompatActivity() {

    var lista:RecyclerView? = null
    var layoutManager: RecyclerView.LayoutManager? = null
    var isActionMode = false
    var adaptador:AdaptadorCustom? = null

    var actionMode:ActionMode? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        val platillos = ArrayList<Platillo>()

        platillos.add(Platillo("Alitas de Pollo Teryaki", 250.0, 3.5F, R.drawable.platillo01))
        platillos.add(Platillo("Ensalada Cesar", 250.0, 3.5F,R.drawable.platillo02))
        platillos.add(Platillo("Biscochos Salados", 250.0, 3.5F,R.drawable.platillo03))
        platillos.add(Platillo("Papas Horneadas con Tocino", 250.0, 3.5F,R.drawable.platillo04))
        platillos.add(Platillo("Coctel de Frutas", 250.0, 3.5F,R.drawable.platillo05))
        platillos.add(Platillo("Papas a la Francesa", 250.0, 3.5F,R.drawable.platillo06))
        platillos.add(Platillo("Pollo al Carbon y Especias", 250.0, 3.5F,R.drawable.platillo07))
        platillos.add(Platillo("Pasta Italiana y Albondigas", 250.0, 3.5F,R.drawable.platillo08))
        platillos.add(Platillo("Bolitas de Jamon Serrano", 250.0, 3.5F,R.drawable.platillo09))
        platillos.add(Platillo("Chiles Rellenos de Atun", 250.0, 3.5F,R.drawable.platillo10))

        lista = findViewById(R.id.lista)
        lista?.setHasFixedSize(true)

        layoutManager = LinearLayoutManager(this)
        lista?.layoutManager = layoutManager


        val callback = object: ActionMode.Callback{

            override fun onActionItemClicked(p0: ActionMode?, p1: MenuItem?): Boolean {

                when(p1?.itemId){
                    R.id.iEliminar ->{
                        adaptador?.eliminarSeleccionados()
                    }

                    else->(return true)

                }

                adaptador?.terminarActionMode()
                p0?.finish()
                isActionMode = false


                return true
            }

            override fun onCreateActionMode(p0: ActionMode?, p1: Menu?): Boolean {
                //inicializar action Mode
                adaptador?.inicarActionMode()
                actionMode = p0
                //inflar menu
                menuInflater.inflate(R.menu.menu_contextual, p1!!)
                return true
            }

            override fun onPrepareActionMode(p0: ActionMode?, p1: Menu?): Boolean {
                p0?.title = "0 Seleccionados"
                return false
            }


            override fun onDestroyActionMode(p0: ActionMode?) {
                //destruir el modo
                adaptador?.destruirActionMode()
                isActionMode = false
            }
        }

        adaptador = AdaptadorCustom(platillos, object: ClickListener{
            override fun onClick(vista: View, index: Int){
                Toast.makeText(applicationContext,platillos.get(index).nombre, Toast.LENGTH_SHORT).show()
            }

        }, object: LongClickListener{
            override fun longClick(vista: View, index: Int) {
                if(!isActionMode){
                    startSupportActionMode(callback)
                    isActionMode = true
                    adaptador?.seleccionarItem(index)
                }else{
                    //hacer selecciones o deselecciones

                    adaptador?.seleccionarItem(index)
                }

                actionMode?.title = adaptador?.obtenerNumeroElementosSeleccionados().toString() +" selecionados"
            }

        })
        lista?.adapter = adaptador



        val swipeToRefresh = findViewById<SwipeRefreshLayout>(R.id.swipeToRefresh)
        swipeToRefresh.setOnRefreshListener {
            for(i in 1..1000000000){
            }
            swipeToRefresh.isRefreshing = false
            platillos.add(Platillo("Platillo 11", 250.0, 3.5F,R.drawable.platillo10))
            adaptador?.notifyDataSetChanged()

        }
    }
}