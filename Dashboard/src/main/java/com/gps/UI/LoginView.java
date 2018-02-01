package com.gps.UI;

import java.util.List;

import com.gps.Radnik.Radnik;
import com.gps.Repository.RadnikRepository;
import com.vaadin.annotations.Theme;
import com.vaadin.event.ShortcutAction.KeyCode;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.server.Page;
import com.vaadin.server.Responsive;
import com.vaadin.server.ThemeResource;
import com.vaadin.server.VaadinSession;
import com.vaadin.shared.Position;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.spring.annotation.UIScope;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.CheckBox;
import com.vaadin.ui.Component;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Image;
import com.vaadin.ui.Notification;
import com.vaadin.ui.PasswordField;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;

@UIScope
@SpringView(name=LoginView.VIEW_NAME)
@SuppressWarnings("serial")
@Theme("dashboard")
public class LoginView extends VerticalLayout implements View{

	public static final String VIEW_NAME = "loginView";
	private final RadnikRepository radnikRep;
	//private Radnik radnik;
	
    public LoginView(RadnikRepository radnikRep) {
    	super();
    	this.radnikRep = radnikRep;
    	setSizeFull();

        Component loginForm = buildLoginForm();
        addComponent(loginForm);
        setComponentAlignment(loginForm, Alignment.MIDDLE_CENTER);
    }

    private Component buildLoginForm() {
        final VerticalLayout loginPanel = new VerticalLayout();
        loginPanel.setSizeUndefined();
        loginPanel.setSpacing(true);
        Responsive.makeResponsive(loginPanel);
        loginPanel.addStyleName("login-panel");

        loginPanel.addComponent(buildLabels());
        loginPanel.addComponent(buildFields());
        loginPanel.addComponent(new CheckBox("Remember me", true));
        loginPanel.setDefaultComponentAlignment(Alignment.MIDDLE_CENTER);
        return loginPanel;
    }

    private Component buildFields() {
        HorizontalLayout fields = new HorizontalLayout();
        fields.setSpacing(true);
        fields.addStyleName("fields");

        final TextField username = new TextField("Korisničko ime");
        username.setIcon(VaadinIcons.USER);
        username.addStyleName(ValoTheme.TEXTFIELD_INLINE_ICON);
        username.focus();

        final PasswordField password = new PasswordField("Lozinka");
        password.setIcon(VaadinIcons.LOCK);
        password.addStyleName(ValoTheme.TEXTFIELD_INLINE_ICON);

        final Button signin = new Button("Sign In");
        signin.addStyleName(ValoTheme.BUTTON_PRIMARY);
        signin.setClickShortcut(KeyCode.ENTER);

        fields.addComponents(username, password, signin);
        fields.setComponentAlignment(signin, Alignment.BOTTOM_LEFT);

        signin.addClickListener(new ClickListener() {
            @Override
            public void buttonClick(final ClickEvent event) {
            	List<Radnik> radnik = radnikRep.findByKorisnickoimeAndLozinka(username.getValue(), password.getValue());
            	if(!radnik.isEmpty()){
            		VaadinSession.getCurrent().setAttribute("Ime", radnik.iterator().next().getIme());
        			VaadinSession.getCurrent().setAttribute("Id", radnik.iterator().next().getId());
        			UI.getCurrent().getNavigator().navigateTo(MainUI.VIEW_NAME);
            	}else{
            		Notification.show("Ne postoji radnik za unesene parametre!");
            	}
               
            }
        });
        return fields;
    }

    private Component buildLabels() {
        CssLayout labels = new CssLayout();
        labels.addStyleName("labels");
        Image logo = new Image();
        logo.setSource(new ThemeResource("img/logo.png"));
        logo.setWidth(50, Unit.PERCENTAGE);
		logo.setHeight(50, Unit.PERCENTAGE);
        labels.addComponent(logo);
        return labels;
    }

	@Override
	public void enter(ViewChangeEvent event) {
			Notification notification = new Notification(
			        "Dobrodošli na login stranicu GPS tracking-a");
			notification
			        .setDescription("<span>Ukoliko se prvi put logujete, kao lozinku koristite broj jedan (1)</span>");
			notification.setHtmlContentAllowed(true);
			notification.setStyleName("tray dark small closable login-help");
			notification.setPosition(Position.BOTTOM_CENTER);
			notification.setDelayMsec(5000);
			notification.show(Page.getCurrent());
		
	}
}
