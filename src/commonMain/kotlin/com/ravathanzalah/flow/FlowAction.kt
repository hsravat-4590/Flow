package com.ravathanzalah.flow

enum class FlowAction {
    /**
     * Initial Action used at the start of a flow
     */
    START,

    /**
     * Continue to next step of flow
     */
    CONTINUE,

    /**
     * Return to the previous step
     */
    BACK,

    /**
     * Throw a Flow Error and enter error flow
     */
    ERROR,

    /**
     * Stop the flow
     */
    STOP,

    /**
     * Gracefully exit flow
     */
    EXIT,

    /**
     * Switch to another flow
     */
    SWITCH
}