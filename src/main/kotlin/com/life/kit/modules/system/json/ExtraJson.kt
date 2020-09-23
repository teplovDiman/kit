package com.life.kit.modules.system.json

import java.io.Serializable

class ExtraJson : HashMap<String?, Any?>(), Serializable {

    companion object {
        val DEFAULT_PARAM_NAME = "value"
    }
}