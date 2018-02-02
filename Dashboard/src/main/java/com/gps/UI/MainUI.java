package com.gps.UI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;

import com.gps.Menu.SideMenu;
import com.vaadin.annotations.Theme;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.server.VaadinSession;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.spring.annotation.UIScope;
import com.vaadin.ui.UI;

@UIScope
@SpringView(name=MainUI.VIEW_NAME)
@Theme("dashboard")
public class MainUI extends SideMenu implements View{
//test Nemanja
	private static final long serialVersionUID = 1L;
	public static final String VIEW_NAME = "mainUI";
	//testiranje Nemanja
	//jos jedno testiranje
	private final VaadinmapsUI vui;

	@Autowired
	public MainUI(VaadinmapsUI vui){
		super();
		this.vui = vui;
		setUserMenuVisible(true);
		setUserName(StringUtils.capitalize(VaadinSession.getCurrent().getAttribute("Ime").toString()));
			addMenuItem("GPS", VaadinIcons.BULLETS, new MenuClickHandler(){
				public void click(){
					setContent(vui.getForm());
				}
			});


	}
	@Override
	public void enter(ViewChangeEvent event) {
		// TODO Auto-generated method stub

	}
}
