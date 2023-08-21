
data class Reservation(
    val internalId: Long,
    val webConfirmationCode: String?,
    val bookingConfirmationCode: String?,
    val travelAgentConfirmationCode: String?
)

fun Reservation.confirmationCodes(): Set<String> {
    return setOfNotNull(this.webConfirmationCode, this.bookingConfirmationCode, this.travelAgentConfirmationCode)
}