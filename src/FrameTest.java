



import java.awt.Graphics;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;

import org.opencv.core.Core;
import org.opencv.core.Mat;



public class FrameTest {
	static {
		System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
	}
/*
	public static void main(String arg[]) {
		JFrame frame1 = new JFrame("WebCamera");
		frame1.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame1.setSize(640, 480);
		frame1.setBounds(0, 0, frame1.getWidth(), frame1.getHeight());
		Panel panel1 = new Panel();
		frame1.setContentPane(panel1);
		frame1.setVisible(true);
		
		// 讀入webcam影像
		VideoCapture capture = new VideoCapture();

		capture.open(0);
		capture.set(4, 1280);
		capture.set(3, 720);
		Mat webcam_image = new Mat();
		capture.read(webcam_image);
		
		frame1.setSize(webcam_image.width() + 40, webcam_image.height() + 60);
		
		if (capture.isOpened()) {
			while (true) {
				capture.read(webcam_image);
				if (!webcam_image.empty()) {
					
					Core.flip(webcam_image, webcam_image, 1);			// 翻转
					Point p = Distinguish.getPosition(webcam_image);	// 获取坐标
					
					System.out.println("x = " + p.x + "y = " + p.y);
					
					// 显示
					Imgproc.drawMarker(webcam_image, p, new Scalar(0, 0, 255, 0), Imgproc.MARKER_CROSS , 1, 8, 20);	// 标记指尖
					panel1.setimagewithMat(webcam_image);
					frame1.repaint();
					
					double max_y = webcam_image.rows(); // 720
			        double max_x = webcam_image.cols(); // 1280
			        p.x = p.x/max_x;
			        p.y = p.y/max_y;					// 计算百分比
					System.out.println("d_x " + p.x + "d_y " + p.y);
					
				} else {
					System.out.println(" Webcam沒有影像-異常!");
					break;
				}
			}
		}
		return;
	}
	*/
}
