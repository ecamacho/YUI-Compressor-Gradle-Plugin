package com.tidyslice

import org.junit.Test
import org.gradle.api.Project
import static org.junit.Assert.*
import org.gradle.testfixtures.ProjectBuilder

class YUICompressorPluginTest {

    @Test
    public void yuiCompressorPluginAddsTaskToProject() {
        Project project = ProjectBuilder.builder().build()
        project.apply plugin: 'yuicompressor'

        assertTrue(project.tasks.compress instanceof YUICompressorTask)
    }
}
