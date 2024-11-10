package store.domain.validator;

import store.common.exception.StoreExceptions;

public class ParamsValidator {

    public static void validateParamsNotNull(Object... params) {
        for (Object obj : params) {
            if (obj == null) {
                throw StoreExceptions.ILLEGAL_ARGUMENT.get();
            }
        }
    }
}
