

import java.io.File;
import weka.classifiers.Classifier;
import weka.classifiers.lazy.IBk;
import weka.core.Attribute;
import weka.core.DenseInstance;
import weka.core.Instance;
import weka.core.Instances;
import weka.core.converters.ArffLoader;
import weka.core.converters.ConverterUtils.DataSink;

public class Learn {

    private Key key;
    private File training;
    private File test;
    private ArffLoader arffLoader;
    private Instance instance;
    private Instances instancesTrain;
    private Instances instancesTest;
    private Classifier m_classifier;

    //构造器（训练集路径，测试集路径）
    public Learn(String trainingpath,String testpath) throws Exception {
        training = new File(trainingpath);
        test = new File(testpath);
        arffLoader = new ArffLoader();

        arffLoader.setFile(training);
        instancesTrain = arffLoader.getDataSet();
        instancesTrain.setClassIndex(instancesTrain.numAttributes() - 1);

        arffLoader.setFile(test);
        instancesTest = arffLoader.getDataSet();
        instancesTest.setClassIndex(instancesTest.numAttributes() - 1);

        m_classifier = new IBk();
        m_classifier.buildClassifier(instancesTrain);
    }
    //构造器（训练集路径，测试集路径，分类器）
    public Learn(String trainingpath,String testpath,Classifier classifier) throws Exception {
        training = new File(trainingpath);
        test = new File(testpath);
        arffLoader = new ArffLoader();

        arffLoader.setFile(training);
        instancesTrain = arffLoader.getDataSet();
        instancesTrain.setClassIndex(instancesTrain.numAttributes() - 1);

        arffLoader.setFile(test);
        instancesTest = arffLoader.getDataSet();
        instancesTest.setClassIndex(instancesTest.numAttributes() - 1);

        m_classifier = classifier;
        m_classifier.buildClassifier(instancesTrain);
    }
    //计算测试集预测正确率
    public void test() throws Exception{
        double rightcount = 0.0f;
        double sum = instancesTest.numInstances();
        for(int  i = 0;i<sum;i++)//测试分类结果
        {
            if(m_classifier.classifyInstance(instancesTest.instance(i))==instancesTest.instance(i).classValue())//如果预测值和答案值相等（测试语料中的分类列提供的须为正确答案，结果才有意义）
            {
                rightcount++;//正确值加1
            }
        }
        System.out.println("classification precision:"+(rightcount/sum));
    }
    //根据坐标构造实例
    private void getInstanceByPosition(double x,double y){
        instance = new DenseInstance(3);
        instance.setValue(new Attribute("x",0),x);
        instance.setValue(new Attribute("y",1),y);
        instance.setValue(new Attribute("value",2),0);
        instance.setDataset(instancesTest);
        //return instance;
    }
    //存储新实例到训练集
    public void saveInstance() throws Exception {
        instance.setValue(instance.attribute(2),key.toString());
        instancesTrain.add(instance);
        DataSink.write(training.getPath(),instancesTrain);
    }
    //预测键值
    public String getKey(double x,double y) throws Exception {
        getInstanceByPosition(x,y);
        //System.out.println(m_classifier.classifyInstance(instancesTest.instance(0)));
        key = Key.getKey(m_classifier.classifyInstance(instance));
        return key.getSymbol();
    }

}
