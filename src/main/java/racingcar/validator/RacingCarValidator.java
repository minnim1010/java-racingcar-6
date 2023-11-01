package racingcar.validator;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Pattern;
import racingcar.common.config.RacingCarRule;
import racingcar.common.exception.ErrorMessage;
import racingcar.racer.Racer;

public final class RacingCarValidator {

    private static final Pattern RACING_CAR_NAME_PATTERN = Pattern.compile("^[a-zA-Z0-9]+$");

    private RacingCarValidator() {
    }

    public static void validateNameLength(String name) {
        if (name.length() > RacingCarRule.MAX_RACER_NAME_LENGTH || name.isEmpty()) {
            throw ErrorMessage.INVALID_RACING_CAR_NAME_LENGTH.getException();
        }
    }

    public static void validateNameFormat(String name) {
        if (!RACING_CAR_NAME_PATTERN.matcher(name).matches()) {
            throw ErrorMessage.INVALID_RACING_CAR_NAME_FORMAT.getException();
        }
    }

    public static <T extends Racer> void validateRacerSize(List<T> racers) {
        if (racers.size() > RacingCarRule.MAX_RACER_SIZE || racers.size() < RacingCarRule.MIN_RACER_SIZE) {
            throw ErrorMessage.INVALID_RACER_SIZE.getException();
        }
    }

    public static <T extends Racer> void validateDuplicatedName(List<T> racers) {
        Set<T> set = new HashSet<>(racers);
        if (set.size() != racers.size()) {
            throw ErrorMessage.DUPLICATED_RACING_CAR_NAME.getException();
        }
    }
}
