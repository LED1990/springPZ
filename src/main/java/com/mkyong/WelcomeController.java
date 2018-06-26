package com.mkyong;

import java.io.UnsupportedEncodingException;
import java.util.Map;

import egate.integracja.SignRespond;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@Slf4j
public class WelcomeController {
	String testDocBase64="PHVzbHVnb2Rhd2N5IHhtbG5zOnhzaT0iaHR0cDovL3d3dy53My5vcmcvMjAwMS9YTUxTY2hlbWEtaW5zdGFuY2UiPg0KPHVzbHVnb2Rhd2NhPg0KPG5hendhPmFzZGZnc2Rhc2Rhc2Q8L25hendhPg0KPGRhdGFSZWplc3RyYWNqaT4xMjM0NTY8L2RhdGFSZWplc3RyYWNqaT4NCjwvdXNsdWdvZGF3Y2E+DQo8L3VzbHVnb2Rhd2N5Pg0K";
	String xmlBase64="<uslugodawcy xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\">\n" +
			"<uslugodawca>\n" +
			"<nazwa>asdfgsdasdasd</nazwa>\n" +
			"<dataRejestracji>123456</dataRejestracji>\n" +
			"</uslugodawca>\n" +
			"</uslugodawcy>";
	@Autowired
	PodpisService podpisService;
	@Autowired
	EgateService egateService;

	// inject via application.properties
	@Value("${welcome.message:test}")
	private String message = "Hello World";


	@RequestMapping("/")
	public String welcome(Map<String, Object> model) {
		model.put("message", this.message);
		return "welcome";
	}
	@RequestMapping(value = "/podpisz", method= RequestMethod.GET)
	public String podpisz(Map<String, Object> model) throws UnsupportedEncodingException {
		log.info("popisuje");
		String succes="http://localhost:8080";
		byte[] xmlBase64bytes = xmlBase64.getBytes("UTF-8");
		String url = podpisService.przekazDokumentDoPodpisania(xmlBase64bytes,succes,succes,"brak");
		if (url==null){
			log.info("FAIL!!!!!");
		}else {
			log.info("Sukcess: "+url);
			return "redirect:" + url;
		}
		return "welcome";
	}

	@RequestMapping(value = "/egate", method= RequestMethod.GET)
	public String podpiszEgate(Map<String, Object> model) throws UnsupportedEncodingException {
		String succes="http://localhost:8080";
		byte[] xmlBase64bytes = xmlBase64.getBytes("UTF-8");
		SignRespond signRespond = egateService.wykonajPodpisEgate(xmlBase64bytes,succes);
		log.info(signRespond.getInfo());
		log.info(signRespond.getUrl());
		log.info("kod: "+signRespond.getCode());
		return "welcome";
	}
}