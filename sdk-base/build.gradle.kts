import com.android.build.gradle.internal.cxx.configure.gradleLocalProperties
import org.jetbrains.dokka.gradle.DokkaTask

plugins {
  id("com.android.library")
  kotlin("android")
  id("com.jaredsburrows.license")
  id("org.jetbrains.dokka")
  //id("com.mapbox.maps.token") #mapbox-android-gradle-plugins/issues/29
}

android {
  compileSdkVersion(AndroidVersions.compileSdkVersion)
  defaultConfig {
    minSdkVersion(AndroidVersions.minSdkVersion)
    targetSdkVersion(AndroidVersions.targetSdkVersion)
    testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
  }
}
val buildFromSource: String by project

dependencies {
  implementation(Dependencies.kotlin)
  implementation(Dependencies.mapboxBase)
  implementation(Dependencies.androidxAnnotations)
  api(Dependencies.mapboxGestures)
  if (buildFromSource.toBoolean()) {
    api(project(":maps-core"))
    api(project(":common"))
  } else {
    api(Dependencies.mapboxGlNative)
//    api(Dependencies.mapboxCoreCommon)
    api("com.mapbox.common:common:20.0.0-worker-thread-priority-SNAPSHOT") {  setForce(true) }
  }

  testImplementation(Dependencies.junit)
  testImplementation(Dependencies.mockk)
  testImplementation(Dependencies.androidxTestCore)
  testImplementation(Dependencies.equalsVerifier)
}

tasks.withType<DokkaTask>().configureEach {
  dokkaSourceSets {
    configureEach {
      reportUndocumented.set(true)
      // https://github.com/mapbox/mapbox-maps-android/issues/301#issuecomment-712736885
      failOnWarning.set(false)
    }
  }
}

project.apply {
  from("$rootDir/gradle/ktlint.gradle")
  from("$rootDir/gradle/lint.gradle")
  from("$rootDir/gradle/jacoco.gradle")
  from("$rootDir/gradle/sdk-registry.gradle")
  from("$rootDir/gradle/track-public-apis.gradle")
}