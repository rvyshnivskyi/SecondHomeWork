package com.playtika.file.reader;

import com.playtika.text.analyzer.Text;

import java.io.*;
import java.nio.charset.StandardCharsets;
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
	private final File startDirectory;

	public FileReader(String startDirectoryPath) throws FileNotFoundException, NotDirectoryException {
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
			fileReader.copyFile(new File("text.txt"));
//			Map<File, Map<String, Integer>> wordFrequenciesForFiles = fileReader.getWordFrequenciesForFiles();
//			fileReader.getAllIncludedFiles().forEach(FileReader::printFileInformation);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (NotDirectoryException e) {
			e.printStackTrace();
		} catch (IOException e) {
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
						fileStringsMap.put(file, new String(Files.readAllBytes(file.toPath()), StandardCharsets.UTF_8));
					} catch (IOException e) {
						e.printStackTrace();
					}
				});
		return fileStringsMap;
	}

	public void copyFile(File file) throws IOException {
		File newFile = new File(file.getPath().replaceAll("(?s)(\\.)(?!.*?(\\.))", "New."));
		try (InputStream inputStream = new BufferedInputStream(new FileInputStream(file)); OutputStream outputStream = new BufferedOutputStream(new FileOutputStream(newFile))) {
			byte[] buffer = new byte[1024];
			int length;
			while ((length = inputStream.read(buffer)) > 0) {
				outputStream.write(buffer, 0, length);
			}
		}
	}

	private List<File> getAllIncludedFiles() {
		return Arrays.asList(startDirectory.listFiles(File::isFile));
	}
}
