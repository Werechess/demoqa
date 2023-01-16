package helpers;

import org.junit.platform.engine.ConfigurationParameters;
import org.junit.platform.engine.support.hierarchical.ParallelExecutionConfiguration;
import org.junit.platform.engine.support.hierarchical.ParallelExecutionConfigurationStrategy;

public class CustomThreadsStrategy implements ParallelExecutionConfiguration, ParallelExecutionConfigurationStrategy {

    Integer threads = Integer.valueOf(System.getProperty("threads"));

    @Override
    public int getParallelism() {
        return threads;
    }

    @Override
    public int getMinimumRunnable() {
        return threads;
    }

    @Override
    public int getMaxPoolSize() {
        return threads;
    }

    @Override
    public int getCorePoolSize() {
        return threads;
    }

    @Override
    public int getKeepAliveSeconds() {
        return 60;
    }

    @Override
    public ParallelExecutionConfiguration createConfiguration(final ConfigurationParameters configurationParameters) {
        return this;
    }
}
