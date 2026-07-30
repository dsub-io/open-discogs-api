package io.dsub.opendiscogs.api.release.query;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class ReleaseQueryUnitTest {

  @Test
  void normalizedTrimsTextFilters() {
    ReleaseQuery normalized =
        new ReleaseQuery("  title  ", "  KR  ", 2026, 7, true).normalized();

    assertThat(normalized.title()).isEqualTo("title");
    assertThat(normalized.country()).isEqualTo("KR");
    assertThat(normalized.year()).isEqualTo(2026);
    assertThat(normalized.month()).isEqualTo(7);
    assertThat(normalized.isMaster()).isTrue();
  }

  @Test
  void normalizedTurnsBlankFiltersIntoNull() {
    ReleaseQuery normalized = new ReleaseQuery(" ", "", null, null, null).normalized();

    assertThat(normalized.title()).isNull();
    assertThat(normalized.country()).isNull();
  }
}
