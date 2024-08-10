package df.base.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.stream.IntStream;

public class StrongPasswordValidator implements ConstraintValidator<StrongPassword, String> {

    private String special;

    @Override
    public void initialize(StrongPassword annotation) {
        special = annotation.special();
    }

    @Override
    public boolean isValid(String password, ConstraintValidatorContext constraintValidatorContext) {

        if (password == null || password.isEmpty()) {
            return false;
        }

        final IntStream characters     = password.chars();
        boolean         hasUpperCase   = characters.anyMatch(Character::isUpperCase);
        boolean         hasLowerCase   = characters.anyMatch(Character::isLowerCase);
        boolean         hasDigit       = characters.anyMatch(Character::isDigit);
        boolean         hasSpecialChar = characters.anyMatch(i -> special.indexOf(i) >= 0);

        return hasUpperCase && hasLowerCase && hasDigit && hasSpecialChar;

    }

}
