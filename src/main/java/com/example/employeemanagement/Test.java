package com.example.employeemanagement;

import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Test 
{
	public synchronized void getDataObject(String value)
	{
		for(int i=0;i<5;i++)
		{
			System.out.println("Value is ->"+value);
		}
	}
	
	public static synchronized void getDataClass(String value)
	{
		for(int i=0;i<5;i++)
		{
			System.out.println("Value is ->"+value);
		}
	}
	
	
	
	
	
	public static void main(String[] args) 
	{
		Test t1=new Test();
		
		Thread thread1=new Thread(()->t1.getDataObject("Thread1"));
		thread1.start();
		
		Test t2=new Test();
		
		Thread thread2=new Thread(()->t2.getDataObject("Thread2"));
		thread2.start();
		
		//=============================================================================
		
		Thread t3=new Thread(()->Test.getDataClass("Thread3"));
		t3.start();
		
		
		ExecutorService exutor1=Executors.newCachedThreadPool();
		ExecutorService exutor2=Executors.newFixedThreadPool(23);
		
		
		exutor1.submit(()->{
		});
		
		exutor1.shutdown();
		
		
	}
}
