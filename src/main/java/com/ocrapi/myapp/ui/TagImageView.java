package com.ocrapi.myapp.ui;

import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;

@Route(value = "tagimagepage")
public class TagImageView extends HorizontalLayout {

    public TagImageView(){
        VerticalLayout view1 = view1(new VerticalLayout());
        VerticalLayout view2 = view2(new VerticalLayout());
        //Button navButton = new Button("DomainSpecificPage", event -> UI.getCurrent().navigate(""));
        add(view1,view2);
    }

    public VerticalLayout view1(VerticalLayout verticalLayout){
        Text text = new Text("Recognize Domain Specific Content API");
        Button navButton = new Button("DomainSpecificPage", event -> UI.getCurrent().navigate(""));
        verticalLayout.add(text,navButton);
        return verticalLayout;
    }

    public VerticalLayout view2(VerticalLayout verticalLayout){
        Image image = new Image("https://www.thenational.ae/image/policy:1.250975:1499351578/image/jpeg.jpg?f=16x9&w=1200&$p$f$w=dfa40e8", "TestImage");
        verticalLayout.add(image);
        return verticalLayout;
    }

}
