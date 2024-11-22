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



dependencies {
    intellijPlatform {
        //intellijIdeaCommunity("2024.2.4")
        //bundledPlugin("com.intellij.java")
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
        sinceBuild.set("242.*")
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
