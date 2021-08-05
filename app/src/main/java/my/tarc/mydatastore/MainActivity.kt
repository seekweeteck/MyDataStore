package my.tarc.mydatastore

import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import my.tarc.mydatastore.databinding.ActivityMainBinding
import java.io.File

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var  preferences: SharedPreferences
    private var counter: Int = 0
    private lateinit var file :File

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //Creat an instance of Preference
        preferences = getPreferences(MODE_PRIVATE)
        counter = preferences.getInt(COUNTER_KEY, 0)

        //Create a new file
        file = File(this.filesDir, FILE_NAME)
        if(file.exists()){
            val content = file.bufferedReader().readText()
            file.bufferedReader().close()
            Log.d("onCreate", "File read")
            binding.editTextMessage.setText(content.toString())
        }else{
            Log.d("onCreate", "File NOT read")
        }


        binding.textViewCounter.text = "Counter: $counter"

        binding.buttonAdd.setOnClickListener {
            counter += 1
            binding.textViewCounter.text = "Counter: $counter"
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        with(preferences.edit()){
            putInt(COUNTER_KEY, counter)
            apply()
        }

        //Write to the file
        file.bufferedWriter().use {out ->
            val fileContent = binding.editTextMessage.text.toString()
            out.write(fileContent)
            out.close()
            Log.d("onDestroy", "File saved")
        }
    }

    companion object{
        val COUNTER_KEY = "counter"
        val FILE_NAME = "myfile"
    }

}