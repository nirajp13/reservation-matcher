import jakarta.enterprise.context.ApplicationScoped
import org.jboss.logging.Logger

@ApplicationScoped
class ReservationMatcher(
    private val storage: ReservationStorage
) {
    private val log: Logger = Logger.getLogger(javaClass)

    fun attemptMatch(request: CheckinDetails) : Long? {
        val allCachedReservations = storage.currentReservations()
        val matchedReservation = allCachedReservations.find {
            request.confirmationId in it.confirmationCodes()
        }
        log.info("Attempted match result: $matchedReservation")
        return matchedReservation?.internalId
    }

}