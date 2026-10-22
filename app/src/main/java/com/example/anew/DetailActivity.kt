package com.example.anew // Sửa lại đúng package của bạn

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.anew.databinding.ActivityDetailBinding // Sửa lại theo package

class DetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // 1. Nhận dữ liệu từ Intent
        val name = intent.getStringExtra("EXTRA_NAME") ?: "Không có"
        val email = intent.getStringExtra("EXTRA_EMAIL") ?: "Không có"
        val phone = intent.getStringExtra("EXTRA_PHONE") ?: "Không có"
        val studentId = intent.getStringExtra("EXTRA_STUDENT_ID") ?: "Không có"
        val className = intent.getStringExtra("EXTRA_CLASS") ?: "Không có"

        // 2. Hiển thị lên TextView
        val displayInfo = """
            Họ tên: $name
            Email: $email
            SĐT: $phone
            MSV: $studentId
            Lớp: $className
        """.trimIndent()

        binding.tvInfo.text = displayInfo

        // 3. Xử lý nút quay lại
        binding.btnBack.setOnClickListener {
            // Dùng Intent chuyển về trang kia theo yêu cầu (hoặc đơn giản nhất là gọi finish())
            val backIntent = Intent(this, MainActivity::class.java)
            backIntent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP // Tránh tạo nhiều lớp Activity chồng lên nhau
            startActivity(backIntent)
        }
    }
}