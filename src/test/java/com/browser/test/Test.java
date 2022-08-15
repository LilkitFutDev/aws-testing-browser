package com.browser.test;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

public class Test {

    private String project_arn;

    Map<String, String> data = new HashMap<String, String>();

    public static void main(String[] args) throws Exception {
        Test test = new Test();
        test.getProperties();
    }


    private void getProperties() throws Exception {

//        InputStream inputStream = new FileInputStream(new File("src\\test\\resources\\action.yml"));

        ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
        mapper.findAndRegisterModules();

        Test test = mapper.readValue(new File("src/test/java/com/browser/test/action.yml"), Test.class);

        System.out.println(test);

    }

}
