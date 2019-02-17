
import javax.swing.text.JTextComponent;
import java.io.PrintStream;
import java.io.OutputStream;
import javax.swing.SwingUtilities;
/**
 * @author hjy
 * @create 2019/01/07
 **/
public class MyPrintStream extends PrintStream {
	private JTextComponent text;
	private StringBuffer sb = new StringBuffer();

	public MyPrintStream(OutputStream out, JTextComponent text) {
		super(out);
		this.text = text;
	}
	/**
	 * 在这里重截,所有的打印方法都要调用的方法
	 */
	public void write(byte[] buf, int off, int len) {
		final String message = new String(buf,off,len);
		SwingUtilities.invokeLater(new Runnable() {
			/*可以从任何线程中调用invokeLater来请求事件分发线程以运行某段代码。你必须将这段代码放入一个Runnable对象的run方法中，
			并将该指定Runnable对象作为参数传递给invokeLater。 invokeLater函数会立即返回，不会等到事件分发线程执行完这段代码*/
			public void run() {
				sb.append(message);
				text.setText(sb.toString());   //在这里把信息添加到组件中
			}
		});
	}
}
