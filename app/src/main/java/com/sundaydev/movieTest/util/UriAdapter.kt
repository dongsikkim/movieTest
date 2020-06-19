package com.sundaydev.movieTest.util

import android.net.Uri
import com.google.gson.*
import java.lang.reflect.Type

internal class UriAdapter : JsonSerializer<Uri?>, JsonDeserializer<Uri?> {
    @Throws(JsonParseException::class)
    override fun deserialize(json: JsonElement, typeOfT: Type?, context: JsonDeserializationContext?): Uri = Uri.parse(json.asString)
    override fun serialize(src: Uri?, typeOfSrc: Type?, context: JsonSerializationContext?): JsonElement =
        src?.let { uri -> JsonPrimitive(uri.toString()) } ?: JsonPrimitive("")
}