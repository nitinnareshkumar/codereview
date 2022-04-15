package com.review.code.RapidDev;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class RapidDevApplication {

	public static void main(String[] args) {
		SpringApplication.run(RapidDevApplication.class, args);
	}

	/*public static Map<String, String> getFileTemplateMap() {
		Map<String, String> fileTemplateMap = Stream.of(new String[][] {
				{ "ItemBean.vm", "-beans.xml" },
				{ "ItemSpring.vm", "-spring.xml" },
				{ "ItemController.vm", "PageController.java" },
				{ "ItemControllerConstants.vm", "$lowercaseConstants.java" },
				{ "ItemFacadeClass.vm", "$classFacade.java" },
				{ "ItemForm.vm", "Form.java" },
				{ "ItemFacadeInterface.vm", "Facade.java" },
				{ "ItemPopulatorClass.vm", "$classPopulator.java" },
				{ "ItemReversePopulatorClass.vm", "$classReversePopulator.java" },
				{ "ItemListPage.vm", "Page.jsp" },
				{ "ItemCreatePage.vm", "CreatePage.jsp" },
				{ "ItemUpdatePage.vm", "UpdatePage.jsp" },
				{ "ItemDaoClass.vm", "$classDao.java" },
				{ "ItemDaoInterface.vm", "Dao.java" },
				{ "ItemServiceClass.vm", "$classService.java" },
				{ "ItemServiceInterface.vm", "Service.java" },
				{ "Addon-web-spring.vm", "-web-spring.xml" },
				{ "AddonImpex.vm", "cms-content.impex" },
				{ "AddonLocaleProperties.vm", "base_en.properties" },
		}).collect(Collectors.collectingAndThen(
				Collectors.toMap(data -> data[0], data -> data[1]),
				Collections::<String, String> unmodifiableMap));
		return fileTemplateMap;
	}*/



}


