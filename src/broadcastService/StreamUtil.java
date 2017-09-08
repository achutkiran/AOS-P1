package broadcastService;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class StreamUtil {
	
	public static String readLine(InputStream inputStream) throws IOException
	{
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		int ch;
		while((ch = inputStream.read()) != '\n') {
			baos.write(ch);
		}
		
		String result = baos.toString();
		return result;
	}
	
	public static void writeLine(String line, OutputStream outputStream) throws IOException
	{
		if(!line.endsWith("\n")) {
			line = line + "\n";
		}
		outputStream.write(line.getBytes());
		outputStream.flush();
	}
	
	

}
