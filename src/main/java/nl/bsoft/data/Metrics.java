package nl.bsoft.data;

import com.hazelcast.config.Config;
import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.HazelcastInstance;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

public class Metrics {
    private final Logger log = LoggerFactory.getLogger(Metrics.class);

    private HazelcastInstance hzInstance = null;

    private static Metrics instance = null;

    private Map<String, Map<String, Integer>> counters = new HashMap<>();

    public static Metrics getInstance() {
        if (null == instance) {
            instance = new Metrics();
        }
        return instance;
    }

    private Metrics() {
        Config cfg = new Config();
        hzInstance = Hazelcast.newHazelcastInstance(cfg);

        Map<String, Integer> queueCounters = hzInstance.getMap("queueCounters");
        counters.put("queueCounters", queueCounters);

        log.info("Created Metrics Object {}", this);
    }

    public void incrementElement(String mapName, String element) {
        log.info("IncrementElement mapname: {}, element: {}", mapName, element);

        if (counters.containsKey(mapName)) {
            Map<String, Integer> collection = counters.get(mapName);
            if (collection.containsKey(element)) {
                // get current element, and increment by one
                Integer curVal = collection.get(element);
                Integer newVal = new Integer(curVal.intValue() + 1);
                collection.put(element, newVal);
                log.info("Updated element: {} from oldvalue: {} to newvalue: {}", element, curVal, newVal);
            } else {
                // element does not exist, create initial element
                collection.put(element, new Integer(1));
                log.info("Element: {} not found created initial element", element);
            }
        } else {
            log.error("Mapname: {}, does not exist");
        }
    }

    public void clearElement(String mapName, String element) {
        log.info("ClearElement mapname: {}, element: {}", mapName, element);

        if (counters.containsKey(mapName)) {
            Map<String, Integer> collection = counters.get(mapName);
            if (collection.containsKey(element)) {
                // remove selected element
                collection.remove(element);
                log.info("Removed element: {}", element);
            } else {
                log.info("Element: {} not found nothing to do", element);
            }
        } else {
            log.error("Mapname: {}, does not exist");
        }
    }

    public Integer getElement(String mapName, String element) {
        Integer result = null;

        if (counters.containsKey(mapName)) {
            Map<String, Integer> collection = counters.get(mapName);
            if (collection.containsKey(element)) {
                result = collection.get(element);
                log.info("Found value for element: {}, value: {}", element, result);
            }
        }
        return result;
    }
}
