package club.iskyc.lulech.layout.bean;

import java.io.ByteArrayOutputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * This class is a basic datatype for method data to restore.
 * Includes console info and parameters history, also include
 * parent object information.
 *
 * @author lulec
 * @since 1.0
 * */
public class SunriseMethodInfo {
    /**
     * This is the target class for method.
     * */
    private final Class<?> clazz;

    /**
     * This is the target object for method invoke.
     * */
    private Object relay;

    /**
     * This is the method info witch all data depended on.
     * */
    private final Method method;

    /**
     * This is the params of the method last time.
     * */
    private Object[] param;

    /**
     * This is the console information for method ran.
     * */
    private final ByteArrayOutputStream consoleInfoBytes;


    /**
     * Create new {@code SunriseMethodInfo} instance.
     * */
    public SunriseMethodInfo(Method method,Class<?> clazz) {
        this.method = method;
        this.clazz = clazz;
        this.consoleInfoBytes = new ByteArrayOutputStream();
    }

    /**
     * Get this method information.
     * */
    public Method method() {
        return method;
    }

    /**
     * Get console history for this method.
     * */
    public synchronized ByteArrayOutputStream consoleInfoBytes() {
        return consoleInfoBytes;
    }

    /**
     * Clear console history for this method.
     * */
    public void clearConsoleInfoBytes() {
        consoleInfoBytes.reset();
    }

    /**
     * Get only one instance for invoke in this method info. Throws Exceptions such as {@code NoSuchMethodException}
     * */
    public synchronized Object relay() throws NoSuchMethodException, InvocationTargetException
            , InstantiationException, IllegalAccessException {
        relay = null == relay ? clazz.getDeclaredConstructor().newInstance() : relay;
        return relay;
    }

    /**
     * Get class of object this method relay on.
     * */
    public Class<?> clazz() {
        return clazz;
    }

    public Object[] getParam() {
        return param;
    }

    public void setParam(Object[] param) {
        this.param = param;
    }

    @Override
    public String toString(){
        return method.getName();
    }
}
