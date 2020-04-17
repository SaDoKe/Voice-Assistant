package org.sadoke.util.general;

import java.io.File;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.apache.commons.io.FileUtils;

public class FileUtil {

	public static final String SYSTEM_PATH_SEPERATOR = "//";
	public Collection<File> getFiles(List<String> path) {

		String s = "";
		for (final String p : path)
			s += FileUtil.SYSTEM_PATH_SEPERATOR + p;
		final File f = new File(s);

		if (f.isDirectory())
			return FileUtils.listFiles(f, null, null);

		return null;
	}
	public Collection<File> getFiles(String path) {

		return null;
	}
	public List<String> normalizePath(String path, String seperator) {
		return Arrays.asList(path.split(seperator));
	}
}
