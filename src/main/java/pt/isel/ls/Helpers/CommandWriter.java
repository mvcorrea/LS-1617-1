package pt.isel.ls.Helpers;


import java.util.LinkedList;

public class CommandWriter {

    LinkedList<String> data = null;

    public CommandWriter(LinkedList<String> data) {
        this.data = data;
    }

    public void addLine(String line){
        data.add(line);
    }

    public void print(){
        data.forEach(x -> {
            System.out.println(x);
        });
    }
}
