package utility;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

public class Compressor {

	public static byte[] compress(byte[] bytes) throws IOException {
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		GZIPOutputStream gzip = new GZIPOutputStream(out);
		gzip.write(bytes);
		gzip.flush();
		gzip.close();
		return out.toByteArray();
	}

	public static byte[] decompress(byte[] bytes) throws IOException {
		ByteArrayInputStream in = new ByteArrayInputStream(bytes);
		GZIPInputStream gzip = new GZIPInputStream(in);
		byte[] buffer = new byte[1024];
		int read;
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		while((read = gzip.read(buffer, 0, 1024)) != -1){
			if(read > 0)
				out.write(buffer, 0, read);
		}
		out.flush();
		out.close();
		return out.toByteArray();
	}
}
