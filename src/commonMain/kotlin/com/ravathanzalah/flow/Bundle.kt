package com.ravathanzalah.flow

class Bundle {
    private val strings: MutableMap<String, String> = mutableMapOf()
    private val numbers: MutableMap<String, Int> = mutableMapOf()
    private val flags: MutableMap<String, Boolean> = mutableMapOf()
    private val parcels: MutableMap<String, Parcel> = mutableMapOf()

    fun add(key: String, value: Any) {
        when(value) {
            is String -> strings[key] = value
            is Int -> numbers[key] = value
            is Boolean -> flags[key] = value
            is Parcel -> parcels[key] = value
            is Bundle -> {
                strings.putAll(value.strings)
                numbers.putAll(value.numbers)
                flags.putAll(value.flags)
                parcels.putAll(value.parcels)
            }
            else -> throw IllegalArgumentException("This type isn't currently supported in a bundle")
        }
    }

    fun addMultiple(items: Map<String, Any>) {
        items.entries.forEach {
            add(it.key, it.value)
        }
    }

    fun getString(key: String) = strings[key]

    fun getNumber(key: String) = numbers[key]

    fun getFlag(key: String) = flags[key]

    fun getParcel(key: String) = parcels[key]
}

fun bundle(init: Bundle.() -> Unit): Bundle {
    val flow = Bundle()
    flow.init()
    return flow
}
