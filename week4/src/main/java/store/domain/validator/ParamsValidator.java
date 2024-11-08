package store.domain.validator;

public class ParamsValidator {

    public static void validateParamsNotNull(Class<?> caller, Object... params) {
        for (Object obj : params) {
            if (obj == null) {
                throw new NullPointerException(caller.getSimpleName());
            }
        }
    }
}
