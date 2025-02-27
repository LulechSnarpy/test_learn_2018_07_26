package club.iskyc.lulech.Interaction;

import com.google.common.util.concurrent.ThreadFactoryBuilder;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Provide thread pool for this hole project.
 *
 * @author lulec
 * @since 1.0
 * */
public class SunriseThreadPool {
    /**
     * Core pool size. Can parallel running threads count.
     * */
    private static final int corePoolSize = 5;

    /**
     * Maximum pool size. Max task size.
     * */
    private static final int maximumPoolSize = 20;

    /**
     * Keep alive time when queue fulled.
     * */
    private static final int keepAliveTime = 2;

    /**
     * Queue used to register and store tasks.
     * */
    private static final LinkedBlockingQueue<Runnable> workQueue = new LinkedBlockingQueue<>();

    /**
     * Thread factory create threads.
     * */
    private static final ThreadFactory threadFactory = new ThreadFactoryBuilder().build();

    /**
     * A thread pool for method running, avoid influence main application running.
     * */
    private static ThreadPoolExecutor poolExecutor;

    /**
     * Hide constructor for only one instance.
     * */
    private SunriseThreadPool() {}

    /**
     * Provide only one instance of {@code TreadPoolExecutor}.
     * */
    synchronized public static ThreadPoolExecutor getInstance() {
        return poolExecutor = null != poolExecutor ? poolExecutor
                : new ThreadPoolExecutor(
                corePoolSize,
                maximumPoolSize,
                keepAliveTime,
                TimeUnit.MINUTES,
                workQueue,
                threadFactory
        );
    }
}
