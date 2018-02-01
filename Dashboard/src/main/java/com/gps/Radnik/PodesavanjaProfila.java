package com.gps.Radnik;


import org.springframework.beans.factory.annotation.Autowired;

import com.gps.Service.RadnikService;
import com.vaadin.annotations.Theme;
import com.vaadin.data.Binder;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.server.Page;
import com.vaadin.server.Responsive;
import com.vaadin.server.ThemeResource;
import com.vaadin.shared.Position;
import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.spring.annotation.UIScope;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Component;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Image;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.PasswordField;
import com.vaadin.ui.TabSheet;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;
import com.vaadin.ui.themes.ValoTheme;

@UIScope
@SpringComponent
@Theme("dashboard")
@SuppressWarnings("serial")
public class PodesavanjaProfila extends Window {

    private TextField imeIPrezime;
    private TextField adresa;
    private TextField brojTelefona;
    private TextField jmbg;
    private TextField kvalifikacija;
    private TextField email;
    private TextField lozinka;
    private PasswordField novaLozinka;
    private PasswordField ponoviLozinku;

    private Binder<Radnik> binder = new Binder<>(Radnik.class);
    
    private Radnik r = new Radnik();
    
    private final RadnikService radnikService;
    
    @Autowired
    public PodesavanjaProfila(RadnikService radnikService) {
    	super();
    	this.radnikService = radnikService;
        addStyleName("profile-window");
        Responsive.makeResponsive(this);
        
        setModal(true);
        setClosable(true);
        setResizable(false);
        setHeight("60%");
        setWidth("60%");
        //setHeight(90.0f, Unit.PERCENTAGE);

        VerticalLayout content = new VerticalLayout();
        content.setSizeFull();
        content.setMargin(new MarginInfo(true, false, false, false));
        setContent(content);

        TabSheet detailsWrapper = new TabSheet();
        detailsWrapper.setSizeFull();
        detailsWrapper.addStyleName(ValoTheme.TABSHEET_PADDED_TABBAR);
        detailsWrapper.addStyleName(ValoTheme.TABSHEET_ICONS_ON_TOP);
        detailsWrapper.addStyleName(ValoTheme.TABSHEET_CENTERED_TABS);
        content.addComponent(detailsWrapper);
        content.setExpandRatio(detailsWrapper, 1f);

        detailsWrapper.addComponent(buildProfileTab());
        
        
        r = radnikService.findRadnik();
        binder.bindInstanceFields(this);
        binder.setBean(r);
        
        
        content.addComponent(buildFooter());

    }
    
	private Component buildProfileTab() {
        HorizontalLayout root = new HorizontalLayout();
        root.setCaption("Profil");
        root.setIcon(VaadinIcons.USER);
        root.setWidth(100.0f, Unit.PERCENTAGE);
        root.setSpacing(true);
        root.setMargin(true);
        root.addStyleName("profile-form");

        VerticalLayout pic = new VerticalLayout();
        pic.setSizeUndefined();
        pic.setSpacing(true);
        Image profilePic = new Image(null, new ThemeResource(
                "img/profile-pic-300px.jpg"));
        profilePic.setWidth(100.0f, Unit.PIXELS);
        pic.addComponent(profilePic);

        Button upload = new Button("Upload…");
        upload.addStyleName(ValoTheme.BUTTON_TINY);
        upload.setEnabled(false);
        pic.addComponent(upload);

        root.addComponent(pic);

        FormLayout details = new FormLayout();
        details.addStyleName(ValoTheme.FORMLAYOUT_LIGHT);
        root.addComponent(details);
        root.setExpandRatio(details, 1);

        Label section = new Label("Osnovni korisnički podaci");
        section.addStyleName(ValoTheme.LABEL_H4);
        section.addStyleName(ValoTheme.LABEL_COLORED);
        details.addComponent(section);
        
        imeIPrezime = new TextField("Ime");
        //imeIPrezime.setValue(VaadinSession.getCurrent().getAttribute("Ime").toString());
        imeIPrezime.setReadOnly(true);
        details.addComponent(imeIPrezime);
        
        adresa = new TextField("Adresa");
        adresa.setReadOnly(true);
        details.addComponent(adresa);
        
        brojTelefona = new TextField("Broj telefona");
        brojTelefona.setReadOnly(true);
        details.addComponent(brojTelefona);
        
        jmbg = new TextField("Matični broj");
        jmbg.setReadOnly(true);
        details.addComponent(jmbg);
        
        kvalifikacija = new TextField("Uloga");
        kvalifikacija.setReadOnly(true);
        details.addComponent(kvalifikacija);
        
        email = new TextField("Korisničko ime");
        email.setWidth("100%");
        email.setReadOnly(true);
        details.addComponent(email);
        
        lozinka = new TextField("Lozinka");
        lozinka.setWidth("100%");
        lozinka.setReadOnly(true);
        details.addComponent(lozinka);

        Label sectionDva = new Label("Podešavanje šifra korisnika");
        sectionDva.addStyleName(ValoTheme.LABEL_H4);
        sectionDva.addStyleName(ValoTheme.LABEL_COLORED);
        details.addComponent(sectionDva);

        novaLozinka = new PasswordField("Nova lozinka");
        novaLozinka.setWidth("100%");
        details.addComponent(novaLozinka);
        
        ponoviLozinku = new PasswordField("Ponovi lozinku");
        ponoviLozinku.setWidth("100%");
        details.addComponent(ponoviLozinku);
        
        
        return root;
    }

    private Component buildFooter() {
        HorizontalLayout footer = new HorizontalLayout();
        footer.addStyleName(ValoTheme.WINDOW_BOTTOM_TOOLBAR);
        footer.setWidth(100.0f, Unit.PERCENTAGE);

        Button ok = new Button("OK");
        ok.addStyleName(ValoTheme.BUTTON_PRIMARY);
        ok.setIcon(VaadinIcons.DISC);

        ok.addClickListener(event->{
    	   try{
    		  
    		   if( novaLozinka.getValue().toString().equals(ponoviLozinku.getValue().toString())){
    			   radnikService.sacuvajSifru(novaLozinka.getValue().toString(), r.getId());
        		   ((UI) getWindow().getParent()).removeWindow(getWindow());
        		   Notification success = new Notification(
                           "Profil uspješno ažuriran");
                   success.setDelayMsec(2000);
                   success.setStyleName("bar success small");
                   success.setPosition(Position.BOTTOM_CENTER);
                   success.show(Page.getCurrent());
    		   }else{
    			   Notification.show("Šifre se moraju podudarati! ");
    		   }
    		  
    	   }catch(Exception ec){
    		   ec.printStackTrace();
    	   }
       });
   
        ok.focus();
        footer.addComponents(ok);
        footer.addStyleName(ValoTheme.LAYOUT_COMPONENT_GROUP);
        footer.setComponentAlignment(ok, Alignment.TOP_RIGHT);
        return footer;
    }
    
    public PodesavanjaProfila getWindow(){
    	return this;
    }
}
