package club.iskyc.lulech.Interaction;

import club.iskyc.lulech.layout.util.MarkedElement;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * A class for interaction components.
 * */
public class SunriseEventCenter {
    /**
     * Thread pool for to run new thead.
     * */
    private static ThreadPoolExecutor threadPool;

    /**
     * Interval between two action.
     * */
    private static final long interval = 10L;

    /**
     * Store action listeners.
     * Signed by {@code String} mark.
     * */
    private static ConcurrentHashMap<String
            , CopyOnWriteArrayList<SunriseObserver>> observersMap;

    /**
     * Store events lists to do
     * */
    private static ConcurrentLinkedDeque<MarkedElement<String, Object>> events;

    /**
     * Singleton for remind.
     * */
    private static SunriseEventCenter eventCenter;

    /**
     * Singleton constructor.
     * */
    private SunriseEventCenter() {
        threadPool = SunriseThreadPool.getInstance();
        observersMap = new ConcurrentHashMap<>();
        events = new ConcurrentLinkedDeque<>();
    }

    /**
     * Add event to center prepare observers act.
     * */
    public void addEvent(MarkedElement<String, Object> event) {
        events.addLast(event);
        onAlive();
    }

    /**
     * Register observer into center.
     * */
     public void addObserver(String eventName, SunriseObserver observer) {
        CopyOnWriteArrayList<SunriseObserver> observers
                = observersMap.computeIfAbsent(eventName, k -> new CopyOnWriteArrayList<>());
        observers.add(observer);
    }

    /**
     * Remove observer form center, only one object.
     * */
    public void removeObserver(String eventName, SunriseObserver observer) {
        CopyOnWriteArrayList<SunriseObserver> observers = observersMap.get(eventName);
        if (observers == null) return;
        observers.remove(observer);
    }

    /**
     * Remove observer form center, by marking all.
     * */
    public void removeObserver(String eventName) {
        observersMap.remove(eventName);
    }

    /**
     * Ran actions on event happened.
     * */
    private void onAlive() {
        threadPool.execute(() -> {
            if (events.isEmpty()) return;
            MarkedElement<String, Object> event = events.pop();
            String mark = event.getMark();
            CopyOnWriteArrayList<SunriseObserver> observers = observersMap.get(mark);
            if (observers == null || observers.isEmpty()) return;
            for (SunriseObserver listener : observers) {
                listener.sunriseEventAction(event.getData());
            }
        });
    }

    synchronized public static SunriseEventCenter getInstance() {
        return eventCenter = eventCenter == null ? new SunriseEventCenter() : eventCenter;
    }
}
