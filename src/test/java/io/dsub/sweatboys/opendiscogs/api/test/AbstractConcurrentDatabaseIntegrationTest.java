package io.dsub.sweatboys.opendiscogs.api.test;

import org.junit.jupiter.api.parallel.Execution;

import static org.junit.jupiter.api.parallel.ExecutionMode.CONCURRENT;

@Execution(CONCURRENT)
public abstract class AbstractConcurrentDatabaseIntegrationTest extends AbstractDatabaseIntegrationTest {

}
