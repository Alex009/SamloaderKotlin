import org.jetbrains.compose.compose
import org.jetbrains.compose.desktop.application.dsl.TargetFormat

plugins {
    kotlin("multiplatform")
    id("org.jetbrains.compose")
    id("dev.icerock.mobile.multiplatform-resources")
}

group = rootProject.extra["groupName"].toString()
version = rootProject.extra["versionName"].toString()

kotlin {
    jvm {
        compilations.all {
            kotlinOptions.jvmTarget = rootProject.extra["javaVersionEnum"].toString()
        }
    }
    sourceSets {
        val jvmMain by getting {
            dependencies {
                implementation(project(":commonCompose"))

                implementation(compose.desktop.currentOs)
            }
        }
    }
}

tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
    kotlinOptions.jvmTarget = rootProject.extra["javaVersionEnum"].toString()
}

tasks.withType<org.gradle.jvm.tasks.Jar> {
    exclude("META-INF/*.RSA", "META-INF/*.DSA", "META-INF/*.SF")
}

compose.desktop {
    val packageName: String by rootProject.extra
    val appName: String by rootProject.extra

    application {
        this.dependsOn(project(":common").tasks.named("generateMRjvmMain").get())

        mainClass = "MainKt"
        nativeDistributions {
            modules("jdk.crypto.ec")

            windows {
                menu = true
                this.console = true

                iconFile.set(project.file("src/jvmMain/resources/icon.ico"))
                targetFormats(TargetFormat.Exe, TargetFormat.AppImage)
            }

            macOS {
                bundleID = packageName
                iconFile.set(project.file("src/jvmMain/resources/icon.icns"))
                packageVersion = "1." + rootProject.extra["versionCode"]
                targetFormats(TargetFormat.Dmg)
                this.packageName = appName
            }

            linux {
                iconFile.set(project.file("src/jvmMain/resources/icon.png"))
                packageVersion = rootProject.extra["versionCode"].toString()
                targetFormats(TargetFormat.Deb, TargetFormat.AppImage)
            }

            targetFormats(TargetFormat.Dmg, TargetFormat.Msi, TargetFormat.Deb, TargetFormat.Exe)
            this.packageName = appName
        }
    }
}

multiplatformResources {
    multiplatformResourcesPackage = "tk.zwander.samloaderkotlin.desktop"
}
