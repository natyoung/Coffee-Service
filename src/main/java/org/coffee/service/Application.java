package org.coffee.service;

import org.wso2.msf4j.MicroservicesRunner;

public class Application {
    public static void main(String[] args) {
        new MicroservicesRunner(4567)
                .deploy(new MenuService())
                .start();
    }
}
