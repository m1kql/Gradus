/**
 * Name: Mike Liang
 * Date: December 21 2021
 * Description:
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * @author @yak-fumblepack
 */

package com.ayj.gradus;

import com.ayj.gradus.utils.CommonUtils;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * This is the class of the application with the main method that runs the web
 * app.
 */
@SpringBootApplication
public class GradusApplication {

	static { // Execute this before the program runs, because we need to create the OS path
		CommonUtils commonUtils = new CommonUtils();
		try {
			commonUtils.createOSPath();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		SpringApplication.run(GradusApplication.class, args);
	}

}
