package kr.ac.hongic.ce.b611065.ck_min.netsec_insec_memo

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import kr.ac.hongic.ce.b611065.ck_min.netsec_insec_memo.databinding.ActivityMainBinding
import kr.ac.hongic.ce.b611065.ck_min.netsec_insec_memo.register.RegisterActivity

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        sendMessage()
    }

    /** Called when the user taps the Send button */
    private fun sendMessage() {
//        val editText = findViewById<EditText>(R.id.editTextTextPersonName)
//        val message = editText.text.toString()
        val intent = Intent(this, RegisterActivity::class.java).apply {
//            putExtra(EXTRA_MESSAGE, message)
        }
        startActivity(intent)
    }
}