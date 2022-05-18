package kr.ac.hongic.ce.b611065.ck_min.netsec_insec_memo.register

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kr.ac.hongic.ce.b611065.ck_min.netsec_insec_memo.R
import kr.ac.hongic.ce.b611065.ck_min.netsec_insec_memo.databinding.ActivityMainBinding
import kr.ac.hongic.ce.b611065.ck_min.netsec_insec_memo.databinding.ActivityRegisterBinding

class RegisterActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRegisterBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        val password = binding.password
        val loading = binding.loading
        val registerButton = binding.register
    }
}