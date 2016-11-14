package pt.isel.ls.Formatters;

import pt.isel.ls.Helpers.CommandWrapper;

import java.io.IOException;

public interface FormatterInterface {
    public FormatterInterface init(CommandWrapper obj) throws IOException;
    public String format();
}
