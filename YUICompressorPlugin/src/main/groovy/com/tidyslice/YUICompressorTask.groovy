package com.tidyslice

import org.gradle.api.DefaultTask
import org.gradle.api.tasks.TaskAction
import org.gradle.api.file.FileCollection
import org.gradle.api.InvalidUserDataException
import org.gradle.api.tasks.*
import com.yahoo.platform.yui.compressor.CssCompressor
import com.yahoo.platform.yui.compressor.JavaScriptCompressor


class YUICompressorTask extends DefaultTask {
	
	FileCollection files
	int lineBreak = -1
	boolean munge
	boolean preserveAllSemiColons
    boolean disableOptimizations
    boolean verbose
    String javascriptDestDir = '.'
	String cssDestDir = '.'

	final String CSS_FILE_EXTENSION = '.css';
	final String JAVASCRIPT_FILE_EXTENSION = '.js';
	

	String description = 'Compress Javascript and CSS files using YUICompressor'

	@TaskAction
	void compressFiles( ) {
		validateFiles( ) 
		files.each { File file ->		    
			logger.debug "file ${file.absolutePath} size ${file.size()}"			
			if( file.name.endsWith( JAVASCRIPT_FILE_EXTENSION ) )
				compressJsFile( file, new File( generateDestinationFileName( javascriptDestDir, file ) ) )
			else if( file.name.endsWith( CSS_FILE_EXTENSION ) )
				compressCssFile( file, new File( generateDestinationFileName( cssDestDir, file ) ) )
			logger.debug 'file compressed'
		}
	}

	
	String generateDestinationFileName( outputDir, sourceFile ) {
		String fileNameWithoutExtension = sourceFile.name.lastIndexOf( '.' ).with { 
											it != -1 ? sourceFile.name[0..<it] : sourceFile.name 
											}
		String fileExtension = sourceFile.name.lastIndexOf( '.' ).with { 
											it != -1 ? sourceFile.name[it..sourceFile.name.length() - 1] : '' 
											}
		println "fileExtension: $fileExtension"
		def fileName = "$outputDir/${fileNameWithoutExtension}.min${fileExtension}"		
		println fileName
		fileName
	}
	
	void compressJsFile( file, destinationFile ) {			
		file.withReader{ reader -> 
			JavaScriptCompressor compressor = new JavaScriptCompressor( reader, null )
			destinationFile.withWriter{ writer ->
				compressor.compress( writer, lineBreak, munge, verbose, preserveAllSemiColons, disableOptimizations )
			}
		}
	}
	
	void compressCssFile( file, destinationFile ) {			
		file.withReader{ reader -> 
			CssCompressor compressor = new CssCompressor( reader )
			destinationFile.withWriter{ writer ->
				compressor.compress( writer, lineBreak )
			}
		}
	}
	
	void validateFiles( ) {
		if( files == null )
			throw new RuntimeException('Error compressing files. You must configure the files to compress using the attribute files')
	}
	
}

