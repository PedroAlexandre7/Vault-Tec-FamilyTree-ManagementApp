import java.text.SimpleDateFormat
import java.util.Calendar

class Dweller(
    private val id: Int,
    val firstName: String,
    val lastName: String,
    val sex: Sex,
    private val motherID: Int?,
    private val fatherID: Int?,
) {
    private val timeOfBirth: String = SimpleDateFormat("yyyy-MM-dd HH:mm").format(Calendar.getInstance().time)
}

enum class Sex {
    MALE, FEMALE
}