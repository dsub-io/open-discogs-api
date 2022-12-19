package io.dsub.sweatboys.opendiscogs.api.core.util;

import java.util.List;
import org.jooq.SortField;
import org.jooq.impl.DSL;
import org.springframework.data.domain.Pageable;

public final class JooqUtility {

  private static final JooqUtility INSTANCE = new JooqUtility();

  private JooqUtility() {
  }

  public static List<SortField<Object>> getSortFields(Pageable pageable) {
    return INSTANCE.doGetSortFields(pageable);
  }

  public List<SortField<Object>> doGetSortFields(Pageable pageable) {
    return pageable.getSort().stream()
        .map(o -> o.isAscending() ? DSL.field(o.getProperty()).asc()
            : DSL.field(o.getProperty()).desc())
        .toList();
  }
}
