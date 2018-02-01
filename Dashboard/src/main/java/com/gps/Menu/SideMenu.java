package com.gps.Menu;

import org.springframework.beans.factory.annotation.Autowired;

import com.gps.Radnik.PodesavanjaProfila;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.server.Page;
import com.vaadin.server.Resource;
import com.vaadin.server.Responsive;
import com.vaadin.server.ThemeResource;
import com.vaadin.server.VaadinSession;
import com.vaadin.shared.ui.ContentMode;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Component;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.HorizontalLayout;
//import com.vaadin.ui.Image;
import com.vaadin.ui.Label;
import com.vaadin.ui.MenuBar;
import com.vaadin.ui.MenuBar.Command;
import com.vaadin.ui.MenuBar.MenuItem;
import com.vaadin.ui.UI;
import com.vaadin.ui.themes.ValoTheme;
//import com.xeonline.app.XeonlineUI;

@SuppressWarnings({ "serial" })
public class SideMenu extends HorizontalLayout {

	/**
	 * A simple lambda compatible handler class for executing code when a menu
	 * entry is clicked.
	 */
	public interface MenuClickHandler {

		/**
		 * This method is called when associated menu entry is clicked.
		 */
		void click();
	}

	/* Class name for hiding the menu when screen is too small */
	private static final String STYLE_VISIBLE = "valo-menu-visible";

	/* Components to handle content and menus */
	private final CssLayout contentArea = new CssLayout();
	private final CssLayout menuArea = new CssLayout();
	public final CssLayout menuItemsLayout = new CssLayout();
	private final MenuBar userMenu = new MenuBar();

	/* Quick access to user drop down menu */
	private MenuItem userItem;

	/* Caption component for the whole menu */
	private HorizontalLayout logoWrapper;
	//private Image menuImage;

	private Label menuCaption;

	@Autowired
	PodesavanjaProfila pp;
	/**
	 * Constructor for creating a SideMenu component. This method sets up all
	 * the components and styles needed for the side menu.
	 */
	public SideMenu() {
		super();

		addStyleName(ValoTheme.UI_WITH_MENU);
		Responsive.makeResponsive(this);
		setSizeFull();

		menuArea.setPrimaryStyleName("valo-menu");
		menuArea.addStyleName("sidebar");
		menuArea.addStyleName(ValoTheme.MENU_PART);
		menuArea.addStyleName("no-vertical-drag-hints");
		menuArea.addStyleName("no-horizontal-drag-hints");
		menuArea.setWidth(null);
		menuArea.setHeight("100%");

		menuArea.addComponents(buildTitle());
		menuArea.addComponent(buildUserMenu());

		Button valoMenuToggleButton = new Button("Menu", new ClickListener() {
			@Override
			public void buttonClick(final ClickEvent event) {
				if (menuArea.getStyleName().contains(STYLE_VISIBLE)) {
					menuArea.removeStyleName(STYLE_VISIBLE);
				} else {
					menuArea.addStyleName(STYLE_VISIBLE);
				}
			}
		});
		valoMenuToggleButton.setIcon(VaadinIcons.LIST);
		valoMenuToggleButton.addStyleName("valo-menu-toggle");
		valoMenuToggleButton.addStyleName(ValoTheme.BUTTON_BORDERLESS);
		valoMenuToggleButton.addStyleName(ValoTheme.BUTTON_SMALL);
		menuArea.addComponent(valoMenuToggleButton);

		menuItemsLayout.addStyleName("valo-menuitems");
		menuArea.addComponents(
				new Label("&nbsp", com.vaadin.shared.ui.ContentMode.HTML),
				menuItemsLayout);

		contentArea.setPrimaryStyleName("dashboard-panels");
		Responsive.makeResponsive(contentArea);
		contentArea.setSizeFull();

		super.addComponent(menuArea);
		super.addComponent(contentArea);
		setExpandRatio(contentArea, 1);
	}

	
	private Component buildTitle() {
        Label logo = new Label("Domar <strong>IS</strong>",
                ContentMode.HTML);
        logo.setSizeUndefined();
        HorizontalLayout logoWrapper = new HorizontalLayout(logo);
        logoWrapper.setComponentAlignment(logo, Alignment.MIDDLE_CENTER);
        logoWrapper.addStyleName("valo-menu-title");
        return logoWrapper;
    }
	private Component buildUserMenu(){
		final MenuBar settings = new MenuBar();
        settings.addStyleName("user-menu");
        userItem = settings.addItem("", new ThemeResource(
                "img/profile-pic-300px.jpg"), null);
        userItem.addItem("Podešavanja profila", new Command() {
            @Override
            public void menuSelected(final MenuItem selectedItem) {
            	UI.getCurrent().addWindow(pp.getWindow());
            }
        });
        
        userItem.addSeparator();
        userItem.addItem("Izlogujte se", new Command() {
            @Override
            public void menuSelected(final MenuItem selectedItem) {
                VaadinSession.getCurrent().close();
                Page.getCurrent().reload();
                //UI.getCurrent().getNavigator().navigateTo(LoginView.VIEW_NAME);
            }
        });
        return settings;
	}
	/**
	 * Adds a menu entry. The given handler is called when the user clicks the
	 * entry.
	 * 
	 * @param text
	 *            menu text
	 * @param handler
	 *            menu click handler
	 */
	public void addMenuItem(String text, MenuClickHandler handler) {
		addMenuItem(text, null, handler);
	}

	/**
	 * Adds a menu entry with given icon. The given handler is called when the
	 * user clicks the entry.
	 * 
	 * @param text
	 *            menu text
	 * @param icon
	 *            menu icon
	 * @param handler
	 *            menu click handler
	 */
	public void addMenuItem(String text, Resource icon, final MenuClickHandler handler) {
		Button button = new Button(text, new ClickListener() {

			@Override
			public void buttonClick(ClickEvent event) {
				handler.click();
				menuArea.removeStyleName(STYLE_VISIBLE);
			}
		});
		button.setIcon(icon);
		button.setPrimaryStyleName("valo-menu-item");
		menuItemsLayout.addComponent(button);
	}

	/**
	 * 
	 * @param text
	 * @param icon
	 */
	/*public void addSeparator(String text, Resource icon) {
		if (!StringUtil.checkString(text))
			text = "&nbsp";
        Label label = new Label(text, ContentMode.HTML);
        label.setPrimaryStyleName(ValoTheme.MENU_SUBTITLE);
        label.addStyleName(ValoTheme.LABEL_H4);
        label.setSizeUndefined();
        label.setIcon(icon);
        menuItemsLayout.addComponent(label);
	}*/

	/**
	 * Adds a menu entry to the user drop down menu. The given handler is called
	 * when the user clicks the entry.
	 * 
	 * @param text
	 *            menu text
	 * @param handler
	 *            menu click handler
	 */
	public void addUserMenuItem(String text, MenuClickHandler handler) {
		addUserMenuItem(text, null, handler);
	}

	/**
	 * Adds a menu entry to the user drop down menu with given icon. The given
	 * handler is called when the user clicks the entry.
	 * 
	 * @param text
	 *            menu text
	 * @param icon
	 *            menu icon
	 * @param handler
	 *            menu click handler
	 */
	public void addUserMenuItem(String text, Resource icon, final MenuClickHandler handler) {
		if ("Separator".equalsIgnoreCase(text)) {
			userItem.addSeparator();
			return;
		}
		userItem.addItem(text, icon, new Command() {
			@Override
			public void menuSelected(MenuItem selectedItem) {
				handler.click();
			}
		});
	}

	/**
	 * Sets the user name to be displayed in the menu.
	 * 
	 * @param userName
	 *            user name
	 */
	public void setUserName(String userName) {
		if (userName.length()>30)
			userName = userName.substring(0, 30) + "" + userName.substring(30, userName.length());
		userItem.setText(userName);
	}

	/**
	 * Sets the portrait of the user to be displayed in the menu.
	 * 
	 * @param icon
	 *            portrait of the user
	 */
	public void setUserIcon(Resource icon) {
		userItem.setIcon(icon);
	}

	/**
	 * Sets the visibility of the whole user menu. This includes portrait, user
	 * name and the drop down menu.
	 * 
	 * @param visible
	 *            user menu visibility
	 */
	public void setUserMenuVisible(boolean visible) {
		userMenu.setVisible(visible);
	}

	/**
	 * Gets the visibility of the user menu.
	 * 
	 * @return {@code true} if visible; {@code false} if hidden
	 */
	public boolean isUserMenuVisible() {
		return userMenu.isVisible();
	}

	/**
	 * Sets the title text for the menu
	 * 
	 * @param caption
	 *            menu title
	 */
	public void setMenuCaption(String caption) {
		setMenuCaption(caption, null);
	}

	public void setMenuCaption(String caption, Object object) {
		if (menuCaption==null) {
			menuCaption = new Label();
			menuCaption.setWidth("170px");
			menuCaption.setContentMode(ContentMode.HTML);
			logoWrapper.addComponent(menuCaption);
			logoWrapper.setComponentAlignment(menuCaption, Alignment.MIDDLE_CENTER);
		}
		menuCaption.setValue("<strong>" + caption + "</strong>");
	}

	/**
	 * Sets the title caption and logo for the menu
	 * 
	 * @param caption
	 *            menu caption
	 * @param logo
	 *            menu logo
	 */
	/*public void setMenuCaption(String caption, Resource logo) {
		if (menuImage != null) {
			logoWrapper.removeComponent(menuImage);
		}
		menuImage = new Image(caption, logo);
		menuImage.setWidth(30, Unit.PIXELS);
		menuImage.setHeight(30, Unit.PIXELS);
		logoWrapper.addComponent(menuImage);
	}*/

	/**
	 * Removes all content from the user drop down menu.
	 */
	public void clearUserMenu() {
		userItem.removeChildren();
	}

	/**
	 * Adds a menu entry to navigate to given navigation state.
	 * 
	 * @param text
	 *            text to display in menu
	 * @param navigationState
	 *            state to navigate to
	 */
	public void addNavigation(String text, String navigationState) {
		addNavigation(text, null, navigationState);
	}

	/**
	 * Adds a menu entry with given icon to navigate to given navigation state.
	 * 
	 * @param text
	 *            text to display in menu
	 * @param icon
	 *            icon to display in menu
	 * @param navigationState
	 *            state to navigate to
	 */
	public void addNavigation(String text, Resource icon, final String navigationState) {
		addMenuItem(text, icon, new MenuClickHandler() {

			@Override
			public void click() {
				getUI().getNavigator().navigateTo(navigationState);
			}
		});
	}

	/**
	 * Removes all components from the content area.
	 */
	@Override
	public void removeAllComponents() {
		contentArea.removeAllComponents();
	}

	/**
	 * Adds a component to the content area.
	 */
	@Override
	public void addComponent(Component c) {
		contentArea.addComponent(c);
	}

	/**
	 * Removes all content from the content area and replaces everything with
	 * given component.
	 * 
	 * @param content
	 *            new content to display
	 */
	public void setContent(Component content) {
		contentArea.removeAllComponents();
		contentArea.addComponent(content);
	}
}