package io.dsub.sweatboys.opendiscogs.api.core.validation;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

import io.dsub.sweatboys.opendiscogs.api.test.ConcurrentTest;
import io.dsub.sweatboys.opendiscogs.api.test.util.TestUtil;
import jakarta.validation.ConstraintViolation;
import java.util.Set;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import reactor.test.StepVerifier;

class ValidatorImplTest extends ConcurrentTest {

  @Mock
  jakarta.validation.Validator delegation;

  @InjectMocks
  ValidatorImpl validator;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
  }

  @Test
  void validateReturnsValidationError() {
    String error = "test constraint validation";
    ConstraintViolation<String> validation = TestUtil.getConstraintViolation(error);
    Set<ConstraintViolation<String>> violations = Set.of(validation);
    given(delegation.validate(any(String.class))).willReturn(violations);
    StepVerifier.create(validator.validate("test"))
        .expectErrorMatches(err -> err.getMessage().contains(error))
        .verify();
  }

  @Test
  void validateDelegatesReturnFromValidator() {
    var expected = new Object();
    given(delegation.validate(any())).willReturn(Set.of());
    StepVerifier.create(validator.validate(expected))
        .expectNext(expected)
        .verifyComplete();
  }
}
