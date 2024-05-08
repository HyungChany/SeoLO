import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.seolo.seolo.R
import com.seolo.seolo.presentation.MainActivity

// LoginActivity 클래스 정의
class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // 테마 설정
        setTheme(android.R.style.Theme_DeviceDefault)
        // 액션바 숨기기
        supportActionBar?.hide()
        // 레이아웃 설정
        setContentView(R.layout.login_layout)


        // DataLayerClient 인스턴스 가져오기
        val dataLayerClient = DataLayerClient.getInstance(applicationContext)

        // 로그인 토큰이 Null이 아니고, 비어있지 않을 때 MainActivity로 이동
        if (dataLayerClient.connectionToken != null && dataLayerClient.connectionToken!!.isNotEmpty()) {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        } else {
            // 로그인 토큰이 존재하지 않는 경우 아무 작업도 하지 않음
        }
    }
}
