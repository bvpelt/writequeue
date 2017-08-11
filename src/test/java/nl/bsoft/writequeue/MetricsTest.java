package nl.bsoft.writequeue;

import nl.bsoft.data.Metrics;
import org.junit.Assert;
import org.junit.Test;

public class MetricsTest {

    @Test
    public void createMetrics() {
        Metrics m = Metrics.getInstance();
        Assert.assertNotNull(m);
    }

    @Test
    public void setValueAndReadValue() {
        Metrics m = Metrics.getInstance();
        Assert.assertNotNull(m);

        m.clearElement("queueCounters", "archiefQueue");
        m.incrementElement("queueCounters", "archiefQueue");
        Integer aq = m.getElement("queueCounters", "archiefQueue");

        Assert.assertEquals(aq.intValue(),1);
    }

    @Test
    public void setValueAndReadWithIncrements() {
        Metrics m = Metrics.getInstance();
        Assert.assertNotNull(m);

        m.clearElement("queueCounters", "archiefQueue");
        for (int i = 0; i < 10; i++) {
            m.incrementElement("queueCounters", "archiefQueue");
        }
        Integer aq = m.getElement("queueCounters", "archiefQueue");

        Assert.assertEquals(aq.intValue(),10);
    }

    @Test
    public void setValueAndReadMultiple() {
        Metrics m = Metrics.getInstance();
        Assert.assertNotNull(m);

        m.clearElement("queueCounters", "archiefQueue");
        m.clearElement("queueCounters", "deadLetterQueue");
        for (int i = 0; i < 10; i++) {
            m.incrementElement("queueCounters", "archiefQueue");
        }
        for (int i = 0; i < 10; i++) {
            m.incrementElement("queueCounters", "deadLetterQueue");
        }
        Integer aq = m.getElement("queueCounters", "archiefQueue");
        Integer bq = m.getElement("queueCounters", "deadLetterQueue");

        Assert.assertEquals(aq.intValue(),10);
        Assert.assertEquals(bq.intValue(),10);
    }
}
