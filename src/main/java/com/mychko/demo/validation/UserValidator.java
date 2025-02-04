package com.mychko.demo.validation;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import com.mychko.demo.models.User;

public class UserValidator implements Validator {
    @Override
    public boolean supports(Class<?> clazz) {
        return User.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        User user = (User) target;
        if (user.getUsername().isEmpty()) {
            errors.rejectValue("username", "NotEmpty");
        }
        if (user.getPassword().isEmpty()) {
            errors.rejectValue("password", "NotEmpty");
        }
    }
}