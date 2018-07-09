package com.stylefeng.guns.core.util;

import java.awt.Font;
import java.util.ArrayList;
import java.util.List;

import com.stylefeng.guns.common.persistence.model.VoteSpy;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartFrame;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.StandardChartTheme;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;

public class ChartUtil {

    private static List<VoteSpy> list;

    public static List<VoteSpy> getList() {
        return list;
    }

    public static void setList(List<VoteSpy> list) {
        ChartUtil.list = list;
    }


    /**
     * 创建数据集合
     * @return dataSet
     */
    public static CategoryDataset createDataSet() {
        List<VoteSpy> entity = new ArrayList<>();
        VoteSpy v = new VoteSpy();
        v.setName("5");
        v.setId(5);
        v.setNum(6);
        entity.add(v);
        // 实例化DefaultCategoryDataset对象
        DefaultCategoryDataset dataSet = new DefaultCategoryDataset();
        // 向数据集合中添加数据
        for (VoteSpy voteSpy : entity) {
            dataSet.addValue(voteSpy.getNum(), "休假类型", voteSpy.getName());
        }
        return dataSet;
    }
    /**
     * 创建JFreeChart对象
     * @return chart
     */
    public static JFreeChart createChart() {
        StandardChartTheme standardChartTheme = new StandardChartTheme("CN"); //创建主题样式
        standardChartTheme.setExtraLargeFont(new Font("隶书", Font.BOLD, 20)); //设置标题字体
        standardChartTheme.setRegularFont(new Font("宋体", Font.PLAIN, 15)); //设置图例的字体
        standardChartTheme.setLargeFont(new Font("宋体", Font.PLAIN, 15)); //设置轴向的字体
        ChartFactory.setChartTheme(standardChartTheme);//设置主题样式
        // 通过ChartFactory创建JFreeChart
        JFreeChart chart = ChartFactory.createBarChart3D(
                "员工假期愿望", //图表标题
                "Java图书", //横轴标题
                "人数（个）",//纵轴标题
                createDataSet(),//数据集合
                PlotOrientation.VERTICAL, //图表方向
                true,//是否显示图例标识
                false,//是否显示tooltips
                false);//是否支持超链接
        return chart;
    }
    // 本地测试
    public static void main(String[] args) {
        List<VoteSpy> entity = new ArrayList<>();
        VoteSpy v = new VoteSpy();
        v.setName("1");
        v.setId(5);
        v.setNum(6);
        entity.add(v);
        ChartFrame cf = new ChartFrame("Test", createChart());
        cf.pack();
        cf.setVisible(true);
    }

}
