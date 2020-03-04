package com.efuture.proxy;

public class HelloServiceMain {
	public static void main(String[] args) {
		HelloServiceProxy helloHandler = new HelloServiceProxy();
		HelloService proxy = (HelloService) helloHandler.bind(new HelloServiceImpl());
		proxy.sayHello("张三");
		proxy.sayHello("李四");
		System.getProperties().put("sun.misc.ProxyGenerator.saveGeneratedFiles", "true");
	}

}
