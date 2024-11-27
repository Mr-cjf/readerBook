plugins {
    id("org.jetbrains.intellij.platform") version "2.1.0"
}
group = "cn.cuijiangfeng.readerbook"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
//    gradlePluginPortal()
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
        bundledPlugin("com.intellij.java")
        local("C:\\Users\\cjf\\AppData\\Local\\Programs\\IntelliJ IDEA Ultimate 3")
        zipSigner()
        instrumentationTools()
    }
}

tasks {
    // Set the JVM compatibility versions
    withType<JavaCompile> {
        sourceCompatibility = "21"
        targetCompatibility = "21"
        // 解决编译时中文报错
        options.encoding = "UTF-8"
    }

    // 添加以下内容，解决运行时控制台中文乱码
    withType<JavaExec> {
        jvmArgs = listOf("-Dfile.encoding=UTF-8", "-Dsun.stdout.encoding=UTF-8", "-Dsun.stderr.encoding=UTF-8")
    }

    patchPluginXml {
        sinceBuild.set("242.23726.103") // 修改为当前 IDE 版本
        untilBuild.set("243.*") // 可以设置一个范围，确保兼容未来的小版本更新
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
