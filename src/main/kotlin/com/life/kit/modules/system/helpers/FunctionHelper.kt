@file:Suppress("UNCHECKED_CAST")

package com.life.kit.modules.system.helpers

import org.springframework.stereotype.Component

@Component
class FunctionHelper

fun <T, R> Any.invoke(worker: (T) -> R): R {
    return worker.invoke(this as T)
}

fun <T> Any.appeal(worker: (T) -> T): T {
    return worker.invoke(this as T)
}