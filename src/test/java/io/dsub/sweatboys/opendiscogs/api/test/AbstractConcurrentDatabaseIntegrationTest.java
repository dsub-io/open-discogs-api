package io.dsub.sweatboys.opendiscogs.api.test;

import static org.junit.jupiter.api.parallel.ExecutionMode.CONCURRENT;

import org.junit.jupiter.api.parallel.Execution;

@Execution(CONCURRENT)
public abstract class AbstractConcurrentDatabaseIntegrationTest extends
    AbstractDatabaseIntegrationTest {

}
