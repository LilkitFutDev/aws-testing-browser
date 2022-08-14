package com.browser.test;

import org.yaml.snakeyaml.Yaml;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Map;

public class Test {

    public static void main(String[] args) throws FileNotFoundException {
         Test test = new Test();
         test.test();
    }

    public void test() throws FileNotFoundException {
        InputStream inputStream = new FileInputStream(new File("src\\test\\resources\\action.yml"));

        Yaml yaml = new Yaml();
        Map<String, Object> data = yaml.load(inputStream);
        data.put("project_arn", "1");
        System.out.println(data.get("aws_access_key"));
    }

}
