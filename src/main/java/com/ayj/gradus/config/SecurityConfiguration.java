package com.ayj.gradus.config;

import com.ayj.gradus.utils.CommonUtils;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * In this class we set up and instantiate the objects we need later on when we
 * autowire it
 */
@Configuration
public class SecurityConfiguration implements WebMvcConfigurer {

  /**
   * Creates the instance object of BCryptPasswordEncoder
   * 
   * @return a new BCryptPasswordEncoder
   */
  @Bean
  public BCryptPasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

  /**
   * Creates the instance object of CommonUtils
   * 
   * @return a new CommonUtils
   */
  @Bean
  public CommonUtils commonUtils() {
    return new CommonUtils();
  }

}
