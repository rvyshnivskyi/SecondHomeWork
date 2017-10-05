package com.playtika.file.reader;

import com.playtika.text.analyzer.Text;

import java.io.*;
import java.nio.file.NotDirectoryException;
import java.nio.file.attribute.BasicFileAttributes;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.nio.charset.StandardCharsets.UTF_8;
import static java.nio.file.Files.readAllBytes;
import static java.nio.file.Files.readAttributes;
import static java.util.Arrays.asList;

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

	public static void main(String[] args) throws IOException {
		FileReader.copyFile(new File("text.txt"));
		Map<String, Integer> wordFrequencies = new FileReader("resources/text").getWordFrequenciesForFiles();
		System.out.println(wordFrequencies);
	}

	public Map<String, Integer> getWordFrequenciesForFiles() {
		Map<String, Integer> wordFrequencies = new HashMap<>();
		List<File> files = getAllIncludedFiles();
		files.forEach(file -> {
			try {
				printFileInformation(file);
				new Text(getStringFromFile(file)).getWordFrequencies()
						.forEach((word, frequencies) -> wordFrequencies.merge(word, frequencies, Integer::sum));
			} catch (IOException e) {
				e.printStackTrace();
			}
		});
		return wordFrequencies;
	}

	public static void printFileInformation(File file) throws IOException {
		BasicFileAttributes fileAttributes = readAttributes(file.toPath(), BasicFileAttributes.class);
		System.out.printf(
				"File path: %s | size: %d Bytes | creation date: %s%n",
				file.getAbsolutePath(),
				file.length(),
				new SimpleDateFormat("dd-MM-yyyy HH:mm").format(fileAttributes.creationTime().toMillis()));
	}

	public static void copyFile(File file) throws IOException {
		File newFile = new File(file.getPath().replaceAll("(?s)(\\.)(?!.*?(\\.))", "New."));
		if (newFile.exists()) {
			newFile.delete();
		}
		try (InputStream in = new BufferedInputStream(new FileInputStream(file));
			 OutputStream out = new BufferedOutputStream(new FileOutputStream(newFile))) {
			byte[] buffer = new byte[1024];
			int length;
			while ((length = in.read(buffer)) > 0) {
				out.write(buffer, 0, length);
			}
		}
	}

	private String getStringFromFile(File file) throws IOException {
		return new String(new String(readAllBytes(file.toPath()), UTF_8));
	}

	private List<File> getAllIncludedFiles() {
		return asList(startDirectory.listFiles(File::isFile));
	}
}
