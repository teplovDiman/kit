@file:Suppress("UNCHECKED_CAST")

package com.life.kit.system.helpers

import org.springframework.stereotype.Component

@Component
class FunctionHelper

fun <T, R> Any.invoke(worker: (T) -> R): R = worker.invoke(this as T)