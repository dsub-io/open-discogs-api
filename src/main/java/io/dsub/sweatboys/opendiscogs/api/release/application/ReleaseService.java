package io.dsub.sweatboys.opendiscogs.api.release.application;

import io.dsub.sweatboys.opendiscogs.api.release.domain.ReleaseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReleaseService {
  private final ReleaseRepository releaseRepository;

}
