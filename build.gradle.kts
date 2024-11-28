plugins {
    id("org.jetbrains.intellij.platform") version "2.1.0"
}

group = "cn.cuijiangfeng.readerbook"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
    gradlePluginPortal()
    maven {
        url = uri("https://maven.aliyun.com/repository/public")
    }
    intellijPlatform {
        defaultRepositories()
    }
}

val commonsLangVersion: String by extra("3.17.0")
val jUniversalChardetVersion: String by extra("1.0.3")
val slf4jVersion: String by extra("2.0.16")
val logbackVersion: String by extra("1.5.12")
val lombokVersion: String by extra("1.18.34")

dependencies {
    implementation("org.apache.commons:commons-lang3:${commonsLangVersion}")
    implementation("com.googlecode.juniversalchardet:juniversalchardet:${jUniversalChardetVersion}")
    implementation("org.slf4j:slf4j-api:${slf4jVersion}")
    implementation("ch.qos.logback:logback-classic:${logbackVersion}")
    implementation("org.projectlombok:lombok:${lombokVersion}")
    annotationProcessor("org.projectlombok:lombok:${lombokVersion}") // 添加 Lombok 注解处理器
    intellijPlatform {
        // intellijIdeaCommunity("2024.2.4")
        // intellijIdeaUltimate("2024.2.4")
        bundledPlugins("com.intellij.java", "com.intellij.database")
        local("C:\\Users\\cjf\\AppData\\Local\\Programs\\IntelliJ IDEA Ultimate 3")
        zipSigner()
        instrumentationTools()
    }
}

tasks {
    withType<JavaCompile> {
        sourceCompatibility = "21"
        targetCompatibility = "21"
        options.encoding = "UTF-8"
    }

    withType<JavaExec> {
        jvmArgs = listOf("-Dfile.encoding=UTF-8", "-Dsun.stdout.encoding=UTF-8", "-Dsun.stderr.encoding=UTF-8")
    }

    patchPluginXml {
        sinceBuild.set("242")
        untilBuild.set("")
    }

    signPlugin {
        certificateChain.set(System.getenv("CERTIFICATE_CHAIN"))
        privateKey.set(System.getenv("PRIVATE_KEY"))
        password.set(System.getenv("PRIVATE_KEY_PASSWORD"))
    }

    publishPlugin {
        token.set(System.getenv("PUBLISH_TOKEN"))
    }
}
