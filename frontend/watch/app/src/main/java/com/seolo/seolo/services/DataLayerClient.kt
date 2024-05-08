import android.content.Context
import com.google.android.gms.wearable.MessageClient
import com.google.android.gms.wearable.MessageEvent
import com.google.android.gms.wearable.Wearable

class DataLayerClient private constructor(private val messageClient: MessageClient) :
    MessageClient.OnMessageReceivedListener {
    // MessageClient 인스턴스 초기화

    // 토큰 저장 변수
    var connectionToken: String? = null

    // 생성자에서 MessageClient 리스너 등록
    private var listener: TokenReceiveListener? = null

    interface TokenReceiveListener {
        fun onTokenReceived(token: String?)
    }

    fun setListener(listener: TokenReceiveListener) {
        this.listener = listener
    }

    init {
        messageClient.addListener(this)
    }


    // 싱글톤 패턴으로 인스턴스 생성
    companion object {
        @Volatile
        private var instance: DataLayerClient? = null

        fun getInstance(context: Context): DataLayerClient = instance ?: synchronized(this) {
            instance
                ?: DataLayerClient(Wearable.getMessageClient(context.applicationContext)).also {
                    instance = it
                }
        }
    }

    // 메시지 전송 메서드
    fun sendMessage(path: String, message: ByteArray = ByteArray(0)) {
        val nodeClient = Wearable.getNodeClient(messageClient.applicationContext)
        nodeClient.connectedNodes.addOnSuccessListener { nodes ->
            for (node in nodes) {
                messageClient.sendMessage(node.id, path, message).addOnSuccessListener {
                    // 메시지 전송 성공 처리
                }.addOnFailureListener {
                    // 실패 처리
                }
            }
        }
    }

    // 토큰 요청 전송
    fun requestConnectionToken() {
        // /request_login" 경로로 "/request_login" 문자열을 UTF-8 바이트 배열로 변환하여 메시지 전송
        sendMessage("/seolo/request_login", "request_login".toByteArray(Charsets.UTF_8))
        cleanup()
    }
    fun cleanup() {
        messageClient.removeListener(this)
    }


    // 메시지 수신 처리
    override fun onMessageReceived(messageEvent: MessageEvent) {
        // App, Watch 연결 검증 토큰 처리
        if (messageEvent.path == "/seolo/login") {
            connectionToken = String(messageEvent.data, Charsets.UTF_8)
        }

        // 리스너 제거 메서드
        fun cleanup() {
            messageClient.removeListener(this)
        }
    }
}

