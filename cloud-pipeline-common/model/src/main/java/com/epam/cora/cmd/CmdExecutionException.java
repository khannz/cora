package com.epam.cora.cmd;

import java.util.Optional;

public class CmdExecutionException extends RuntimeException {

    public CmdExecutionException(String cmd, Throwable cause) {
        super(cmd, cause);
    }

    public CmdExecutionException(String cmd) {
        super(cmd);
    }

    public String getRootMessage() {
        return getRootMessage(this);
    }

    private String getRootMessage(final Throwable e) {
        return Optional.of(e)
                .map(Throwable::getCause)
                .map(e1 -> Optional.ofNullable(e.getMessage()).orElse("") + " -> " + getRootMessage(e1))
                .orElseGet(e::getMessage);
    }
}
