package kr.ac.hongic.ce.b611065.ck_min.netsec_insec_memo.edit

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.appcompat.app.AppCompatActivity
import androidx.security.crypto.EncryptedFile
import androidx.security.crypto.MasterKeys
import kr.ac.hongic.ce.b611065.ck_min.netsec_insec_memo.R
import kr.ac.hongic.ce.b611065.ck_min.netsec_insec_memo.databinding.ActivityEditActiviryBinding
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.IOException
import java.nio.charset.StandardCharsets


class EditActivity : AppCompatActivity() {
    private lateinit var binding: ActivityEditActiviryBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditActiviryBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        val memo = binding.editTextTextMultiLine
        memo.setText(loadMemo())

        memo.addTextChangedListener(object: TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                writeMemoToFile(p0.toString())
            }

            override fun afterTextChanged(p0: Editable?) {

            }
        })
    }

    private fun writeMemoToFile(memo: String) {
        val filename = "memo.txt"

        this.openFileOutput(filename, Context.MODE_PRIVATE).use {
            it.write(memo.toByteArray())
        }
    }

    private fun loadMemo(): String {
        return try {
            this.openFileInput("memo.txt").bufferedReader().useLines { lines ->
                lines.fold("") { some, text ->
                    "$some\n$text"
                }
            }
        } catch (e: IOException) {
            ""
        }
    }
}