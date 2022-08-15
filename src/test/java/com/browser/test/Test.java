package com.browser.test;

import org.yaml.snakeyaml.Yaml;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

public class Test {

    Map<String, String> data = new HashMap<String, String>();

    public static void main(String[] args) throws Exception {
         Test test = new Test();
         test.test();
    }

    public void test() throws Exception {
        System.out.println(getProperties());

    }

    private Map<String, String> getProperties() throws Exception {

        InputStream inputStream = new FileInputStream(new File("src\\test\\java\\com\\browser\\test\\action.yml"));

        System.out.println(inputStream.read());

        Yaml yaml = new Yaml();
        data = yaml.load(inputStream);

        data.replace("project_arn","2");


        return data;

    }

}
