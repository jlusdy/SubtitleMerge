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
		// ʱ���ʽ
		String timeFormat = "HH:mm:ss,SSS";
		// ����ʱ���ʽ
		String timeFormatRegex = "\\d\\d:\\d\\d:\\d\\d,\\d\\d\\d";
		// ������Ļ�ļ�
		try {
			BufferedReader input = new BufferedReader(new InputStreamReader(
					new FileInputStream(args[1])));

			// д����Ļ�ļ�
			BufferedWriter out = new BufferedWriter(new OutputStreamWriter(
					new FileOutputStream(args[2])));
			// ����
			Calendar calendar = Calendar.getInstance();
			// ��ʽ����
			DateFormat dateFormat = new SimpleDateFormat(timeFormat);
			Pattern pattern = Pattern.compile(timeFormatRegex);

			while ((line = input.readLine()) != null) {
				matcher = pattern.matcher(line);
				// �ҵ�ƥ��
				while (matcher.find()) {
					originalTime = matcher.group();

					time = dateFormat.parse(originalTime);
					calendar.setTime(time);
					// ���ֶ� ����
					calendar.add(Calendar.SECOND, second);

					replaceTime = dateFormat.format(calendar.getTime());
					// �滻��ԭ��
					line = line.replaceAll(originalTime, replaceTime);
					// ���
					calendar.clear();
				}
				// д��
				out.write(line);
				out.write("\r\n");
			}
			// ����
			input.close();
			out.close();
			System.out.println("Move Success");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
