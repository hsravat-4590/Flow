package com.ravathanzalah.flow

import kotlin.reflect.KClass

interface StepInjector {
    fun <T : ProcessStep> getStep(clazz: KClass<T>): T?

}