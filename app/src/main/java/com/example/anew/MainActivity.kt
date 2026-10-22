package com.example.anew // Sửa lại đúng package của bạn

import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.doOnTextChanged
import com.example.anew.databinding.ActivityMainBinding // Sửa lại theo package

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupRealtimeValidation()

        binding.btnRegister.setOnClickListener {
            if (validateAllForm()) {
                // Tạo Intent để chuyển sang DetailActivity
                val intent = Intent(this, DetailActivity::class.java).apply {
                    // Truyền dữ liệu qua Intent
                    putExtra("EXTRA_NAME", binding.etName.text.toString().trim())
                    putExtra("EXTRA_EMAIL", binding.etEmail.text.toString().trim())
                    putExtra("EXTRA_PHONE", binding.etPhone.text.toString().trim())
                    putExtra("EXTRA_STUDENT_ID", binding.etStudentId.text.toString().trim())
                    putExtra("EXTRA_CLASS", binding.etClassName.text.toString().trim())
                }
                startActivity(intent)
            }
        }
    }

    private fun setupRealtimeValidation() {
        binding.etName.doOnTextChanged { text, _, _, _ -> validateField(text.toString(), binding.tilName, "Vui lòng nhập họ tên") }
        binding.etEmail.doOnTextChanged { text, _, _, _ -> validateEmail(text.toString()) }
        binding.etPhone.doOnTextChanged { text, _, _, _ -> validatePhone(text.toString()) }
        binding.etStudentId.doOnTextChanged { text, _, _, _ -> validateField(text.toString(), binding.tilStudentId, "Vui lòng nhập MSV") }
        binding.etClassName.doOnTextChanged { text, _, _, _ -> validateField(text.toString(), binding.tilClassName, "Vui lòng nhập Lớp") }
    }

    // Hàm dùng chung cho các trường chỉ cần check rỗng
    private fun validateField(text: String, til: com.google.android.material.textfield.TextInputLayout, errorMsg: String): Boolean {
        return if (text.trim().isEmpty()) {
            til.error = errorMsg
            false
        } else {
            til.error = null
            true
        }
    }

    private fun validateEmail(email: String): Boolean {
        return if (email.trim().isEmpty()) {
            binding.tilEmail.error = "Vui lòng nhập email"
            false
        } else if (!Patterns.EMAIL_ADDRESS.matcher(email.trim()).matches()) {
            binding.tilEmail.error = "Email không hợp lệ"
            false
        } else {
            binding.tilEmail.error = null
            true
        }
    }

    private fun validatePhone(phone: String): Boolean {
        return if (phone.trim().isEmpty()) {
            binding.tilPhone.error = "Vui lòng nhập số điện thoại"
            false
        } else if (phone.trim().length < 10) {
            binding.tilPhone.error = "Số điện thoại phải từ 10 số"
            false
        } else {
            binding.tilPhone.error = null
            true
        }
    }

    private fun validateAllForm(): Boolean {
        val isNameValid = validateField(binding.etName.text.toString(), binding.tilName, "Vui lòng nhập họ tên")
        val isEmailValid = validateEmail(binding.etEmail.text.toString())
        val isPhoneValid = validatePhone(binding.etPhone.text.toString())
        val isIdValid = validateField(binding.etStudentId.text.toString(), binding.tilStudentId, "Vui lòng nhập MSV")
        val isClassValid = validateField(binding.etClassName.text.toString(), binding.tilClassName, "Vui lòng nhập Lớp")

        return isNameValid && isEmailValid && isPhoneValid && isIdValid && isClassValid
    }
}