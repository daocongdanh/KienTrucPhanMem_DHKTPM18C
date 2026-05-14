package com.patterns.adapter;

public class AdapterDemo {
    public static void main(String[] args) {
        JsonService webService = new JsonWebService();
        XmlService xmlClientAdapter = new XmlToJsonAdapter(webService);

        XmlData xmlRequest = new XmlData("<data>order-123</data>");
        xmlClientAdapter.send(xmlRequest);

        XmlData xmlResponse = xmlClientAdapter.fetch();
        System.out.println("Client received XML: " + xmlResponse.getRaw());
    }
}

class JsonData {
    private final String raw;

    JsonData(String raw) {
        this.raw = raw;
    }

    public String getRaw() {
        return raw;
    }
}

class XmlData {
    private final String raw;

    XmlData(String raw) {
        this.raw = raw;
    }

    public String getRaw() {
        return raw;
    }
}

interface JsonService {
    void send(JsonData data);
    JsonData fetch();
}

interface XmlService {
    void send(XmlData data);
    XmlData fetch();
}

class JsonWebService implements JsonService {
    @Override
    public void send(JsonData data) {
        System.out.println("Web service received JSON: " + data.getRaw());
    }

    @Override
    public JsonData fetch() {
        return new JsonData("{\"data\":\"ok\"}");
    }
}

class XmlToJsonAdapter implements XmlService {
    private final JsonService jsonService;

    XmlToJsonAdapter(JsonService jsonService) {
        this.jsonService = jsonService;
    }

    @Override
    public void send(XmlData data) {
        JsonData json = XmlJsonConverter.toJson(data);
        jsonService.send(json);
    }

    @Override
    public XmlData fetch() {
        JsonData json = jsonService.fetch();
        return XmlJsonConverter.toXml(json);
    }
}

class XmlJsonConverter {
    static JsonData toJson(XmlData xml) {
        String content = extractBetween(xml.getRaw(), "<data>", "</data>");
        if (content.isEmpty()) {
            content = xml.getRaw();
        }
        String json = "{\"data\":\"" + content + "\"}";
        return new JsonData(json);
    }

    static XmlData toXml(JsonData json) {
        String content = extractBetween(json.getRaw(), "\"data\":\"", "\"");
        if (content.isEmpty()) {
            content = json.getRaw();
        }
        String xml = "<data>" + content + "</data>";
        return new XmlData(xml);
    }

    private static String extractBetween(String raw, String start, String end) {
        int startIndex = raw.indexOf(start);
        if (startIndex < 0) {
            return "";
        }
        int from = startIndex + start.length();
        int endIndex = raw.indexOf(end, from);
        if (endIndex < 0) {
            return "";
        }
        return raw.substring(from, endIndex);
    }
}
