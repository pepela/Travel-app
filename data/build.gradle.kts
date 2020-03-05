plugins {
    id("kotlin")
}

java {
    sourceCompatibility = JavaVersion.VERSION_1_7
    targetCompatibility = JavaVersion.VERSION_1_7
}

dependencies {
    implementation(DataDependencies.kotlin)
    implementation(DataDependencies.rxJava)
    implementation(DataDependencies.rxKotlin)

    testImplementation(DataTestDependencies.junit)
    testImplementation(DataTestDependencies.kotlinJUnit)
    testImplementation(DataTestDependencies.mockito)
    testImplementation(DataTestDependencies.assertj)
}
