import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author jlusdy
 * 
 */
public class MoveTime {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		if (args == null || args.length != 1) {
			System.out.println("please set parameter:\"second|oldSubFile|newSubFile\"");
			return;
		}
		String parameter = args[0];
		args = parameter.split("\\|");
		Integer second = null;
		try {
			second = new Integer(args[0]);
		} catch (Exception e) {
			System.out.println("Second is wrong");
		}
		Date time = null;
		Matcher matcher = null;
		String line = null;
		String replaceTime = null;
		String originalTime = null;
		// 时间格式
		String timeFormat = "HH:mm:ss,SSS";
		// 正则时间格式
		String timeFormatRegex = "\\d\\d:\\d\\d:\\d\\d,\\d\\d\\d";
		// 读入字幕文件
		try {
			BufferedReader input = new BufferedReader(new InputStreamReader(
					new FileInputStream(args[1])));

			// 写入字幕文件
			BufferedWriter out = new BufferedWriter(new OutputStreamWriter(
					new FileOutputStream(args[2])));
			// 日历
			Calendar calendar = Calendar.getInstance();
			// 格式化器
			DateFormat dateFormat = new SimpleDateFormat(timeFormat);
			Pattern pattern = Pattern.compile(timeFormatRegex);

			while ((line = input.readLine()) != null) {
				matcher = pattern.matcher(line);
				// 找到匹配
				while (matcher.find()) {
					originalTime = matcher.group();

					time = dateFormat.parse(originalTime);
					calendar.setTime(time);
					// 秒字段 减三
					calendar.add(Calendar.SECOND, second);

					replaceTime = dateFormat.format(calendar.getTime());
					// 替换回原行
					line = line.replaceAll(originalTime, replaceTime);
					// 清空
					calendar.clear();
				}
				// 写回
				out.write(line);
				out.write("\r\n");
			}
			// 回收
			input.close();
			out.close();
			System.out.println("Move Success");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
