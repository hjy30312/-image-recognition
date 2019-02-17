

import java.util.List;

import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.MatOfPoint;
import org.opencv.core.Point;
import org.opencv.core.Scalar;
import org.opencv.core.Size;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;
import org.opencv.videoio.VideoCapture;

/*
 *
 * 		1.预处理
 *		2.由于RGB颜色的离散性转换为HSV通道
 *		3.对HSV空间进行量化，得到2值图像，亮的部分为手的形状
 *		4.去除杂点造成的伪轮廓，留下手的真实轮廓
 *		5.面积确定轮廓
 *		5.最高点-x即为坐标
 *
 */
public class Distinguish {

	public static void main(String args[]){
		System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
		// Mat src = Imgcodecs.imread("E:\\test\\gaoyang.jpg");
		Distinguish distinguish = new Distinguish();
		//Mat src = distinguish.getVideoCaptureMat(1280, 720, );
		//Point p = distinguish.getPosition(src);
		//System.out.println("x = " + p.x + "y = " + p.y);
	}

	public  Point getPosition(Mat src) {

		Mat Preprocess_res = Handle_pre(src);					// 预处理
		Mat HSV_res = Handle_HSV(Preprocess_res);				// HSV 二值化
		// Imgcodecs.imwrite("E:\\test\\HSV_res.jpg", HSV_res);
		Mat edulcoration_res = Handle_clear(HSV_res);			// 去除杂点
		// Imgcodecs.imwrite("E:\\test\\edulcoration_res.jpg", edulcoration_res);
		Mat handle_res = Handle_2(edulcoration_res);			// 画黑边
		// Imgcodecs.imwrite("E:\\test\\handle_res.jpg", handle_res);
		Point p = new Point();
		Mat find_res = findFinger(handle_res, p);				// 找手指
		Imgcodecs.imwrite("E:\\test\\find_res.jpg", find_res);
		p = change(p);
		return p;
	}

	public static Point change(Point p){

		double max_y = 720; // 720
		double max_x = 1280; // 1280
		p.x = p.x/max_x;
		p.y = p.y/max_y;
		return p;
	}
	public  Mat getVideoCaptureMat(int w, int h, VideoCapture capture) {


		capture.open(0);
		capture.set(4, w);
		capture.set(3, h);
		Mat webcam_image = new Mat();
		capture.read(webcam_image);
		if(!webcam_image.empty()) {
			Core.flip(webcam_image, webcam_image, 1);			// 翻转
			return webcam_image;
		}
		return null;
	}


	public static Mat findFinger(Mat src3, Point p) {
		Mat dst = src3.clone();
		Imgproc.cvtColor(dst, dst, Imgproc.COLOR_BGRA2GRAY);
		Imgproc.adaptiveThreshold(dst, dst, 255, Imgproc.ADAPTIVE_THRESH_MEAN_C, Imgproc.THRESH_BINARY_INV, 3, 3);

		java.util.List<MatOfPoint> contours = new java.util.ArrayList<MatOfPoint>();
		Mat hierarchy = new Mat();
		Imgproc.findContours(dst, contours, hierarchy, Imgproc.RETR_EXTERNAL, Imgproc.CHAIN_APPROX_SIMPLE,
				new Point(0, 0));
		// System.out.println(contours.size());
		for (int i = 0; i < contours.size(); i++)
		{
			Imgproc.drawContours(src3, contours, i, new Scalar(0, 255, 0, 0), 3);	// 轮廓加绿
			// System.out.println("maxArea=" + Imgproc.contourArea(contours.get(i)) + "maxAreaIdx=" + i);
		}

		int idx = getSec(contours);

		Imgproc.drawContours(src3, contours, idx, new Scalar(255, 0, 0, 0), 3);		// 手的轮廓加蓝

		try {
			// 计算指尖
			Mat m = contours.get(idx);

			Point[] points = ((MatOfPoint) m).toArray();
			// 求Point.y的最小最大
			double min = 10000, max = 0;
			int min_i = 0, max_i = 0;
			for(int i = 0; i < points.length; i++) {
				if(points[i].y>max) {
					max = points[i].y;
					max_i = i;
				}
				if(points[i].y<min) {
					min = points[i].y;
					min_i = i;
				}
			}
			double x = points[min_i].x;
			double y = min + (max-min)/18;
			p.x = x;p.y = y;
			Mat thefam = new Mat();
			thefam = src3.clone();
			Imgproc.drawMarker(thefam, points[max_i], new Scalar(0, 0, 255, 0), Imgproc.MARKER_CROSS , 1, 8, 20);
			Imgproc.drawMarker(thefam, points[min_i], new Scalar(0, 0, 255, 0), Imgproc.MARKER_CROSS , 1, 8, 20);
			Imgproc.drawMarker(thefam, p, new Scalar(0, 0, 255, 0), Imgproc.MARKER_CROSS , 1, 8, 20);

			return thefam;
		}catch (Exception e) {
			return src3;
		}
	}

	public static int getSec(List<MatOfPoint> contours) {
		double maxArea = -1;
		int maxAreaIdx = -1, idx = -1;

		for (int i=0; i < contours.size(); i++) {
			Mat contour = contours.get(i);
			double contourarea = Imgproc.contourArea(contour);
			if (contourarea > maxArea) {
				maxArea = contourarea;
				idx = maxAreaIdx;
				maxAreaIdx = i;
			}
		}
		return idx;
	}

	public static Mat Handle_clear(Mat src2) {

		Mat dilateImage = src2.clone();
		Mat erodeImage = src2.clone();

		Mat element = Imgproc.getStructuringElement(Imgproc.MORPH_RECT, new Size(3,3));

		// 先腐蚀再膨胀
		// 腐蚀去除小亮点
		Imgproc.erode(src2, erodeImage, element, new Point(-1, -1), 2);// 最后一个参数为迭代次数
		// 膨胀修复目标内容的腐蚀
		Imgproc.dilate(erodeImage, dilateImage, element, new Point(-1, -1), 5);// 最后一个参数为迭代次数

		Imgcodecs.imwrite("E:\\test\\test_erodeImage.jpg", erodeImage);
		Imgcodecs.imwrite("E:\\test\\test_dilateImage.jpg", dilateImage);
		return dilateImage;
	}
	public static Mat Handle_HSV(Mat src) {

		Mat imgHSV = new Mat(src.rows(), src.cols(), CvType.CV_8UC3);
		Mat img2 = new Mat(src.rows(), src.cols(), CvType.CV_8UC3);

		//转成HSV空间
		Imgproc.cvtColor(src, imgHSV, Imgproc.COLOR_BGR2HSV);

		int num_rows = imgHSV.rows();
		int num_col = imgHSV.cols();

		for (int i = 0; i < num_rows; i++)
			for (int j = 0; j < num_col; j++) {
				// 获取每个像素
				double[] clone = imgHSV.get(i, j).clone();
				// 肤色判断
//                if ((clone[0] >= 0 && clone[0] < 25/2) || (clone[0] > 335/2 && clone[0] < 360/2)
//                		&& (clone[1] > 0.2 * 255 && clone[1] < 0.6 * 255)
//                		&& (clone[2] > 0.4 * 255 && clone[2] < 256)) {
				if ((clone[0] >= 0 && clone[0] < 20) //|| (clone[0] > 335/2 && clone[0] < 360/2)
						&& (clone[1] > 10 && clone[1] < 150)
						&& (clone[2] > 60 && clone[2] < 256)) {
					// 范围内,全部设置为白色,
					clone[0] = 0;
					clone[1] = 0;
					clone[2] = 255;
					imgHSV.put(i, j, clone);
				} else {
					clone[0] = 0;
					clone[1] = 0;
					clone[2] = 0;
					imgHSV.put(i, j, clone);
				}
			}

		//转回BGR空间
		Imgproc.cvtColor(imgHSV, img2, Imgproc.COLOR_HSV2BGR);

		return img2;
	}
	public static Mat Handle_2(Mat src) {
		//给图像外加一层黑边
		Mat imgHSV = new Mat(src.rows(), src.cols(), CvType.CV_8UC3);
		Mat img2 = new Mat(src.rows(), src.cols(), CvType.CV_8UC3);

		//转成HSV空间
		Imgproc.cvtColor(src, imgHSV, Imgproc.COLOR_BGR2HSV);

		int num_rows = imgHSV.rows();
		int num_col = imgHSV.cols();

		for (int i = 0; i < num_rows; i++)
			for (int j = 0; j < num_col; j++) {
				// 获取每个像素
				double[] clone = imgHSV.get(i, j).clone();
				if (i==0||j==0||i==num_rows-1||j==num_col-1) {
					// 范围内,全部设置为白色,
					clone[0] = 0;
					clone[1] = 0;
					clone[2] = 0;
					imgHSV.put(i, j, clone);
				}
			}

		//转回BGR空间
		Imgproc.cvtColor(imgHSV, img2, Imgproc.COLOR_HSV2BGR);

		return img2;
	}
	public static Mat Handle_pre(Mat src) {
		Mat dst1 = src.clone();
		// 均值滤波
		//Imgproc.blur(src, dst1, new Size(9,9), new Point(-1, -1), Core.BORDER_DEFAULT);
		// 高斯滤波
		Imgproc.GaussianBlur(src, dst1, new Size(9,9), 0, 0, Core.BORDER_DEFAULT);
		Imgcodecs.imwrite("E:\\test\\test_lvbo.jpg", dst1);

		// lpls增强
        /*
	    Mat dst2 = dst1.clone();
	    Mat kernel = new Mat(3,3,CvType.CV_32FC1);
        kernel.put(0, 0, 0);
        kernel.put(0, 1, -1);
        kernel.put(0, 2, 0);
        kernel.put(1, 0, -1);
        kernel.put(1, 1, 5.05);
        kernel.put(1, 2, -1);
        kernel.put(2, 0, 0);
        kernel.put(2, 1, -1);
        kernel.put(2, 2, 0);
        System.out.print(kernel.dump());

        Imgproc.filter2D(src, dst2, CvType.CV_8UC3, kernel);
        Imgcodecs.imwrite("E:\\test\\test_zengqiang.jpg", dst2);
        */
		return dst1;
	}

}
