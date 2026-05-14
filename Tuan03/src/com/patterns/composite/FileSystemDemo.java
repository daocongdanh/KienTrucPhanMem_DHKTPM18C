package com.patterns.composite;

import java.util.ArrayList;
import java.util.List;

public class FileSystemDemo {
    public static void main(String[] args) {
        FolderComposite root = new FolderComposite("root");
        root.add(new FileLeaf("readme.txt", 1200));

        FolderComposite images = new FolderComposite("images");
        images.add(new FileLeaf("logo.png", 2048));
        images.add(new FileLeaf("banner.jpg", 4096));
        root.add(images);

        FolderComposite docs = new FolderComposite("docs");
        docs.add(new FileLeaf("manual.pdf", 12000));
        root.add(docs);

        root.display("");
        System.out.println("Total size: " + root.getSize());
    }
}

interface FileSystemComponent {
    String getName();
    long getSize();
    void display(String indent);
}

class FileLeaf implements FileSystemComponent {
    private final String name;
    private final long size;

    FileLeaf(String name, long size) {
        this.name = name;
        this.size = size;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public long getSize() {
        return size;
    }

    @Override
    public void display(String indent) {
        System.out.println(indent + "- File: " + name + " (" + size + ")");
    }
}

class FolderComposite implements FileSystemComponent {
    private final String name;
    private final List<FileSystemComponent> children = new ArrayList<>();

    FolderComposite(String name) {
        this.name = name;
    }

    public void add(FileSystemComponent component) {
        children.add(component);
    }

    public void remove(FileSystemComponent component) {
        children.remove(component);
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public long getSize() {
        long total = 0;
        for (FileSystemComponent child : children) {
            total += child.getSize();
        }
        return total;
    }

    @Override
    public void display(String indent) {
        System.out.println(indent + "+ Folder: " + name);
        String childIndent = indent + "  ";
        for (FileSystemComponent child : children) {
            child.display(childIndent);
        }
    }
}
