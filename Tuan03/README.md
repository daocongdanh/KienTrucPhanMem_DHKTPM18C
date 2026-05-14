# Tuan03 - Design Patterns Practice

This folder contains examples for Composite, Observer, Adapter, and a Library System that combines multiple patterns. Diagrams come before code as requested.

## Composite Pattern (File System)

```mermaid
classDiagram
    class FileSystemComponent {
        <<interface>>
        +getName()
        +getSize()
        +display(indent)
    }
    class FileLeaf {
        -name
        -size
        +getName()
        +getSize()
        +display(indent)
    }
    class FolderComposite {
        -name
        -children
        +add(child)
        +remove(child)
        +getName()
        +getSize()
        +display(indent)
    }
    FileSystemComponent <|.. FileLeaf
    FileSystemComponent <|.. FolderComposite
    FolderComposite o-- FileSystemComponent
```

## Composite Pattern (UI Components)

```mermaid
classDiagram
    class UIComponent {
        <<interface>>
        +render(indent)
    }
    class Button {
        -label
        +render(indent)
    }
    class TextField {
        -label
        +render(indent)
    }
    class UIContainer {
        -name
        -children
        +add(component)
        +render(indent)
    }
    class Dialog
    class NavigationBar

    UIComponent <|.. Button
    UIComponent <|.. TextField
    UIComponent <|.. UIContainer
    UIContainer <|-- Dialog
    UIContainer <|-- NavigationBar
    UIContainer o-- UIComponent
```

## Observer Pattern (Stock Price)

```mermaid
classDiagram
    class Subject {
        <<interface>>
        +addObserver(o)
        +removeObserver(o)
        +notifyObservers(event)
    }
    class Observer {
        <<interface>>
        +update(event, source)
    }
    class Stock {
        -symbol
        -price
        +setPrice(price)
    }
    class Investor {
        -name
        +update(event, source)
    }

    Subject <|.. Stock
    Observer <|.. Investor
    Stock o-- Observer
```

## Observer Pattern (Task Status)

```mermaid
classDiagram
    class Subject {
        <<interface>>
        +addObserver(o)
        +removeObserver(o)
        +notifyObservers(event)
    }
    class Observer {
        <<interface>>
        +update(event, source)
    }
    class Task {
        -id
        -status
        +setStatus(status)
    }
    class TeamMember {
        -name
        +update(event, source)
    }

    Subject <|.. Task
    Observer <|.. TeamMember
    Task o-- Observer
```

## Adapter Pattern (XML <-> JSON)

```mermaid
classDiagram
    class JsonService {
        <<interface>>
        +send(json)
        +fetch()
    }
    class XmlService {
        <<interface>>
        +send(xml)
        +fetch()
    }
    class JsonWebService
    class XmlToJsonAdapter
    class XmlJsonConverter

    JsonService <|.. JsonWebService
    XmlService <|.. XmlToJsonAdapter
    XmlToJsonAdapter --> JsonService
    XmlToJsonAdapter --> XmlJsonConverter
```

## Library System (Multiple Patterns)

```mermaid
classDiagram
    class Library {
        <<Singleton>>
        -instance
        +getInstance()
        +addBook(book)
        +borrowBook(id, borrowing)
        +returnBook(id)
        +search(strategy, query)
    }
    class BookFactory {
        <<abstract>>
        +create(id, title, author, genre)
    }
    class PaperBookFactory
    class EBookFactory
    class AudioBookFactory
    class Book {
        <<abstract>>
    }
    class PaperBook
    class EBook
    class AudioBook
    class SearchStrategy {
        <<interface>>
        +search(books, query)
    }
    class TitleSearch
    class AuthorSearch
    class GenreSearch
    class LibraryObserver {
        <<interface>>
        +onNotify(event, book)
    }
    class Borrowing {
        <<interface>>
        +getDescription()
        +getDays()
    }
    class BasicBorrowing
    class BorrowingDecorator
    class ExtendedLoanDecorator
    class SpecialEditionDecorator

    Library --> BookFactory
    BookFactory <|-- PaperBookFactory
    BookFactory <|-- EBookFactory
    BookFactory <|-- AudioBookFactory

    Book <|-- PaperBook
    Book <|-- EBook
    Book <|-- AudioBook

    Library --> SearchStrategy
    SearchStrategy <|.. TitleSearch
    SearchStrategy <|.. AuthorSearch
    SearchStrategy <|.. GenreSearch

    Library o-- LibraryObserver

    Borrowing <|.. BasicBorrowing
    Borrowing <|.. BorrowingDecorator
    BorrowingDecorator <|-- ExtendedLoanDecorator
    BorrowingDecorator <|-- SpecialEditionDecorator

    Library --> Borrowing
```

## Run Examples

```bash
# from Tuan03
javac -d out $(find src -name "*.java")
java -cp out com.patterns.composite.FileSystemDemo
java -cp out com.patterns.composite.UIDemo
java -cp out com.patterns.observer.ObserverDemos
java -cp out com.patterns.adapter.AdapterDemo
java -cp out com.patterns.library.LibraryDemo
```
