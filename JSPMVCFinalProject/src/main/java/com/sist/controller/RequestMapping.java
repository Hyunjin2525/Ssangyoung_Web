package com.sist.controller;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

@Retention(RUNTIME)
@Target(METHOD)
/*
 * 	@ => TYPE
 *  class class_name
 *  {
 *  	@ => FIELD
 *  	private A a;
 *  	
 *  	@ => CONSTRUTOR
 *  	public class_name()
 *  	{
 *  	}
 *  
 *  	@METHOD
 *  	public void display()
 *  	{
 *  	}
 *  }
 *  
 *  @RequestMappping("list,do")
 *  public void display1()
 *  	{
 *  	}
 *  @RequestMappping()
 *  public void display2()
 *  	{
 *  	}
 *  @RequestMappping()
 *  public void display3()
 *  	{
 *  	}
 *  @RequestMappping()
 *  public void display4()
 *  	{
 *  	}
 */
public @interface RequestMapping {
	public String value();
}
