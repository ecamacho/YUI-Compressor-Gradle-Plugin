package com.tidyslice

import org.gradle.api.Project
import org.gradle.api.Plugin


class YUICompressorPlugin implements Plugin<Project> {
    void apply(Project target) {
		
        target.task('compress', type: YUICompressorTask)
    }
}

