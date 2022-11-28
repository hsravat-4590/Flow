package com.ravathanzalah.flow

class FlowManagerImpl(
    private val stepInjector: StepInjector): FlowManager {
    private val flows: MutableMap<String, Flow> = mutableMapOf()


    constructor(stepInjector: StepInjector, flows: Map<String, Flow> = mapOf(), bundle: Bundle?) : this(stepInjector) {
        this.flows.putAll(flows)
        bundle?.let {
            this.globalBundle = it
        }
    }

    var globalBundle: Bundle = Bundle()
        private set

    fun globalBundle(init: Bundle.() -> Unit) {
        val bundle = Bundle()
        bundle.init()
        this.globalBundle = bundle
    }

    var currentFlow: Flow? = null
        private set

    var currentStep: Step? = null
        private set

    override fun startFlow(flowName: String) {
        if(flows.containsKey(flowName)) {
            throw IllegalStateException("Can't start a flow while another is running")
        }
        currentFlow = flows[flowName]
        currentFlow?.let {flow ->
            flow.getSteps()[flow.startingStep]?.let {step ->
                executeStep(step)
            }
        }
    }

    private fun executeStep(
        step: Step,
    ): FlowAction {
        currentStep = step
        val processStep = stepInjector.getStep(step.process!!)
        return processStep?.execute(this, bundle {
            add("globalBundle", globalBundle)
            add("flowBundle", currentFlow!!.flowBundle)
            add("stepBundle", step.stepBundle)
        }) ?: return FlowAction.ERROR
    }

    override fun cancelFlow(): Boolean {
        return if (currentFlow != null && currentFlow!!.isCancellable()) {
            terminateFlow()
        } else
            false
    }

    override fun terminateFlow(): Boolean {
        return try {
            currentFlow!!.terminate()
            true
        } catch (e: Exception) {
            false
        }
    }

    override fun executeAction(action: FlowAction) {
        when(action) {
            FlowAction.ERROR -> throw IllegalArgumentException("Error when executing flow")
            else -> {
                currentStep?.actionHandler!!.getHandler(action)?.let { handler ->
                    currentFlow?.getSteps()?.get(handler.nextStep)?.let {
                        val next = executeStep(it)
                        executeAction(next)
                    }
                }
            }
        }
    }

    override fun getCurrentStep(): String = currentStep!!.id

    override fun getStepInjector(): StepInjector = stepInjector

    override fun flow(name: String, init: Flow.() -> Unit) {
        val flow = Flow(name)
        flow.init()
        flows[name] = flow
    }

    override fun removeFlow(name: String){
        val flowMatch = currentFlow?.takeIf { it.name == name }
        flowMatch?.let {
            throw IllegalStateException("Cannot remove a flow that is in progress")
        }
        flows.remove(name)
    }
}