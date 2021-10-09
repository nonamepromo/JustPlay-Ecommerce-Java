package it.univaq.disim.mwt.justplay.configuration;

import java.time.format.DateTimeFormatter;
import java.util.Locale;

import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.format.FormatterRegistry;
import org.springframework.format.datetime.standard.DateTimeFormatterRegistrar;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;

@Configuration
public class WebConfig implements WebMvcConfigurer {

	private static final String dateFormat = "dd-MM-yyyy";
    private static final String dateTimeFormat = "dd-MM HH:mm";
	
	@Override
	public void addViewControllers(ViewControllerRegistry registry) {
		registry.addViewController("/common/welcome").setViewName("/common/welcome");
		registry.addViewController("/common/login").setViewName("/common/login");
		// registry.addViewController("/common/register").setViewName("/common/register");
		registry.addViewController("/common/operazioneok").setViewName("/common/operazioneok");
		registry.addViewController("/common/accessdenied").setViewName("/common/accessdenied");
	}
	
	  @Bean
	    public MessageSource messageSource() {
	        ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
	        messageSource.setBasenames("classpath:i18n/justplay", "classpath:i18n/common");
	        messageSource.setDefaultEncoding("UTF-8");
	        return messageSource;
	    }
	  
	  @Bean
	    public LocalValidatorFactoryBean validator(MessageSource messageSource) {
	        LocalValidatorFactoryBean bean = new LocalValidatorFactoryBean();
	        bean.setValidationMessageSource(messageSource);
	        return bean;
	    }

	@Bean
	public LocaleResolver localeResolver() {
		SessionLocaleResolver slr = new SessionLocaleResolver();
		slr.setDefaultLocale(Locale.ITALY);
		return slr;
	}

	@Bean
	public LocaleChangeInterceptor localeChangeInterceptor() {
		LocaleChangeInterceptor lci = new LocaleChangeInterceptor();
		lci.setParamName("lang");
		return lci;
	}
	
	@Override
    public void addFormatters(FormatterRegistry registry) {
        DateTimeFormatterRegistrar registrar = new DateTimeFormatterRegistrar();
        registrar.setUseIsoFormat(true);
        registrar.registerFormatters(registry);
    }

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(localeChangeInterceptor());
	}
	
	 @Bean
	    public Jackson2ObjectMapperBuilderCustomizer jsonCustomizer() {
	        return builder -> {
	            builder.simpleDateFormat(dateTimeFormat);
	            builder.serializers(new LocalDateSerializer(DateTimeFormatter.ofPattern(dateFormat)));
	            builder.serializers(new LocalDateTimeSerializer(DateTimeFormatter.ofPattern(dateTimeFormat)));
	        };
	    }

}
