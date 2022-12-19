package io.dsub.sweatboys.opendiscogs.api.core.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class SortParamsValidator implements ConstraintValidator<SortableParams, Pageable> {
    public SortParamsValidator() {}

    private final Set<String> allowedParams = new HashSet<>();
    @Override
    public void initialize(SortableParams params) {
        allowedParams.addAll(Arrays.asList(params.value()));
    }

    @Override
    public boolean isValid(Pageable value, ConstraintValidatorContext context) {
        return value.getSort()
                .stream()
                .map(Sort.Order::getProperty)
                .allMatch(allowedParams::contains);
    }
}
