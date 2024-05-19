package com.ukma;

import com.netflix.appinfo.InstanceInfo;
import io.micronaut.runtime.Micronaut;
import io.micronaut.serde.annotation.SerdeImport;

@SerdeImport(InstanceInfo.PortWrapper.class)
public class Application {

    public static void main(String[] args) {
        Micronaut.run(Application.class, args);
    }
}