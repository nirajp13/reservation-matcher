import jakarta.enterprise.context.ApplicationScoped
import org.jboss.logging.Logger
import java.util.*

@ApplicationScoped
class ReservationStorage {
    private val log: Logger = Logger.getLogger(javaClass)
    private val reservations: MutableList<Reservation> = mutableListOf()

    fun store(reservation: Reservation) {
        log.info("Saving $reservation")
        reservations.add(reservation)
    }

    fun currentReservations(): List<Reservation> {
        log.info("Fetching reservations, current size: ${reservations.size}")
        return Collections.unmodifiableList(reservations)
    }

}

