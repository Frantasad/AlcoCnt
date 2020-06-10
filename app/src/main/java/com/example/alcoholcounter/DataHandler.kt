package com.example.alcoholcounter

import android.content.Context
import android.util.Log
import android.widget.Toast
import com.example.alcoholcounter.ui.events.Event
import com.google.gson.Gson
import java.io.*

// Ja bych si vybral jiny zpusob storage dat, ale o tom uz jsme si povidali
class DataHandler(private val context: Context){        // Tady to volate s aplikacnim contextem, takze se nic nepokazi, ale vzdycky pozor, abyste si nikdy neukladali context aktivit nekde mimo, jinak to leakne
    private val _fileName : String = "events.txt"
    var events : ArrayList<Event> = ArrayList()

    private fun writeToFile(fileName: String, data: String) {
        try {
            val outputStreamWriter =
                OutputStreamWriter(context.openFileOutput(fileName, Context.MODE_PRIVATE))
            outputStreamWriter.write(data)
            outputStreamWriter.close()
        } catch (e: IOException) {
            Log.e("JsonToFile", "File write failed: " + e.toString())
        }
    }

    private fun readFromFile(fileName: String?): String? {
        val file = context.getFileStreamPath(fileName)
        if (file == null || !file.exists()) {
            return null
        }

        val data: String
        try {
            val inputStream: InputStream = context.openFileInput(fileName)

            val inputStreamReader = InputStreamReader(inputStream)
            val bufferedReader = BufferedReader(inputStreamReader)
            var line: String?
            val stringBuilder = java.lang.StringBuilder()
            while (bufferedReader.readLine().also { line = it } != null) {
                stringBuilder.append(line)
            }

            inputStream.close()
            data = stringBuilder.toString()
        } catch (e: IOException) {
            Log.e("FileToJson", "Can not read file: $e")
            Toast.makeText(context, "Unable to save data", Toast.LENGTH_LONG).show()
            return null
        }

        return data
    }

    fun loadEvents() {
        val fileData = readFromFile(_fileName)
        events = if (fileData == null || fileData == "") {
            ArrayList()
        } else {
            ArrayList(Gson().fromJson(fileData, Array<Event>::class.java).toList())
        }
    }

    fun saveEvents() {
        writeToFile(_fileName, Gson().toJson(events))
    }

}

