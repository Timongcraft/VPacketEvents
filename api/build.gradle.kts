plugins {
    `java-library`
    `maven-publish`
    signing
}

dependencies {
    compileOnlyApi(libs.velocity.api)
    compileOnlyApi(libs.velocity.proxy)
}

java {
    withSourcesJar()
    withJavadocJar()
}

tasks {
    javadoc {
        options.encoding = Charsets.UTF_8.name()
        (options as StandardJavadocDocletOptions).links(
            "https://jd.advntr.dev/api/4.13.0/",
            "https://jd.papermc.io/velocity/3.0.0/"
        )
    }
}

publishing {
    publications {
        create<MavenPublication>("mavenJava") {
            repositories {
                maven {
                    name = "skyblocksquad"
                    url = uri("https://repo.skyblocksquad.de/repo")
                    credentials(PasswordCredentials::class)
                    authentication {
                        create<BasicAuthentication>("basic")
                    }
                }
            }
            from(components["java"])
            pom {
                url.set("https://github.com/4drian3d/VPacketEvents")
                licenses {
                    license {
                        name.set("GNU General Public License version 3 or later")
                        url.set("https://opensource.org/licenses/GPL-3.0")
                    }
                }
                scm {
                    connection.set("scm:git:https://github.com/4drian3d/VPacketEvents.git")
                    developerConnection.set("scm:git:ssh://git@github.com/4drian3d/VPacketEvents.git")
                    url.set("https://github.com/4drian3d/VPacketEvents")
                }
                developers {
                    developer {
                        id.set("4drian3d")
                        name.set("Adrian Gonzales")
                        email.set("adriangonzalesval@gmail.com")
                    }
                }
                issueManagement {
                    name.set("GitHub")
                    url.set("https://github.com/4drian3d/VPacketEvents/issues")
                }
                ciManagement {
                    name.set("GitHub Actions")
                    url.set("https://github.com/4drian3d/VPacketEvents/actions")
                }
                name.set(project.name)
                description.set(project.description)
                url.set("https://github.com/4drian3d/VPacketEvents")
            }
        }
    }
}

signing {
    useGpgCmd()
    sign(configurations.archives.get())
    sign(publishing.publications["mavenJava"])
}