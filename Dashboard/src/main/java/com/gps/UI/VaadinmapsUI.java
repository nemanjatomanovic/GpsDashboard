package com.gps.UI;

import com.vaadin.annotations.Theme;
import com.vaadin.navigator.View;
import com.vaadin.server.FontAwesome;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.spring.annotation.UIScope;
import com.vaadin.tapio.googlemaps.GoogleMap;
import com.vaadin.tapio.googlemaps.client.LatLon;
import com.vaadin.tapio.googlemaps.client.overlays.GoogleMapMarker;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Panel;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;

@SuppressWarnings("serial")
@UIScope
@SpringComponent
@Theme("dashboard")
public class VaadinmapsUI extends VerticalLayout implements View{

    private GoogleMap googleMap;
    private final String apiKey = " AIzaSyDMf8kU5MfeC3NhFhIHN5xP4f1Ujn6eL7Q";

	public VaadinmapsUI (){
        VerticalLayout rootLayout = new VerticalLayout();
        rootLayout.setSizeFull();
        addComponent(rootLayout);

        googleMap = new GoogleMap(apiKey, null, null);
        googleMap.setZoom(10);
        googleMap.setSizeFull();
        googleMap.setMinZoom(4);
        googleMap.setMaxZoom(16);

        Panel mapsPanel = new Panel();
        mapsPanel.setSizeFull();
        mapsPanel.setContent(googleMap);
        rootLayout.addComponent(mapsPanel);

        Window mapToolBox = new Window("Map Tool Box");
        mapToolBox.setClosable(false);
        mapToolBox.setResizable(false);
        mapToolBox.setPosition(10, 100);
        mapToolBox.setWidth("350px");
        mapToolBox.setHeight("520px");
        mapToolBox.addStyleName("mywindowstyle");
        UI.getCurrent().addWindow(mapToolBox);

        HorizontalLayout latlonLayout = new HorizontalLayout();
        latlonLayout.setSpacing(true);
        
        TextField latitude = new TextField("Lat");
        latitude.setWidth("100px");
//        latitude.setNullSettingAllowed(true);
//        latitude.setNullRepresentation("0.0");
        
        TextField longitude = new TextField("Long");
        longitude.setWidth("100px");
//        longitude.setNullSettingAllowed(true);
//        longitude.setNullRepresentation("0.0");
        
        latlonLayout.addComponent(latitude);
        latlonLayout.addComponent(longitude);
        
        HorizontalLayout infoLayout = new HorizontalLayout();
        infoLayout.setSpacing(true);
        
        Label currentLat = new Label();
        currentLat.setCaption("Trenutna Latitude");
        
        Label currentLon = new Label();
        currentLon.setCaption("Trenutna Longitude");

        infoLayout.addComponent(currentLat);
        infoLayout.addComponent(currentLon);
        
        TextField markerName = new TextField("Naziv markera");

        Label latErrMsg = new Label();
        latErrMsg.addStyleName("mylabelstyle");
        Label lonErrMsg = new Label();
        lonErrMsg.addStyleName("mylabelstyle");
        
        Button.ClickListener addMarkerListener = new ClickListener() {
			
			@Override
			public void buttonClick(ClickEvent event) {
				String mName = markerName.getValue();
				if(mName.isEmpty()){
					mName = "Marker";
				}
		
				Double dLat = 0.0; 
				Double dLon = 0.0; 
				dLat = Double.valueOf(currentLat.getValue());
				dLon = Double.valueOf(currentLon.getValue());

				GoogleMapMarker customMarker = new GoogleMapMarker(mName, new LatLon(dLat, dLon),true, null);
				googleMap.addMarker(customMarker);
			}
		};
        
        Button addMarker = new Button("Dodaj marker", FontAwesome.ANCHOR);
        addMarker.addClickListener(addMarkerListener);
        
        Button.ClickListener moveView = new ClickListener() {
			
			@Override
			public void buttonClick(ClickEvent event) {
				Boolean val = true;
				Double dLat = 0.0; 
				Double dLon = 0.0; 

				try {
					dLat = Double.valueOf(latitude.getValue());
				} catch (Exception e) {
					val = false;
					latErrMsg.setValue("Latitude is not a valid number");
				}
				try {
					dLon = Double.valueOf(longitude.getValue());
				} catch (Exception e) {
					val = false;
					lonErrMsg.setValue("Longitude is not a valid number");
				}
				
				if(val){
					latErrMsg.setValue("");
					lonErrMsg.setValue("");
					if((dLat<= -85.0) || (dLat >= 85.0)){
						val = false;
						latErrMsg.setValue("Latitude must be between -85.0 and 85.0");
					}
					if((dLon<= -180.0) || (dLon >= 180.0)){
						val = false;
						lonErrMsg.setValue("Longitude  must be between -180.0 and 180.0");
					}
				}
				
				if(val){
					latErrMsg.setValue("");
					lonErrMsg.setValue("");
	                googleMap.setCenter(new LatLon(dLat, dLon));
	                googleMap.setZoom(12);
	                currentLat.setValue(latitude.getValue());
	                currentLon.setValue(longitude.getValue());
				}
			}
		};
        
        Button moveButton = new Button("Pronadji marker", FontAwesome.BULLSEYE);
        moveButton.addClickListener(moveView);

        Button.ClickListener clearMarkerListener = new ClickListener() {
			
			@Override
			public void buttonClick(ClickEvent event) {
                googleMap.clearMarkers();
			}
		};

		Button clearMarkersButton = new Button("Izbrisi markere", FontAwesome.REMOVE);
        clearMarkersButton.addClickListener(clearMarkerListener);

        Double newyorkLat = 40.7128;
        Double newyorkLon = -74.0059;
        googleMap.setCenter(new LatLon(40.7128, -74.0059));
		GoogleMapMarker newyorkMarker = new GoogleMapMarker("Mjesto", new LatLon(newyorkLat, newyorkLon),true, null);
		googleMap.addMarker(newyorkMarker);
		latitude.setValue(newyorkLat.toString());
		longitude.setValue(newyorkLon.toString());
		currentLat.setValue(latitude.getValue());
		currentLon.setValue(longitude.getValue());

        VerticalLayout toolLayout = new VerticalLayout();
        toolLayout.setMargin(true);
        toolLayout.setSpacing(true);
        mapToolBox.setContent(toolLayout);
        toolLayout.addComponent(clearMarkersButton);
        toolLayout.addComponent(latlonLayout);
        toolLayout.addComponent(moveButton);
        toolLayout.addComponent(infoLayout);
        toolLayout.addComponent(markerName);
        toolLayout.addComponent(addMarker);
        toolLayout.addComponent(latErrMsg);
        toolLayout.addComponent(lonErrMsg);
	}
	
	public VaadinmapsUI getForm(){
		return this;
	}
	
}