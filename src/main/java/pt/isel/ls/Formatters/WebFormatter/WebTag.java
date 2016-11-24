package pt.isel.ls.Formatters.WebFormatter;


import java.util.HashMap;
import java.util.LinkedList;
import java.util.StringJoiner;
import java.util.stream.Collectors;

public class WebTag implements WebInterface {
    String tagName, data;
    HashMap<String,String> attr = new HashMap<>();
    LinkedList<WebElem> contents = new LinkedList<>();
    boolean newline = false;

    // a webcontent could be a tag or a string
    // only a webtag permits add content in it

    public WebTag() { } // simple text

    public WebTag(String tagName) { this.tagName = tagName; } // html tag

    @Override
    public WebTag setData(String data){
        contents.add(new WebElem(new WebText().setData(data)));
        return this;
    }

    @Override
    public Object getData() {
        return this;
    }

    public WebTag setAttr(String key, String value){
        // only add atributes to tags
        if(this.tagName != null) attr.put(key, value);
        return this;
    }

    public WebTag addContent(WebInterface cnt){
        contents.add(new WebElem(cnt));
        return this;
    }

    private String genAttrStr(){
        String attrStr = "";
        if(!attr.isEmpty()){
            attrStr = " " + attr.entrySet()
                    .stream()
                    .map(x -> x.getKey()+"='"+x.getValue()+"'")
                    .collect(Collectors.joining(" "));
        }
        return  attrStr;
    }

    private String genDataStr(){
        StringJoiner joiner = new StringJoiner(" ");
        contents.forEach(x ->{
            joiner.add(x.getContent().toString());
        });
        return joiner.toString();
    }

    public WebTag nl(){
        newline = true;
        return this;
    }

    @Override
    public String toString() {
        String nl = newline ? "\n" : "";
        return nl+ "<"+tagName+genAttrStr() +">"+
               genDataStr() + "</"+tagName+">"+ nl;
    }
}
