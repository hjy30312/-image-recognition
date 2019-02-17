import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.videoio.VideoCapture;

/**
 * @author hjy
 * @create 2019/01/06
 **/
public class Main {
	static {
		System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
	}
	public static void main(String args[]) throws Exception {
		//界面类初始化
		Frame frame = new Frame();
		//识别类初始化
		Distinguish distinguish = new Distinguish();
		VideoCapture capture = new VideoCapture();

		//1.显示
		frame.shows();

		while(true) {
			//2.拍照  其中参数为拍照电脑确定
			Mat mat = distinguish.getVideoCaptureMat(Const.HOST_SCREEN_WIDTH, Const.HOST_SCREEN_HIGHT, capture);
			//3.处理照片  返回 长宽百分比
			Point point = distinguish.getPosition(mat);
			//4.长宽百分比  返回对应键值  （完成,未测试)
			String ans = ReturnChar.getKey(point.x,point.y);
			//5.通过机器学习 返回预测键   （完成，但未整合测试）
			Learn learn = new Learn("C:\\Users\\Administrator\\Desktop\\learn\\learn\\training.arff","C:\\Users\\Administrator\\Desktop\\learn\\learn\\test.arff");
			String key = learn.getKey(point.x,point.y);
			if(1 == 1) {
				//6.成功，更新界面 显示键值  失败则跳过 继续循环
				System.out.println("ans:" + ans);
				System.out.println("key:" + key);
			}
		}
	}
}
