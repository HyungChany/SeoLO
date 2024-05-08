import com.google.android.gms.wearable.MessageClient
import com.google.android.gms.wearable.MessageEvent
import com.google.android.gms.wearable.Wearable
import android.content.Context

class DataLayerClient private constructor(private val context: Context): MessageClient.OnMessageReceivedListener {
    // MessageClient 인스턴스 초기화
    private val messageClient: MessageClient = Wearable.getMessageClient(context)

    // 생성자에서 MessageClient 리스너 등록
    init {
        messageClient.addListener(this)
    }

    // 싱글톤 패턴으로 인스턴스 생성
    companion object {
        @Volatile private var instance: DataLayerClient? = null

        fun getInstance(context: Context) = instance ?: synchronized(this) {
            instance ?: DataLayerClient(context).also { instance = it }
        }
    }

    // 메시지 전송 메서드
    fun sendMessage(path: String, message: ByteArray) {
        // 메시지 전송 코드
        val nodeListTask = Wearable.getNodeClient(context).connectedNodes
        nodeListTask.addOnSuccessListener { nodes ->
            nodes.forEach { node ->
                messageClient.sendMessage(node.id, path, message).addOnSuccessListener {
                    // 메시지 전송 성공 처리
                }.addOnFailureListener {
                    // 실패 처리
                }
            }
        }
    }

    // 메시지 수신 처리
    override fun onMessageReceived(messageEvent: MessageEvent) {
        // 수신된 메시지 처리
        if (messageEvent.path == "/example_path") {
            val message = String(messageEvent.data)
            // 메시지 처리
        }
    }

    // 리스너 제거 메서드
    fun cleanup() {
        messageClient.removeListener(this)
    }
}
