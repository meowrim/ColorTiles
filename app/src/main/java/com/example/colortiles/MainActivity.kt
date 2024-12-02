package com.example.colortiles

import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlin.random.Random

data class Coord(val x: Int, val y: Int)

class MainActivity : AppCompatActivity() {

    lateinit var tiles: Array<Array<View>>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        tiles = arrayOf(
            arrayOf(findViewById(R.id.t00), findViewById(R.id.t01), findViewById(R.id.t02), findViewById(R.id.t03)),
            arrayOf(findViewById(R.id.t10), findViewById(R.id.t11), findViewById(R.id.t12), findViewById(R.id.t13)),
            arrayOf(findViewById(R.id.t20), findViewById(R.id.t21), findViewById(R.id.t22), findViewById(R.id.t23)),
            arrayOf(findViewById(R.id.t30), findViewById(R.id.t31), findViewById(R.id.t32), findViewById(R.id.t33))
        )
        initField()
    }

    fun getCoordFromString(s: String): Coord {
        val x = s[0].toString().toInt()
        val y = s[1].toString().toInt()
        return Coord(x, y)
    }

    fun changeColor(view: View) {
        val brightColor = resources.getColor(R.color.bright)
        val darkColor = resources.getColor(R.color.dark)
        val drawable = view.background as ColorDrawable
        if (drawable.color == brightColor) {
            view.setBackgroundColor(darkColor)
        } else {
            view.setBackgroundColor(brightColor)
        }
    }

    fun onClick(v: View) {
        val coord = getCoordFromString(v.tag.toString())
        changeColor(v)

        for (i in 0..3) {
            changeColor(tiles[coord.x][i])
            changeColor(tiles[i][coord.y])
        }

        checkVictory()
    }

    fun checkVictory() {
        val firstTileColor = (tiles[0][0].background as ColorDrawable).color
        var allSameColor = true

        for (row in tiles) {
            for (tile in row) {
                val tileColor = (tile.background as ColorDrawable).color
                if (tileColor != firstTileColor) {
                    allSameColor = false
                    break
                }
            }
        }

        if (allSameColor) {
            Toast.makeText(this, "Победа!", Toast.LENGTH_SHORT).show()
        }
    }

    fun initField() {
        for (i in 0..3) {
            for (j in 0..3) {
                val randomColor = if (Random.nextBoolean()) R.color.bright else R.color.dark
                tiles[i][j].setBackgroundColor(resources.getColor(randomColor))
            }
        }
    }
}