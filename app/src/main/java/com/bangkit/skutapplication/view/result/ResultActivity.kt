package com.bangkit.skutapplication.view.result

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.bangkit.skutapplication.R
import com.bangkit.skutapplication.databinding.ActivityResultBinding
import com.bangkit.skutapplication.model.response.UploadResponse
import com.bangkit.skutapplication.view.dailytreatment.DailyTreatmentActivity
import com.bangkit.skutapplication.view.main.MainActivity
import com.bumptech.glide.Glide
import com.google.android.material.appbar.MaterialToolbar
import com.google.gson.Gson
import kotlin.reflect.full.memberProperties

class ResultActivity : AppCompatActivity() {

    private var maxEntry: Map.Entry<String, Double>? = null

    private lateinit var binding: ActivityResultBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityResultBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val toolbar: MaterialToolbar = binding.toolbar
        setSupportActionBar(toolbar)

        supportActionBar?.title=getString(R.string.result_title)
        supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_baseline_arrow_back_24)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val data = intent.getParcelableExtra<UploadResponse>("extra_data") as UploadResponse

        binding.dailyButton.setOnClickListener {
            val intent = Intent(this, DailyTreatmentActivity::class.java)
            startActivity(intent)
        }

        setupView(data)
    }

    private fun setupView(scanResult: UploadResponse) {

        val scanResultMap: Map<String, Double>? = scanResult.scanResult?.asMap()


        if (scanResultMap != null) {
            for (entry in scanResultMap.entries) {
                if (maxEntry == null || entry.value > maxEntry!!.value) {
                    maxEntry = entry
                }
            }
        }

//        if (scanResultMap != null) {
//            for (key in scanResultMap.keys) {
//                if (maxKey == null || scanResultMap[key]!! > scanResultMap[maxKey]!!) {
//                    maxKey = key
//                }
//            }
//        }

        Log.d("scanMap", scanResultMap.toString())
        Log.d("scanMap", maxEntry.toString())
        maxEntry?.let { Log.d("scanMap", it.key) }
        Log.d("scanMap", maxEntry?.value.toString())


        Glide.with(this)
            .load(scanResult.imgLink)
            .into(binding.imgPreview)

        val scanPercentage = (maxEntry!!.value * 100)
        val scanPercentageFormat = String.format("%.2f", scanPercentage)


        when (maxEntry?.key) {
            "acne" -> {
                binding.tvResultName.text = getString(R.string.acne)
                binding.tvPercentage.text = getString(R.string.percentage, scanPercentageFormat.toString())
                binding.tvResult.text = getString(R.string.acne_description)

                binding.nextButton.setOnClickListener {
                    val intent = Intent(this, SolutionActivity::class.java)
                    intent.putExtra("extra_disease", maxEntry!!.key)
                    startActivity(intent)
                }
            }
            "eksim" -> {
                binding.tvResultName.text = getString(R.string.eczema)
                binding.tvPercentage.text = getString(R.string.percentage, scanPercentageFormat.toString())
                binding.tvResult.text = getString(R.string.eczema_description)

                binding.nextButton.setOnClickListener {
                    val intent = Intent(this, SolutionActivity::class.java)
                    intent.putExtra("extra_disease", maxEntry!!.key)
                    startActivity(intent)
                }
            }
            "normal" -> {
                binding.tvResultName.text = getString(R.string.normal)
                binding.tvPercentage.text = getString(R.string.percentage, scanPercentageFormat.toString())
                binding.tvResult.text = getString(R.string.normal_description)

                binding.nextButton.setOnClickListener {
                    val intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)
                }
            }
            "rosacea" -> {
                binding.tvResultName.text = getString(R.string.rosacea)
                binding.tvPercentage.text = getString(R.string.percentage, scanPercentageFormat.toString())
                binding.tvResult.text = getString(R.string.rosacea_description)

                binding.nextButton.setOnClickListener {
                    val intent = Intent(this, SolutionActivity::class.java)
                    intent.putExtra("extra_disease", maxEntry!!.key)
                    startActivity(intent)
                }
            }

        }

    }

    override fun onSupportNavigateUp(): Boolean {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
        return super.onSupportNavigateUp()
    }

    override fun onBackPressed() {
        Log.d("CDA", "onBackPressed Called")
        val setIntent = Intent(this, MainActivity::class.java)
        startActivity(setIntent)

        return super.onBackPressed()
    }



    private inline fun <reified T : Any> T.asMap() : Map<String, Double> {
        val props = T::class.memberProperties.associateBy { it.name }
        return props.keys.associateWith { props[it]?.get(this) as Double }
    }
}