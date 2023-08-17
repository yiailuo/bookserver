
package com.book.manager.security;

import com.book.manager.model.User;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	private Logger	logger	= LoggerFactory.getLogger(getClass());

	@Override
	protected void configure(HttpSecurity http) throws Exception {  //配置策略
		 http.csrf().disable();
        //允许页面在iframe中扩展
        http.headers().frameOptions().disable();
		 http.authorizeRequests()
       .antMatchers(HttpMethod.OPTIONS, "/**").permitAll()
       .antMatchers(
//          HttpMethod.GET,如果加上这行，post方法则无法访问
          "/**",
          "/user/login",
          "/*.html",
          "/favicon.ico",
          "/**/*.html",
          "/**/*.css",
          "/**/*.js",
          "/**/*.jpg",
          "/**/*.png",
          "/**/*.woff",
          "/**/*.woff2",
          "/**/*.ttf",
          "/**/*.svg",
          "/**/*.xlsx",
          "/**/*.cer"
        ).permitAll()
       .anyRequest().authenticated()
       .and().formLogin().loginPage("/login")//设置登录页面
       .successHandler(loginSuccessHandler())//设置登录成功后执行的方法
       .and().logout().permitAll().invalidateHttpSession(true)
       .deleteCookies("JSESSIONID").logoutSuccessHandler(logoutSuccessHandler())
       .and().sessionManagement().maximumSessions(10).expiredUrl("/login");
	}
	

	 @Bean
    public SavedRequestAwareAuthenticationSuccessHandler loginSuccessHandler() { //登入处理
        return new SavedRequestAwareAuthenticationSuccessHandler() {
            @Override
            public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
                User userDetails = (User) authentication.getPrincipal();
                logger.info("USER : " + userDetails.getUsername() + " LOGIN SUCCESS !  ");
                response.setHeader("content-type","text/html;charset=UTF-8");
                PrintWriter out = response.getWriter();
                out.write("{\"status\":\"true\",\"user\":"+JSONObject.fromObject(userDetails)+"}");
                out.flush();
                out.close();
               // getRedirectStrategy().sendRedirect(request, response, "/home");  //登录成功后，跳转首页home
               // super.onAuthenticationSuccess(request, response, authentication);
            }
        };
    }
   
	//登出管理
	@Bean
   public LogoutSuccessHandler logoutSuccessHandler() { //登出处理
       return new LogoutSuccessHandler() {
           @Override
           public void onLogoutSuccess(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Authentication authentication) throws IOException, ServletException {
               try {
                   	User user = (User) authentication.getPrincipal();
                   logger.info("USER : " + user.getUsername() + " LOGOUT SUCCESS !  ");
               } catch (Exception e) {
                   logger.info("LOGOUT EXCEPTION , e : " + e.getMessage());
               }
               httpServletResponse.sendRedirect("/login");
           }
       };
   }
	
}
