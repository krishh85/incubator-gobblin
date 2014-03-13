package com.linkedin.uif.scheduler;

import java.io.File;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;

import com.codahale.metrics.ConsoleReporter;
import com.codahale.metrics.Counter;
import com.codahale.metrics.CsvReporter;
import com.codahale.metrics.Gauge;
import com.codahale.metrics.Meter;
import com.codahale.metrics.MetricRegistry;
import com.codahale.metrics.Slf4jReporter;

/**
 * A convenient class for using {@link com.codahale.metrics.MetricRegistry}.
 */
public class Metrics {

    private static final MetricRegistry METRICS = new MetricRegistry();

    /**
     * Get the {@link com.codahale.metrics.MetricRegistry}.
     *
     * @return {@link com.codahale.metrics.MetricRegistry}
     */
    public static MetricRegistry get() {
        return METRICS;
    }

    /**
     * Create a metric name.
     *
     * @param cls Class where the metric.
     * @param names name components
     * @return the metric name
     */
    public static String metricName(Class<?> cls, String... names) {
        return MetricRegistry.name(cls, names);
    }

    /**
     * Create a metric name.
     *
     * @param rootName root metric name
     * @param names name components
     * @return the metric name
     */
    public static String metricName(String rootName, String... names) {
        return MetricRegistry.name(rootName, names);
    }

    /**
     * Register a {@link com.codahale.metrics.Gauge}.
     *
     * @param name name of the {@link com.codahale.metrics.Gauge}
     * @param gauge the {@link com.codahale.metrics.Gauge} to register
     * @param <T> gauge data type
     */
    public static <T> Gauge<T> getGauge(String name, Gauge<T> gauge) {
        return METRICS.register(name, gauge);
    }

    /**
     * Create a new {@link com.codahale.metrics.Counter} with the given name.
     *
     * @param name name of the {@link com.codahale.metrics.Counter}
     * @return newly created {@link com.codahale.metrics.Counter}
     */
    public static Counter getCounter(String name) {
        return METRICS.counter(name);
    }

    /**
     * Get a {@link com.codahale.metrics.Meter} with the given name.
     *
     * @param name name of the {@link com.codahale.metrics.Meter}
     * @return newly created {@link com.codahale.metrics.Meter}
     */
    public static Meter getMeter(String name) {
        return METRICS.meter(name);
    }

    /**
     * Start a {@link com.codahale.metrics.ConsoleReporter}.
     *
     * @param period interval between reports in milliseconds
     */
    public static void startConsoleReporter(long period) {
        ConsoleReporter reporter = ConsoleReporter.forRegistry(METRICS)
                .convertRatesTo(TimeUnit.SECONDS)
                .convertDurationsTo(TimeUnit.MILLISECONDS)
                .build();
        reporter.start(period, TimeUnit.MILLISECONDS);
    }

    /**
     * Start a {@link com.codahale.metrics.CsvReporter}.
     *
     * @param period interval between reports in milliseconds
     * @param metricsDir directory where metrics csv files are stored
     */
    public static void startCsvReporter(long period, String metricsDir) {
        CsvReporter reporter = CsvReporter.forRegistry(METRICS)
                .convertRatesTo(TimeUnit.SECONDS)
                .convertDurationsTo(TimeUnit.MILLISECONDS)
                .build(new File(metricsDir));
        reporter.start(period, TimeUnit.MILLISECONDS);
    }

    /**
     * Start a {@link com.codahale.metrics.Slf4jReporter}.
     *
     * @param period interval between reports in milliseconds
     * @param logger SLF4J logger
     */
    public static void startSlf4jReporter(long period, Logger logger) {
        Slf4jReporter reporter = Slf4jReporter.forRegistry(METRICS)
                .convertRatesTo(TimeUnit.SECONDS)
                .convertDurationsTo(TimeUnit.MILLISECONDS)
                .outputTo(logger)
                .build();
        reporter.start(period, TimeUnit.MILLISECONDS);
    }
}
