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
public class SubtitleMerge {

	public static void main(String[] args) {
		if (args == null || args.length != 1) {
			System.out.println("please set parameter:\"cnSubfile|enSubFile|MergeFile\"");
			return;
		}
		String parameter = args[0];
		args = parameter.split("\\|");

		if(args.length != 3){
			System.out.println("please set parameter:\"cnSubfile|enSubFile|MergeFile\"");
			return;
		}
		
		try {
			Matcher matcher = null;
			String cnLine = null;
			String enLine = null;

			// HH:mm:ss,SSS
			String timeFormatRegex = "\\d\\d:\\d\\d:\\d\\d,\\d\\d\\d";
			Pattern pattern = Pattern.compile(timeFormatRegex);

			// reader sub
			BufferedReader inputCnSub = new BufferedReader(
					new InputStreamReader(new FileInputStream(args[0])));
			// reader eng sub
			BufferedReader inputEnSub = new BufferedReader(
					new InputStreamReader(new FileInputStream(args[1])));

			Map<String, String> map = new HashMap<String, String>();
			String Key = "";
			String Value = "";

			while ((enLine = inputEnSub.readLine()) != null) {
				if (enLine.length() == 0) {
					continue;
				}

				matcher = pattern.matcher(enLine);
				// find
				if (matcher.find()) {
					if (Key.length() > 0) {
						map.put(Key, Value);
						Key = "";
						Value = "";

					}
					Key = enLine;

				} else {
					try {
						new Integer(enLine);
					} catch (Exception e) {
						if (Value.length() > 0) {
							Value += ("\r\n" + enLine);
						} else {
							Value = enLine;
						}
					}
				}
			}
			map.put(Key, Value);

			BufferedWriter out = new BufferedWriter(new OutputStreamWriter(
					new FileOutputStream(args[2])));
			while ((cnLine = inputCnSub.readLine()) != null) {
				matcher = pattern.matcher(cnLine);
				// find
				if (matcher.find()) {
					// write
					out.write(cnLine);
					out.write("\r\n");

					if (map.get(cnLine) != null) {
						out.write(map.get(cnLine));
						out.write("\r\n");

					}
				} else {
					// write
					out.write(cnLine);
					out.write("\r\n");
				}

			}
			// gb
			inputCnSub.close();
			inputEnSub.close();
			out.close();
			System.out.println("Merge Success");
		} catch (Exception e1) {
			e1.printStackTrace();
		}

	}

}
