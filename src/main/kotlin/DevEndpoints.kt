import com.exploreshackle.api.reservation.v1.MutinyReservationServiceGrpc
import com.exploreshackle.api.reservation.v1.ReservationServiceOuterClass
import io.quarkus.grpc.GrpcClient
import io.smallrye.mutiny.Uni
import jakarta.ws.rs.GET
import jakarta.ws.rs.Path
import jakarta.ws.rs.Produces
import jakarta.ws.rs.core.MediaType.APPLICATION_JSON
import org.jboss.logging.Logger

@Path("/http")
@Produces(APPLICATION_JSON)
class DevEndpoints(
    private val storage: ReservationStorage
) {
    private val log: Logger = Logger.getLogger(javaClass)

    @GrpcClient
    lateinit var reservation: MutinyReservationServiceGrpc.MutinyReservationServiceStub

    @GET
    @Path("/reservations")
    fun getReservations() = storage.currentReservations()

    @GET
    @Path("/manual")
    fun triggerStreaming(): Uni<String> {
        log.info("Handling request to trigger streaming manually")
        val request = ReservationServiceOuterClass.StreamReservationsRequest.newBuilder().build()

        reservation.streamReservations(request)
            .subscribe().with {
                log.info("Received ${it.internalId}")
                storage.store(toInternal(it))
            }

        return Uni.createFrom().nothing()
    }

    private fun toInternal(external: ReservationServiceOuterClass.Reservation): Reservation {
        return Reservation(
            internalId = external.internalId,
            webConfirmationCode = external.webConfirmationCode,
            bookingConfirmationCode = external.bookingConfirmationNumber,
            travelAgentConfirmationCode = external.travelAgentConfirmationCode
        )
    }

}