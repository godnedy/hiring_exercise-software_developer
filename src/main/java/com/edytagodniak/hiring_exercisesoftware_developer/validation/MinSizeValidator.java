package com.edytagodniak.hiring_exercisesoftware_developer.validation;

import com.edytagodniak.hiring_exercisesoftware_developer.api.FeedUrl;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.List;

public class MinSizeValidator implements ConstraintValidator<MinSize, List<FeedUrl>> {
    @Override
    public boolean isValid(List<FeedUrl> values, ConstraintValidatorContext context) {
        return values.size() >= 2;
    }
}