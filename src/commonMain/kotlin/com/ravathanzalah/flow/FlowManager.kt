package com.ravathanzalah.flow

interface FlowManager {

    fun startFlow(flowName: String)

    fun cancelFlow(): Boolean

    fun terminateFlow(): Boolean

    fun executeAction(action: FlowAction)

    fun getCurrentStep(): String

    fun getStepInjector(): StepInjector

    fun flow(name: String, init: Flow.() -> Unit)

    fun removeFlow(name: String)

}