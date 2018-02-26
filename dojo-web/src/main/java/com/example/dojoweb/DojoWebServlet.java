package com.example.dojoweb;

import javax.servlet.annotation.WebInitParam;
import javax.servlet.annotation.WebServlet;

@WebServlet(
    asyncSupported=false,
    urlPatterns={"/*","/VAADIN/*"},
    initParams={
        @WebInitParam(name="ui", value="com.example.dojoweb.DojoWebUI")
    })
public class DojoWebServlet extends com.vaadin.server.VaadinServlet { }
