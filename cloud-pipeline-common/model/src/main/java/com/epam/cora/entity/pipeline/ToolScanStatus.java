package com.epam.cora.entity.pipeline;

/**
 * Describes the status of Tool security scan
 */
public enum ToolScanStatus {
    /**
     * The tool is pending scan. Security scan is a time costly operaion, so there's a queue for it.
     */
    PENDING(1),

    /**
     * Tool's security scan has been completed
     */
    COMPLETED(2),

    /**
     * Tool's security scan has failed for some reason
     */
    FAILED(3),


    /**
     * Tool's security scan never been executed,
     * WARNING: This value isn't stored in a database
     */
    NOT_SCANNED(4);

    private int code;

    public int getCode() {
        return code;
    }

    ToolScanStatus(int code) {
        this.code = code;
    }

    public static ToolScanStatus getByCode(int code) {
        return values()[code - 1];
    }
}
