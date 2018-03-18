package com.example.j.findingpokemon

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.sdk25.coroutines.onClick
import org.jetbrains.anko.uiThread
import org.json.JSONObject
import java.io.FileNotFoundException
import java.net.URL
import java.nio.file.Files.exists

class MainActivity : AppCompatActivity() {


    private val url = "https://pokeapi.co/api/v2/pokemon/"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        search_btn.setOnClickListener {
            scrollView1.visibility = View.GONE
            progressBar.visibility = View.VISIBLE
            try {
                doAsync {
                    val pokeSearched = pokeSearched_et.text.toString()
                    val resultJson = URL(url + pokeSearched + "/").readText()
                    uiThread {
                        //Toast.makeText(this@MainActivity,"123123",Toast.LENGTH_SHORT).show()
                        val jsonObj = JSONObject(resultJson)
                        val name = jsonObj.getString("name")
                        val sprites = jsonObj.getString("sprites")
                        val jsonObj1 = JSONObject(sprites)
                        val image = jsonObj1.getString("front_default")
                        val Capitalizes = name.substring(0, 1).toUpperCase() + name.substring(1)
                        val myPokemonData = PokemonData(Capitalizes, image)
                        setViews(myPokemonData)
                    }
                }
            } catch (e: FileNotFoundException) {
                Toast.makeText(this@MainActivity, "Pokemon Not Found", Toast.LENGTH_SHORT).show()
            } finally {
            }
        }
    }

    fun setViews(MyPokeData: PokemonData) {
        progressBar.visibility = View.GONE
        scrollView1.visibility = View.VISIBLE

        pokeName_tv.text = MyPokeData.pokeName
        val PokeImage = poke_image
        Picasso.with(this@MainActivity).load(MyPokeData.pokeImage).into(PokeImage)

    }
}
