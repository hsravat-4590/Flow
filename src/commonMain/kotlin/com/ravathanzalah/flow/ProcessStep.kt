package com.ravathanzalah.flow

interface ProcessStep {

    fun getId(): String

    fun execute(flowManager: FlowManager, bundle: Bundle?): FlowAction
}