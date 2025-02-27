package club.iskyc.lulech.Interaction;

/**
 * Sunrise event.
 *
 * @author lulec
 * @since 1.0
 * */
public interface SunriseEvent {
    /**
     * Send event to action observers.
     * */
    void sendEvent(Object obj);
}