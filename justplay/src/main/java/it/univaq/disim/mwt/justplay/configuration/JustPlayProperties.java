package it.univaq.disim.mwt.justplay.configuration;

import javax.validation.Valid;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Data;

@Data
@Component
@Valid
@ConfigurationProperties(prefix = "justplay")
public class JustPlayProperties {

	private String dateFormat;
}
