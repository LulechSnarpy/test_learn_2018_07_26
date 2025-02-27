package club.iskyc.lulech.layout.base;

import java.awt.*;
import java.util.Optional;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * This class control the change console layer and form layer
 * on choose a function to work.
 * @author lulec
 * @since 1.0
 * */
public class SunriseLayerChanger {
    /**
     * Max size of {@code cardLayouts}.
     * */
    private static final int maxSize = 5;

    /**
     * First constants index.
     * */
    private static final int first = 0;

    /**
     * Current constant index.
     * */
    private static int current;

    /**
     * This cardLayouts store registered {@code CardLayout} data.
     * */
    private static final CopyOnWriteArrayList<CardLayout> cardLayouts
            = new CopyOnWriteArrayList<>(new CardLayout[maxSize]);

    /**
     * Store only one instance of {@code SunriseLayerChanger}.
     * */
    private static SunriseLayerChanger sunriseLayerChanger;

    /**
     * Create new {@code SunriseLayerChanger}.
     * */
    private SunriseLayerChanger() {}

    /**
     * Add register component.
     * */
    private void registerComponent(Container container) {
        cardLayouts.add((CardLayout) container.getLayout());
    }

    /**
     * Remove component.
     * */
    private void removeComponent(Container container) {
        cardLayouts.remove((CardLayout) container.getLayout());
    }

    /**
     * Show layer by selected function.
     * */
    private void show(Container container) {
        cardLayouts.getFirst().show(container, "");
    }

    /**
     * Create only one instance of {@code SunriseLayerChange}.
     * */
    synchronized public static SunriseLayerChanger getInstance() {
        sunriseLayerChanger =
            Optional.ofNullable(sunriseLayerChanger).orElse(new SunriseLayerChanger());
        return sunriseLayerChanger;
    }
}
