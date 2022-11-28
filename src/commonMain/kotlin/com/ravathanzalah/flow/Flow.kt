package com.ravathanzalah.flow

import kotlin.reflect.KClass

class Flow(val name: String) {
    lateinit var startingStep: String
    var flowBundle: Bundle = Bundle()
        private set

    private var terminate = false

    fun bundle(init: Bundle.() -> Unit) {
        val bundle = Bundle()
        bundle.init()
        this.flowBundle = bundle
    }
    private val steps: MutableMap<String, Step> = mutableMapOf()

    fun getSteps(): Map<String, Step> =
        if(terminate) {
            mapOf()
        } else {
            steps.toMap()
        }

    fun step(name: String, stepClass: KClass<ProcessStep>, init: Step.() -> Unit) {
        val step = Step(name).apply {
            this.process = stepClass
            init()
        }
        steps[name] = step
    }

    fun isCancellable(): Boolean = true

    fun terminate() {
        terminate = true
    }

}

fun flow(name: String, init: Flow.() -> Unit): Flow = Flow(name).apply { init() }
