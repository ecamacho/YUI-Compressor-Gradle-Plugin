package com.tidyslice

import org.junit.Test
import org.junit.Before
import org.gradle.api.Project
import static org.junit.Assert.*
import org.gradle.testfixtures.ProjectBuilder

class YUICompressorTaskTest {

	Project project 
	def compressTask
	File sourceCSSFile
	File sourceJavascriptFile	
	String javascriptTestFileName = 'jquery-1.6.1.js'	
	String javascriptTestMinimizedFileName = 'jquery-1.6.1.min.js'
	String cssTestFileName = "jquery.mobile.theme.css"
	String cssTestMinimizedFileName = "jquery.mobile.theme.min.css"
	
	@Before
	void setup( ) {
		project      = ProjectBuilder.builder().build()
        compressTask = project.task( 'yuicompressor', type: YUICompressorTask )
		sourceJavascriptFile = new File( YUICompressorTaskTest.class.getResource( javascriptTestFileName ).toURI( ) ) 		
		sourceCSSFile = new File( YUICompressorTaskTest.class.getResource( cssTestFileName ).toURI( ) ) 		
	}
	
	@Test
    void canAddTaskToProject() {        
        assertTrue( compressTask instanceof YUICompressorTask )
    }

	@Test
	void canCompressJavascriptFiles( ) {						
		copyJavascriptFileToProjectDir( project )
		compressTask.files   = project.files( javascriptTestFileName )		
		compressTask.javascriptDestDir = new File(".").absolutePath
		compressTask.compressFiles()
		assertTrue( sourceJavascriptFile.size( ) > new File( javascriptTestMinimizedFileName ).size( ) )
	}

	@Test
	void canCompressCSSFiles( ) {						
		copyCSSFileToProjectDir( project )
		compressTask.files   = project.files( cssTestFileName )		
		compressTask.cssDestDir = new File(".").absolutePath
		compressTask.compressFiles()
		assertTrue( sourceCSSFile.size( ) > new File( cssTestMinimizedFileName ).size( ) )
	}

	
	private void copyJavascriptFileToProjectDir( Project project ) {		
		File destinationFile = project.file( javascriptTestFileName )
		destinationFile.write( sourceJavascriptFile.text )
	}
	
	private void copyCSSFileToProjectDir( Project project ) {				
		File destinationFile = project.file( cssTestFileName )
		destinationFile.write( sourceCSSFile.text )
	}
	
	@Before
	void tearDown() {
		new File( javascriptTestMinimizedFileName ).delete( )
		new File( cssTestMinimizedFileName ).delete( )
	}
	 

}
