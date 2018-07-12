package com.portal.config;

import java.net.InetSocketAddress;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Configuration;

import com.codahale.metrics.ConsoleReporter;
import com.codahale.metrics.JmxReporter;
import com.codahale.metrics.MetricFilter;
import com.codahale.metrics.MetricRegistry;
import com.codahale.metrics.graphite.Graphite;
import com.codahale.metrics.graphite.GraphiteReporter;
import com.ryantenney.metrics.spring.config.annotation.EnableMetrics;
import com.ryantenney.metrics.spring.config.annotation.MetricsConfigurerAdapter;

@Configuration
@ConditionalOnProperty(prefix = "customer.metrics", name = "enabled", havingValue = "true", matchIfMissing = false)
@EnableMetrics
public class MetricsConfiguration extends MetricsConfigurerAdapter {

	private static final Logger logger = LoggerFactory.getLogger(MetricsConfiguration.class);

	@Value("${customer.metrics.console.enabled}")
	private boolean consoleEnabled;

	@Value("${customer.metrics.console.interval}")
	private Long consoleInterval;

	@Value("${customer.metrics.graphite.enabled}")
	private boolean graphiteEnabled;

	@Value("${customer.metrics.jmx.enabled}")
	private boolean jmxEnabled;

	@Value("${customer.metrics.graphite.server}")
	private String graphiteServer;

	@Value("${customer.metrics.graphite.port}")
	private int graphitePort;

	@Value("${customer.metrics.prefix}")
	private String metricsPrefix;

	@Override
	public void configureReporters(MetricRegistry metricRegistry) {
		logger.info("configureReporters start");

		if (graphiteEnabled) {
			/* Configuring the Graphite Reporter. */
			final Graphite graphite = new Graphite(new InetSocketAddress(graphiteServer, graphitePort));
			final GraphiteReporter reporter = GraphiteReporter.forRegistry(metricRegistry).prefixedWith(metricsPrefix)
					.convertRatesTo(TimeUnit.SECONDS).convertDurationsTo(TimeUnit.MILLISECONDS).filter(MetricFilter.ALL)
					.build(graphite);
			reporter.start(1, TimeUnit.SECONDS);
			logger.info("Graphite reporter for metrics was initialized successfully.");
		}

		if (consoleEnabled) {
			/* Configuring the Console Reporter. */
			ConsoleReporter consoleReporter = ConsoleReporter.forRegistry(metricRegistry)
					.convertRatesTo(TimeUnit.SECONDS).convertDurationsTo(TimeUnit.MILLISECONDS).build();
			consoleReporter.start(consoleInterval, TimeUnit.SECONDS);
			logger.info("Console reporter for metrics was initialized successfully.");
		}

		if (jmxEnabled) {
			/* Configuring the Jmx Reporter. */
			final JmxReporter reporter = JmxReporter.forRegistry(metricRegistry).build();
			reporter.start();
			logger.info("JMX reporter for metrics was initialized successfully.");
		}

		logger.info("configureReporters end");
	}
}
