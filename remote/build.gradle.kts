plugins {
    id("kotlin")
}

java {
    sourceCompatibility = JavaVersion.VERSION_1_8
    targetCompatibility = JavaVersion.VERSION_1_8
}

dependencies {
    implementation(project(":data"))

    implementation(RemoteDependencies.kotlin)
    rx()
    implementation(RemoteDependencies.gson)
    implementation(RemoteDependencies.okHttp)
    implementation(RemoteDependencies.okHttpLogger)
    implementation(RemoteDependencies.retrofit)
    implementation(RemoteDependencies.retrofitConverter)
    implementation(RemoteDependencies.retrofitAdapter)

    unitTesting()
}
