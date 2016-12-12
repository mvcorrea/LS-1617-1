package pt.isel.ls.Formatters.WebFormatter;


import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.LinkedList;
import java.util.StringJoiner;
import java.util.stream.Collectors;

public class WebDocument {
    public String document;
    LinkedList<WebElem> body = new LinkedList<>();

    public WebDocument() throws IOException {

//        Path currentRelativePath = Paths.get("");
//        String s = currentRelativePath.toAbsolutePath().toString();
//        System.out.println("Current relative path is: " + s);


        this.document = Files.lines(Paths.get("src/main/java/pt/isel/ls/Formatters/WebFormatter/tmpl.html"))
                .parallel() // for parallel processing
                .map(String::trim) // to change line
                .filter(line -> line.length() > 2) // to filter some lines by a predicate
                .collect(Collectors.joining()); // to join line
    }

    public WebDocument setTitle(String title){
        this.document = this.document.replace("%title%", title);
        return this;
    }

    public WebDocument setMenu(WebTag menu){
        this.document = this.document.replace("%menu%", menu.toString());
        return this;
    }

    public WebDocument setMenu(String menu){


        return this;
    }

    public WebDocument addElem(Object data){
        body.add(new WebElem((WebInterface) data));
        return this;
    }

    private String buildBodyStr(){
        StringJoiner joiner = new StringJoiner("");
        body.forEach(x ->{
            joiner.add(x.getContent().toString());
            joiner.add("\n");
        });
        return joiner.toString();
    }

    @Override
    public String toString() {
        this.document = this.document.replace("%body%", "\n"+buildBodyStr());
        return this.document;
    }

//    public static void main(String [] args) throws IOException {
//        WebDocument doc = new WebDocument();
//
//        doc.setTitle("sample page");
//
//        WebTag para = new WebTag("p");
//        para.addContent(new WebText().setData("simple text before"));
//        para.addContent(new WebTag("i").setData("some italics text").setAttr("id", "italic"));
//        para.addContent(new WebText().setData("simple text in between"));
//        para.addContent(new WebTag("b").setData("some bold text").setAttr("id", "bold"));
//        para.addContent(new WebText().setData("simple text after"));
//        doc.addElem(para);
//
//        WebTag para1 = new WebTag("p");
//        WebTag cent1  = new WebTag("center");
//        para1.addContent(cent1);
//        cent1.addContent(new WebTag("a").setData("bunny link1").setAttr("href", "https://goo.gl/images/q4h5Xv"));
//        cent1.addContent(new WebTag("a").setData("bunny link2").setAttr("href", "https://goo.gl/images/vWqIn8"));
//        doc.addElem(para1);
//
//        WebTag para2 = new WebTag("p");
//        WebTag cent2  = new WebTag("center");
//        para2.addContent(cent2);
//        cent2.addContent(new WebText().setData("one"));
//        cent2.addContent(new WebText().setData("two"));
//        cent2.addContent(new WebText().setData("three"));
//        doc.addElem(para2);
//
//
//        WebTag img = new WebTag("img").setAttr("src", "https://pbs.twimg.com/profile_images/447374371917922304/P4BzupWu.jpeg").setAttr("height", "200").setAttr("width", "200");
//        doc.addElem(img);
//
//        System.out.println(doc.toString());
//    }
}
