package com.patterns.composite;

import java.util.ArrayList;
import java.util.List;

public class UIDemo {
    public static void main(String[] args) {
        UIContainer root = new UIContainer("Screen");
        root.add(new NavigationBar("TopNav")
            .addChild(new Button("Home"))
            .addChild(new Button("Profile"))
            .addChild(new Button("Settings")));

        Dialog settingsDialog = new Dialog("SettingsDialog");
        settingsDialog.add(new TextField("Email"));
        settingsDialog.add(new Button("Save"));
        settingsDialog.add(new Button("Cancel"));

        root.add(settingsDialog);

        root.render("");
    }
}

interface UIComponent {
    void render(String indent);
}

class UIContainer implements UIComponent {
    private final String name;
    private final List<UIComponent> children = new ArrayList<>();

    UIContainer(String name) {
        this.name = name;
    }

    public UIContainer add(UIComponent component) {
        children.add(component);
        return this;
    }

    @Override
    public void render(String indent) {
        System.out.println(indent + "+ Container: " + name);
        String childIndent = indent + "  ";
        for (UIComponent child : children) {
            child.render(childIndent);
        }
    }
}

class Dialog extends UIContainer {
    Dialog(String name) {
        super("Dialog: " + name);
    }
}

class NavigationBar extends UIContainer {
    NavigationBar(String name) {
        super("NavBar: " + name);
    }

    public NavigationBar addChild(UIComponent component) {
        add(component);
        return this;
    }
}

class Button implements UIComponent {
    private final String label;

    Button(String label) {
        this.label = label;
    }

    @Override
    public void render(String indent) {
        System.out.println(indent + "- Button: " + label);
    }
}

class TextField implements UIComponent {
    private final String label;

    TextField(String label) {
        this.label = label;
    }

    @Override
    public void render(String indent) {
        System.out.println(indent + "- TextField: " + label);
    }
}
