package club.iskyc.lulech.Interaction;

/**
 * Listen event happened, action when it happened.
 * Should override {@code SunriseEventAction} for action.
 *
 * @author lulec
 * @since 1.0
 * */
public interface SunriseObserver {
    /**
     * Do reaction on event happened.
     * */
    void sunriseEventAction(Object object);
}
