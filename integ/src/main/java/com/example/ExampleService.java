package com.example;

import com.example.InternalError;
import com.example.NotFoundError;
import com.example.resources.*;

public class ExampleService {
    private void testing() {
       var input = CreateTestOutput.builder().build();
       var output = DeleteTestOutput.builder()
               .test(TestEnum.DEFINED).build();
       // throw new InternalError("test");
    }
}