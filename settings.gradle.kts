plugins {
    id("org.gradle.toolchains.foojay-resolver-convention") version "0.5.0"
}
rootProject.name = "hello-chat-system"
include("hello-chat-server")
include("hello-chat-client")
