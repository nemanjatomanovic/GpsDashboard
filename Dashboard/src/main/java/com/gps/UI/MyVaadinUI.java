package com.gps.UI;

import javax.servlet.annotation.WebServlet;

import org.springframework.beans.factory.annotation.Autowired;

import com.vaadin.annotations.Theme;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.annotations.Widgetset;
import com.vaadin.navigator.Navigator;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.spring.navigator.SpringViewProvider;
import com.vaadin.ui.UI;

@SpringUI
@Theme("dashboard")
public class MyVaadinUI extends UI{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final SpringViewProvider svp;
	@WebServlet(value = "/*", asyncSupported = true)
	@VaadinServletConfiguration(productionMode = false, ui = MyVaadinUI.class)
	public static class Servlet extends VaadinServlet {}
	
	@Autowired
	public MyVaadinUI(SpringViewProvider svp){
		this.svp = svp;
	}
	
	@Override
	protected void init(VaadinRequest request) {
		/*final VerticalLayout root = new VerticalLayout();
        root.setSizeFull();
        root.setMargin(true);
        root.setSpacing(true);
        setContent(root);

        final Panel viewContainer = new Panel();
        viewContainer.setWidth(50, Unit.PERCENTAGE);
        viewContainer.setHeight(70, Unit.PERCENTAGE);
        Responsive.makeResponsive(viewContainer);
        root.addComponent(viewContainer);
        root.setComponentAlignment(viewContainer, Alignment.MIDDLE_CENTER);*/
        
        
		Navigator navigator = new Navigator(this, this);
        navigator.addProvider(svp);
        navigator.addView(MainUI.VIEW_NAME, MainUI.class);
        navigator.addView(LoginView.VIEW_NAME, LoginView.class);
        navigator.navigateTo(LoginView.VIEW_NAME);
        
	}

}
