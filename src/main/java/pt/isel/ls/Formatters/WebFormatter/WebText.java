package pt.isel.ls.Formatters.WebFormatter;

public class WebText implements WebInterface {

    public String data;

    @Override
    public WebText setData(String data) {
        this.data = data;
        return this;
    }

    @Override
    public WebText getData() {
        return this;
    }

    @Override
    public String toString() {
        return this.data;
    }

}
