buildscript {
    repositories {
        mavenRepo urls: uri('repo')
    }
    dependencies {
        classpath 'com.tidyslice:YUICompressorPlugin:0.1-SNAPSHOT'
    }
}

apply plugin: 'yuicompressor'

compress  {
	files = files( 'aaaa', 'aaa' ) 
}