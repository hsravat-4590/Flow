package com.ravathanzalah.flow

import kotlin.reflect.KClass

class Step(val id: String) {
    var process: KClass<ProcessStep>? = null
        set(value) {
            if(process != null) {
                throw IllegalStateException("Process has been set")
            }
            field = value
        }
    val stepBundle = Bundle()
    var actionHandler = ActionHandler()
        private set

    fun actionHandler(init: ActionHandler.() -> Unit) {
        val actionHandler = ActionHandler()
        actionHandler.init()
        this.actionHandler = actionHandler
    }
}

fun step(id: String, init: Step.() -> Unit): Step =
    Step(id).apply {
        init()
    }


