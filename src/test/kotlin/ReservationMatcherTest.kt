import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class ReservationMatcherTest {

    private val storage = ReservationStorage()
    private val matcher = ReservationMatcher(storage)

    @BeforeEach
    fun setUp() {
        storage.store(Reservation(987L, "web", null, null))
    }

    @Test
    fun `when no match, then return null`() {
        val request = CheckinDetails("does-not-have-a-match")
        assertNull(matcher.attemptMatch(request))
    }

    @Test
    fun `when match found, then return internal ID`() {
        val request = CheckinDetails("web")
        assertEquals(987L, matcher.attemptMatch(request))
    }
}