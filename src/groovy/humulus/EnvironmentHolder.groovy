package humulus

public class EnvironmentHolder {

    private static final ThreadLocal<Map> contextHolder = new ThreadLocal<Map>()

    static void setEnvironment(Map environment) {
        contextHolder.set(environment)
    }

    static Map getEnvironment() {
        return contextHolder.get()
    }

    static void clear() {
        contextHolder.remove()
    }
}
