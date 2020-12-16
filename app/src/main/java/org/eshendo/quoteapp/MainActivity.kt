package org.eshendo.quoteapp

import android.content.res.Configuration
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import org.eshendo.quoteapp.databinding.ActivityMainBinding
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : AppCompatActivity(), RequestCallback {

    lateinit var binding: ActivityMainBinding

    private var lastDate: String? = null
    private var lastQuote: String? = null
    private var lastName: String? = null

    private var mode: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        processConfig(resources.configuration)

        getLastQuoteAndNameAndDate()

        if (isNewDay()){
            QuoteRequester.call(this)
        }else{
            setLastQuote()
        }

        if (isFirstTime()){
            showFirstTimeDialog()
        }
    }

    private fun showFirstTimeDialog(){
        val f = FirstDialog(mode)
        f.show(supportFragmentManager, "first time dialog")
        setFirstTime()
    }

    private fun isFirstTime() : Boolean{
        return getSharedPreferences("app", MODE_PRIVATE).getBoolean("firstTime", true)
    }

    private fun setFirstTime(){
        getSharedPreferences("app", MODE_PRIVATE)
            .edit()
            .putBoolean("firstTime", false)
            .apply()
    }

    private fun isNewDay() : Boolean{
        val c = Calendar.getInstance()
        val curFormater = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
        curFormater.format(c.time).let {
            saveNewDate(it)
            return lastDate != it
        }
    }

    private fun getLastQuoteAndNameAndDate(){
        getSharedPreferences("app", MODE_PRIVATE).apply {
            lastDate = this.getString("date", "")
            lastQuote = this.getString("quote", "")
            lastName = this.getString("name", "")
        }
    }

    private fun saveNewDate(date: String){
        getSharedPreferences("app", MODE_PRIVATE)
            .edit()
            .putString("date", date)
            .apply()
    }

    private fun setLastQuote(){
        binding.quote.text = lastQuote
        binding.name.text = lastName
    }

    private fun processConfig(configuration: Configuration){

        var bgColor: Drawable? = null
        var textColor: Int? = null
        var statusBarColor: Int? = null

        when (configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK) {
            Configuration.UI_MODE_NIGHT_NO -> {
                mode = true
                Log.i("duck", "light mode")
                bgColor = ContextCompat.getDrawable(this, R.color.bgLightColor)
                textColor = ContextCompat.getColor(this, R.color.textLightColor)
                statusBarColor = ContextCompat.getColor(this, R.color.statusBarLightColor)

            }
            Configuration.UI_MODE_NIGHT_YES -> {
                mode = false
                Log.i("duck", "dark mode")
                bgColor = ContextCompat.getDrawable(this, R.color.bgDarkColor)
                textColor = ContextCompat.getColor(this, R.color.textDarkColor)
                statusBarColor = ContextCompat.getColor(this, R.color.bgDarkColor)

            }
        }

        binding.root.background = bgColor
        binding.quote.setTextColor(textColor!!)
        binding.name.setTextColor(textColor)

        window.statusBarColor = statusBarColor!!

    }

    private fun setQuote(quote: Quote){
        binding.quote.text = quote.content
        binding.name.text = quote.originator.name

        saveLastQuote(quote)
    }

    private fun saveLastQuote(quote: Quote){
        getSharedPreferences("app", MODE_PRIVATE)
            .edit()
            .putString("quote", quote.content)
            .putString("name", quote.originator.name)
            .apply()
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        processConfig(newConfig)
    }

    override fun onResult(quote: Quote) {
        setQuote(quote)
    }

    override fun onError(description: String) {
        Toast.makeText(this, description, Toast.LENGTH_SHORT).show()
    }
}