import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
	id("org.springframework.boot") version "3.1.5"
	id("io.spring.dependency-management") version "1.1.3"
	id("org.jmailen.kotlinter") version "4.0.0"
	kotlin("jvm") version "1.9.20-Beta2"
	kotlin("plugin.spring") version "1.8.22"
}

group = "com.sopp"
version = "0.0.1-SNAPSHOT"

java {
	sourceCompatibility = JavaVersion.VERSION_21
}

repositories {
	mavenCentral()
	maven("https://repo.spring.io/milestone")
}

dependencies {
	// Spring
	implementation("org.springframework.boot:spring-boot-starter")
	implementation("org.springframework.boot:spring-boot-starter-webflux")
	implementation("org.springframework.boot:spring-boot-starter-actuator")

	// Security
	implementation("org.springframework.boot:spring-boot-starter-oauth2-resource-server")
	//implementation ("org.springframework.security:spring-security-web:5.7.8")
	//implementation("org.springframework.boot:spring-boot-starter-security")



	//kotlin
	implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
	implementation("io.projectreactor.kotlin:reactor-kotlin-extensions")
	implementation("org.jetbrains.kotlin:kotlin-reflect")
	implementation("org.jetbrains.kotlinx:kotlinx-coroutines-reactor")
	implementation("com.google.firebase:firebase-admin:9.1.1")

	testImplementation("org.springframework.boot:spring-boot-starter-test")
	testImplementation("io.projectreactor:reactor-test")

	//database
	runtimeOnly("org.postgresql:postgresql")
	implementation("org.springframework.boot:spring-boot-starter-data-jpa")

	//compileOnly("javax.servlet:javax.servlet-api:4.0.1")
}

tasks.withType<KotlinCompile> {
	kotlinOptions {
		freeCompilerArgs += "-Xjsr305=strict"
		jvmTarget = "21"
	}
}

tasks.withType<Test> {
	useJUnitPlatform()
}
