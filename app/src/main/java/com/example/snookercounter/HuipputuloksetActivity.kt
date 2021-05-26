package com.example.snookercounter

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.widget.AdapterView.OnItemClickListener
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import java.io.*
import java.util.*

class HuipputuloksetActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_huipputulokset)
        val list = findViewById<ListView>(R.id.list)
        val arrayList = ArrayList<String>()
        val mappi = HashMap<String?, Int>()
        val g = readFromFile(this)
        val gg = g.split(";").toTypedArray()
        for (o in gg) {
            if (g != "") {
                val o2 = arrayOfNulls<String>(2)
                val i = o.indexOf(":")
                o2[0] = o.substring(0, i)
                o2[1] = o.substring(i + 2)
                mappi[o2[0]] = o2[1]!!.toInt()
                //arrayList.add(o);
            } else {
                Toast.makeText(this, "nothing to show", Toast.LENGTH_LONG).show()
            }
        }
        val mapValues: List<Int> = ArrayList(mappi.values)
        Collections.sort(mapValues, Collections.reverseOrder())
        for (i in mapValues) {
            if (!arrayList.contains(i.toString())) {
                arrayList.add(getKeyByValue(mappi, i).toString() + " : " + i)
            }
        }
        val arrayAdapter = ArrayAdapter(this,
                android.R.layout.simple_list_item_1, arrayList)
        list.adapter = arrayAdapter
        list.onItemClickListener = OnItemClickListener { parent, view, position, id ->
            //String clickedItem=(String) list.getItemAtPosition(position);
            //Toast.makeText(HuipputuloksetActivity.this,clickedItem, Toast.LENGTH_LONG).show();
        }
    }

    fun readFromFile(context: Context): String {
        var ret = ""
        try {
            val inputStream: InputStream? = context.openFileInput("data.txt")
            if (inputStream != null) {
                val inputStreamReader = InputStreamReader(inputStream)
                val bufferedReader = BufferedReader(inputStreamReader)
                var receiveString: String? = ""
                val stringBuilder = StringBuilder()
                while (bufferedReader.readLine().also { receiveString = it } != null) {
                    stringBuilder.append("\n").append(receiveString)
                }
                inputStream.close()
                ret = stringBuilder.toString()
            }
        } catch (e: FileNotFoundException) {
            Log.e("login activity", "File not found: $e")
        } catch (e: IOException) {
            Log.e("login activity", "Can not read file: $e")
        }
        return ret
    }

    companion object {
        fun <T, E> getKeyByValue(map: Map<T, E>, value: E): T? {
            for ((key, value1) in map) {
                if (value == value1) {
                    return key
                }
            }
            return null
        }
    }
}