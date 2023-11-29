package com.mapbox.maps.extension.localization

import com.mapbox.maps.MapboxStyleManager
import java.util.*

/**
 * Extension function to localize style labels
 *
 * @param locale the locale that applied for localization
 * @param layerIds the id of layers that will localize on, default is null which means will localize all the feasible layers.
 */
@JvmOverloads
fun MapboxStyleManager.localizeLabels(locale: Locale, layerIds: List<String>? = null) {
  setMapLanguage(locale, this, layerIds)
}