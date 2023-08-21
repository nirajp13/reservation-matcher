import io.quarkus.test.junit.QuarkusTest
import io.restassured.RestAssured.given
import jakarta.inject.Inject
import org.hamcrest.Matchers.equalTo
import org.hamcrest.core.IsNull.nullValue
import org.junit.jupiter.api.Test

@QuarkusTest
class DevEndpointsTest {

    @Inject
    lateinit var storage: ReservationStorage

    @Test
    fun `when get reservations called, then return cache`() {
        val stub = Reservation(12345L, "web", null, null)
        storage.store(stub)

        given()
        .`when`()
            .get("/http/reservations")
        .then()
            .statusCode(200)
            .body("internalId[0]", equalTo(12345))
            .body("webConfirmationCode[0]", equalTo("web"))
            .body("bookingConfirmationCode[0]", nullValue())
            .body("travelAgentConfirmationCode[0]", nullValue())
    }
}