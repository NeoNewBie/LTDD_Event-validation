package com.example.anew

import android.os.Bundle
import android.util.Patterns
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.doOnTextChanged
import com.google.android.material.snackbar.Snackbar

// Dòng import quan trọng để nhận diện được ActivityMainBinding
import com.example.anew.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Khởi tạo ViewBinding
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Lắng nghe sự kiện gõ phím thời gian thực
        setupRealtimeValidation()

        // Xử lý sự kiện bấm nút Đăng ký
        binding.btnRegister.setOnClickListener {
            if (validateAllForm()) {
                // Hiển thị Snackbar khi thành công
                Snackbar.make(binding.root, "Đăng ký thành công!", Snackbar.LENGTH_LONG).show()
            }
        }
    }

    // Lắng nghe sự kiện doOnTextChanged để validate thời gian thực
    private fun setupRealtimeValidation() {
        binding.etName.doOnTextChanged { text, _, _, _ ->
            validateName(text.toString())
        }

        binding.etEmail.doOnTextChanged { text, _, _, _ ->
            validateEmail(text.toString())
        }

        binding.etPassword.doOnTextChanged { text, _, _, _ ->
            validatePassword(text.toString())
        }

        binding.etPhone.doOnTextChanged { text, _, _, _ ->
            validatePhone(text.toString())
        }
    }

    private fun validateName(name: String): Boolean {
        return if (name.trim().isEmpty()) {
            binding.tilName.error = "Vui lòng nhập họ tên"
            false
        } else {
            binding.tilName.error = null
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

    private fun validatePassword(password: String): Boolean {
        return if (password.trim().isEmpty()) {
            binding.tilPassword.error = "Vui lòng nhập mật khẩu"
            false
        } else if (password.length < 6) {
            binding.tilPassword.error = "Mật khẩu tối thiểu 6 ký tự"
            false
        } else {
            binding.tilPassword.error = null
            true
        }
    }

    private fun validatePhone(phone: String): Boolean {
        return if (phone.trim().isEmpty()) {
            binding.tilPhone.error = "Vui lòng nhập số điện thoại"
            false
        } else if (phone.trim().length < 10) {
            binding.tilPhone.error = "Số điện thoại không hợp lệ"
            false
        } else {
            binding.tilPhone.error = null
            true
        }
    }

    // Kiểm tra tổng thể khi bấm nút "Đăng ký ngay"
    private fun validateAllForm(): Boolean {
        val isNameValid = validateName(binding.etName.text.toString())
        val isEmailValid = validateEmail(binding.etEmail.text.toString())
        val isPasswordValid = validatePassword(binding.etPassword.text.toString())
        val isPhoneValid = validatePhone(binding.etPhone.text.toString())

        return isNameValid && isEmailValid && isPasswordValid && isPhoneValid
    }
}