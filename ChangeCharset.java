import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author jlusdy
 * 
 */
public class ChangeCharset {

	/**
	 * @param args
	 * @throws Exception
	 */
	public static void main(String[] args) {
		if (args == null || args.length != 1) {
			System.out.println("please set parameter:\"cnSubfile|Utf-8File\"");
			return;
		}
		String parameter = args[0];
		args = parameter.split("\\|");

		if(args.length != 2){
			System.out.println("please set parameter:\"cnSubfile|Utf-8File\"");
			return;
		}
		
		try {
			String cnLine = null;

			// reader sub
			BufferedReader inputCnSub = new BufferedReader(
					new InputStreamReader(new FileInputStream(args[0]),"GB2312"));

			BufferedWriter out = new BufferedWriter(new OutputStreamWriter(
					new FileOutputStream(args[1]),"UTF-8"));
			while ((cnLine = inputCnSub.readLine()) != null) {
					// write
					out.write(cnLine);
					out.write("\r\n");
			}
			// gb
			inputCnSub.close();
			out.close();
			System.out.println("Merge Success");
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
