/**
 * slf4j-fluent provides a fluent API for SLF4J {@link https://www.slf4j.org/}.
 *
 * <h4>Example code:</h4>
 * <pre>
 * import static org.fissore.slf4j.Util.lazy;
 *
 * ...
 *
 * FluentLogger log = FluentLoggerFactory.getLogger(getClass());
 *
 * log.debug().log("A debug log entry with {} args: {}, {}", 2, "value 1", lazy(() -> someObject.expensiveMethod()));
 *
 * // will add the stacktrace of the cause to the log entry
 * log.error().withCause(exception).log("An error occured while fetching user {}", user.getId());
 *
 * // will log every 5 calls to `log` method, instead of every single time
 * log.error().every(5).log("Errors occured, but we print only one entry every 5");
 *
 * // will log every 1 second, instead of every single time
 * log.error().every(1, ChronoUnit.SECONDS).log("Errors occured, but we print only one entry every 1 second");
 * </pre>
 */
package org.fissore.slf4j;
