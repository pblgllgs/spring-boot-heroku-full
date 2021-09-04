package com.bolsadeideas.springboot.app;

import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;

@Configuration
public class MvcConfig implements WebMvcConfigurer {
	
	@SuppressWarnings("unused")
	private final Logger log= LoggerFactory.getLogger(getClass());

	/*@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		// TODO Auto-generated method stub
		String resourcePath = Paths.get("uploads").toAbsolutePath().toUri().toString();
		log.info(resourcePath);

		registry.addResourceHandler("/uploads/**").addResourceLocations(resourcePath);
	}*/
	
	public void addViewControllers(ViewControllerRegistry registry) {	
		registry.addViewController("/error_403").setViewName("error_403");
	}
	
	@Bean 
	public BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	/*
	 * Beans para la congifuración de los idiomas
	 */
	@Bean
	public LocaleResolver localeResolver() {
		SessionLocaleResolver localeResolver = new SessionLocaleResolver();
		localeResolver.setDefaultLocale(new Locale("es","ES"));
		return localeResolver;
	}
	
	/*
	 * interceptor para cambiar el lenguaje cada vez que pasemos el 'lang' por url
	 */
	@Bean
	public LocaleChangeInterceptor localeChangeInterceptor() {
		LocaleChangeInterceptor localeInterceptor = new LocaleChangeInterceptor();
		localeInterceptor.setParamName("lang");
		return localeInterceptor;

	}
	
	/*
	 * registro del interceptor
	 */

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(localeChangeInterceptor());
	}

	/*
	ayuda para convertir un objeto/contenido en XML
	 */
	@Bean
	public  Jaxb2Marshaller jaxb2Marshaller(){
		 Jaxb2Marshaller marshaller = new Jaxb2Marshaller();
		 marshaller.setClassesToBeBound(new Class[]{com.bolsadeideas.springboot.app.view.xml.ClienteList.class});
		 return marshaller;
	}

}
