import com.exploreshackle.api.checkin.v1.CheckinService
import com.exploreshackle.api.checkin.v1.CheckinServiceOuterClass.*
import io.quarkus.grpc.GrpcService
import io.smallrye.mutiny.Uni
import org.jboss.logging.Logger

@GrpcService
class CheckinGrpcService(
    private val reservationMatcher: ReservationMatcher
) : CheckinService {
    private val log: Logger = Logger.getLogger(javaClass)

    override fun checkin(request: CheckinRequest): Uni<CheckinResponse> {
        log.info("Handling $request")

        val potentialMatch = reservationMatcher.attemptMatch(CheckinDetails(request.confirmationNumber))

        val response = if (potentialMatch == null) {
            noMatchFound()
        } else {
            reservationFound(potentialMatch)
        }

        return Uni.createFrom().item(response)
    }

    private fun reservationFound(potentialMatch: Long) = CheckinResponse.newBuilder()
        .setConfirmation(
            CheckinConfirmation.newBuilder()
                .setReservationId(potentialMatch)
                .build()
        )
        .build()

    private fun noMatchFound(): CheckinResponse = CheckinResponse.newBuilder()
        .setError(
            CheckinError.newBuilder()
                .setReason("No match found, additional details required")
                .build()
        )
        .build()
}