package dependencies

object versions {

    val appcompat = "1.0.0"
    val ktx = "1.3.0"
    val material = "1.0.0"
    val constraintlayout = "1.1.3"
    val recyclerview = "1.0.0"
    val cardview = "1.0.0"
    val dagger = "2.28.2"
    val rxjava = "2.2.19"
    val rxandroid = "2.1.1"
    val paperparcel = "2.0.4"
    val retrofit = "2.6.2"
    val okhttp = "3.14.4"
    val picasso = "2.71828"

    // versions for build dependencies
    val kotlin = "1.3.72"
    val gradleplugin = "3.6.3"

    // versions for testing dependencies
    val junit = "4.12"
    val androidxjunit = "1.1.1"
    val mockito = "2.28.1"
    val mockitokotlin = "2.1.0"
    val espresso = "3.2.0"
}

object libs {

    object build {
        val gradleplugin = "com.android.tools.build:gradle:${versions.gradleplugin}"

        object kotlin {
            val kotlingradleplugin = "org.jetbrains.kotlin:kotlin-gradle-plugin:${versions.kotlin}"
        }
    }

    object kotlin {
        val kotlinstdlib = "org.jetbrains.kotlin:kotlin-stdlib:${versions.kotlin}"
        val javastdlib = "org.jetbrains.kotlin:kotlin-stdlib-jdk7:${versions.kotlin}"
    }

    object retrofit {
        val retrofit = "com.squareup.retrofit2:retrofit:${versions.retrofit}"
        val gson = "com.squareup.retrofit2:converter-gson:${versions.retrofit}"
        val adapterrxjava2 = "com.squareup.retrofit2:adapter-rxjava2:${versions.retrofit}"
    }

    object okhttp {
        val okhttp = "com.squareup.okhttp3:okhttp:${versions.okhttp}"
    }

    object picasso {
        val picasso = "com.squareup.picasso:picasso:${versions.picasso}"
    }

    object dagger {
        val compiler = "com.google.dagger:dagger-compiler:${versions.dagger}"
        val androidsupport = "com.google.dagger:dagger-android-support:${versions.dagger}"
        val androidprocessor = "com.google.dagger:dagger-android-processor:${versions.dagger}"
    }

    object paperparcel {
        val runtime = "nz.bradcampbell:paperparcel:${versions.paperparcel}"
        val compiler = "nz.bradcampbell:paperparcel-compiler:${versions.paperparcel}"
    }

    object rx {
        val rxjava = "io.reactivex.rxjava2:rxjava:${versions.rxjava}"
        val rxandroid = "io.reactivex.rxjava2:rxandroid:${versions.rxandroid}"
    }

    object androidx {
        val appcompat = "androidx.appcompat:appcompat:${versions.appcompat}"
        val corektx = "androidx.core:core-ktx:${versions.ktx}"
        val material = "com.google.android.material:material:${versions.material}"
        val constraintlayout =
            "androidx.constraintlayout:constraintlayout:${versions.constraintlayout}"
        val recyclerview = "androidx.recyclerview:recyclerview:${versions.recyclerview}"
        val cardview = "androidx.cardview:cardview:${versions.cardview}"
    }

    object testing {
        val junit = "junit:junit:${versions.junit}"
        val androidxjunit = "androidx.test.ext:junit:${versions.androidxjunit}"

        object mockito {
            val android = "org.mockito:mockito-android:${versions.mockito}"
            val core = "org.mockito:mockito-core:${versions.mockito}"
            val inline = "org.mockito:mockito-inline:${versions.mockito}"
            val kotlin = "com.nhaarman.mockitokotlin2:mockito-kotlin:${versions.mockitokotlin}"
        }

        object espresso {
            val core = "androidx.test.espresso:espresso-core:${versions.espresso}"
        }
    }
}
