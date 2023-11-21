
import org.gradle.api.tasks.testing.logging.TestExceptionFormat.FULL
import org.gradle.api.tasks.testing.logging.TestLogEvent.*
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
	id("org.springframework.boot") version "3.1.5"
	id("io.spring.dependency-management") version "1.1.3"
	kotlin("jvm") version "1.8.22"
	kotlin("plugin.spring") version "1.8.22"
	id("io.gitlab.arturbosch.detekt") version "1.22.0"
	jacoco
}

group = "com.unburden"
version = "0.0.1-SNAPSHOT"

java {
	sourceCompatibility = JavaVersion.VERSION_17
}

repositories {
	mavenCentral()
}

dependencies {
	implementation("org.springframework.boot:spring-boot-starter")
	implementation("org.jetbrains.kotlin:kotlin-reflect")
	implementation("org.springframework.boot:spring-boot-starter-web:3.1.5")

	implementation("javax.inject:javax.inject:1")
	implementation("jakarta.ws.rs:jakarta.ws.rs-api:3.1.0")
	implementation("org.glassfish.jersey.inject:jersey-hk2:3.1.3")

	// swagger
	implementation("org.springdoc:springdoc-openapi-starter-webmvc-ui:2.2.0")

	//mongo
	implementation("org.springframework.boot:spring-boot-starter-data-mongodb:3.1.5")
	testImplementation("de.bwaldvogel:mongo-java-server:1.44.0")
	testImplementation("de.flapdoodle.embed:de.flapdoodle.embed.mongo:4.9.2")

	//jwt
	implementation("com.auth0:java-jwt:4.3.0")

	compileOnly("javax.servlet:servlet-api:2.5")

	// tests
	testImplementation("io.cucumber:cucumber-java:7.14.0")
	testImplementation("io.cucumber:cucumber-junit:7.14.0")
	testImplementation("io.rest-assured:rest-assured:5.3.2")
	testImplementation("io.cucumber:cucumber-picocontainer:7.14.0")
	testImplementation("io.cucumber:cucumber-junit:7.14.0")
	testImplementation("org.springframework.boot:spring-boot-starter-test")
	testImplementation("com.jayway.jsonpath:json-path-assert:2.8.0")

	// #devtool
	developmentOnly("org.springframework.boot:spring-boot-devtools:3.0.4")
}

tasks.withType<KotlinCompile> {
	kotlinOptions {
		freeCompilerArgs += "-Xjsr305=strict"
		jvmTarget = "17"
	}
}

tasks.withType<Test> {
	useJUnitPlatform()
}

tasks.withType<KotlinCompile> {
	kotlinOptions {
		freeCompilerArgs = listOf("-Xjsr305=strict")
		jvmTarget = "17"
	}
}

tasks.withType<Test> {
	useJUnitPlatform()
	testLogging {
		events(FAILED, STANDARD_ERROR, SKIPPED, PASSED)
		exceptionFormat = FULL
	}
}

tasks.test {
	finalizedBy(tasks.jacocoTestReport)
}
tasks.jacocoTestReport {
	dependsOn(tasks.test)
	finalizedBy(tasks.jacocoTestCoverageVerification)
}

jacoco {
	toolVersion = "0.8.7"
	reportsDirectory.set(layout.buildDirectory.dir("$buildDir/reports/jacoco"))
}

tasks.jacocoTestReport {
	reports {
		xml.required.set(false)
		csv.required.set(false)
		html.outputLocation.set(layout.buildDirectory.dir("jacocoHtml"))
	}
}

tasks.jacocoTestCoverageVerification {
	violationRules {
		rule {
			limit {
				minimum = "0.1".toBigDecimal()
			}
		}

		rule {
			isEnabled = false
			element = "CLASS"
			includes = listOf("org.gradle.*")

			limit {
				counter = "LINE"
				value = "TOTALCOUNT"
				maximum = "0.1".toBigDecimal()
			}
		}
	}
}

detekt {
	buildUponDefaultConfig = true // preconfigure defaults
	allRules = false // activate all available (even unstable) rules.
	config = files("$projectDir/config/detekt.yml") // point to your custom config defining rules to run, overwriting default behavior
}

tasks.withType<io.gitlab.arturbosch.detekt.Detekt>().configureEach {
	jvmTarget = JavaVersion.VERSION_17.toString()
}

tasks.withType<io.gitlab.arturbosch.detekt.DetektCreateBaselineTask>().configureEach {
	jvmTarget = JavaVersion.VERSION_17.toString()
}

