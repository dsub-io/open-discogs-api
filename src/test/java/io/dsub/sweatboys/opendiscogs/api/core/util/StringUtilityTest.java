package io.dsub.sweatboys.opendiscogs.api.core.util;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.assertThat;

class StringUtilityTest {

    @ParameterizedTest
    @ValueSource(strings = {"", " "})
    void normalizeReturnsNull(String in) {
        assertThat(StringUtility.normalize(in)).isNull();
    }

    @Test
    void normalizeSkipsNull() {
        assertThat(StringUtility.normalize(null)).isNull();
    }

    @ParameterizedTest
    @ValueSource(strings = {" test", "test ", " test ", "test"})
    void normalizeTrims(String in) {
        assertThat(StringUtility.normalize(in)).isEqualTo("test");
    }

    @ParameterizedTest
    @ValueSource(strings = {"path", "test.path", "test.test.test.path", ".test.path", ".path..", "..path.", "..path", "path.."})
    void getMostChildPath(String in) {
        assertThat(StringUtility.getMostChildPath(in)).isEqualTo("path");
    }
}