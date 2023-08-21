import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class ReservationTest {

    @Test
    fun `confirmationCodes excludes null confirmation codes`() {
        val travelAgentConfirmationCode = "travelAgentCode"
        val example = Reservation(
            internalId = 1L,
            webConfirmationCode = null,
            bookingConfirmationCode = null,
            travelAgentConfirmationCode = travelAgentConfirmationCode
        )

        assertEquals(setOf(travelAgentConfirmationCode), example.confirmationCodes())
    }
}