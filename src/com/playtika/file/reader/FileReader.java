package com.playtika.file.reader;

import com.playtika.text.analyzer.Text;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.NotDirectoryException;
import java.nio.file.attribute.BasicFileAttributes;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.nio.file.Files.readAttributes;

public class FileReader {
	private final String startDirectoryPath;
	private final File startDirectory;

	public FileReader(String startDirectoryPath) throws FileNotFoundException, NotDirectoryException {
		this.startDirectoryPath = startDirectoryPath;
		startDirectory = new File(startDirectoryPath);
		if(!startDirectory.exists()) {
			throw new FileNotFoundException("File with next path doesn't exist: [" + startDirectoryPath + "]");
		}
		if(!startDirectory.isDirectory()) {
			throw new NotDirectoryException(startDirectory.getAbsolutePath());
		}
	}

	public static void main(String[] args) {
		try {
			FileReader fileReader = new FileReader("C:\\Users\\rvyshnivskyi\\Google Drive\\old_pc\\Docs");
			Map<File, Map<String, Integer>> wordFrequenciesForFiles = fileReader.getWordFrequenciesForFiles();
			fileReader.getAllIncludedFiles().forEach(FileReader::printFileInformation);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (NotDirectoryException e) {
			e.printStackTrace();
		}
	}

	public static void printFileInformation(File file) {
		BasicFileAttributes fileAttributes = null;
		try {
			fileAttributes = readAttributes(file.toPath(), BasicFileAttributes.class);
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.printf(
				"File path: %s | size: %d Bytes | creation date: %s%n",
				file.getPath(),
				file.length(),
				new SimpleDateFormat("dd-MM-yyyy HH:mm").format(fileAttributes.creationTime().toMillis()));
	}

	public Map<File, Map<String, Integer>> getWordFrequenciesForFiles() {
		Map<File, Map<String, Integer>> filesWordFrequencies = new HashMap<>();
		getAllFilesTextAsString().forEach((file, fileText) -> filesWordFrequencies.put(file, new Text(fileText).getWordFrequencies()));
		return filesWordFrequencies;
	}

	public Map<File, String> getAllFilesTextAsString() {
		Map<File, String> fileStringsMap = new HashMap<>();
		getAllIncludedFiles().forEach(
				(file) -> {
					try {
						fileStringsMap.put(file, new String(Files.readAllBytes(file.toPath()), "UTF-8"));
					} catch (IOException e) {
						e.printStackTrace();
					}
				});
		return fileStringsMap;
	}

	private List<File> getAllIncludedFiles() {
		return Arrays.asList(startDirectory.listFiles(File::isFile));
	}
}
