import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class ReservationStorageTest {

    private val storage = ReservationStorage()

    @Test
    fun `when multiple reservations stored, then return all`() {
        val first = Reservation(
            internalId = 1L,
            webConfirmationCode = null,
            bookingConfirmationCode = "bookingConfCode",
            travelAgentConfirmationCode = null
        )

        val second = Reservation(
            internalId = 2L,
            webConfirmationCode = "web",
            bookingConfirmationCode = null,
            travelAgentConfirmationCode = null
        )

        storage.store(first)
        storage.store(second)

        assertEquals(listOf(first, second), storage.currentReservations())
    }
}