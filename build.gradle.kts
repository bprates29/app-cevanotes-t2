plugins {
    id("java")
}

group = "br.com.cevanotes"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation("io.javalin:javalin:6.6.0")
    implementation("com.h2database:h2:2.2.224")
    implementation("com.zaxxer:HikariCP:5.1.0") //conexão h2 otimizada
    implementation("com.fasterxml.jackson.core:jackson-databind:2.17.0")
    implementation("com.fasterxml.jackson.datatype:jackson-datatype-jsr310:2.14.0")
    implementation("org.slf4j:slf4j-simple:2.0.9")
    implementation("org.jdbi:jdbi3-core:3.43.0")
    implementation("org.jdbi:jdbi3-sqlobject:3.43.0")

    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")
}

tasks.test {
    useJUnitPlatform()
}