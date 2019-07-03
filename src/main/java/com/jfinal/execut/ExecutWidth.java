package com.jfinal.execut;

import java.io.File;
import java.io.IOException;
import java.net.URL;

import org.apache.commons.io.FileUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class ExecutWidth {

    public void executWidth() throws IOException {
        URL url = ExecutWidth.class.getClassLoader().getResource("stringTemplet/commission/execut/list.jf");

        File file = new File(url.getFile());

        System.out.println(file.exists());
        String str = FileUtils.readFileToString(file);
        // System.out.println(str);
        Document doc = Jsoup.parse(str);
        // System.out.println(doc.toString());
        Elements elements = doc.select("td");
        int total = 0;
        for (Element e : elements) {
            String width = e.attr("width");
            System.out.println(width);
            total += Integer.valueOf(width);
        }
        System.out.println(total);

    }

    public void auditWidth() throws IOException {
        URL url = ExecutWidth.class.getClassLoader().getResource("stringTemplet/commission/audit/list.jf");

        File file = new File(url.getFile());

        System.out.println(file.exists());
        String str = FileUtils.readFileToString(file);
        // System.out.println(str);
        Document doc = Jsoup.parse(str);
        // System.out.println(doc.toString());
        Elements elements = doc.select("td");
        int total = 0;
        for (Element e : elements) {
            String width = e.attr("width");
            System.out.println(width);
            total += Integer.valueOf(width);
        }
        System.out.println(total);

    }

    public void purchaseWidth() throws IOException {
        URL url = ExecutWidth.class.getClassLoader().getResource("stringTemplet/commission/purchase/list.jf");

        File file = new File(url.getFile());

        System.out.println(file.exists());
        String str = FileUtils.readFileToString(file);
        // System.out.println(str);
        Document doc = Jsoup.parse(str);
        // System.out.println(doc.toString());
        Elements elements = doc.select("td");
        int total = 0;
        for (Element e : elements) {
            String width = e.attr("width");
            System.out.println(width);
            total += Integer.valueOf(width);
        }
        System.out.println(total);

    }

    public void CommissioncollectWidth() throws IOException {
        URL url = ExecutWidth.class.getClassLoader().getResource("stringTemplet/commission/collect/list.jf");

        File file = new File(url.getFile());

        System.out.println(file.exists());
        String str = FileUtils.readFileToString(file);
        // System.out.println(str);
        Document doc = Jsoup.parse(str);
        // System.out.println(doc.toString());
        Elements elements = doc.select("td");
        int total = 0;
        for (Element e : elements) {
            String width = e.attr("width");
            System.out.println(width);
            total += Integer.valueOf(width);
        }
        System.out.println(total);

    }

    public void WatiInWidth() throws IOException {
        URL url = ExecutWidth.class.getClassLoader().getResource("stringTemplet/warehouse/waitIn/list.jf");

        File file = new File(url.getFile());

        System.out.println(file.exists());
        String str = FileUtils.readFileToString(file);
        // System.out.println(str);
        Document doc = Jsoup.parse(str);
        // System.out.println(doc.toString());
        Elements elements = doc.select("td");
        int total = 0;
        for (Element e : elements) {
            String width = e.attr("width");
            System.out.println(width);
            total += Integer.valueOf(width);
        }
        System.out.println(total);

    }

    public void checkWidth() throws IOException {
        URL url = ExecutWidth.class.getClassLoader().getResource("stringTemplet/warehouse/check/list.jf");

        File file = new File(url.getFile());

        System.out.println(file.exists());
        String str = FileUtils.readFileToString(file);
        // System.out.println(str);
        Document doc = Jsoup.parse(str);
        // System.out.println(doc.toString());
        Elements elements = doc.select("td");
        int total = 0;
        for (Element e : elements) {
            String width = e.attr("width");
            System.out.println(width);
            total += Integer.valueOf(width);
        }
        System.out.println(total);

    }

    public void checkCollectWidth() throws IOException {
        URL url = ExecutWidth.class.getClassLoader().getResource("stringTemplet/warehouse/checkCollect/list.jf");

        File file = new File(url.getFile());

        System.out.println(file.exists());
        String str = FileUtils.readFileToString(file);
        // System.out.println(str);
        Document doc = Jsoup.parse(str);
        // System.out.println(doc.toString());
        Elements elements = doc.select("td");
        int total = 0;
        for (Element e : elements) {
            String width = e.attr("width");
            System.out.println(width);
            total += Integer.valueOf(width);
        }
        System.out.println(total);

    }

    public void checkExceptionWidth() throws IOException {
        URL url = ExecutWidth.class.getClassLoader().getResource("stringTemplet/warehouse/checkException/list.jf");

        File file = new File(url.getFile());

        System.out.println(file.exists());
        String str = FileUtils.readFileToString(file);
        // System.out.println(str);
        Document doc = Jsoup.parse(str);
        // System.out.println(doc.toString());
        Elements elements = doc.select("td");
        int total = 0;
        for (Element e : elements) {
            String width = e.attr("width");
            System.out.println(width);
            total += Integer.valueOf(width);
        }
        System.out.println(total);

    }

    public void storageWidth() throws IOException {
        URL url = ExecutWidth.class.getClassLoader().getResource("stringTemplet/warehouse/storage/list.jf");

        File file = new File(url.getFile());

        System.out.println(file.exists());
        String str = FileUtils.readFileToString(file);
        // System.out.println(str);
        Document doc = Jsoup.parse(str);
        // System.out.println(doc.toString());
        Elements elements = doc.select("td");
        int total = 0;
        for (Element e : elements) {
            String width = e.attr("width");
            System.out.println(width);
            total += Integer.valueOf(width);
        }
        System.out.println(total);

    }

    public void outHouseWidth() throws IOException {
        URL url = ExecutWidth.class.getClassLoader().getResource("stringTemplet/warehouse/outHouse/list.jf");

        File file = new File(url.getFile());

        System.out.println(file.exists());
        String str = FileUtils.readFileToString(file);
        // System.out.println(str);
        Document doc = Jsoup.parse(str);
        // System.out.println(doc.toString());
        Elements elements = doc.select("td");
        int total = 0;
        for (Element e : elements) {
            String width = e.attr("width");
            System.out.println(width);
            total += Integer.valueOf(width);
        }
        System.out.println(total);

    }

    public void PayWidth() throws IOException {
        URL url = ExecutWidth.class.getClassLoader().getResource("stringTemplet/finance/pay/list.jf");

        File file = new File(url.getFile());

        System.out.println(file.exists());
        String str = FileUtils.readFileToString(file);
        // System.out.println(str);
        Document doc = Jsoup.parse(str);
        // System.out.println(doc.toString());
        Elements elements = doc.select("td");
        int total = 0;
        for (Element e : elements) {
            String width = e.attr("width");
            System.out.println(width);
            total += Integer.valueOf(width);
        }
        System.out.println(total);

    }

    public void PaybillWidth() throws IOException {
        URL url = ExecutWidth.class.getClassLoader().getResource("stringTemplet/finance/paybill/list.jf");

        File file = new File(url.getFile());

        System.out.println(file.exists());
        String str = FileUtils.readFileToString(file);
        // System.out.println(str);
        Document doc = Jsoup.parse(str);
        // System.out.println(doc.toString());
        Elements elements = doc.select("td");
        int total = 0;
        for (Element e : elements) {
            String width = e.attr("width");
            System.out.println(width);
            total += Integer.valueOf(width);
        }
        System.out.println(total);

    }

    public void getpayWidth() throws IOException {
        URL url = ExecutWidth.class.getClassLoader().getResource("stringTemplet/finance/getpay/list.jf");

        File file = new File(url.getFile());

        System.out.println(file.exists());
        String str = FileUtils.readFileToString(file);
        // System.out.println(str);
        Document doc = Jsoup.parse(str);
        // System.out.println(doc.toString());
        Elements elements = doc.select("td");
        int total = 0;
        for (Element e : elements) {
            String width = e.attr("width");
            System.out.println(width);
            total += Integer.valueOf(width);
        }
        System.out.println(total);

    }

    public void getpaybillWidth() throws IOException {
        URL url = ExecutWidth.class.getClassLoader().getResource("stringTemplet/finance/getpaybill/list.jf");

        File file = new File(url.getFile());

        System.out.println(file.exists());
        String str = FileUtils.readFileToString(file);
        // System.out.println(str);
        Document doc = Jsoup.parse(str);
        // System.out.println(doc.toString());
        Elements elements = doc.select("td");
        int total = 0;
        for (Element e : elements) {
            String width = e.attr("width");
            System.out.println(width);
            total += Integer.valueOf(width);
        }
        System.out.println(total);

    }

    public void getOrderReceiveWidth() throws IOException {
        URL url = ExecutWidth.class.getClassLoader().getResource("stringTemplet/product/receive/list.jf");

        File file = new File(url.getFile());

        System.out.println(file.exists());
        String str = FileUtils.readFileToString(file);
        // System.out.println(str);
        Document doc = Jsoup.parse(str);
        // System.out.println(doc.toString());
        Elements elements = doc.select("td");
        int total = 0;
        for (Element e : elements) {
            String width = e.attr("width");
            System.out.println(width);
            total += Integer.valueOf(width);
        }
        System.out.println(total);

    }

    public void getPlanReceiveWidth() throws IOException {
        URL url = ExecutWidth.class.getClassLoader().getResource("stringTemplet/product/plan/list.jf");

        File file = new File(url.getFile());

        System.out.println(file.exists());
        String str = FileUtils.readFileToString(file);
        // System.out.println(str);
        Document doc = Jsoup.parse(str);
        // System.out.println(doc.toString());
        Elements elements = doc.select("td");
        int total = 0;
        for (Element e : elements) {
            String width = e.attr("width");
            System.out.println(width);
            total += Integer.valueOf(width);
        }
        System.out.println(total);

    }

    public void getCollectReceiveWidth() throws IOException {
        URL url = ExecutWidth.class.getClassLoader().getResource("stringTemplet/product/collect/list.jf");

        File file = new File(url.getFile());

        System.out.println(file.exists());
        String str = FileUtils.readFileToString(file);
        // System.out.println(str);
        Document doc = Jsoup.parse(str);
        // System.out.println(doc.toString());
        Elements elements = doc.select("td");
        int total = 0;
        for (Element e : elements) {
            String width = e.attr("width");
            System.out.println(width);
            total += Integer.valueOf(width);
        }
        System.out.println(total);

    }

    public void getCollectAssistWidth() throws IOException {
        URL url = ExecutWidth.class.getClassLoader().getResource("stringTemplet/product/assist/list.jf");

        File file = new File(url.getFile());

        System.out.println(file.exists());
        String str = FileUtils.readFileToString(file);
        // System.out.println(str);
        Document doc = Jsoup.parse(str);
        // System.out.println(doc.toString());
        Elements elements = doc.select("td");
        int total = 0;
        for (Element e : elements) {
            String width = e.attr("width");
            System.out.println(width);
            total += Integer.valueOf(width);
        }
        System.out.println(total);

    }

    public void getAddReadyReceiveWidth() throws IOException {
        URL url = ExecutWidth.class.getClassLoader().getResource("stringTemplet/addtion/readreceive/list.jf");

        File file = new File(url.getFile());

        System.out.println(file.exists());
        String str = FileUtils.readFileToString(file);
        // System.out.println(str);
        Document doc = Jsoup.parse(str);
        // System.out.println(doc.toString());
        Elements elements = doc.select("td");
        int total = 0;
        for (Element e : elements) {
            String width = e.attr("width");
            System.out.println(width);
            total += Integer.valueOf(width);
        }
        System.out.println(total);

    }

    public void getAddReadyWidth() throws IOException {
        URL url = ExecutWidth.class.getClassLoader().getResource("stringTemplet/addtion/ready/list.jf");

        File file = new File(url.getFile());

        System.out.println(file.exists());
        String str = FileUtils.readFileToString(file);
        // System.out.println(str);
        Document doc = Jsoup.parse(str);
        // System.out.println(doc.toString());
        Elements elements = doc.select("td");
        int total = 0;
        for (Element e : elements) {
            String width = e.attr("width");
            System.out.println(width);
            total += Integer.valueOf(width);
        }
        System.out.println(total);

    }

}
