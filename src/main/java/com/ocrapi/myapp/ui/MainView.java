package com.ocrapi.myapp.ui;

import com.ocrapi.myapp.model.Lines;
import com.ocrapi.myapp.model.Words;
import com.ocrapi.myapp.services.FileUploader;
import com.ocrapi.myapp.services.GreetService;
import com.ocrapi.myapp.services.OcrService;
import com.vaadin.flow.component.*;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.*;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.component.upload.Upload;
import com.vaadin.flow.component.upload.receivers.MemoryBuffer;
import com.vaadin.flow.dom.Element;
import com.vaadin.flow.internal.MessageDigestUtil;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.PWA;
import com.vaadin.flow.server.StreamResource;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;

import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * A sample Vaadin view class.
 * <p>
 * To implement a Vaadin view just extend any Vaadin component and
 * use @Route annotation to announce it in a URL as a Spring managed
 * bean.
 * Use the @PWA annotation make the application installable on phones,
 * tablets and some desktop browsers.
 * <p>
 * A new instance of this class is created for every new user and every
 * browser tab/window.
 */
@Route
@PWA(name = "Vaadin Application",
        shortName = "Vaadin App",
        description = "This is an example Vaadin application.",
        enableInstallPrompt = false)
@CssImport("./styles/shared-styles.css")
@CssImport(value = "./styles/vaadin-text-field-styles.css", themeFor = "vaadin-text-field")
public class MainView extends HorizontalLayout {

    @Autowired
    GreetService service;
    @Autowired
    OcrService ocrService;

    VerticalLayout leftVerticalLayout = new VerticalLayout();
    List<Words> words = new ArrayList<>();
    Grid<Words> grid =new Grid<>();
    Button navButton = new Button("DomainSpecificPage", event -> UI.getCurrent().navigate("domainpage"));
    Image logo = new Image("img/2idLogo.PNG","logo");

    public MainView() {
        VerticalLayout rightView = righView(new VerticalLayout());
        VerticalLayout leftView = leftView(leftVerticalLayout, words, grid,navButton);
        add(leftView,rightView);
    }

    public VerticalLayout leftView(VerticalLayout verticalLayout, List<Words> wordsList, Grid<Words> newGrid,Button newButton){

        Text title = new Text("OCR API DEMO");
        System.out.println("words on the view : "+wordsList);
        Grid<Words> viewGrid = dataGrid(newGrid,wordsList);
        logo.setHeight("50px");
        logo.setWidth("100px");
        verticalLayout.add(title,viewGrid,logo);
        return verticalLayout;
    }

    public VerticalLayout righView(VerticalLayout verticalLayout){

        Label subjectText = new Label("RESOURCE IMAGE");

        //**************************************************
        Div output = new Div();
        MemoryBuffer buffer = new MemoryBuffer();
        Upload upload = new Upload(buffer);
        FileUploader fileUploader = new FileUploader();

        upload.addSucceededListener(event -> {
            if (buffer.getFileName() != null && buffer.getFileName() != ""){
                String fileName =  buffer.getFileName();
                File file = new File(fileName);
                String path = file.getAbsolutePath();
                OcrService.ocrCall(fileName);
                //OcrService.multipartTest(path);
                System.out.println("file path : "+ path);
                words = OcrService.getAllWords();
                leftView(leftVerticalLayout,words, grid,navButton);

                System.out.println("number of words : "+OcrService.getAllWords().size());
            }
            Component component = fileUploader.createComponent(event.getMIMEType(),
                    event.getFileName(), buffer.getInputStream());
            fileUploader.showOutput(event.getFileName(), component, output);

        });
       //******************************************************
        // Button imageLoader = new Button("Load Image",
         //       e -> Notification.show(service.greet(textField.getValue())));
        verticalLayout.add(subjectText, upload, output);
        return verticalLayout;
    }

    public Grid<Words> dataGrid(Grid<Words> wordsGrid,List<Words> wordsList){

        for(Grid.Column column: wordsGrid.getColumns()) {
            wordsGrid.removeColumn(column);
        }
        if(!wordsList.isEmpty()) {
            List<Words> listWords = wordsList;
            wordsGrid.setItems(listWords);
            wordsGrid.addColumn(Words::getText).setHeader("WORDS");
            wordsGrid.getDataProvider().refreshAll();
        }
        return wordsGrid;
    }
}
