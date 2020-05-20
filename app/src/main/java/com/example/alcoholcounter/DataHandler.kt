package com.example.alcoholcounter

import android.content.Context
import android.util.Log
import com.example.alcoholcounter.ui.events.Event
import com.google.gson.Gson
import java.io.*


class DataHandler(private val context: Context){
    private val _fileName : String = "events.txt"
    var events : ArrayList<Event> = ArrayList()

    private fun WriteToFile(fileName: String, data: String) {
        try {
            val outputStreamWriter =
                OutputStreamWriter(context.openFileOutput(fileName, Context.MODE_PRIVATE))
            outputStreamWriter.write(data)
            outputStreamWriter.close()
        } catch (e: IOException) {
            Log.e("JsonToFile", "File write failed: " + e.toString())
        }
    }



    private fun ReadFromFile(fileName: String?): String? {
        val file = context.getFileStreamPath(_fileName)
        if (file == null || !file.exists()) {
            return null
        }

        var data: String = ""
        try {
            val inputStream: InputStream = context.openFileInput(_fileName)

            if (inputStream != null) {
                val inputStreamReader = InputStreamReader(inputStream)
                val bufferedReader = BufferedReader(inputStreamReader)
                var line: String? = ""
                val stringBuilder = java.lang.StringBuilder()
                while (bufferedReader.readLine().also { line = it } != null) {
                    stringBuilder.append(line)
                }

                inputStream.close()
                data = stringBuilder.toString()
            }
        } catch (e: IOException) {
            Log.e("FileToJson", "Can not read file: $e")
            return null
        }

        return data
    }

    fun LoadEvents() {
        val fileData = ReadFromFile(_fileName)
        //events = ArrayList(Gson().fromJson(fileData, Array<Event>::class.java).toList())
        if (fileData == null || fileData == "") {
            events = ArrayList<Event>()
        } else {
            events = ArrayList(Gson().fromJson(fileData, Array<Event>::class.java).toList())
        }
    }

    fun SaveEvents() {
        WriteToFile(_fileName, Gson().toJson(events))
    }

}

