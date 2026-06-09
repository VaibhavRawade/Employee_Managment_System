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
		
		getStringData();
		getStringData2();
		
	}
	
	public static  void getStringData()
	{
		String str="aaaabbbccccc";
		char ch[]=str.toCharArray();
		
		StringBuffer buffer=new StringBuffer();
		
		for(int i=0;i<ch.length;i++)
		{
			int flag=1;
			for(int j=i+1;j<ch.length;j++)
			{
				if(ch[i]==ch[j])
				{
					flag++;
					ch[j]='\0';
				}
			}
			if(ch[i]!='\0')
			{
				buffer.append(ch[i]);
				buffer.append(flag);
			}
		}
		
		System.out.println("Final String is ->"+buffer);
	}
	
	
	public static  void getStringData2()
	{
		String str="a10b5c3D2";
		char ch[]=str.toCharArray();
		
		for(int i=0;i<ch.length;i++)
		{
			char c=ch[i];
			String strNum="";
			for(int j=i+1;j<ch.length;j++)
			{
				if(Character.isLetter(ch[j]))
				{
					i=j-1;
					break;
				}
				else
				{
					strNum=strNum+ch[j];
				}
			}
			if(strNum!=null && strNum!="" && strNum!=" ")
			{
				int num=Integer.valueOf(strNum);
				for(int j=0;j<num;j++)
				{
					System.out.print(c);
				}
			}
			
		}
		System.out.println("Worked On Non-Repeting Character");
		System.out.println("Vaibah aRWAEDE");
		
		
	}

}
