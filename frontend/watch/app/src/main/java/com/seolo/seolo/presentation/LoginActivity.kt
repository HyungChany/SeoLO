import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.seolo.seolo.R
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKeys
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

        // MasterKeyAlias 생성
        val masterKeyAlias = MasterKeys.getOrCreate(MasterKeys.AES256_GCM_SPEC)
        // 암호화된 공유 프리퍼런스 생성
        val sharedPreferences = EncryptedSharedPreferences.create(
            "secure_prefs",
            masterKeyAlias,
            applicationContext,
            EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
            EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
        )

        // 로그인 토큰 가져오기
        val loginToken = sharedPreferences.getString("auth_token", null)

        // 로그인 토큰이 존재하는 경우 MainActivity로 이동
        if (loginToken != null && loginToken.isNotEmpty()) {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        } else {
            // 로그인 토큰이 존재하지 않는 경우 아무 작업도 하지 않음
        }
    }
}
