package com.ravathanzalah.flow

import kotlin.reflect.KClass


class ActionHandler {

    private var handlerMap = mutableMapOf<FlowAction, Handler>()


    class Handler {
        var nextStep: String = ""
        private var bundle: Bundle = Bundle()
            get() = field

        fun bundle(init: Bundle.() -> Unit) {
            val bundle = Bundle()
            bundle.init()
            this.bundle = bundle
        }
    }

    private var bundle: Bundle = Bundle()
        get() = field

    fun bundle(init: Bundle.() -> Unit) {
        val bundle = Bundle()
        bundle.init()
        this.bundle = bundle
    }

    fun handler(flowAction: FlowAction, init: Handler.() -> Unit) {
        val handler = Handler()
        handler.init()
        this.handlerMap[flowAction] = handler
    }

    fun remove(action: FlowAction) {
        handlerMap.remove(action)
    }

    fun contains(action: FlowAction) = handlerMap.containsKey(action)

    fun getHandler(action: FlowAction): Handler? = handlerMap[action]

}

fun actionHandler(init: ActionHandler.() -> Unit): ActionHandler {
    val actionHandler = ActionHandler()
    actionHandler.init()
    return actionHandler
}
