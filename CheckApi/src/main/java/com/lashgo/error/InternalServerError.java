package com.lashgo.error;

import com.lashgo.model.ErrorCodes;

/**
 * Created by Eugene on 22.06.2014.
 */
public class InternalServerError extends LashgoRuntimeError {
    public InternalServerError() {
        super(ErrorCodes.INTERNAL_SERVER_ERROR);
    }
}
