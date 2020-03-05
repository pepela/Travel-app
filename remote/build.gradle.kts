plugins {
    id("kotlin")
}

java {
    sourceCompatibility = JavaVersion.VERSION_1_7
    targetCompatibility = JavaVersion.VERSION_1_7
}

dependencies {
    implementation(project(":data"))

    implementation(RemoteDependencies.kotlin)
    implementation(RemoteDependencies.rxJava)
    implementation(RemoteDependencies.rxKotlin)
    implementation(RemoteDependencies.gson)
    implementation(RemoteDependencies.okHttp)
    implementation(RemoteDependencies.okHttpLogger)
    implementation(RemoteDependencies.retrofit)
    implementation(RemoteDependencies.retrofitConverter)
    implementation(RemoteDependencies.retrofitAdapter)

    testImplementation(RemoteTestDependencies.junit)
    testImplementation(RemoteTestDependencies.kotlinJUnit)
    testImplementation(RemoteTestDependencies.mockito)
    testImplementation(RemoteTestDependencies.assertj)
}
