plugins {
    id("java")
}

group = "com.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    // Dipendenze per Google API Client
    implementation("com.google.api-client:google-api-client:1.32.1")
    implementation("com.google.apis:google-api-services-gmail:v1-rev110-1.25.0")

    // Dipendenze per autenticazione OAuth2
    implementation("com.google.oauth-client:google-oauth-client:1.32.1")
    implementation("com.google.oauth-client:google-oauth-client-jetty:1.32.1")
    implementation("com.google.http-client:google-http-client-jackson2:1.39.2")

    // Dipendenze JavaMail per creare l'email
    implementation("com.sun.mail:javax.mail:1.6.2")

    // Dipendenze per Java Activation Framework
    implementation("javax.activation:javax.activation-api:1.2.0") // Assicurati di utilizzare questa versione

    // Per la gestione delle eccezioni e il logging (opzionale)
    implementation("org.slf4j:slf4j-api:1.7.30")
    implementation("org.slf4j:slf4j-simple:1.7.30")

    // JUnit per i test (opzionale)
    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")
}


tasks.test {
    useJUnitPlatform()
}
